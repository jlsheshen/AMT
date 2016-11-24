package com.edu.library.util;

/**
 * 验证MD5
 * 
 * @author lucher
 * 
 */
public class MD5Util {

	/**
	 * 用来将字节转换成 16 进制表示的字符
	 */
	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 获取字符串的MD5值
	 * 
	 * @param string
	 * @return
	 */
	public static String getMD5(String string) {
		String s = null;

		try {
			byte[] source = string.getBytes();
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			return toHexString(md.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 获取资源MD5的byte[]
	 * 
	 * @param src
	 * @return
	 */
	public static byte[] getMD5(byte[] src) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(src);
			return md.digest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取资源的MD5十六进制字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String getMD5String(byte[] src) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(src);
			return toHexString(md.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字节数组转换成一个16进制字符串
	 * 
	 * @param data
	 * @return
	 */
	public static String toHexString(byte[] data) {
		try {
			if (data != null && data.length > 0) {
				StringBuilder builder = new StringBuilder();

				for (int i = 0; i < 16; i++) {
					// 从第一个字节开始，对 MD5 的每一个字节转换成 16 进制字符的转换
					byte byte0 = data[i];

					// 取字节中高 4 位的数字转换
					builder.append(hexDigits[byte0 >>> 4 & 0xf]);

					// >>> 为逻辑右移，将符号位一起右移
					// 取字节中低 4 位的数字转换
					builder.append(hexDigits[byte0 & 0xf]);
				}
				// 换后的结果转换为字符串
				return builder.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
