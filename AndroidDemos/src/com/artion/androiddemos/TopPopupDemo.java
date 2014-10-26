package com.artion.androiddemos;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.artion.androiddemos.utils.ToastUtils;
import com.artion.androiddemos.view.BasePopupWindowManager;
import com.artion.androiddemos.view.BasePopupWindowManager.LifecycleListener;
import com.artion.androiddemos.view.BasePopupWindowManager.POSITION;
import com.artion.androiddemos.view.TopPopupWindowManager;

public class TopPopupDemo extends BaseActivity {

	private Button btn;
	
	private BasePopupWindowManager pwm = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_button);
		initLayout();
		initListener();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void initLayout() {
		super.initLayout();
		
		btn = (Button) findViewById(R.id.button1);
	}

	@Override
	protected void initListener() {
		super.initListener();
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(pwm == null) {
					pwm = new TopPopupWindowManager(mAct);
					
					pwm.setLifecycleListener(new LifecycleListener() {
						
						@Override
						public void onRemoved() {
							ToastUtils.showMessage(mAct, "onRemoved");
						}
						
						@Override
						public void onDisplayed() {
							ToastUtils.showMessage(mAct, "onDisplayed");
						}
					});
				}
				
//				pwm.setOutsideTouchable(false);
//				pwm.showPopupWindow(findViewById(R.id.layout));
//				pwm.showPopupWindowAsParentTop(btn);
				pwm.showPopupWindowByPos(btn, POSITION.DOWN);
//				pwm.showPopupWindowByPos(findViewById(R.id.layout), POSITION.NORMAL);
			}
		});
	}

}
