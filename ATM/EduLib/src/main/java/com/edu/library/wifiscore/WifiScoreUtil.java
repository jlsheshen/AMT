package com.edu.library.wifiscore;

import android.content.Context;

import com.edu.library.util.ApplicationUtil;

public class WifiScoreUtil {

	public final static String MESSAGERECEIVED = ".messageReceived";

	/*
	 * 消息标识--type的值--表示连接服务器失败
	 */
	public final static String CONNECTFAILED = "CONNECTFAILED";

	/*
	 * 连接成功--服务器返回
	 */
	public final static String CONNECTSUCCESS = "@Connect";
	
	/*
	 * 消息标识--type的值--表示是信息类型
	 */
	public final static String MESSAGE = "message";
	
	/*
	 * 发送成功
	 */
	public final static String SUCCESS = "Sucess";
	
	/*
	 * 消息类型标识
	 * intent.putExtra(TYPE, CONNECTFAILED);--连接服务器失败
	 * intent.putExtra(TYPE, MESSAGE);--消息类型
	 */
	public final static String TYPE = "type";
	
	/*
	 * 消息内容标识
	 * CONNECTSUCCESS、SUCCESS
	 */
	public final static String VALUE = "value";

	private static String broadcast = null;

	public static String getBroadcast(Context context) {
		if (broadcast == null) {
			broadcast = ApplicationUtil.getPackageName(context) + MESSAGERECEIVED;
		}
		return broadcast;
	}

}
