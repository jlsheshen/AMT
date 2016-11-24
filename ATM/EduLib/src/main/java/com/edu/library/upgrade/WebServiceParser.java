package com.edu.library.upgrade;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.alibaba.fastjson.JSON;

/**
 * WebService解析基类
 * 
 * @author DBW
 * 
 */
public class WebServiceParser {

	/**
	 * 通过webService返回的String类型
	 * 
	 * @param methodName
	 *            访问的方法名
	 * @param map
	 *            参数名+内容
	 * @return
	 */
	public static String getStringValue(String methodName, LinkedHashMap<String, Object> map) {
		return String.valueOf(getWebServiceValue(methodName, map, null, null, null));
	}

	/**
	 * 通过webService返回的Object类型
	 * 
	 * @param methodName
	 *            访问的方法名
	 * @param name
	 *            参数名
	 * @param obj
	 *            参数内容
	 * @param objV
	 *            返回的对象类型
	 * @return
	 */
	public static Object getObjectValue(String methodName, LinkedHashMap<String, Object> map, Object objV) {
		return getWebServiceValue(methodName, map, objV, null, null);
	}

	/**
	 * 
	 * @param methodName
	 *            访问的方法名
	 * @param name
	 *            参数名
	 * @param obj
	 *            参数内容
	 * @param objL
	 *            需要返回的List
	 * @param listType
	 *            常见的接口实现所有Java类型,Type listType = new TypeToken<ArrayList
	 *            <需要的对象类型>>() { }.getType()
	 * @return
	 */
	public static Object getListValue(String methodName, LinkedHashMap<String, Object> map, ArrayList<?> objL, Type listType) {
		return getWebServiceValue(methodName, map, null, objL, listType);
	}

	/**
	 * 
	 * @param methodName
	 *            访问的方法名
	 * @param name
	 *            参数名
	 * @param obj
	 *            参数内容
	 * @param objV
	 *            需要返回的对象,如果返回值是List类型添null
	 * @param objL
	 *            需要返回的List,如果返回值是对象类型添null
	 * @param listType
	 *            常见的接口实现所有Java类型,Type listType = new TypeToken<ArrayList
	 *            <需要的对象类型>>() { }.getType();，,如果返回值是对象类型添null
	 * @return
	 */
	private static Object getWebServiceValue(String methodName, LinkedHashMap<String, Object> map, Object objV, ArrayList<?> objL, Type listType) {
		System.out.println("<<<<<<<<<<--" + methodName + "-->>>>>>>>>>");
		SoapObject rpc = new SoapObject(ApkUpgradeManager.NAME_SPACE, methodName);
		if (map != null) {
			for (String dataKey : map.keySet()) {
				rpc.addProperty(dataKey, map.get(dataKey));
			}
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		envelope.bodyOut = rpc;
		envelope.dotNet = true;
		envelope.encodingStyle = "UTF-8";
		envelope.setOutputSoapObject(rpc);

		try {
			HttpTransportSE ht = new HttpTransportSE(ApkUpgradeManager.SERVICE_URL, 5000);
			ht.call(methodName, envelope);
			if (envelope.getResponse() != null) {
				return getObjectFJosn(String.valueOf(envelope.getResponse()), objV, objL, listType);
			} else {
				System.out.println("null");
				return null;
			}
		} catch (Exception e) {
			System.out.println("异常~~~");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 返回的对象
	 * 
	 * @param jsonData
	 *            JSON解析的字符串
	 * @param objV
	 *            需要返回的对象
	 * @return
	 */
	public static Object parserJsonObject(String jsonData, Class<?> obClass) {
		return JSON.parseObject(jsonData, obClass);
	}

	/**
	 * 获取Josn中的对象
	 * 
	 * @param string
	 *            JSON解析的字符串
	 * @param objV
	 *            需要返回的对象
	 * @param objL
	 *            需要返回的List对象
	 * @param listType
	 *            常见的接口实现所有Java类型
	 * @return
	 */
	private static Object getObjectFJosn(String string, Object objV, ArrayList<?> objL, Type listType) {
		System.out.println("Web " + string);
		if (objV != null) {
			return parserJsonObject(string, objV.getClass());
		} else if (objL != null) {
			return parserJsonObjectArray(string, objL, listType);
		} else {
			return string;
		}
	}

	/**
	 * 返回的List对象
	 * 
	 * @param jsonData
	 *            JSON解析的字符串
	 * @param objL
	 *            需要返回的List对象
	 * @param listType
	 *            常见的接口实现所有Java类型
	 * @return
	 */
	public static Object parserJsonObjectArray(String jsonData, ArrayList<?> objL, Type listType) {
		return JSON.parseObject(jsonData, listType);
	}

}
