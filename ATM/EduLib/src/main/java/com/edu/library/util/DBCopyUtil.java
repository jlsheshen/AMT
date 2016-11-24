package com.edu.library.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.edu.library.data.DbVersionData;
import com.edu.library.data.DbVersionDataDao;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.util.Log;

/**
 * 数据库拷贝工具类，实现把数据库copy到sdcard
 * 
 * @author Lucher
 */
public class DBCopyUtil {

	private static final String TAG = "DBCopyUtil";

	/**
	 * 上下文
	 */
	private Context context;

	/**
	 * 数据库对应的路径
	 */
	private String database_path;

	public DBCopyUtil(Context context) {
		this.context = context;
		database_path = getPackgetPath(context);
	}

	/**
	 * @deprecated 已过时，被checkDBVersion取代 检查数据库是否存在,不存在则从assets里拷贝过去
	 */
	public void checkDBExist(String fileName) {
		if (!new File(database_path + "databases//" + fileName + "").exists()) {
			copyAssets(fileName, fileName, context);
			Log.d(TAG, "db copy done");
		}
	}

	/**
	 * 检查数据库版本，先判断数据库是否存在，不存在则直接从assets里拷贝过去
	 * 如果存在，则判断assets里的数据库与当前数据库版本是否一致，不一致则替换过去，一致则不做处理
	 */
	public void checkDBVersion(String fileName) {
		File file = new File(database_path + "databases//" + fileName + "");
		// 判断数据库文件是否存在,如果不存在，则直接从assets里拷贝到对应文件夹里
		if (!file.exists()) {
			copyAssets(fileName, fileName, context);
			Log.d(TAG, "db copy done");
		} else {
			// 如果存在，则判断当前数据库版本号与assets里数据库版本是否一致，不是则替换
			// 需要先把assets里的数据库拷贝到临时文件下，读取版本号后进行判断
			File tmpFile = new File(database_path + "databases//" + "tmp.db");
			copyAssets(fileName, tmpFile.getName(), context);
			int currentDbVersion = getDBVersion(fileName);
			int newDbVersion = getDBVersion(tmpFile.toString());
			Log.d(TAG, "currentDbVersion:" + currentDbVersion + ",newDbVersion:" + newDbVersion);
			if (currentDbVersion != newDbVersion) {
				//如果两个数据库版本号不一样，则替换assets里的数据库到sd卡
				file.delete();
				tmpFile.renameTo(file);
				Log.e(TAG, "new db version, replace db file with new db file");
			} else {
				//如果两个数据库版本号一样，则删除临时数据库
				tmpFile.delete();
				Log.e(TAG, "delete tmp db file");
			}
		}
	}

	/**
	 * 获取数据库版本号
	 * 
	 * @param file 数据库文件名称
	 * @return
	 */
	private int getDBVersion(String file) {
		int version = -1;
		DbVersionData versionData = new DbVersionDataDao(context, file).getDbVersionData();
		if (versionData != null) {
			version = versionData.getVersion();
		}

		return version;
	}

	/**
	 * 拷贝asset文件夹下的文件到sd卡里
	 * 
	 * @param sFile
	 *            源文件名
	 * @param tFile
	 *            目标文件名
	 * @param context
	 */
	private void copyAssets(String sFile, String tFile, Context context) {
		AssetManager assetManager = context.getAssets();

		try {
			InputStream input = assetManager.open(sFile); // 获取文件的输入流

			File folder = new File(database_path + "databases"); // 文件夹
			File file = new File(database_path + "databases", tFile); // 文件
			if (!folder.exists()) {
				// 若不存在，创建目录，可以在应用启动的时候创建
				folder.mkdirs();
			}
			if (!file.exists()) {
				// 若不存在，创建文件，可以在应用启动的时候创建，并初始化
				file.createNewFile();
			}

			copyFile(input, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * copy一个file到另一个file
	 * 
	 * @param inputstream
	 *            被copy的输入流
	 * @param file
	 *            copy的目标文件
	 */
	private static void copyFile(InputStream inputstream, File file) {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inputstream.read(buffer)) != -1) {
				fileOutputStream.write(buffer, 0, len);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (inputstream != null) {
					inputstream.close();
				}
				if (fileOutputStream != null) {
					fileOutputStream.flush();
					fileOutputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取本程序对应的文件存储路径，以包名识别
	 * 
	 * @return 程序唯一的文件存储路径
	 */
	private static String getPackgetPath(Context context) {
		PackageManager packageManager = context.getPackageManager();
		String path;
		try {
			path = new File(packageManager.getPackageInfo(context.getPackageName(), 0).applicationInfo.dataDir).getPath() + File.separator;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			path = null;
		}
		return path;
	}

}
