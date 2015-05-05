package com.artion.androiddemos.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
	
	public static void gotoTargetActivityByScheme(Context context, String uri) {
		if(uri == null) {
			return;
		}
		Intent intent = new Intent();  
		intent.setAction("android.intent.action.VIEW");  
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setData(Uri.parse(uri));  //匹配了text/*  
		context.startActivity(intent);      //此方法中调用intent.addCategory("android.intent.category.DEFAULT");
	}
	
	/**
	 * 进入系统选择图片
	 * @param activity
	 * @param Title 弹出选择浏览选择框的title
	 * @param requestCode 请求的响应code
	 */
	public static void choosePhoto(Activity activity, final String Title, final int requestCode) {
		
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		intent.putExtra("return-data", false);
//		intent.setType("image/*");
//		intent.addCategory(Intent.CATEGORY_OPENABLE);
		
		activity.startActivityForResult(Intent.createChooser(intent, Title), requestCode);
	}
	
	
	/**
	 * 调用系统相机拍照
	 * @param activity
	 * @param requestCode 请求的响应code
	 * @param uri 拍照后返回的图片uri
	 */
	public static void takePhoto(Activity activity, final int requestCode, Uri uri) {
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		intent.putExtra("return-data", false);

		activity.startActivityForResult(intent, requestCode);
	}
	
	/**
	 * 进入系统图片裁剪
	 * @param activity
	 * @param uri 传入的图片uri
	 * @param outUri 输出的图片uri
	 * @param outputX 输出图片的宽度
	 * @param outputY 输出图片的高度
	 * @param requestCode 请求的响应code
	 */
	public static void cropPhoto(Activity activity, Uri uri, Uri outUri, int outputX, int outputY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false); //true:不返回uri，false：返回uri
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);//可适当解决OOM
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", false); // no face detection
		//系统的裁剪图片默认对图片进行人脸识别，当识别到有人脸时，会按aspectX和aspectY为1来处理
		//如果想设置成自定义的裁剪比例，需要设置noFaceDetection为true。取消人脸识别功能。
		activity.startActivityForResult(intent, requestCode);
	}
}
