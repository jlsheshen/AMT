package com.edu.accountingteachingmaterial.util;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;


/**
 * 创建一个数组类型的GsonRequest
 * Created by ${jl} on 16/5/16.
 */
public class ArrayGsonRequest<T> extends Request<T> {


    Gson gson;
    Response.Listener listener;
    Type type;
    //new一个Gson,这里metod可以直接写上get或者Post
    public ArrayGsonRequest( String url, Response.ErrorListener listener, Response.Listener listener1, Type type) {
        super(Method.GET, url, listener);
        this.listener = listener1;
        gson = new Gson();
        this.type = type;
    }
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        //先将response转化成String类型的数据,
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            //返回我们gosn解析成功后的的数据,直接把数据作为解析后的网络相应
            return (Response<T>) Response.success(gson.fromJson(json,type),HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
    //反馈我们上面定义好的接口对象,交付相应
    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);

    }
}
