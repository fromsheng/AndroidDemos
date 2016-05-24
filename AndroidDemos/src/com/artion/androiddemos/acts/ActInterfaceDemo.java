package com.artion.androiddemos.acts;

import com.artion.androiddemos.impl.ActInterface;
import com.artion.androiddemos.utils.DebugTool;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ActInterfaceDemo extends CommonBtnDemo {
	
	private ActInterface actInterface = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		actInterface = (ActInterface) getIntent().getSerializableExtra("ActInterface");
		if(actInterface != null) {
			DebugTool.info("ActInterface", "传后== " + actInterface.toString());
		}
	}

	@Override
	protected void initListener() {
		super.initListener();
		
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(actInterface != null) {
					actInterface.onClick("按钮111111");
				}
			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(actInterface != null) {
					actInterface.onClick("按钮222222");
				}
			}
		});
		btn3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(actInterface != null) {
					actInterface.onClick("按钮333333");
				}
			}
		});
	}
	
	

}
