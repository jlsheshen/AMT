package com.edu.accountingteachingmaterial.util;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by ${jl} on 16/5/17.
 */
public class GsonRequest<T> extends Request<T> {
    private Response.Listener<T> mListener;
    private Gson mGson;
    private Class<T> mClass;
    //构造方法中,我们传入的参数,第一个请求数据类型,第二个:url,第三个:失败时候的回调,第四个:成功时候的回调,第五个:实体类
    public GsonRequest(String url, Response.ErrorListener listener, Response.Listener<T> mListener, Class<T> mClass) {
        super(Method.GET, url, listener);
        this.mClass = mClass;
        this.mGson = new Gson();
        this.mListener = mListener;
    }


    @Override
    //返回我们想要的解析后的数据,在这里,首先将response转化成String类型的数据,然后通过Gson解析
    //转化为可以直接使用的数据
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            //解析data
            //成功之后的回调,第一个参数我们将data直接解析,第二个参数就是我们的缓存入口,这里的缓存是规定好的
            //我们直接将response请求作为缓存入口
            return Response.success(mGson.fromJson(data,mClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
    //对请求的一个反馈,反馈就是我们上面定义好的接口对象
    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);

    }
}
