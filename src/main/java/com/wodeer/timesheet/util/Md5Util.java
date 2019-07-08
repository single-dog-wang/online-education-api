package com.wodeer.timesheet.util;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author guoya
 */
public class Md5Util {

	/**
	 * String DigestUtils.md5Hex(str)
	 * 作用：加密
	 * 参数：要加密的字符串
	 * 返回值
	 */
	private static String md5(String str) {
		return DigestUtils.md5Hex(str);
	}

	/**
	 * String firstEncryption(String str)
	 * 作用：第一次加盐
	 * 参数：要加入的字符串,直接定义
	 * 返回值：返回第一次加盐的密码
	 */
	private static String firstEncryption(String str) {
		String salt = "123098";
		return md5(str + salt);
	}

	/**
	 * secondEncryption(String str, String dbSalt)
	 * 作用：第二次加盐
	 * 参数：第一次返回的字符串,第二次手动传入的字符串
	 * 返回值：返回第二次加盐的密码
	 */
	private static String secondEncryption(String str, String dbSalt) {
		return md5(str + dbSalt);
	}


	/**
	 * 封装两次加盐加密的方法
	 */
	public static String encryption(String str, String dbSalt) {
		return secondEncryption(firstEncryption(str), dbSalt);
	}
	
	
}
