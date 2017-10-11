package cn.tedu.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WebUtils {
	private WebUtils(){}
	
	/**
	 * 检查字符串是为null或者为""
	 * @param str
	 * @return boolean true-表示字符串为空null或""
	 */
	public static boolean isNull(String str){
		return str == null || "".equals(str.trim());
	}
	
	/**
	 * 使用md5加密算法进行加密
	 * @param plainText
	 * @return
	 */
	public static String md5(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5这个算法！");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

}
