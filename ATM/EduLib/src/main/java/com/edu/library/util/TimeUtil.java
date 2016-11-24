package com.edu.library.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间操作工具类
 * 
 * @author Lucher
 */
public class TimeUtil {

	/**
	 * 显示格式如 "yyyy-MM-dd HH:mm:ss" 以指定格式获取当前时间
	 * 
	 * @param type
	 *            显示的格式
	 * 
	 */
	public static String getCurrentTime(String type) {

		String time = "";

		Calendar calendar = Calendar.getInstance();
		Date date = (Date) calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat(type, Locale.CHINA);
		time = format.format(date);

		return time;
	}
	
	/**
	 * 返回日期：距离time还有day
	 * @param time
	 * @param day
	 * @return
	 */
	public static String getTime(String mCurrentTime, int day){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Calendar calendar = Calendar.getInstance();
		try {
            calendar.setTime(format.parse(mCurrentTime));
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        calendar.add(calendar.DAY_OF_YEAR, day);//把日期往后增加一天.整数往后推,负数往前移动
        return format.format(calendar.getTime());//这个时间就是日期往后推一天的结果
	}
}
