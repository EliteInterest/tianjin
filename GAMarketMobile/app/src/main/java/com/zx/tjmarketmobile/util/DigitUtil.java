package com.zx.tjmarketmobile.util;

public class DigitUtil {

	/**
	 * 功能：将字符串转化为double类型变量
	 * 
	 * @param str
	 *            double类型的字符串
	 * @return 返回的double
	 */
	public static double StringToDouble(String str) {
		double resule = 0;
		try {
			resule = Double.parseDouble(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resule;
	}

	/**
	 * 功能：将字符串转化为int类型变量
	 * 
	 * @param str
	 *            字符串
	 * @return 返回的int
	 */
	public static int StringToInt(String str) {
		int resule = 0;
		try {
			resule = Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resule;
	}

	/**
	 * 功能：将字符串转化为float类型变量
	 * 
	 * @param str
	 *            字符串
	 * @return 返回的float
	 */
	public static float StringToFloat(String str) {
		float resule = 0f;
		try {
			resule = Float.parseFloat(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resule;
	}
	/**
	 * 功能：将字符串转化为long类型变量
	 * 
	 * @param str
	 *            字符串
	 * @return 返回的long
	 */
	public static long StringToLong(String str) {
		long resule = 0;
		try {
			resule = Long.parseLong(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resule;
	}
}
