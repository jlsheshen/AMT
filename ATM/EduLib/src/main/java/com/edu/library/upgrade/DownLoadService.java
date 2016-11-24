package com.edu.library.upgrade;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

import com.edu.library.R;
import com.edu.library.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * apk文件下载服务
 * 
 * @author lucher
 * 
 */
public class DownLoadService extends Service {

	private NotificationManager updateNotificationMgr = null;
	// 下载状态
	private final int DOWNLOAD_COMPLETE = 1, DOWNLOAD_FALL = 2, DOWNLOAD_SUCCESS = 3, DOWNLOAD_CANCEL = 4;
	// apk存放路径
	private String mApkPath;
	// 通知图标id
	private int mIconResId;

	@Override
	public void onCreate() {
		super.onCreate();
		this.updateNotificationMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mApkPath = UpgradeUtil.APK_PATH;
		mIconResId = UpgradeUtil.ICON_RES_ID;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (UpgradeUtil.updateInfo != null && UpgradeUtil.updateInfo.getDownloadState() != UpgradeUtil.DOWNLOADING) {
			UpgradeUtil.updateInfo.setDownloadState(UpgradeUtil.DOWNLOADING);
			new Thread(new downloadThread(UpgradeUtil.updateInfo, startId)).start();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 文件下载线程
	 * 
	 * @author lucher
	 * 
	 */
	class downloadThread implements Runnable {
		private int NOTIFICATION_ID;
		private Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case DOWNLOAD_SUCCESS:
					upApp.setDownloadState(UpgradeUtil.DOWNLOAD_FINISH);
					upApp.setFilePath(updateFile.getPath());
					sendUpdateUiBroadcast();
					updateNotification.defaults = Notification.DEFAULT_SOUND;
					updateNotification.flags = Notification.FLAG_AUTO_CANCEL;
					//updateNotification.setLatestEventInfo(DownLoadService.this, upApp.getApkName(), "下载成功,点击安装。", installPendingIntent);
					updateNotificationMgr.notify(NOTIFICATION_ID, updateNotification);
					break;
				case DOWNLOAD_COMPLETE:
					upApp.setPercent(msg.arg1);
					upApp.setDownloadSpeed(msg.arg2);
					sendUpdateUiBroadcast();
					updateNotification.contentIntent = updatePendingIntent1;
					updateNotification.contentView = rvDownLoad;
					updateNotification.contentView.setProgressBar(R.id.pb_download, 100, msg.arg1, false);
					updateNotification.contentView.setTextViewText(R.id.tv_percent, msg.arg1 + "%");
					updateNotification.contentView.setTextViewText(R.id.tv_appname, upApp.getApkName());
					updateNotification.contentView.setTextViewText(R.id.tv_filesize, UpgradeUtil.formetFileSize(upApp.getFileSize()));
					updateNotification.contentView.setTextViewText(R.id.tv_speed, UpgradeUtil.formetFileSize(msg.arg2) + "/s");
					updateNotificationMgr.notify(NOTIFICATION_ID, updateNotification);
					break;
				case DOWNLOAD_FALL:
					upApp.setDownloadState(UpgradeUtil.DOWNLOAD_WAIT);
					sendUpdateUiBroadcast();
					updateNotification.flags = Notification.FLAG_AUTO_CANCEL;
				//	updateNotification.setLatestEventInfo(DownLoadService.this, upApp.getApkName(), "下载失败。", null);
					updateNotificationMgr.notify(NOTIFICATION_ID, updateNotification);
					break;
				case DOWNLOAD_CANCEL:
					sendUpdateUiBroadcast();
					updateNotificationMgr.cancel(NOTIFICATION_ID);
					break;
				}
			}
		};
		Message msg = handler.obtainMessage();
		private ApkDownloadInfo upApp;
		private int startId;
		private File updateFile;
		private Notification updateNotification;
		private PendingIntent installPendingIntent;
		private PendingIntent updatePendingIntent1;
		private RemoteViews rvDownLoad;

		public downloadThread(ApkDownloadInfo upApp, int startId) {
			this.upApp = upApp;
			this.startId = startId;
		}

		public void sendUpdateUiBroadcast() {
			Intent intent = new Intent();
			intent.setAction(UpgradeUtil.ACTION_UPDATE_UI);
			sendBroadcast(intent);
		}

		@Override
		public void run() {
			try {
				NOTIFICATION_ID = 1;
				if (mApkPath != null) {
					updateFile = new File(mApkPath + upApp.getApkName() + ".apk");
					Intent installIntent = new Intent(Intent.ACTION_VIEW);
					Uri uri = Uri.fromFile(updateFile);
					installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
					installPendingIntent = PendingIntent.getActivity(DownLoadService.this, 0, installIntent, PendingIntent.FLAG_UPDATE_CURRENT);
					// 初始化通知
					updateNotification = new Notification();
					if (mIconResId <= 0) {
						mIconResId = com.edu.library.R.drawable.ic_launcher;
					}
					updateNotification.icon = mIconResId;
					rvDownLoad = new RemoteViews(getPackageName(), R.layout.nofification_update_app);

					long downSize = downloadFile(upApp.getApkUrl(), updateFile);
					if (downSize > 0) {
						msg.what = DOWNLOAD_SUCCESS;
						handler.sendMessage(msg);
					} else if (downSize == 0) {
						msg.what = DOWNLOAD_CANCEL;
						handler.sendMessage(msg);
					} else {
						msg.what = DOWNLOAD_FALL;
						handler.sendMessage(msg);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				msg.what = DOWNLOAD_FALL;
				handler.sendMessage(msg);
			} finally {
				stopSelf(startId);
			}
		}

		/**
		 * 下载文件
		 * 
		 * @param downloadUrl
		 *            文件地址
		 * @param saveFile
		 *            保存路径
		 * @return
		 * @throws Exception
		 */
		public long downloadFile(String downloadUrl, File saveFile) throws Exception {
			Log.d(ApkUpgradeManager.TAG, "downloadFile start:" + downloadUrl);
			int downloadCount = -1;
			int currentSize = 0;
			long updateTime = 0;
			long currentTime = 0;
			long updateSize = 0;
			long totalSize = 0;
			int updateTotalSize = 0;
			HttpURLConnection httpConnection = null;
			InputStream is = null;
			FileOutputStream fos = null;
			try {
				URL url = new URL(downloadUrl);
				httpConnection = (HttpURLConnection) url.openConnection();
				httpConnection.setRequestProperty("User-Agent", "PacificHttpClient");
				if (currentSize > 0) {
					httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");
				}
				httpConnection.setConnectTimeout(10000);
				httpConnection.setReadTimeout(20000);
				updateTotalSize = httpConnection.getContentLength();
				upApp.setFileSize(updateTotalSize);
				if (httpConnection.getResponseCode() == 404) {
					throw new Exception("conection net 404");
				}
				is = httpConnection.getInputStream();
				try {
					FileUtil.createFile(saveFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				fos = new FileOutputStream(saveFile);
				byte[] buf = new byte[1024];
				int readSize = -1;
				System.out.println(upApp);
				while (upApp.getDownloadState() == UpgradeUtil.DOWNLOADING && (readSize = is.read(buf)) != -1) {
					fos.write(buf, 0, readSize);
					totalSize += readSize;
					Log.d(ApkUpgradeManager.TAG, "downloading, totalSize:" + totalSize);
					int tmp = (int) (totalSize * 100 / updateTotalSize);
					currentTime = System.currentTimeMillis();
					if (tmp > downloadCount + 2 || (currentTime - updateTime > 2000)) {
						Message msg = handler.obtainMessage();
						downloadCount = tmp;
						msg.arg2 = (int) ((totalSize - updateSize) / ((currentTime - updateTime) / (double) 1000));
						updateTime = currentTime;
						updateSize = totalSize;
						msg.what = DOWNLOAD_COMPLETE;
						msg.arg1 = downloadCount;
						handler.sendMessage(msg);
					}
				}
				if (totalSize < updateTotalSize) {
					return 0;
				}
				Log.i(ApkUpgradeManager.TAG, "download finish:" + UpgradeUtil.formetFileSize(totalSize));
			} catch (Exception ex) {
				ex.printStackTrace();
				totalSize = -1;
			} finally {
				if (httpConnection != null) {
					httpConnection.disconnect();
				}
				if (is != null) {
					is.close();
				}
				if (fos != null) {
					fos.close();
				}
			}
			return totalSize;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
