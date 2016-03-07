package com.artion.androiddemos.acts;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.R.anim;
import com.artion.androiddemos.R.id;
import com.artion.androiddemos.R.layout;
import com.artion.androiddemos.common.TimerUtils;
import com.artion.androiddemos.common.TimerUtils.TimerListener;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.common.ViewUtils;
import com.artion.androiddemos.common.ViewUtils.OnViewClickListener;
import com.artion.androiddemos.view.Rotate3dAnimation;

public class AnimationDemo extends BaseActivity {
	
	private Button btn1, btn2, btn3;
	
	private TimerUtils timerUtils;
	
	private Animation animation = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		timerUtils = new TimerUtils();
		
		animation = AnimationUtils.loadAnimation(mAct, R.anim.move_anim);
		
		setContentView(R.layout.act_button);
		
		initLayout();
		initListener();
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
				// TODO Auto-generated method stub
				ViewUtils.onViewClickTimes(v, 300, new OnViewClickListener() {
					
					@Override
					public void onClicked(View view, int clickTimes) {
						ToastUtils.showMessage(mAct, "clickTimes == " + clickTimes);
					}

					@Override
					public void onClicking(View view, int clickTimes) {
						if(view instanceof TextView) {
							((TextView) view).setText("Click=" + clickTimes);
						}
					}
				});
			}
		});
		
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				DeviceTool.shockView(btn1);
				btn2.clearAnimation();
				btn2.startAnimation(animation);
				
				timerUtils.startTimer(3000, new TimerListener() {
					
					@Override
					public void timeOnTick(long seconds) {
					}
					
					@Override
					public void timeOnFinish() {
						ViewUtils.shockView(btn1);
					}
				}, 5);
		        
			}
		});
		
		btn3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btn1.clearAnimation();
				ViewUtils.shockView(btn1);
				
//				Animation an = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);  
//		        an.setInterpolator(new LinearInterpolator());//不停顿  
//		        an.setRepeatCount(1);//重复次数  
//		        an.setFillAfter(true);//停在最后  
//		        an.setDuration(2000);  
//		        an.setAnimationListener(new AnimationListener() {  
//		            @Override  
//		            public void onAnimationStart(Animation animation) {  
//		                ToastUtils.showMessage(mAct, "开始了");
//		            }  
//		            @Override  
//		            public void onAnimationRepeat(Animation animation) {  
//		                ToastUtils.showMessage(mAct, "重复了");
//		            }  
//		            @Override  
//		            public void onAnimationEnd(Animation animation) {  
//		                ToastUtils.showMessage(mAct, "结束了");
//		            }  
//		        });  
//		        //动画开始  
//		        btn3.startAnimation(an);  
				
				
				Rotate3dAnimation ra = new Rotate3dAnimation(0, 360, btn3.getWidth()/2, 0, 0, true);
//				ra.setInterpolator(new AccelerateInterpolator());//不停顿  
				ra.setInterpolator(new AccelerateDecelerateInterpolator());//不停顿  
		        ra.setRepeatCount(1);//重复次数  
		        ra.setFillAfter(true);//停在最后  
		        ra.setDuration(2000); 
		        ra.setAnimationListener(new AnimationListener() {  
		            @Override  
		            public void onAnimationStart(Animation animation) {  
		                ToastUtils.showMessage(mAct, "开始了");
		            }  
		            @Override  
		            public void onAnimationRepeat(Animation animation) {  
		                ToastUtils.showMessage(mAct, "重复了");
		            }  
		            @Override  
		            public void onAnimationEnd(Animation animation) {  
		                ToastUtils.showMessage(mAct, "结束了");
		            }  
		        });  
		        //动画开始  
		        btn3.startAnimation(ra);  
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(timerUtils != null) {
			timerUtils.cancelTimer();
		}
	}
	
	

}
