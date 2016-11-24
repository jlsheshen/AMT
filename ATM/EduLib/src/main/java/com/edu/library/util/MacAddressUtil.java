package com.edu.library.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * 获取mac地址工具类
 * @author lucher
 *
 */
public class MacAddressUtil {
	/**
	 * 直接读取文件
	 * 
	 * @return
	 */
	public static String getMac() {
		String macSerial = null;
		String str = "";
		try {
			Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			for (; null != str;) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// 去空格
					break;
				}
			}
		} catch (IOException ex) {
			// 赋予默认值
			ex.printStackTrace();
		}
		return macSerial;
	}

	/**
	 * 通过wifimanager获取，只有打开wifi时才能获取到
	 * 
	 * @param context
	 * @return
	 */
	public static String getMac(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		String macAddress = wifiManager.getConnectionInfo().getMacAddress();
		return macAddress;
	}
}
