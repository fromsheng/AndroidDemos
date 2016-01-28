package com.artion.androiddemos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.artion.androiddemos.utils.DebugTool;

public class AnimatorDemo extends CommonBtnDemo {

	@Override
	protected void initLayout() {
		super.initLayout();
	}

	@Override
	protected void initListener() {
		super.initListener();
		
		btn1.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				ObjectAnimator animator1 = ObjectAnimator.ofFloat(btn1, "translationX", 0F, 360F);//X轴平移旋转
		        ObjectAnimator animator2 = ObjectAnimator.ofFloat(btn1, "translationY", 0F, 360F);//Y轴平移旋转
		        ObjectAnimator animator3 = ObjectAnimator.ofFloat(btn1, "rotation", 0F, 360F);//360度旋转
		        ObjectAnimator animator4 = ObjectAnimator.ofFloat(btn1, "alpha", 0F, 1F);//360度旋转
		        ObjectAnimator animator5 = ObjectAnimator.ofFloat(btn1, "rotationX", 0F, 360F);//360度旋转
		        ObjectAnimator animator6 = ObjectAnimator.ofFloat(btn1, "rotationY", 0F, 360F);//360度旋转
		        AnimatorSet set = new AnimatorSet();
		        set.setDuration(1000);
		        //set.playSequentially(animator1, animator2, animator3, animator4, animator5, animator6);//分步执行
		        set.playTogether(animator1, animator2, animator3, animator4, animator5, animator6);//同步执行
		        set.start();
		        
//				ObjectAnimator oa = ObjectAnimator.ofFloat(btn3, "rotation", 0f, 360f);
//				oa.setDuration(3000);
//				oa.setInterpolator(new AccelerateDecelerateInterpolator());
//				oa.addUpdateListener(new AnimatorUpdateListener() {
//					
//					@Override
//					public void onAnimationUpdate(ValueAnimator animation) {
//						DebugTool.info(tag, "onAnimationUpdate == " + animation.getAnimatedValue().toString());
//						
//					}
//				});
//				
//				oa.addListener(new AnimatorListenerAdapter() {
//
//					@Override
//					public void onAnimationEnd(Animator animation) {
//						super.onAnimationEnd(animation);
//						DebugTool.info(tag, "onAnimationEnd == ");
//					}
//					
//				});
//				oa.start();				
			}
		});
	}

}
