package com.edu.accountingteachingmaterial.util;

import android.widget.EditText;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * EditText工具类
 *
 * @author lucher
 */
public class EditTextUtil {
    /**
     * 禁用系统输入法 且使用反射达到显示光标 让其默认不弹出输入法，可在manifest里加入
     * android:windowSoftInputMode="adjustUnspecified|stateHidden" >
     *
     * @param et
     */
    public static void setInputtypeNull(EditText et) {
        Class<EditText> cls = EditText.class;
        try {
            Method setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(false);
            setShowSoftInputOnFocus.invoke(et, false);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
