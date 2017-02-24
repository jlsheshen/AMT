package com.edu.accountingteachingmaterial.oldsubject.subject.util;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.edu.R;

import java.util.HashMap;


/**
 * 音频文件播放工具类
 * 
 * @author Lucher
 * 
 */
public class SoundPoolUtil implements SoundPool.OnLoadCompleteListener {

	/**
	 * 选择错误声音
	 */
	public static final int SOUND_SELECT_ERROR_ID = 1;
	/**
	 * 选择关卡结果成功声音
	 */
	public static final int SOUND_RESULT_SUCCESS_ID = 2;
	/**
	 * 选择关卡结果失败声音
	 */
	public static final int SOUND_RESULT_FAILURE_ID = 3;
	/**
	 * 盖章成功声音
	 */
	public static final int SOUND_SEAL_SUCCESS_ID = 4;

	private static com.edu.subject.util.SoundPoolUtil mSoundPoolUtil; // 保存自身引用

	private SoundPool soundPool;
	private float volume; // 播放音量
	private HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static com.edu.subject.util.SoundPoolUtil getInstance() {
		if (mSoundPoolUtil == null) {
			mSoundPoolUtil = new com.edu.subject.util.SoundPoolUtil();
		}

		return mSoundPoolUtil;
	}

	/**
	 * 初始化
	 * 
	 * @param activity
	 * @param context
	 */
	public void init(Context context) {
		initSoundPool();
		loadSoundRes(context);
	}

	/**
	 * 播放声音
	 * 
	 * @param id
	 */
	public void play(int id) {
		// 1、Map中取值 2、当前音量 3、最大音量 4、优先级 5、重播次数 6、播放速度
		soundPool.play(soundMap.get(id), volume, volume, 1, 0, 1);
	}

	/**
	 * 初始化声音播放器
	 * 
	 * @param context
	 */
	private void initSoundPool() {
		if (soundPool == null) {
			// 设置最多可容纳5个音频流,音频的品质为5
			soundPool = new SoundPool(5, AudioManager.STREAM_SYSTEM, 5);
			soundPool.setOnLoadCompleteListener(this);
		}
	}

	/**
	 * 加载播放资源
	 */
	private void loadSoundRes(Context context) {
		if (soundMap.size() == 0) {
			// load方法加载指定音频文件,并返回所加载的音频ID．此处使用HashMap来管理这些音频流
			soundMap.put(SOUND_SEAL_SUCCESS_ID, soundPool.load(context, R.raw.seal_success, 1));
		}
	}

	/**
	 * 初始化音量
	 * 
	 * @param activity
	 */
	private void updateVolume(Activity activity) {
		AudioManager am = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
		// 获取当前音量
		float streamVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		// 获取系统最大音量
		float streamVolumeMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		// 计算得到播放音量
		volume = streamVolumeCurrent / streamVolumeMax;
	}

	@Override
	public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
	}
}
