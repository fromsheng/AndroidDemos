package com.artion.androiddemos.acts;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.R.id;
import com.artion.androiddemos.R.layout;
import com.artion.androiddemos.common.TimerUtils;

public class MoveViewDemo extends BaseActivity {

	private Button btn1, btn2, btn3;
	private TimerUtils timerUtils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		timerUtils = new TimerUtils();
		setContentView(R.layout.act_button);
		
		initLayout();
		initListener();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startMoveView(v, 500);

			}
		});

		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startMoveView(v, 1000);

			}
		});

		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startMoveView(v, 2000);

			}
		});
	}
	
	private void startMoveView(final View view, int duration) {
		
		AnimationSet animationSet = new AnimationSet(false);
		TranslateAnimation tran = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 3.0f,
				Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 3.0f);
		tran.setDuration(1000);
		tran.setFillAfter(true);
		TranslateAnimation tas[] = new TranslateAnimation[10];
		for(int i = 0; i < 10; i ++) {
			float x,y;
			y = i*10;
			x = (float) Math.sqrt(y);
			float fromX = view.getLeft() + x;
			float fromY = view.getTop() + y;
			float toX = fromX + x;
			float toY = fromY + y;
			tas[i] = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 1.0f,
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 1.0f);
			final TranslateAnimation curTa = tas[i];
			final int curIndex = i;
			tas[i].setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					if(curIndex < 9) {
						view.startAnimation(curTa);
					}
				}
			});
			tas[i].setDuration(100);
			tas[i].setFillAfter(true);
			animationSet.addAnimation(tas[i]);
		}
		view.startAnimation(tas[0]);
		
		
//		timerUtils.startTimer(3000, new TimerListener() {
//			
//			@Override
//			public void timeOnTick(long seconds) {
//				final int l=view.getLeft(), t=view.getTop(), r=view.getRight(), b=view.getBottom();
//				final double x, y;
//					y = seconds;
//					x = Math.sqrt(y);
//					view.post(new Runnable() {
//						
//						@Override
//						public void run() {
//							view.layout((int)(l+x), (int)(t+y), (int)(r+x), (int)(b+y));
//						}
//					});
//			}
//			
//			@Override
//			public void timeOnFinish() {
//			}
//		});
//		int l=view.getLeft(), t=view.getTop(), r=view.getRight(), b=view.getBottom();
//		double x, y;
//		for(int i = 0; i < duration; i ++) {
//			y = i;
//			x = Math.sqrt(y);
//			SystemClock.sleep(10);
//			view.layout((int)(l+x), (int)(t+y), (int)(r+x), (int)(b+y));
//		}
	}

}
