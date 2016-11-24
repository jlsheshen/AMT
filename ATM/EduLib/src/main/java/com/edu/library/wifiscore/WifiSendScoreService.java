package com.edu.library.wifiscore;

import com.dbw.wifihotmessage.callback.ConnectServerInterface;
import com.dbw.wifihotmessage.server.MinaClient;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 发送成绩的服务
 * 
 * @author Daibw
 * 
 */
public class WifiSendScoreService extends Service implements ConnectServerInterface {

	private int count = 0;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		startThread();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		MinaClient.close();
	}

	// 是否已经连接到服务器
	@Override
	public void isConnectServer(boolean b) {
		if (!b && count < 50) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			startThread();
			count++;
		}
		System.out.println("count : " + count);
		if (count >= 50) {
			Intent intent = new Intent(WifiScoreUtil.getBroadcast(WifiSendScoreService.this));
			intent.putExtra(WifiScoreUtil.TYPE, WifiScoreUtil.CONNECTFAILED);
			intent.putExtra(WifiScoreUtil.VALUE, "连接失败");
		}
	}

	private void startThread() {
		new Thread() {
			public void run() {
				MinaClient.startServer("192.168.43.1", 1234, WifiSendScoreService.this, new TsHandlerAdapter(WifiSendScoreService.this));
			};
		}.start();
	}
}