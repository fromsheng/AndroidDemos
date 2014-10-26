package com.artion.androiddemos.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast工具, 解决Toast延迟弹出问题.
 * 
 * @since 2013-10-25
 * @author jinsheng_cai
 */
public class ToastUtils {

	private static Handler handler = new Handler(Looper.getMainLooper());

	private static Toast toast = null;

	public static void showMessage(final Context context, final String msg) {
		showMessage(context, msg, Toast.LENGTH_SHORT);
	}

	public static void showMessage(final Context context, final String msg,
			final int len) {
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						synchronized (this) {
							if (toast != null) {
								DebugTool.info("toast != null");
								//toast.cancel();//TODO:第二次弹出会有问题
								toast.setText(msg);
								toast.setDuration(len);
								toast.setGravity(Gravity.CENTER, 0, 0);
							} else {
								DebugTool.info("toast != null");
								toast = Toast.makeText(context, msg, len);
								toast.setGravity(Gravity.CENTER, 0, 0);
							}
							toast.show();
						}
					}
				});
			}
		}).start();
	}

	public static void showMessage(final Context context, final int msg) {
		showMessage(context, msg, Toast.LENGTH_SHORT);
	}

	public static void showMessage(final Context context, final int msg,
			final int len) {
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						synchronized (this) {
							if (toast != null) {
								//toast.cancel();
								toast.setText(msg);
								toast.setDuration(len);
								toast.setGravity(Gravity.CENTER, 0, 0);
							} else {
								toast = Toast.makeText(context, msg, len);
								toast.setGravity(Gravity.CENTER, 0, 0);
							}
							toast.show();
						}
					}
				});
			}
		}).start();
	}

}
