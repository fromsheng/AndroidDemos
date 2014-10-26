package com.artion.androiddemos.utils;

import android.util.Log;


/**
 * 调试日志工具类.需要配置IS_DEBUG_ENABLE和DEBUG_TAG
 * 
 * @since 2013-10-25
 * @author jinsheng_cai
 */
public class DebugTool {
	
	public static final boolean IS_DEBUG_ENABLE = true;
	public static final String DEBUG_TAG = "Kingdee";
	
	/**
	 * 打印调试信息.
	 * 
	 * @param msg
	 */
	public static void debug(String msg) {
		if (IS_DEBUG_ENABLE) {
			Log.d(DEBUG_TAG, msg);
		}
	}

	/**
	 * 打印警告信息.
	 * 
	 * @param msg
	 */
	public static void warn(String msg) {
		if (IS_DEBUG_ENABLE) {
			Log.w(DEBUG_TAG, msg);
		}
	}
	
	/**
	 * 打印提示信息.
	 * 
	 * @param msg
	 */
	public static void info(String msg) {
		if (IS_DEBUG_ENABLE) {
			Log.i(DEBUG_TAG, msg);
		}
	}
	
	/**
	 * 打印错误信息.
	 * 
	 * @param msg
	 */
	public static void error(String msg, Exception e) {
		if (IS_DEBUG_ENABLE) {
			Log.w(DEBUG_TAG, msg, e);
		}
	}
	
	/**
	 * 打印提示信息.
	 * 
	 * @param msg
	 */
	public static void info(String tag, String msg) {
		if (IS_DEBUG_ENABLE) {
			Log.i(tag, msg);
		}
	}
	
}
