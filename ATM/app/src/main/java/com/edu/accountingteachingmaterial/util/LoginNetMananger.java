package com.edu.accountingteachingmaterial.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.AccToken;
import com.edu.accountingteachingmaterial.entity.HomepageInformationData;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;

import org.apache.http.Header;

import java.util.List;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.COURSE_ID;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.KEY_LOGIN_STATE;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.URL_NAME;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.USER_ID;

/**
 * Created by Administrator on 2016/12/15.
 */

public class LoginNetMananger extends JsonNetReqManager {

    private Context context;
    // 需要上传答题结果的所有数据
    private static LoginNetMananger mSingleton;
    String studentNumber;
    String studentPassword;
    loginListener loginListener;
    public static final String STUDNET_NUMBER = "STUDNET_NUMBER";

    public static final String STUDNET_PASSWORD = "STUDNET_PASSWORD";

    public static final String TOKEN = "TOKEN";



    private LoginNetMananger(Context context) {
       this.context = context;
    }

    /**
     * 单例模式获取实例
     *
     * @param context
     * @return
     */
    public static LoginNetMananger getSingleton(Context context) {
        if (mSingleton == null) {
            mSingleton = new LoginNetMananger(context);
        }
        return mSingleton;
    }

    /**
     * 发送答案和密码
     *
     * @param
     */
    public void login(String number,String passWord,loginListener listener) {
        studentNumber = number;
        studentPassword = passWord;
        String url = NetUrlContstant.getLoginUrl() + "username="  + number + "&password=" + passWord + "&rememberme=1";
        NetSendCodeEntity entity = new NetSendCodeEntity(context, RequestMethod.POST, url);
        sendRequest(entity, "登陆中");
        Log.d(TAG, "url");
        loginListener = listener;

    }

    @Override
    public void onConnectionSuccess(JSONObject json, Header[] arg1) {

        boolean result = json.getBoolean("result");
        String message = json.getString("message");
        if (result) {
            if (message == null || message.length() == 0) {
                Log.d("StartStudyActivity", "学号有误");
            }
            Log.d("StartStudyActivity", "----" + message);
            AccToken accToken = JSON.parseObject(message, AccToken.class);
            PreferenceHelper.getInstance(context).setStringValue(STUDNET_NUMBER, studentNumber);
            PreferenceHelper.getInstance(context).setStringValue(STUDNET_PASSWORD, studentPassword);
            PreferenceHelper.getInstance(context).setStringValue(TOKEN, accToken.getLoginToken());
            PreferenceHelper.getInstance(context).setStringValue(PreferenceHelper.USER_ID, String.valueOf(accToken.getStuId()));
            uploadHomepageInfo();
            PreferenceHelper.getInstance(context).setBooleanValue(KEY_LOGIN_STATE, true);
        } else {
            Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionError(String arg0) {
        loginListener.onFailure(arg0);
    }

    @Override
    public void onConnectionFailure(String arg0, Header[] arg1) {
        loginListener.onFailure(arg0);

    }
    /**
     * 登陆成功后获取课程的id
     */
    private void uploadHomepageInfo() {

        Log.d("LaunchActivity", NetUrlContstant.getHomeInfoUrl() + PreferenceHelper.getInstance(context).getStringValue(USER_ID));

        String s = PreferenceHelper.getInstance(context).getStringValue(URL_NAME);

        SendJsonNetReqManager sendJsonNetReqManager = SendJsonNetReqManager.newInstance();

        NetSendCodeEntity netSendCodeEntity = new NetSendCodeEntity(context, RequestMethod.POST, NetUrlContstant.getHomeInfoUrl() + PreferenceHelper.getInstance(context).getStringValue(USER_ID));
        sendJsonNetReqManager.sendRequest(netSendCodeEntity);
        sendJsonNetReqManager.setOnJsonResponseListener(new SendJsonNetReqManager.JsonResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (jsonObject.getString("success").equals("true")) {
                    List<HomepageInformationData> hData = JSON.parseArray(jsonObject.getString("message"), HomepageInformationData.class);
                    HomepageInformationData data = hData.get(0);
                    PreferenceHelper.getInstance(context).setStringValue(STUDNET_NUMBER, studentNumber);
                    PreferenceHelper.getInstance(context).setStringValue(STUDNET_PASSWORD, studentPassword);
                    PreferenceHelper.getInstance(context).setIntValue(COURSE_ID, data.getCourse_id());
                    GetBillTemplatesManager.newInstance(context).sendLocalTemplates();
                    loginListener.onSuccess();

                }
            }

            @Override
            public void onFailure(String errorInfo) {
                loginListener.onFailure(errorInfo);

            }
        });
    }

    public interface loginListener{
        void onSuccess();
        void onFailure(String message);

    }

}