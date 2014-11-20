package com.artion.androiddemos;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.artion.androiddemos.utils.DeviceTool;
import com.artion.androiddemos.utils.TimerUtils;
import com.artion.androiddemos.utils.DeviceTool.OnViewClickListener;
import com.artion.androiddemos.utils.TimerUtils.TimerListener;
import com.artion.androiddemos.utils.ToastUtils;

public class AnimationDemo extends BaseActivity {
	
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
				DeviceTool.onViewClickTimes(v, 300, new OnViewClickListener() {
					
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
				
				timerUtils.startTimer(3000, new TimerListener() {
					
					@Override
					public void timeOnTick(long seconds) {
					}
					
					@Override
					public void timeOnFinish() {
						DeviceTool.shockView(btn1);
					}
				}, 5);
		        
			}
		});
		
		btn3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btn1.clearAnimation();
				DeviceTool.shockView(btn1);
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
