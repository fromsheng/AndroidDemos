package com.artion.androiddemos.common;

import static android.os.Environment.MEDIA_MOUNTED;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

public class DeviceUtils {
	private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
	
	public static final String NETWORK_TYPE_2G = "2G";
	public static final String NETWORK_TYPE_3G = "3G";
	public static final String NETWORK_TYPE_4G = "4G";
	public static final String NETWORK_TYPE_WIFI = "WIFI";
	public static final String NETWORK_TYPE_UNKNOW = "UNKNOW";
	
	public static boolean hasExternalStoragePermission(Context context) {
		int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
		return perm == PackageManager.PERMISSION_GRANTED;
	}
	
	private static File getExternalCacheDir(Context context) {
		File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
		File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
		if (!appCacheDir.exists()) {
			if (!appCacheDir.mkdirs()) {
				return null;
			}
		}
		return appCacheDir;
	}
	
	public static File getCacheDirectory(Context context, boolean preferExternal) {
		File appCacheDir = null;
		if (preferExternal && MEDIA_MOUNTED
				.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
			appCacheDir = getExternalCacheDir(context);
		}
		if (appCacheDir == null) {
			appCacheDir = context.getCacheDir();
		}
		if (appCacheDir == null) {
			String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
			appCacheDir = new File(cacheDirPath);
		}
		return appCacheDir;
	}
	
	/**
	 * 取得手机IMEI号
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
	
	public static String getSPN(Context context) {
		String spn = "";
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = telephonyManager.getSubscriberId();

		if (imsi != null) {
			if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
				// 因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号 //中国移动
				spn = "CMCC";
			} else if (imsi.startsWith("46001")) {
				// 中国联通
				spn = "CUCC";
			} else if (imsi.startsWith("46003")) {
				// 中国电信
				spn = "CNCC";
			}
		}
		return spn;
	}

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if(mConnectivityManager != null) {
				NetworkInfo mNetworkInfo = mConnectivityManager
						.getActiveNetworkInfo();
				if (mNetworkInfo != null) {
					return mNetworkInfo.isAvailable();
				}
			}
		}
		return false;
	}

	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if(mConnectivityManager != null) {
				NetworkInfo mWiFiNetworkInfo = mConnectivityManager
						.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if (mWiFiNetworkInfo != null) {
					return mWiFiNetworkInfo.isAvailable();
				}
			}
		}
		return false;
	}

	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if(mConnectivityManager != null) {
				NetworkInfo mMobileNetworkInfo = mConnectivityManager
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				if (mMobileNetworkInfo != null) {
					return mMobileNetworkInfo.isAvailable();
				}
			}
		}
		return false;
	}
	
	public static String checkNetType(Context context) {
		String netType = NETWORK_TYPE_UNKNOW;
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);// 获取系统的连接服务
			NetworkInfo activeNetInfo = connectivityManager
					.getActiveNetworkInfo();// 获取网络的连接情况
			if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				// 判断WIFI网
				netType = NETWORK_TYPE_WIFI;
			} else if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				// 判断3G网
				int subType = activeNetInfo.getSubtype();
				Log.d("subType", "subType:" + subType);
				switch (subType) {
				case TelephonyManager.NETWORK_TYPE_GPRS:
					// 联通2G
					netType = NETWORK_TYPE_2G;
					break;
				case TelephonyManager.NETWORK_TYPE_EDGE:
					// 移动2.75G
					netType = NETWORK_TYPE_2G;
					break;
				case TelephonyManager.NETWORK_TYPE_UMTS:
					// 联通3G
					netType = NETWORK_TYPE_3G;
					break;
				case TelephonyManager.NETWORK_TYPE_CDMA:
					// 电信2G
					netType = NETWORK_TYPE_2G;
					break;
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
					// 电信3G
					netType = NETWORK_TYPE_3G;
					break;
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
					// 电信3G
					netType = NETWORK_TYPE_3G;
					break;
				case TelephonyManager.NETWORK_TYPE_1xRTT:
					netType = NETWORK_TYPE_2G;
					break;
				case TelephonyManager.NETWORK_TYPE_HSDPA:
					// 联通3G
					netType = NETWORK_TYPE_3G;
					break;
				case TelephonyManager.NETWORK_TYPE_HSUPA:
					netType = NETWORK_TYPE_3G;
					break;
				case TelephonyManager.NETWORK_TYPE_HSPA:
					netType = NETWORK_TYPE_3G;
					break;
				case TelephonyManager.NETWORK_TYPE_IDEN:
					netType = "IDEN";
					break;
				case 12: // TelephonyManager.NETWORK_TYPE_EVDO_B:
					// 电信3G
					netType = NETWORK_TYPE_3G;
					break;
				case 13:// TelephonyManager.NETWORK_TYPE_LTE://LTE是3g到4g的过渡，是3.9G的全球标准
					netType = NETWORK_TYPE_3G;
					break;
				case 14:// TelephonyManager.NETWORK_TYPE_EHRPD:
					netType = NETWORK_TYPE_3G;
					break;
				case 15:// TelephonyManager.NETWORK_TYPE_HSPAP:
					netType = NETWORK_TYPE_3G;
					break;
				case TelephonyManager.NETWORK_TYPE_UNKNOWN:

					break;

				default:
					break;
				}
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return netType;
	}

	/**
	 * 用来判断服务是否运行.
	 * 
	 * @param context
	 * @param className
	 *            判断的服务名字：包名+类名
	 * @return true 在运行, false 不在运行
	 */

	public static boolean isServiceRunning(Context context, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(Integer.MAX_VALUE);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	/**
	 * 判断某个广播接收器是否存在
	 * 
	 * @param context
	 * @param actionName
	 *            广播接收器接收的广播actionname
	 * @param className
	 *            广播接收器完整类名
	 * @return
	 */
	public static boolean hasBroadcastReceivers(Context context,
			String actionName, String className) {
		boolean has = false;
		PackageManager packageManager = context.getPackageManager();
		Intent intent = new Intent(actionName);
		List<ResolveInfo> ris = packageManager.queryBroadcastReceivers(intent,
				0);
		for (ResolveInfo resolveInfo : ris) {
			if (resolveInfo.activityInfo.name.equalsIgnoreCase(className)) {
				has = true;
				break;
			}
		}
		return has;
	}
	
	public static int getVersionCode(Context context) {
		PackageManager packageManager = context.getPackageManager();
		int version_code = -1;
		try {
			version_code = packageManager.getPackageInfo(context.getPackageName(),
					0).versionCode;

			return version_code;
		} catch (NameNotFoundException e) {
			// e.printStackTrace();
		}
		return 0;
	}
	
	public static String getVersionName(Context context) {
		PackageManager pm = context.getPackageManager();
		PackageInfo pi;
		try {
			pi = pm.getPackageInfo(context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
//			e.printStackTrace();
		}
		return "1.0.0";
	}
	
	/**
	 * DESC 先获取ANDROID_ID，获取不到时，再获取UUID，注意ANDROID_ID在每次恢复出厂设置后，都会改变。</p>
	 * @see http://blog.csdn.net/huanghr_1/article/details/7721707
	 * @param context
	 * @return
	 */
	public static String getAndroidId(Context context){
		String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		
		if(StringUtils.isEmpty(androidId)){
			androidId = newRandomUUID();
		}else{
			androidId = "an"+androidId;//设备android_id
		}
		if(!StringUtils.isEmpty(androidId)){
			androidId.replaceAll("-", "");
		}
		return androidId;
	}
	
    private static String newRandomUUID() {
        String uuidRaw = UUID.randomUUID().toString();
        uuidRaw = "uu" + uuidRaw; //标志UIID
        return uuidRaw.replaceAll("-", "");
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
    
    /** 
     * 设置手机飞行模式 
     * @param context 
     * @param enabling true:设置为飞行模式 false:取消飞行模式 
     */  
    public static void setAirplaneModeOn(Context context,boolean enabling) {  
        Settings.System.putInt(context.getContentResolver(),  
                             Settings.System.AIRPLANE_MODE_ON,enabling ? 1 : 0);  
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);  
        intent.putExtra("state", enabling);  
        context.sendBroadcast(intent);  
    }  
    
    /**
	 * 判断手机是否处于飞行模式.
	 * @param context
	 * @return
	 */
	public static boolean isAirplaneModeOn(Context context) {
		return Settings.System.getInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) == 1;
	}
	
	/**
	 * 判断当前应用是否在后台
	 * @param context
	 * @return
	 */
	public static boolean isOnBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	public static boolean isPackageExists(Context context, String packageName) { 
		final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
		PackageInfo packageInfo;
		try {
			packageInfo = packageManager.getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			packageInfo = null;
			//e.printStackTrace();
		}
		if(packageInfo ==null){
			return false;
		}else{
			return true;
		}
	}
	
	
	private static long getAvailableMemorySize(String path) {
		StatFs stat = new StatFs(path);
		long blockSize, availableBlocks;
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
			blockSize = stat.getBlockSize();
			availableBlocks = stat.getAvailableBlocks();
		} else {
			blockSize = stat.getBlockSizeLong();
			availableBlocks = stat.getAvailableBlocksLong();
		}

		return availableBlocks * blockSize;
	}
	/**
	 * 获取系统可用内存大小，单位byte
	 * @return
	 */
	public static long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		if(path == null) {
			return 0;
		}
		return getAvailableMemorySize(path.getPath());
	}
	
	public static long getAvailableExternalMemorySize() {
		File path = Environment.getExternalStorageDirectory();
		if(path == null) {
			return 0;
		}
		return getAvailableMemorySize(path.getPath());
	}
}
