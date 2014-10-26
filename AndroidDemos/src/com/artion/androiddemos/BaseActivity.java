package com.artion.androiddemos;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	
	protected String tag = "Base";
	
	protected Activity mAct = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.mAct = this;
		tag = this.getClass().getSimpleName();
		
		BaseApplication.getInstance().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		BaseApplication.getInstance().removeActivity(this);
	}
	
	protected void initLayout() {}
	
	protected void initListener() {}
	
}
