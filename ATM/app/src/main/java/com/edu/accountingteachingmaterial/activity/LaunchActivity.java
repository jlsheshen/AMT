package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.library.util.DBCopyUtil;
import com.edu.testbill.Constant;


public class LaunchActivity extends BaseActivity{
	CountDownTimer timer;

	@Override
	public int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.activity_launch;
	}

	@Override
	public void initView(Bundle savedInstanceState) {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//
//				String databaseFilename ="/sdcard/EduResources/AccCourse";
//				File dir = new File(databaseFilename);
//
//				if (!dir.exists()){
//					dir.mkdir();}
//				File dir2 = new File("/sdcard/EduResources/AccCourse/video");
//
//				if (!dir2.exists()){
//					dir2.mkdir();}
//
//				if (!(new File("/sdcard/EduResources/AccCourse/video/aaa.mp4")).exists())
//				{
//
//					InputStream is = getResources().openRawResource(R.raw.aaa);
//					FileOutputStream fos = null;
//					try {
//						fos = new FileOutputStream("/sdcard/EduResources/AccCourse/video/aaa.mp4");
//					} catch (FileNotFoundException e) {
//						e.printStackTrace();
//					}
//					byte[] buffer = new byte[8192];
//					int count = 0;
//
//					try {
//						while ((count = is.read(buffer)) > 0)
//                        {
//                            fos.write(buffer, 0, count);
//                        }
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					try {
//						fos.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					try {
//						is.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//
//
//		}).start();
		// TODO Auto-generated method stub
		timer = new CountDownTimer(3000,1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				// 检测数据库是否已拷贝
				DBCopyUtil fileCopyUtil = new DBCopyUtil(LaunchActivity.this);
				fileCopyUtil.checkDBVersion(Constant.DATABASE_NAME);

			}
			@Override
			public void onFinish() {
				startActivity(StartStudyActivity.class);
				finish();
			}
		}.start();
		
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}



	
}
