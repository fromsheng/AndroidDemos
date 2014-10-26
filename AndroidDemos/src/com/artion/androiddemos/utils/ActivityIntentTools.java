package com.artion.androiddemos.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;

/**
 * Activity跳转管理类
 * @author jinsheng_cai
 * @since 2013-10-30
 */
public class ActivityIntentTools {
	
	/**
	 * 获取状态栏高度
	 * @param activity
	 * @return
	 */
	public static int getWindowStatusBarHeight(Activity activity) {
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

		return frame.top;
	}
	
	/**
	 * 获取标题栏高度
	 * @param activity
	 * @return
	 */
	public static int getTitleBarHeight(Activity activity) {
		int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
		int statusBarHeight = getWindowStatusBarHeight(activity);
		return contentTop - statusBarHeight;
	}

	/**
	 * Activity Finish Jump
	 * */
	@SuppressWarnings("all")
	public static void gotoActivity(Context context, Class actClass) {
		Intent intent = new Intent(context, actClass);
		context.startActivity(intent);
		((Activity) context).finish();
	}
	
	/**
	 * Activity Not Finish Jump
	 * */
	@SuppressWarnings("all")
	public static void gotoActivityNotFinish(Context context, Class actClass) {
		Intent intent = new Intent(context, actClass);
		context.startActivity(intent);
	}
	
	public static void gotoActivityWithBundle(Context context, Class actClass, Bundle bundle) {
		Intent intent = new Intent(context, actClass);
		intent.putExtras(bundle);
		context.startActivity(intent);
		((Activity) context).finish();
	}
	
	/**
	 * Get IntentData FilePath
	 * */
	public static String getIntentDataFilePath(Activity activity, Uri intentDataUrl) {
		
		if(null == intentDataUrl) return null;
		String filePath = null;
		if ("content".equals(intentDataUrl.getScheme())) {
			Cursor cursor = activity.managedQuery(intentDataUrl,
					null, null, null, null);
			cursor.moveToFirst();
			filePath = cursor.getString(1);
		} else {
			filePath = intentDataUrl.getPath();
		}
		
		return filePath;
	}
	
	/**
	 * Get IntentData FileName
	 * */
	public static String getIntentDataFileName(Activity activity, Uri intentDataUrl) {
		
		if(null == intentDataUrl) return null;
		String fileName = null;
		if ("content".equals(intentDataUrl.getScheme())) {
			Cursor cursor = activity.managedQuery(intentDataUrl,
					null, null, null, null);
			cursor.moveToFirst();
			fileName = cursor.getString(3);
		} else {
			fileName = intentDataUrl.getLastPathSegment();
		}
		
		return fileName;
	}
	
	/**
	 * Image Choose Intent
	 * */
	public static void gotoActivityForResult(Activity activity, Class actClass, int requestCode) {
		Intent intent = new Intent(activity, actClass);
		activity.startActivityForResult(intent, requestCode);
	}
	
	public static void gotoActivityNotFinishWithBundle(Context context, Class actClass, Bundle bundle) {
		Intent intent = new Intent(context, actClass);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
	
	public static void gotoActivityForResultWithBundle(Activity activity, Class actClass, Bundle bundle, int key) {
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
		//intent.setType("image/*");
		intent.setType("text/plain");  
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);  
		intent.putExtra(Intent.EXTRA_TEXT, content);  
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		context.startActivity(Intent.createChooser(intent, title)); 
	}
	
//	public static void startViewUrl(Context context, String url) {
//		try {
//			Uri u = Uri.parse(url);  
//			Intent it = new Intent(Intent.ACTION_VIEW, u);
//			it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(it);	
//		} catch (Exception e) {
//			ToastUtils.showMessage(context, "当前无可用浏览器");
//		}
//	}
}
