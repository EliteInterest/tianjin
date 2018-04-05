package com.zx.tjmarketmobile.util;

import android.util.Log;

/**
 * @description
 */
public class LogUtil {
	public static boolean DEBUG = true;
	public static boolean INFO = false;
	private final static String TAG = "[Log]";

	/**
	 * @param cls
	 * @param message
	 */
	public static void e(Object cls, String message) {
		if (DEBUG) {
			if (message == null) {
				return;
			}
			Log.e(TAG + cls.getClass().getSimpleName(), message);
		}
	}

	/**
	 * @param tag
	 * @param message
	 */
	public static void e(String tag, String message) {
		if (DEBUG) {
			if (message == null) {
				return;
			}
			Log.e(TAG + tag, message);
		}
	}

	/**
	 * @param cls
	 * @param message
	 */
	public static void d(Object cls, String message) {
		if (DEBUG) {
			if (message == null) {
				return;
			}
			Log.e(TAG + cls.getClass().getSimpleName(), message);
		}
	}

	/**
	 * @param tag
	 * @param message
	 */
	public static void d(String tag, String message) {
		if (DEBUG) {
			if (message == null) {
				return;
			}
			Log.e(TAG + tag, message);
		}
	}

	/**
	 * @param cls
	 * @param message
	 */
	public static void i(Object cls, String message) {
		if (INFO) {
			if (message == null) {
				return;
			}
			Log.i(TAG + cls.getClass().getSimpleName(), message);
		}
	}

	/**
	 * @param tag
	 * @param message
	 */
	public static void i(String tag, String message) {
		if (INFO) {
			if (message == null) {
				return;
			}
			Log.i(TAG + tag, message);
		}
	}

	/**
	 * @param tr
	 */
	public static void w(Throwable tr) {
		if (DEBUG) {
			tr.printStackTrace();
		}
	}
	public static void e(Throwable tr){
		if (DEBUG) {
			Log.e(TAG, "ERROR:", tr);
		}
	}
	/**
	 * unicode 转换�? 中文
	 * 
	 * @param Unicode
	 * @return
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}
}
