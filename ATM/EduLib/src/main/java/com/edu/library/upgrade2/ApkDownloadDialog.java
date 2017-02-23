package com.edu.library.upgrade2;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.edu.library.R;

import java.io.File;

/**
 * apk版本更新下载对话框
 * 
 * @author lucher
 * 
 */
public class ApkDownloadDialog extends Dialog {
	// 取消按钮
	private Button btnCancel;
	// 确定按钮
	private Button btnOK;
	private TextView tvTip;
	private ProgressBar pb;
	private Context mContext;
	private UpdateAppInfo ua;
	private boolean downloading = false;
	private boolean secondDialog = false;
	private TextView tvVersion;
	private TextView tvInfo;

	public ApkDownloadDialog(Context context, UpdateAppInfo upInfo) {
		super(context);
		this.mContext = context;
		this.ua = upInfo;
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setCanceledOnTouchOutside(false);
		setContentView(R.layout.layout_upgrade_dialog);
		btnCancel = (Button) findViewById(R.id.btn_cancel);
		btnOK = (Button) findViewById(R.id.btn_ok);
		tvTip = (TextView) findViewById(R.id.tv_updatedialog_text);
		pb = (ProgressBar) findViewById(R.id.pb_download);
		tvVersion = (TextView) findViewById(R.id.tv_version);
		tvInfo = (TextView) findViewById(R.id.tv_info);
		if (ua.getApkVersionUser() != null) {
			tvVersion.setText("版本号：" + ua.getApkVersionUser());
		}
		if (ua.getApkIntro() != null) {
			tvInfo.setText("描述信息：" + ua.getApkIntro());
		}
		// 注册广播
		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				updateUI(intent.getExtras().getInt("state"));
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(UpgradeUtil.ACTION_UPDATE_UI);
		mContext.registerReceiver(receiver, filter);
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (secondDialog) {
					secondDialog = false;
					changeUI();
				} else if (downloading) {
					// 弹出提示dialog
					secondDialog = true;
					changeUI();
				} else {
					dismiss();
				}
			}
		});
		btnOK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (secondDialog) {
					ua.setDownloadState(UpgradeUtil.DOWNLOAD_WAIT);
					dismiss();
				} else if (ua.getDownloadState() == UpgradeUtil.DOWNLOAD_FINISH) {
					// 安装
				} else {
					UpgradeUtil.updateInfo = ua;
					downloading = true;
					changeUI();
					Intent serviceIntent = new Intent(mContext, DownLoadService.class);
					mContext.startService(serviceIntent);
				}
			}
		});
	}

	/**
	 * 改变当前ui状态
	 */
	protected void changeUI() {
		if (secondDialog) {
			setTip("确定要取消?");
			pb.setVisibility(View.GONE);
			tvVersion.setVisibility(View.GONE);
			tvInfo.setVisibility(View.GONE);
			btnOK.setEnabled(true);
			btnOK.setText("确定");
		} else if (downloading) {
			setTip("正在下载...");
			pb.setVisibility(View.VISIBLE);
			btnOK.setEnabled(false);
			btnOK.setText("下载更新");
		} else if (ua.getDownloadState() == UpgradeUtil.DOWNLOAD_FINISH) {
			setTip("下载成功");
			pb.setVisibility(View.GONE);
			btnOK.setEnabled(true);
			btnOK.setText("安装");
		} else {
			setTip("下载失败");
			pb.setVisibility(View.GONE);
			btnOK.setEnabled(true);
			btnOK.setText("下载更新");
		}
	}

	/**
	 * 根据下载状态更新界面
	 */
	private void updateUI(int state) {
		if (state == UpgradeUtil.DOWNLOADING) {
			pb.setProgress(ua.getPercent());
		} else if (state == UpgradeUtil.DOWNLOAD_FINISH) {
			pb.setVisibility(View.GONE);
			downloading = false;
			showInstallUI();
			Intent serviceIntent = new Intent(mContext, DownLoadService.class);
			mContext.stopService(serviceIntent);
		} else {
			if (!secondDialog) {
				setTip("下载失败");
			}
			downloading = false;
			pb.setVisibility(View.GONE);
			btnOK.setEnabled(true);
			Intent serviceIntent = new Intent(mContext, DownLoadService.class);
			mContext.stopService(serviceIntent);
		}
		// if (ua.getDownloadState() == UpgradeUtil.DOWNLOADING) {
		// pb.setProgress(ua.getPercent());
		// } else if (ua.getDownloadState() == UpgradeUtil.DOWNLOAD_FINISH) {
		// pb.setVisibility(View.GONE);
		// downloading = false;
		// showInstallUI();
		// System.out.println("下载成功--------updateUI" + ua.getFilePath());
		// Intent serviceIntent = new Intent(mContext, DownLoadService.class);
		// mContext.stopService(serviceIntent);
		// } else {
		// if (!secondDialog) {
		// setTip("下载失败");
		// }
		// downloading = false;
		// pb.setVisibility(View.GONE);
		// btnOK.setEnabled(true);
		// Intent serviceIntent = new Intent(mContext, DownLoadService.class);
		// mContext.stopService(serviceIntent);
		// }
	}

	/**
	 * 显示安装页面
	 */
	private void showInstallUI() {
		Intent installIntent = new Intent(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(ua.getFilePath()));
		installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
		mContext.startActivity(installIntent);
		dismiss();
	}

	private void setTip(String string) {
		tvTip.setText(string);
	}
}
