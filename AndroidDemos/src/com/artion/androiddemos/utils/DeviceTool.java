package com.artion.androiddemos.utils;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import com.artion.androiddemos.R;
import com.artion.androiddemos.common.TimerUtils;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.common.TimerUtils.TimerListener;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 设备相关操作工具类.
 * 
 * @since 1.0
 * @author jinsheng_cai
 */
public class DeviceTool {

	public static long MAXCacheByteSize = 5 * 1024 * 1024;

	/**
	 * 判断是否已连接到网络.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 检查网络接连类型.
	 * 
	 * @param context
	 * @return SysConstants.NETWORK_TYPE_NONE: 无网络连接;
	 *         SysConstants.NETWORK_TYPE_WIFI: 通过WIFI连接网络;
	 *         SysConstants.NETWORK_TYPE_WAP: 通过GPRS连接网络.
	 */
//	public static int checkNetWorkType(Context context) {
//		if (isAirplaneModeOn(context)) {
//			return Constants.NETWORK_TYPE_NONE;
//		}
//
//		ConnectivityManager connectivityManager = (ConnectivityManager) context
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//		if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
//				.getState() == NetworkInfo.State.CONNECTED) {
//			return Constants.NETWORK_TYPE_NET;
//		}
//
//		if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
//				.getState() == NetworkInfo.State.CONNECTED) {
//			String type = connectivityManager.getActiveNetworkInfo()
//					.getExtraInfo();
//			if (!StringTool.isEmpty(type)
//					&& type.toLowerCase().indexOf("wap") >= 0) {
//				if (type.toLowerCase().indexOf("ctwap") >= 0) {
//					return Constants.NETWORK_TYPE_WAP_2;
//				} else {
//					return Constants.NETWORK_TYPE_WAP;
//				}
//			} else {
//				return Constants.NETWORK_TYPE_NET;
//			}
//		}
//
//		return Constants.NETWORK_TYPE_NONE;
//	}

	/**
	 * 判断手机是否处于飞行模式.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isAirplaneModeOn(Context context) {
		return Settings.System.getInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) != 0;
	}

	/**
	 * 获取设备序列号.
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	/**
	 * 判断手机SDCard是否已安装并可读写.
	 * 
	 * @return
	 */
	public static boolean isSDCardUsable() {
		return Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment
				.getExternalStorageState());
	}

	/**
	 * 获取指定的SDCard中图片缓存目录.
	 * 
	 * @param defaultImageFolderName
	 * @return
	 */
	public static File getImageCacheDir() {

		return getImageCacheDir("KingdeeCache");
	}

	/**
	 * 获取指定的SDCard中图片缓存目录.
	 * 
	 * @param defaultImageFolderName
	 * @return
	 */
	public static File getImageCacheDir(String defaultImageFolderName) {
		if (isSDCardUsable()) {
			File dir = new File(Environment.getExternalStorageDirectory(),
					defaultImageFolderName);
			if (!dir.exists())
				dir.mkdirs();

			return dir;
		}
		return null;
	}

	/**
	 * 隐藏某焦点控件弹出的软件键盘.
	 * 
	 * @param context
	 * @param view
	 */
	public static void hideSoftKeyboardFromView(Context context, View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Service.INPUT_METHOD_SERVICE);
		IBinder binder = view.getWindowToken();
		inputMethodManager.hideSoftInputFromWindow(binder, 0);
	}

	/**
	 * 弹出某焦点控件的软件键盘.
	 * 
	 * @param context
	 * @param view
	 */
	public static void showSoftKeyboardFromView(Context context, View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Service.INPUT_METHOD_SERVICE);
		IBinder binder = view.getWindowToken();
		// inputMethodManager.showSoftInput(view, 0);
		inputMethodManager.showSoftInputFromInputMethod(binder, 0);
	}

	public static void showSoftKeyboard(Context context, View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Service.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(view, 0);
	}

	/**
	 * 取得手机IMEI号
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		// check if has the permission
		if (PackageManager.PERMISSION_GRANTED == context.getPackageManager()
				.checkPermission(Manifest.permission.READ_PHONE_STATE,
						context.getPackageName())) {
			return manager.getDeviceId();
		} else {
			return "";
		}
	}

	/**
	 * 取得手机IMSI号
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		// check if has the permission
		if (PackageManager.PERMISSION_GRANTED == context.getPackageManager()
				.checkPermission(Manifest.permission.READ_PHONE_STATE,
						context.getPackageName())) {
			return manager.getSubscriberId();
		} else {
			return "";
		}
	}

//	/**
//	 * 取得手机网络运营商ID
//	 * 
//	 * @param context
//	 * @return
//	 */
	// public static String getNetWorkOperator(Context context) {
	// TelephonyManager manager = (TelephonyManager) context
	// .getSystemService(Activity.TELEPHONY_SERVICE);
	// // check if has the permission
	// if (PackageManager.PERMISSION_GRANTED == context.getPackageManager()
	// .checkPermission(Manifest.permission.READ_PHONE_STATE,
	// context.getPackageName())) {
	// return manager.getNetworkOperator();
	// } else {
	// return "";
	// }
	// }

	/**
	 * 取得联网方式，WIFI, 用移动CMNET方式 getExtraInfo 的值是cmnet 用移动CMWAP方式 getExtraInfo
	 * 的值是cmwap 但是不在代理的情况下访问普通的网站访问不了 用联通3gwap方式 getExtraInfo 的值是3gwap
	 * 用联通3gnet方式 getExtraInfo 的值是3gnet 用联通uniwap方式 getExtraInfo 的值是uniwap
	 * 用联通uninet方式 getExtraInfo 的值是uninet
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getNetWorkType(Context ctx) {
		// 获取网络连接管理者
		ConnectivityManager connectionManager = (ConnectivityManager) ctx
				.getSystemService(ctx.CONNECTIVITY_SERVICE);
		// 获取网络的状态信息，有下面三种方式
		NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {

				String type = networkInfo.getTypeName();

				if (type != null && type.toUpperCase().equals("WIFI")) {
					return "WIFI";
				}

				if (type != null && type.toLowerCase().equals("mobile")) {
					return networkInfo.getExtraInfo();
				}

				return "other";
			}
			return "";
		}

		return "";
	}

	/**
	 * 取得当前系统的版本号
	 * 
	 * @return
	 */
	public static String getSystemVersion() {
		String systemVersion = android.os.Build.MODEL + ","
				+ android.os.Build.VERSION.SDK + ","
				+ android.os.Build.VERSION.RELEASE;
		return systemVersion;

	}

	/**
	 * 用来获取手机拨号上网（包括CTWAP和CTNET）时由PDSN分配给手机终端的源IP地址。
	 * 
	 * @return
	 * @author SHANHY
	 */
	public static String getPsdnIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& inetAddress instanceof Inet4Address) {
						// if (!inetAddress.isLoopbackAddress() && inetAddress
						// instanceof Inet6Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
		}
		return "";
	}

	public static long getTotalExternalMemorySize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());

		long blockSize = stat.getBlockSize();
		long blocks = stat.getAvailableBlocks();
		long availableSpare = (blocks * blockSize);
		return availableSpare;
	}

	public static String getExternalStoragePath() {
		// 获取SdCard状态
		String state = android.os.Environment.getExternalStorageState();

		// 判断SdCard是否存在并且是可用的
		if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
			if (android.os.Environment.getExternalStorageDirectory().canWrite()) {
				return android.os.Environment.getExternalStorageDirectory()
						.getPath();
			}
		}
		return null;
	}

	/**
	 * 判断是否连接了sdcard
	 * 
	 * @return
	 */
	public static boolean externalMemoryAvailable() {
		if (getExternalStoragePath() != null) {
			return true;
		}
		return false;
	}

	/**
	 * 打开apk文件安装
	 * 
	 * @param activity
	 * @param file
	 */
	public static void openInstallApkFile(Context activity, File file) {
		Intent intent = new Intent();
		// Intent respondIntent = new Intent(activity, activity.getClass());
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		activity.startActivity(intent);
	}

	public static String getSoftVersion(Context ctx) {
		PackageManager packageManager = ctx.getPackageManager();
		String version_name = null;
		int version_code = -1;
		try {
			version_name = packageManager.getPackageInfo(ctx.getPackageName(),
					0).versionName;
			version_code = packageManager.getPackageInfo(ctx.getPackageName(),
					0).versionCode;

			return version_name + "." + version_code;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static int getVersionCode(Context ctx) {
		PackageManager packageManager = ctx.getPackageManager();
		int version_code = -1;
		try {
			version_code = packageManager.getPackageInfo(ctx.getPackageName(),
					0).versionCode;

			return version_code;
		} catch (NameNotFoundException e) {
			// e.printStackTrace();
		}
		return 0;
	}

	private static final String APP_DIR_NAME = "Kingdee";
	private static final String PHOTO_DIR_NAME = ".photo";
	private static final String IMAGE_COMPRESS_DIR_NAME = ".imageCompress";
	private static final String DISK_LRU_CACHE_DIR_NAME = ".DiskLruCache";
	private static final String DISK_CACHE_DIR_NAME = ".DiskCache";

	private static File getAppDir() {
		File file = new File(Environment.getExternalStorageDirectory(),
				APP_DIR_NAME);
		if (!file.exists()) {
			file.mkdirs();// 创建文件夹
		}

		return file;
	}

	public static File getPhotoDir() {
		File file = new File(getAppDir(), PHOTO_DIR_NAME);
		if (!file.exists()) {
			file.mkdirs();// 创建文件夹
		}

		return file;
	}

	public static File getImageCompressDir() {
		File file = new File(getAppDir(), IMAGE_COMPRESS_DIR_NAME);
		if (!file.exists()) {
			file.mkdirs();// 创建文件夹
		}

		return file;
	}

	public static File getDiskLruCacheDir() {
		File file = new File(getAppDir(), DISK_LRU_CACHE_DIR_NAME);
		if (!file.exists()) {
			file.mkdirs();// 创建文件夹
		}

		return file;
	}

	public static File getDiskCacheDir() {
		File file = new File(getAppDir(), DISK_CACHE_DIR_NAME);
		if (!file.exists()) {
			file.mkdirs();// 创建文件夹
		}

		return file;
	}

	public static File getLazyFileCacheDir() {
		File file = new File(getAppDir(), ".Lazy");
		if (!file.exists()) {
			file.mkdirs();// 创建文件夹
		}

		return file;
	}

	/**
	 * 清空截图文件夹
	 */
	public static void clearImageCompressDIr() {
		// File file = new File(Environment.getExternalStorageDirectory(),
		// PHOTO_DIR_NAME);
		File file = getImageCompressDir();
		if (file.exists()) {
			delDir(file);
		}
	}

	private static void delDir(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isFile()) {
					file.delete();
				} else {
					delDir(file);
				}
			}
		} else {
			dir.delete();
		}
	}

//	public static void clearPhotoCache(Context context) {
//		ImageLoader mImageLoader = ImageLoader.getImageLoader(context);
//		// DiskLruCache imageManager2Cache = DiskLruCache.openCache(context);
//		double cacheSize = mImageLoader.getFileCacheSize();
//		// double cacheSize2 = imageManager2Cache.getCacheDirSize();
//		// double cacheSize = cacheSize1 + cacheSize2;
//
//		// DebugTool.info("Device", "cacheSize1 == " + cacheSize1);
//		// DebugTool.info("Device", "cacheSize2 == " + cacheSize2);
//		DebugTool.info("Device", "cacheSize == " + cacheSize);
//
//		if (DeviceTool.externalMemoryAvailable()) {
//			// 属于sdcard
//			if (cacheSize >= Configuration.MAX_IMAGE_CACHE_SIZE) {
//				mImageLoader.clearFileCache();
//				// imageManager2Cache.clearCache();
//			}
//		} else {
//			// 属于内部cache
//			if (cacheSize >= Configuration.MAX_INNER_CACHE_SIZE) {
//				mImageLoader.clearFileCache();
//				// imageManager2Cache.clearCache();
//			}
//		}
//	}

	public static void setEditTextIndex(EditText editText) {
		Editable etable = editText.getText();
		Selection.setSelection(etable, etable.length());
	}
	
	/** view是否正被双击
	 * @param view
	 * @param isByAllView 是否全部view都在计算内，默认暂时无效
	 * @return
	 */
	public static boolean isOnViewDoubleClick(View view, boolean isByAllView) {
		Long t1 = (Long) view.getTag(view.getId());
		if(t1 == null) {
			view.setTag(view.getId(), System.currentTimeMillis());
			return false;
		}
		long t2 = System.currentTimeMillis();
		view.setTag(view.getId(), t2);
		
		if(Math.abs(t2 - t1) > 500) {
			return false;
		}
		DebugTool.info("DeviceTool", "isOnViewDoubleClick + " + view.toString());
		
		Integer doubleTimes = (Integer) view.getTag(R.id.tag_view_double_times);
		if(doubleTimes == null) {
			doubleTimes = 1;
			view.setTag(R.id.tag_view_double_times, doubleTimes);
		} else {
			view.setTag(R.id.tag_view_double_times, ++ doubleTimes);
		}
		
		ToastUtils.showMessage(view.getContext(), "双击次数:" + doubleTimes);
		
		return true;
	}
	
	/**
	 * 控件是否被连续点击多少次
	 * @param view 控件必须设置id
	 * @param MAX_COUNT 点击最大次数响应
	 * @param DIFF_TIME 点击时间差（毫秒），可选500，或 1000等
	 * @return
	 */
	public static boolean isOnViewClickCount(View view, final int MAX_COUNT, final int DIFF_TIME) {
		if(MAX_COUNT < 2) {
			return true;
		}
		final int TAG_ID_TIME = view.getId() + 1;
		final int TAG_ID_CLICKTIMES = view.getId() + 2;
		
		Long t1 = (Long) view.getTag(TAG_ID_TIME);
		Integer count1 = (Integer) view.getTag(TAG_ID_CLICKTIMES);
		
		if(t1 == null) {
			t1 = Long.valueOf(0);
		}
		
		if(count1 == null) {
			count1 = Integer.valueOf(0);
		}
		
		long t2 = System.currentTimeMillis();
		
		view.setTag(TAG_ID_TIME, t2);
		view.setTag(TAG_ID_CLICKTIMES, ++ count1);
		
		if(Math.abs(t2 - t1) > DIFF_TIME) {
			if(t1 != 0) {
				view.setTag(TAG_ID_CLICKTIMES, Integer.valueOf(1));
			}
			return false;
		}
		if(count1 >= MAX_COUNT) {
			view.setTag(TAG_ID_TIME, Long.valueOf(0));
			view.setTag(TAG_ID_CLICKTIMES, Integer.valueOf(0));
			return true;
		}
		
		return false;
	}
	
	/**
	 * 控件被连续点击多少次之后响应
	 * @param view 控件必须设置id
	 * @param DIFF_TIME 点击时间差（毫秒），可选300，500，或 1000等
	 * @param listener 控件点击响应包含点击次数
	 * @return
	 */
	public static int onViewClickTimes(final View view, final int DIFF_TIME, final OnViewClickListener listener) {
		final int TAG_ID_CLICKTIMES = view.getId() + 20;
		final int TAG_ID_TIMERUTILS = view.getId() + 30;
		
		Integer count1 = (Integer) view.getTag(TAG_ID_CLICKTIMES);
		TimerUtils timer = (TimerUtils) view.getTag(TAG_ID_TIMERUTILS);
		
		if(count1 == null) {
			count1 = Integer.valueOf(0);
		}
		
		if(timer == null) {
			timer = new TimerUtils();
			view.setTag(TAG_ID_TIMERUTILS, timer);
		}
		
		view.setTag(TAG_ID_CLICKTIMES, ++ count1);
		
		if(listener != null) {
			listener.onClicking(view, count1);
		}
		
		timer.startTimer(DIFF_TIME, new TimerListener() {
			
			@Override
			public void timeOnTick(long seconds) {
			}
			
			@Override
			public void timeOnFinish() {
				DebugTool.info("DeviceTool", "onViewClickTimes + " + (Integer) view.getTag(TAG_ID_CLICKTIMES));
				if(listener != null) {
					listener.onClicked(view, (Integer) view.getTag(TAG_ID_CLICKTIMES));
				}
				
				view.setTag(TAG_ID_CLICKTIMES, Integer.valueOf(0));
			}
		});
		return 0;
	}
	
	/**
	 * 控件点击响应包含点击次数响应接口
	 * @author jinsheng_cai
	 * @since 2014-10-27
	 */
	public interface OnViewClickListener {
		/**
		 * 点击结束后响应
		 * @param view
		 * @param clickTimes 被点击次数
		 */
		public void onClicked(View view, int clickTimes);
		/**
		 * 点击过程中响应
		 * @param view
		 * @param clickTimes 被点击次数
		 */
		public void onClicking(View view, int clickTimes);
	}
	
	/**
	 * 控件跳动单次
	 * @param view
	 */
	public static void shockView(View view) {
		DebugTool.info("DeviceTool", "shockView");
//		view.clearAnimation();
		AnimationSet set = new AnimationSet(false);
		TranslateAnimation transAnim = new TranslateAnimation(0, 2, 0, -5);
        transAnim.setDuration(250);
        transAnim.setRepeatCount(5);
        transAnim.setRepeatMode(Animation.REVERSE);
        
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.2f, 0f, -1f);
        scaleAnim.setDuration(250);
        scaleAnim.setRepeatCount(5);
        scaleAnim.setRepeatMode(Animation.REVERSE);
        
        set.addAnimation(transAnim);
        set.addAnimation(scaleAnim);
        view.startAnimation(set);
	}
	
	public static void recycleDrawable(Drawable drawable) {
		if(drawable == null) {
			return;
		}
		
		drawable.setCallback(null);
		
		BitmapDrawable db = (BitmapDrawable) drawable;
		if(db != null) {
			db.setCallback(null);
//			Bitmap bm = db.getBitmap();
//			if(bm != null && !bm.isRecycled()) {
//				bm.recycle();
//				bm = null;
//			}
		}
	}
	
	/**
	 * 回收控件的背景资源
	 * @param view
	 */
	public static void recycleDrawable(View view) {
		if(view == null) {
			return;
		}
		
		recycleDrawable(view.getBackground());
		
		if(view instanceof ImageView) {
			recycleDrawable(((ImageView) view).getDrawable());
			
		}
	}
}
