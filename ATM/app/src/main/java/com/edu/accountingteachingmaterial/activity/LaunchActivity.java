package com.edu.accountingteachingmaterial.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.NetUrlContstant;
import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseActivity;
import com.edu.accountingteachingmaterial.entity.HomepageInformationData;
import com.edu.accountingteachingmaterial.util.GetBillTemplatesManager;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.edu.accountingteachingmaterial.util.SendJsonNetReqManager;
import com.edu.library.usercenter.UserCenterHelper;
import com.edu.library.usercenter.UserData;
import com.edu.library.util.DBCopyUtil;
import com.edu.subject.util.SoundPoolUtil;
import com.edu.testbill.Constant;
import com.lucher.net.req.RequestMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class LaunchActivity extends BaseActivity {
    CountDownTimer timer;
    HomepageInformationData data;
    boolean isShow = true;

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

//		// TODO Auto-generated method stub
        timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                // 检测数据库是否已拷贝
                DBCopyUtil fileCopyUtil = new DBCopyUtil(LaunchActivity.this);
                fileCopyUtil.checkDBVersion(Constant.DATABASE_NAME);
                // 预加载网络图片
//                SubjectImageLoader.getInstance(LaunchActivity.this).preloadAllPics();

                // 初始化声音播放工具，如果不初始化，盖章没声
                SoundPoolUtil.getInstance().init(LaunchActivity.this);

            }

            @Override
            public void onFinish() {
                Bundle bundle = new Bundle();
                bundle.putSerializable("HomepageInformationData", data);


                    startActivity(StartStudyActivity.class, bundle);

                if (!isShow) {
                    finish();
                }
            }
        }.start();
        uploadHomepageInfo();


    }

    /**
     * 根据用户id请求用户数据
     */
    private void uploadHomepageInfo() {
        UserData user = UserCenterHelper.getUserInfo(this);
        user.setUserId(35605);
        PreferenceHelper.getInstance(this).setIntValue(PreferenceHelper.USER_ID, 35605);
        Log.d("LaunchActivity", NetUrlContstant.homeInfoUrl + PreferenceHelper.getInstance(this).getIntValue(PreferenceHelper.USER_ID));

        String s = PreferenceHelper.getInstance(this).getStringValue(NetUrlContstant.URL_NAME);
        if ("".equals(s)) {
            Log.d("LaunchActivity", "--------------" + s);
        } else {
            NetUrlContstant.BASE_URL = s;
        }


        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this, RequestMethod.POST, NetUrlContstant.homeInfoUrl + PreferenceHelper.getInstance(this).getIntValue(PreferenceHelper.USER_ID));
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    List<HomepageInformationData> hData = JSON.parseArray(jsonObject.getString("message"), HomepageInformationData.class);
                    data = hData.get(0);
                    Log.d("LaunchActivity", "线程启动获取成功");

                    EventBus.getDefault().post(data);
                    if (!isShow) {
                        finish();
                    }
                    isShow = false;


                }
            }

            @Override
            public void onFailure(String errorInfo) {
                Log.d("LaunchActivity", "线程启动获取失败");

            }
        });
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onStop() {
        isShow = false;
        super.onStop();
    }

}
