package com.edu.accountingteachingmaterial.util.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.accountingteachingmaterial.constant.NetUrlContstant;
import com.edu.accountingteachingmaterial.entity.AccToken;
import com.edu.accountingteachingmaterial.util.NetSendCodeEntity;
import com.edu.accountingteachingmaterial.util.PreferenceHelper;
import com.lucher.net.req.RequestMethod;
import com.lucher.net.req.impl.JsonNetReqManager;

import org.apache.http.Header;

import static com.edu.accountingteachingmaterial.util.PreferenceHelper.CLASS_ID;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.KEY_LOGIN_STATE;
import static com.edu.accountingteachingmaterial.util.PreferenceHelper.STUDENT_NAME;

/**
 * Created by Administrator on 2016/12/15.
 */

public class LoginNetMananger extends JsonNetReqManager implements GetBillTemplatesManager.GetBillListener {

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
     * @param context
     * @return
     */
    public static LoginNetMananger getSingleton(Context context) {
        if (mSingleton == null) {
            synchronized (LoginNetMananger.class) {
                if (mSingleton == null) {
                    mSingleton = new LoginNetMananger(context);

                }
            }
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
        Log.d(TAG, "url");

        sendRequest(entity);
        loginListener = listener;

    }

    @Override
    public void cancelRequest() {
        SendJsonNetReqManager.newInstance().cancelRequest();
        super.cancelRequest();
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
            PreferenceHelper.getInstance(context).setStringValue(STUDENT_NAME, accToken.getStuName());
            PreferenceHelper.getInstance(context).setStringValue(CLASS_ID, ""  + accToken.getClazzId());
            PreferenceHelper.getInstance(context).setStringValue(PreferenceHelper.USER_ID, String.valueOf(accToken.getStuId()));
            //班级,
//            uploadHomepageInfo();
            GetBillTemplatesManager.newInstance(context).setGetBillListener(LoginNetMananger.this).sendLocalTemplates();

            PreferenceHelper.getInstance(context).setBooleanValue(KEY_LOGIN_STATE, true);
        } else {
            Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void dismissDialog(){
        GetBillTemplatesManager.newInstance(context).dismissDialog();
    }

    @Override
    public void onConnectionError(String arg0) {
        loginListener.onFailure(arg0);
    }

    @Override
    public void onConnectionFailure(String arg0, Header[] arg1) {
        loginListener.onFailure(arg0);

    }
    @Override
    public void onGetBillSuccess() {
                loginListener.onSuccess();
    }

    @Override
    public void onGetBillFail() {
        loginListener.onFailure("模板更新失败");

    }

    public interface loginListener{
        void onSuccess();
        void onFailure(String message);
    }

}