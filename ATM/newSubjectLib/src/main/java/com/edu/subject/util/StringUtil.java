package com.edu.subject.util;

/**
 * String工具类
 * @author lucher
 *
 */
public class StringUtil {

	/** 
	 * 判断str1中包含str2的个数 
	  * @param str1 
	 * @param str2 
	 * @return counter 
	 */
	public static int countStr(String str1, String str2) {
		int counter = 0;
		while (str1.contains(str2)) {
			int index = str1.indexOf(str2);
			if (index >= 0) {
				counter++;
				str1 = str1.substring(index + str2.length());
			}
		}
		return counter;
	}
}
