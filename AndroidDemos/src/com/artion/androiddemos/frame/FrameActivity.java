package com.artion.androiddemos.frame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public abstract class FrameActivity extends Activity {
	
	protected String tag = null;
	protected Activity mAct = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.mAct = this;
		tag = this.getClass().getSimpleName();
		
		setContentView(getLayoutRid());
		
		initIntentDatas(getIntent());
		
		initLayout();
		initListener();
		
	}
	
	public abstract int getLayoutRid();
	
	protected void initIntentDatas(Intent intent) {
		if(intent == null) {
			this.finish();
			return;
		}
	}
	
	public abstract void initLayout();
	
	public abstract void initListener();

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

}
