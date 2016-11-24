package com.edu.library.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import android.os.Environment;

/**
 * SD卡路径获取工具类
 * @author lucher
 *
 */
public class SdcardPathUtil {

	/**
	 * 获取扩展SD卡存储目录
	 * 
	 * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录 否则：返回内置SD卡目录，如果都没有返回空
	 * 
	 * @return
	 */
	public static String getExternalSdCardPath() {
		//默认获取/mnt/sdcard，如果挂载直接使用
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			File sdCardFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
			return sdCardFile.getAbsolutePath();
		}
		//否则扫描/system/etc/vold.fstab里的path，找出可以写的路径即可用的存储
		String path = null;
		File sdCardFile = null;
		ArrayList<String> devMountList = getDevMountList();
		for (String devMount : devMountList) {
			File file = new File(devMount);
			if (file.isDirectory() && file.canWrite()) {
				path = file.getAbsolutePath();
				String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
				File testWritable = new File(path, "test_" + timeStamp);
				if (testWritable.mkdirs()) {
					testWritable.delete();
				} else {
					path = null;
				}
			}
		}

		if (path != null) {
			sdCardFile = new File(path);
			return sdCardFile.getAbsolutePath();
		}

		return null;
	}

	/**
	 * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
	 * 
	 * @return
	 */
	private static ArrayList<String> getDevMountList() {
		ArrayList<String> out = new ArrayList<String>();
		try {
			File voldFile = new File("/system/etc/vold.fstab");
			if (voldFile.exists()) {
				Scanner scanner = new Scanner(voldFile);
				while (scanner.hasNext()) {
					String line = scanner.nextLine();
					if (line.startsWith("dev_mount")) {
						String[] lineElements = line.split(" ");
						for (int i = 0; i < lineElements.length; i++) {
							out.add(lineElements[i]);
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return out;
	}
}
