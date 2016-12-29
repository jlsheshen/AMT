package com.edu.accountingteachingmaterial.activity;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.base.BaseMvpActivity;
import com.edu.accountingteachingmaterial.presenterview.LaunchPresenter;
import com.edu.accountingteachingmaterial.presenterview.LaunchView;
import com.edu.library.util.DBCopyUtil;
import com.edu.subject.util.SoundPoolUtil;
import com.edu.testbill.Constant;


/**
 * Created by Administrator on 2016/12/28.
 */

public class LaunchActivity extends BaseMvpActivity<LaunchView, LaunchPresenter> implements LaunchView {

    boolean isSuccess;
    @Override
    public LaunchPresenter initPresenter() {
        return new LaunchPresenter();
    }

    @Override
    public int setLayout() {
        return R.layout.activity_launch;
    }



    @Override
    public void initData() {

        presenter.loadData(this);
    }

    @Override
    public void launchLogin() {
    }

    @Override
    public void loadData() {
        // 检测数据库是否已拷贝
        DBCopyUtil fileCopyUtil = new DBCopyUtil(this);
        fileCopyUtil.checkDBVersion(Constant.DATABASE_NAME);
        // 初始化声音播放工具，如果不初始化，盖章没声
        SoundPoolUtil.getInstance().init(this);
    }

    @Override
    public void startActivity() {
        if (!isSuccess){
        startActivity(StartStudyActivity.class);
        ;}else {
            startActivity(MainActivity.class);

        }
        finish();
    }

    @Override
    public void jumpMain() {

        isSuccess = true;
    }

    @Override
    public void jumpLogin() {

        isSuccess = false;

    }
//public class LaunchActivity extends BaseActivity {
//    CountDownTimer timer;
//    HomepageInformationData data;
//    boolean isShow = true;
//
//    @Override
//    public int setLayout() {
//        // TODO Auto-generated method stub
//        return R.layout.activity_launch;
//    }
//
//    @Override
//    public void initView(Bundle savedInstanceState) {
//        String s = PreferenceHelper.getInstance(this).getStringValue(URL_NAME);
//        Log.d("LaunchActivity", "--------------" + s);
//        if ("".equals(s)) {
//        } else {
//            BASE_URL.BASE_URL = s;
//
//        }
////        GetBillTemplatesManager.newInstance(LaunchActivity.this).sendLocalTemplates();
////		new Thread(new Runnable() {
////			@Override
////			public void run() {
////
////				String databaseFilename ="/sdcard/EduResources/AccCourse";
////				File dir = new File(databaseFilename);
////
////				if (!dir.exists()){
////					dir.mkdir();}
////				File dir2 = new File("/sdcard/EduResources/AccCourse/video");
////
////				if (!dir2.exists()){
////					dir2.mkdir();}
////
////				if (!(new File("/sdcard/EduResources/AccCourse/video/aaa.mp4")).exists())
////				{
////
////					InputStream is = getResources().openRawResource(R.raw.aaa);
////					FileOutputStream fos = null;
////					try {
////						fos = new FileOutputStream("/sdcard/EduResources/AccCourse/video/aaa.mp4");
////					} catch (FileNotFoundException e) {
////						e.printStackTrace();
////					}
////					byte[] buffer = new byte[8192];
////					int count = 0;
////
////					try {
////						while ((count = is.read(buffer)) > 0)
////                        {
////                            fos.write(buffer, 0, count);
////                        }
////					} catch (IOException e) {
////						e.printStackTrace();
////					}
////					try {
////						fos.close();
////					} catch (IOException e) {
////						e.printStackTrace();
////					}
////					try {
////						is.close();
////					} catch (IOException e) {
////						e.printStackTrace();
////					}
////				}
////			}
////
////
////		}).start();
//
////		// TODO Auto-generated method stub
//        timer = new CountDownTimer(3000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                // TODO Auto-generated method stub
//                // 检测数据库是否已拷贝
//                DBCopyUtil fileCopyUtil = new DBCopyUtil(LaunchActivity.this);
//                fileCopyUtil.checkDBVersion(Constant.DATABASE_NAME);
//                // 预加载网络图片
////                SubjectImageLoader.getInstance(LaunchActivity.this).preloadAllPics();
//
//                // 初始化声音播放工具，如果不初始化，盖章没声
//                SoundPoolUtil.getInstance().init(LaunchActivity.this);
//
//
//            }
//
//            @Override
//            public void onFinish() {
////                Bundle bundle = new Bundle();
////                bundle.putSerializable("HomepageInformationData", data);
////
////
////                    startActivity(StartStudyActivity.class, bundle);
//                startActivity(StartStudyActivity.class);
//
////                if (!isShow) {
//                    finish();
////                }
//            }
//        }.start();
////        uploadHomepageInfo();
//
//
//    }
//
//    /**
//     * 根据用户id请求用户数据
//     */
//    private void uploadHomepageInfo() {
////        UserData user = UserCenterHelper.getUserInfo(this);
////        user.setUserId(35605);
//
//        PreferenceHelper.getInstance(this).setStringValue(PreferenceHelper.USER_ID, "" + 201660001);
//        Log.d("LaunchActivity", NetUrlContstant.getHomeInfoUrl() + PreferenceHelper.getInstance(this).getStringValue(PreferenceHelper.USER_ID));
//
//        String s = PreferenceHelper.getInstance(this).getStringValue(URL_NAME);
//        if ("".equals(s)) {
//            Log.d("LaunchActivity", "--------------" + s);
//        } else {
//            BASE_URL.BASE_URL = s;
//        }
//
//        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();
//        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(this, RequestMethod.POST, NetUrlContstant.getHomeInfoUrl() + PreferenceHelper.getInstance(this).getIntValue(PreferenceHelper.USER_ID));
//        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
//        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
//            @Override
//            public void onSuccess(JSONObject jsonObject) {
//                if (jsonObject.getString("success").equals("true")) {
//                    List<HomepageInformationData> hData = JSON.parseArray(jsonObject.getString("message"), HomepageInformationData.class);
//                    data = hData.get(0);
//                    Log.d("LaunchActivity", "线程启动获取成功");
//                  //  GetBillTemplatesManager.newInstance(LaunchActivity.this).sendLocalTemplates();
//                    EventBus.getDefault().post(data);
//                    if (!isShow) {
//                        finish();
//                    }
//                    isShow = false;
//                }
//            }
//
//            @Override
//            public void onFailure(String errorInfo) {
//                Log.d("LaunchActivity", "线程启动获取失败");
//            }
//        });
//    }
//
//    @Override
//    public void initData() {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    protected void onStop() {
//        isShow = false;
//        super.onStop();
//    }

}

