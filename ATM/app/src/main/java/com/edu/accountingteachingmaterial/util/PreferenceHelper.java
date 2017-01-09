package com.edu.accountingteachingmaterial.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 保存preference的帮助类
 *
 * @author lucher
 */
public class PreferenceHelper {
    /**
     * url地址
     */

    public static final String URL_NAME = "URL_NAME";

    /**
     * 用户id
     */
    public static final String USER_ID = "USER_ID";
    /**
     * 课程id
     */
    public static final String COURSE_ID = "COURSE_ID";
    /**
     * 学号
     */
    public static final String STUDNET_NUMBER = "STUDNET_NUMBER";
    /**
     * 密码
     */
    public static final String STUDNET_PASSWORD = "STUDNET_PASSWORD";
    /**
     * token
     */
    public static final String TOKEN = "TOKEN";

    /**
     * 自测examid
     */
    public static final String EXAM_ID = "EXAM_ID";
    /**
     * 保存记录是否第一次进入某个页面的key
     */
    private static final String PREFERENCE_ISFIRSTTIME_KEY = "is_first_time";
    /**
     * 章节考试是否没有提交
     */
    public static final String KEY_IS_TEST_NOT_FINISH = "isTestNotFinish";
    /**
     * 在线考试是否没有提交
     */
    public static final String KEY_IS_ONLINE_TEST_NOT_FINISH = "isOnlineTestNotFinish";
    /**
     * 引导页preference的key
     */
    public static final String KEY_GUIDANCE_PAGE = "guidance_page";
    /**
     * 章节测试，是否可查看答案
     */
    public static final String KEY_ANSWER_STATUS = "key_answer_status";

    /**
     * 保存value的key
     */
    private static final String PREFERENCE_VALUE_KEY = "value";

    /**
     * 审核模块第一次使用的指示图片key
     */
    public static final String KEY_AUDIT_INDICATOR = "audit_indicator";

    public static final String KEY_ROPE_INDICATOR = "rope";

    private static Context mContext;
    /**
     * 练习模式下时间
     */
    public static final String KEY_TIME_PRACTISE = "key_time_practise";
    /**
     * 测试模式下时间
     */
    public static final String KEY_TIME_TEST = "key_time_test";
    /**
     * 练习模式下考试系数
     */
    public static final String KEY_NUM_PRACTISE = "key_num_practise";
    /**
     * 测试模式下考试系数
     */
    public static final String KEY_NUM_TEST = "key_num_test";
    /**
     * 练习模式--单选
     */
    public static final String KEY_SINGEL_PRACTISE = "key_singel_practise";
    /**
     * 测试模式--单选
     */
    public static final String KEY_SINGEL_TEST = "key_singel_test";
    /**
     * 练习模式--多选
     */
    public static final String KEY_MULTI_PRACTISE = "key_multi_practise";
    /**
     * 测试模式--多选
     */
    public static final String KEY_MULTI_TEST = "key_multi_test";
    /**
     * 练习模式--判断
     */
    public static final String KEY_JUGE_PRACTISE = "key_juge_practise";
    /**
     * 测试模式--判断
     */
    public static final String KEY_JUGE_TEST = "key_juge_test";
    /**
     * 练习模式--实训
     */
    public static final String KEY_EX_PRACTISE = "key_ex_practise";
    /**
     * 测试模式--实训
     */
    public static final String KEY_EX_TEST = "key_ex_test";
    /**
     * 第一次进入章节测试
     */
    public static final String KEY_COMEIN_CHAPTER = "key_comein_chapter";
    /**
     * 每次进入apk并且第一次进入章节测试
     */
    public static final String KEY_COMEIN_CHAPTER_TEST = "key_comein_chapter_test";

    /**
     * 屏幕高度
     */
    public static final String SCREEN_HEIGHT = "screen_height";
    /**
     * 屏幕宽度
     */
    public static final String SCREEN_WIDTH = "screen_width";

    /**
     * 登录状态
     */
    public static final String KEY_LOGIN_STATE = "loginState";
    /**
     * 自身实例
     */
    private static PreferenceHelper instance;

    /**
     * 单例模式获取引用
     *
     * @param context
     * @return
     */
    public static PreferenceHelper getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new PreferenceHelper();
        }
        return instance;
    }

    private PreferenceHelper() {
    }

    /**
     * 是否第一次进入某页面
     *
     * @param key
     * @return
     */
    public boolean isFirstTime(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_ISFIRSTTIME_KEY, Context.MODE_PRIVATE);
        boolean isFirstTime = sp.getBoolean(key, true);

        return isFirstTime;

    }

    /**
     * 设置不是第一次进入某页面
     *
     * @param key
     */
    public void setFirstTimeFalse(String key) {
        Editor editor = mContext.getSharedPreferences(PREFERENCE_ISFIRSTTIME_KEY, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, false);
        editor.commit();
    }

    /**
     * 获取指定key对应的value
     *
     * @param key
     * @return
     */
    public boolean getBooleanValue(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE);
        boolean value = sp.getBoolean(key, false);

        return value;
    }

    /**
     * 设置指定key对应的value到preference
     *
     * @param key
     * @param value
     */
    public void setBooleanValue(String key, boolean value) {
        Editor editor = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 设置指定key对应的value到preference
     *
     * @param key
     * @param value
     */
    public void setStringValue(String key, String value) {
        Editor editor = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取指定key对应的value
     *
     * @param key
     * @return
     */
    public String getStringValue(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE);
        String value = sp.getString(key, "");

        return value;
    }

    /**
     * 获取指定key对应的value
     *
     * @param key
     * @return
     */
    public String getStringValue(String key, String def) {
        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE);
        String value = sp.getString(key, def);

        return value;
    }


    /**
     * 设置指定key对应的value到preference
     *
     * @param key
     * @param value
     */
    public void setIntValue(String key, int value) {
        Editor editor = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 获取指定key对应的value
     *
     * @param key
     * @return
     */
    public int getIntValue(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE);
        int value = sp.getInt(key, 0);
        return value;
    }

    public int getAfterIntValue(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE);
        int value = sp.getInt(key, 2);
        return value;
    }

    /**
     * 获取指定key对应的value
     *
     * @param key
     * @return
     */
    public int getIntTestValue(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE);
        int value = sp.getInt(key, -1);
        return value;
    }

    /**
     * 设置指定key对应的value到preference
     *
     * @param key
     * @param value
     */
    public void setLongValue(String key, long value) {
        Editor editor = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 获取指定key对应的value
     *
     * @param key
     * @return
     */
    public long getLongValue(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_VALUE_KEY, Context.MODE_PRIVATE);
        long value = sp.getLong(key, 0);
        return value;
    }
}
