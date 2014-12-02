package com.artion.androiddemos.common;

import android.util.Log;

/**
 * Log日志输出相关常用操作类
 * @author jinsheng_cai
 * @since 2014-12-01
 */
public class LogUtils {
	public static final boolean IS_DEBUG_ENABLE = true;
	public static final String DEBUG_TAG = "LogUtils";
	
	/**
	 * 打印调试信息.
	 * @param msg
	 */
	public static void debug(String msg) {
		debug(DEBUG_TAG, msg);
	}

	/**
	 * 打印警告信息.
	 * @param msg
	 */
	public static void warn(String msg) {
		warn(DEBUG_TAG, msg);
	}
	
	/**
	 * 打印提示信息.
	 * @param msg
	 */
	public static void info(String msg) {
		info(DEBUG_TAG, msg);
	}
	
	/**
	 * 打印错误信息.
	 * @param msg
	 */
	public static void error(String msg, Throwable t) {
		error(DEBUG_TAG, msg, t);
	}
	
	/**
	 * 打印调试信息.
	 * @param tag 自定义tag
	 * @param msg
	 */
	public static void debug(String tag, String msg) {
		if (IS_DEBUG_ENABLE) {
			if(msg == null) {
				return;
			}
			Log.d(DEBUG_TAG, msg);
		}
	}

	/**
	 * 打印警告信息.
	 * @param tag 自定义tag
	 * @param msg
	 */
	public static void warn(String tag, String msg) {
		if (IS_DEBUG_ENABLE) {
			if(msg == null) {
				return;
			}
			Log.w(DEBUG_TAG, msg);
		}
	}

	/**
	 * 打印提示信息.
	 * @param tag 自定义tag
	 * @param msg
	 */
	public static void info(String tag, String msg) {
		if (IS_DEBUG_ENABLE) {
			if(msg == null) {
				return;
			}
			Log.i(tag, msg);
		}
	}

	/**
	 * 打印错误信息.
	 * @param tag 自定义tag
	 * @param msg
	 * @param throwable
	 */
	public static void error(String tag, String msg, Throwable throwable) {
		if (IS_DEBUG_ENABLE) {
			if(msg == null) {
				return;
			}
			Log.w(DEBUG_TAG, msg, throwable);
		}
	}
}
