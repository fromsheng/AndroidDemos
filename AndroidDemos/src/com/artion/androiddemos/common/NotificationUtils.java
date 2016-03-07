package com.artion.androiddemos.common;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.artion.androiddemos.R;
import com.artion.androiddemos.acts.AnimationDemo;

/**
 * Notification相关常用操作类
 * @author jinsheng_cai
 * @since 2014-12-01
 */
public class NotificationUtils {
	
	public static void showNotification(Context context, String title, String content) {
		Bitmap btm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.message_img_new_normal);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.common_img_toolbarnew_normal)
				.setContentTitle(title)
				.setContentText(content);
		mBuilder.setTicker("微信红包");//第一次提示消息的时候显示在通知栏上
		mBuilder.setNumber(12);
		mBuilder.setLargeIcon(btm);
		mBuilder.setAutoCancel(true);//自己维护通知的消失

		//构建一个Intent
		Intent resultIntent = new Intent(context,
				AnimationDemo.class);
		//封装一个Intent
		PendingIntent resultPendingIntent = PendingIntent.getActivity(
				context, 0, resultIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// 设置通知主题的意图
		mBuilder.setContentIntent(resultPendingIntent);
		//获取通知管理器对象
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());
	}

}
