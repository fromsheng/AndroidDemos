package com.artion.androiddemos;

import com.artion.androiddemos.utils.DebugTool;

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
		
		DebugTool.info("BaseActivity", tag + " onCreate...");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		BaseApplication.getInstance().removeActivity(this);
		DebugTool.info("BaseActivity", tag + " onCreate...");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		DebugTool.info("BaseActivity", tag + " onPause...");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		DebugTool.info("BaseActivity", tag + " onRestart...");
	}

	@Override
	protected void onResume() {
		super.onResume();
		DebugTool.info("BaseActivity", tag + " onResume...");
	}

	@Override
	protected void onStart() {
		super.onStart();
		DebugTool.info("BaseActivity", tag + " onStart...");
	}

	@Override
	protected void onStop() {
		super.onStop();
		DebugTool.info("BaseActivity", tag + " onStop...");
	}

	protected void initLayout() {}
	
	protected void initListener() {}
	
}
