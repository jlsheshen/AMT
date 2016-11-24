package com.edu.accountingteachingmaterial.util;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.edu.accountingteachingmaterial.base.BaseApplication;


import java.lang.reflect.Type;

/**
 * 创建请求的单例
 * Created by ${jl} on 16/5/16.
 */
public class SingleRequestQueue {
    private RequestQueue queue;
    private static SingleRequestQueue singleRequestQueue;


    private SingleRequestQueue() {

        queue = getQueue();
    }

    public static SingleRequestQueue getInstance() {


        if (singleRequestQueue == null) {
            synchronized (Volley.class) {
                if (singleRequestQueue == null) {
                    singleRequestQueue = new SingleRequestQueue();
                }
            }
        }
        return singleRequestQueue;
    }

    public RequestQueue getQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(BaseApplication.getContext());
        }
        return queue;
    }

    //单例删除方法
    public void removeRequest(Object tag){
        queue.cancelAll(tag);

    }

    //在添加队列的方法中,调用我们封装好的数组Gson请求方法
    public static <T> void addArrayRequest(String url, Type type,
                                           Response.Listener<T> tListener,
                                           Response.ErrorListener errorListener,
                                           Object tag){
        ArrayGsonRequest arrayGsonRequest = new ArrayGsonRequest(
                url,
                errorListener,
                tListener,
                type);
        //加入一个tag,方便离开的时候停止
        arrayGsonRequest.setTag(tag);

        getInstance().queue.add(arrayGsonRequest);
    }
    //普通gson请求方法
    public static <T> void addRequest(String url,
                                           Response.Listener<T> tListener,
                                           Response.ErrorListener errorListener,
                                           Object tag,Class<T> tClass){
        GsonRequest arrayGsonRequest = new GsonRequest(
                url,
                errorListener,
                tListener,tClass
                );
        //加入一个tag,方便离开的时候停止
        arrayGsonRequest.setTag(tag);

        getInstance().queue.add(arrayGsonRequest);

    }

}
