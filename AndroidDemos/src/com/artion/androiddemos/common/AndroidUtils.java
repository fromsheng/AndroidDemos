package com.artion.androiddemos.common;

import android.content.Context;

public class AndroidUtils {
	private static Context mContext = null;
	
	public static void regAppContext(Context context) {
		mContext = context;
	}
	
	public static Context appContext() {
		return mContext;
	}
}
