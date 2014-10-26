package com.artion.androiddemos.utils;

import android.os.CountDownTimer;
import android.os.Handler;

/**
 * 倒计时类
 * @author jinsheng_cai
 * @since 2014-07-09
 */
public class TimerUtils {
	private CountDownTimer mDownTimer = null; // 倒计时
	
	private Handler mHandler = null;
	
	public TimerUtils() {
		mHandler = new Handler();
	}
	
	
	/**
	 * 需要在ui线程中启动
	 * @param delay
	 * @param mListener
	 */
	public void startTimer(final long delay, final TimerListener mListener) {
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				cancelTimer();
				
				mDownTimer = new CountDownTimer(delay, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {
						if(mListener != null) {
							mListener.timeOnTick(millisUntilFinished / 1000);
						}
					}

					@Override
					public void onFinish() {
						if(mListener != null) {
							mListener.timeOnFinish();
						}
					}
				};
				
				mDownTimer.start();
			}
		});
		
	}
	
	public void cancelTimer() {
		if(mDownTimer != null) {
			mDownTimer.cancel();
			mDownTimer = null;
		}
	}
	
	/**
	 * 倒计时监听回调
	 * @author jinsheng_cai
	 * @since 2014-07-09
	 */
	public interface TimerListener {
		/**
		 * 倒数秒级回调
		 * @param seconds
		 */
		public void timeOnTick(long seconds);
		/**
		 * 倒数完成回调
		 */
		public void timeOnFinish();
	}
}
