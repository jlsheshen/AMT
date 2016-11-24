package com.edu.library.common;

import java.io.File;
import java.io.IOException;

import com.edu.library.util.FileUtil;

import android.media.MediaRecorder;

/**
 * 音频录制类
 * 
 * @author lucher
 * 
 */
public class SoundRecorder {

	// 自身唯一实例
	private static SoundRecorder mSingleton;
	// 系统提供的录音实现类
	MediaRecorder mediaRecorder;
	// 录音文件
	private File audioFile;

	private SoundRecorder() {
		mediaRecorder = new MediaRecorder();
	}

	/**
	 * 单例模式获取实例
	 * 
	 * @return
	 */
	public static SoundRecorder getSingleton() {
		if (mSingleton == null) {
			mSingleton = new SoundRecorder();
		}

		return mSingleton;
	}

	/**
	 * 初始化录音类
	 * 
	 * @throws IOException
	 */
	private void initMediaRecorder(String path) throws IOException {
		mediaRecorder.reset();
		// mediaRecorder.setOnErrorListener(null);
		// 第1步：设置音频来源（MIC表示麦克风）
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		// 第2步：设置音频输出格式（默认的输出格式）
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		// 第3步：设置音频编码方式（默认的编码方式）
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

		// audioFile = File.createTempFile("record_", ".amr");
		audioFile = new File(path);
		FileUtil.createFile(audioFile);

		// 第4步：指定音频输出文件
		mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
	}

	/**
	 * 开始录音
	 * 
	 * @param path
	 *            录音文件存放路径
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public void startRecord(String path) throws Exception {
		initMediaRecorder(path);
		mediaRecorder.prepare();
		mediaRecorder.start();
	}

	/**
	 * 结束录音
	 * 
	 * @return 录音文件 如果出错则返回空
	 */
	public File stopRecord() {
		try {
			mediaRecorder.stop();
		} catch (Exception e) {
			e.printStackTrace();
			audioFile = null;
		}

		return audioFile;
	}

	/**
	 * 释放资源
	 */
	public void release() {
		mediaRecorder.release();
		mediaRecorder = null;
	}
}
