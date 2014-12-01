package com.artion.androiddemos;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.utils.DebugTool;

public class ListDownDemo extends BaseActivity {
	
	private Button btn;
	private TextView tv;
	private ListView lv;
	private LinearLayout ll;
	
	private boolean isShowing = false;
	
	private String arrays[] = {
			"A", "B", "C", "D",
			"E", "R", "G", "H",
			"I", "J", "K", "L",
			"M", "N", "O", "P"
	};
	
	private Animation pushDown, pushUp;
	private Animation alphaIn, alphaOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_list2);
		
		initLayout();
		initListener();
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		btn = (Button) findViewById(R.id.btn);
		tv = (TextView) findViewById(R.id.tv);
		lv = (ListView) findViewById(R.id.listview);
		ll = (LinearLayout) findViewById(R.id.ll_out);
		
		lv.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrays));
		
		pushDown = AnimationUtils.loadAnimation(this, R.anim.push_down);
		pushUp = AnimationUtils.loadAnimation(this, R.anim.push_up);
		alphaIn = AnimationUtils.loadAnimation(this, R.anim.alpha_in);
		alphaOut = AnimationUtils.loadAnimation(this, R.anim.alpha_out);
		
//		tv.setVisibility(View.GONE);
		ll.setVisibility(View.GONE);
		
//		startTranslate(tv, 0, -100, View.GONE);//如需tv也跟着走解注释
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		
		ll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DebugTool.info("Demo", "点击我做什么 ");
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
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ToastUtils.showMessage(ListDownDemo.this, "点击了 " + arrays[arg2]);
			}
		});
	}
	
	private void ajustView(boolean isShowing) {
		if(isShowing){
//			tv.setVisibility(View.GONE);
//			tv.startAnimation(alphaOut);
//			DebugTool.info("height", "tv.getHeight() == " + tv.getHeight());
			startTranslate(lv, 0, -100, View.GONE);
//			startTranslate(tv, 0, -100, View.GONE);//如需tv也跟着走解注释
		}else{
//			tv.setVisibility(View.VISIBLE);
//			tv.startAnimation(alphaIn);
//			DebugTool.info("height", "tv.getHeight() == " + tv.getHeight());
			startTranslate(lv, 0, 100, View.VISIBLE);
//			startTranslate(tv, 0, 100, View.GONE);//如需tv也跟着走解注释
		}
	}
	
	private void startTranslate(final View view, final int fromY, final int toY, final int status) {
		TranslateAnimation animation = new TranslateAnimation(0, 0, fromY, toY);
		animation.setDuration(500);
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
