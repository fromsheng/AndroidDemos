package com.artion.androiddemos.common;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Activity相关常用操作类
 * @author jinsheng_cai
 * @since 2014-12-01
 */
public class ActivityUtils {
	public static void finishActivity(Context context) {
		if(context == null) {
			return ;
		}
		
		if(context instanceof Activity) {
			((Activity) context).finish();
		}
	}
	
	/**
	 * Activity Finish Jump
	 * */
	public static void gotoActivity(Context context, Class<?> actClass) {
		Intent intent = new Intent(context, actClass);
		context.startActivity(intent);
		finishActivity(context);
	}
	
	/**
	 * Activity Not Finish Jump
	 * */
	public static void gotoActivityNotFinish(Context context, Class<?> actClass) {
		Intent intent = new Intent(context, actClass);
		context.startActivity(intent);
	}
	
	public static void gotoActivityWithBundle(Context context, Class<?> actClass, Bundle bundle) {
		Intent intent = new Intent(context, actClass);
		if(bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
		finishActivity(context);
	}
	
	public static void gotoActivityNotFinishWithBundle(Context context, Class<?> actClass, Bundle bundle) {
		Intent intent = new Intent(context, actClass);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
	
	public static void gotoActivityForResult(Activity activity, Class<?> actClass, int requestCode) {
		Intent intent = new Intent(activity, actClass);
		activity.startActivityForResult(intent, requestCode);
	}
	
	
	public static void gotoActivityForResultWithBundle(Activity activity, Class<?> actClass, Bundle bundle, int key) {
		Intent intent = new Intent(activity, actClass);
		intent.putExtras(bundle);
		activity.startActivityForResult(intent, key);
	}
	
	/**
	 * 分享到各客户端
	 * @param context
	 * @param subject
	 * @param title
	 * @param content
	 */
	public static void startShare(Context context, String subject, String title, String content) {
		Intent intent=new Intent(Intent.ACTION_SEND);  
		//intent.setType(KdweiboConstantTypes.IMAGE_UNKNOW);
		intent.setType("text/plain");  
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);  
		intent.putExtra(Intent.EXTRA_TEXT, content);  
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		context.startActivity(Intent.createChooser(intent, title)); 
	}
	
	public static boolean isClientTopActivy(Context context, String packageName) {
		if(packageName == null) {
			return false;
		}
		try {
			ActivityManager activityManager = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			List<RunningTaskInfo> runningTaskInfos = activityManager
					.getRunningTasks(1);
			if (null != runningTaskInfos) {
				if ((runningTaskInfos.get(0).topActivity).getPackageName()
						.startsWith(packageName)) {
					return true;
				}

			}
		} catch (Exception ex) {
		}
		return false;
	}

	public static String getClientTopActivy(Context context) {
		String name = "";
		try {
			ActivityManager activityManager = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			List<RunningTaskInfo> runningTaskInfos = activityManager
					.getRunningTasks(1);
			if (null != runningTaskInfos) {
				name = (runningTaskInfos.get(0).topActivity).getClassName();
			} else {
				name = "";
			}
		} catch (Exception ex) {
		}
		return name;
	}
}
