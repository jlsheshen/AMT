package com.edu.library.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;

/**
 * 文件拷贝工具类
 * 
 * @author Lucher
 */
public class FileCopyUtil {

	private static final String TAG = "FileCopyUtil";

	/**
	 * 文件拷贝
	 * 
	 * @param srcFile
	 * @param tarFile
	 */
	public static void copyFile(String srcFile, String tarFile) {
		try {
			Log.d(TAG, "copy file:from " + srcFile + " to " + tarFile);
			FileUtil.createFile(new File(tarFile));

			FileInputStream input = new FileInputStream(srcFile);
			BufferedInputStream inBuff = new BufferedInputStream(input);

			FileOutputStream output = new FileOutputStream(tarFile);
			BufferedOutputStream outBuff = new BufferedOutputStream(output);

			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}

			outBuff.flush();

			inBuff.close();
			outBuff.close();
			output.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
