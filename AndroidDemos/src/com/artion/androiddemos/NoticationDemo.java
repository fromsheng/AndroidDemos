package com.artion.androiddemos;

import com.artion.androiddemos.common.NotificationUtils;

import android.view.View;
import android.view.View.OnClickListener;


public class NoticationDemo extends CommonBtnDemo {

	int count = 0;
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		
		btn1.setText("通知:" + count);
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NotificationUtils.showNotification(mAct, "呵呵", btn1.getText().toString());
				btn1.setText("通知:" + count++);
			}
		});
		
	}

}
