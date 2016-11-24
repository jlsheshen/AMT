package com.edu.library.wifiscore;

import android.content.Context;
import android.content.Intent;

import com.dbw.wifihotmessage.adapter.MinaHandlerAdapter;

/**
 * 发送成绩的适配器
 * 
 * @author Daibw
 * 
 */
public class TsHandlerAdapter extends MinaHandlerAdapter {

	private Context context;

	public TsHandlerAdapter(Context context) {
		this.context = context;
	}

	@Override
	public void messageReceived(String message) {
		System.out.println("handler message : " + message);
		Intent intent = new Intent(WifiScoreUtil.getBroadcast(context));
		intent.putExtra("type", "message");
		intent.putExtra("value", message);
		context.getApplicationContext().sendBroadcast(intent);
	}

}
