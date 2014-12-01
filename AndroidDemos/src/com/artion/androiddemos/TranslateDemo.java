package com.artion.androiddemos;

import com.artion.androiddemos.common.ToastUtils;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;

public class TranslateDemo extends BaseActivity {
	
	private ImageView iv;
	private Button btn;
	private boolean isShowing = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_translate);
		
		initLayout();
		initListener();
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		btn = (Button) findViewById(R.id.trans_btn);
		iv = (ImageView) findViewById(R.id.trans_iv);
		
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		
		iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ToastUtils.showMessage(TranslateDemo.this, "好看哦去");
			}
		});
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isShowing) {
					btn.setText("hide");
					//需要显示出来
				}else{
					btn.setText("show");
					//需要隐藏起来
				}
				ajustView(isShowing);
				isShowing = !isShowing;
			}
		});
	}
	
	private void ajustView(boolean isShowing) {
		if(isShowing){
//			tv.setVisibility(View.GONE);
//			tv.startAnimation(alphaOut);
//			DebugTool.info("height", "tv.getHeight() == " + tv.getHeight());
			startTranslate(iv, 0, -iv.getHeight(), View.GONE);
//			startTranslate(tv, 0, -100, View.GONE);//如需tv也跟着走解注释
		}else{
//			tv.setVisibility(View.VISIBLE);
//			tv.startAnimation(alphaIn);
//			DebugTool.info("height", "tv.getHeight() == " + tv.getHeight());
			startTranslate(iv, 0, iv.getHeight(), View.VISIBLE);
//			startTranslate(tv, 0, 100, View.GONE);//如需tv也跟着走解注释
		}
	}

	private void startTranslate(final View view, final int fromY, final int toY, final int status) {
		TranslateAnimation animation = new TranslateAnimation(0, 0, fromY, toY);
		animation.setDuration(200);
//		animation.setFillAfter(true);//只是将view移动到了目标位置，但是view绑定的点击事件还在原来位置
		animation.setAnimationListener(new AnimationListener() {
			
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
				
				int l = view.getLeft();
				int width = view.getWidth();
				int height = view.getHeight();
				int t = view.getTop() + (toY - fromY);
				view.clearAnimation();
				
				view.layout(l, t, l + width, t + height);
				
				
			}
		});
//		view.setAnimation(animation);
		view.startAnimation(animation);
		
	}
}
