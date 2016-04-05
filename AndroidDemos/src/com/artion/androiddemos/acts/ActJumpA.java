package com.artion.androiddemos.acts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.artion.androiddemos.R;
import com.artion.androiddemos.fragment.BaseFragment;
import com.artion.androiddemos.fragment.FragmentA;
import com.artion.androiddemos.utils.DebugTool;

public class ActJumpA extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DebugTool.info("ActJump", "ActJumpA onCreate ");
		setContentView(R.layout.act_fragment);
		
		BaseFragment fm = new FragmentA();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.fragment_container, fm);
		ft.commitAllowingStateLoss();
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		DebugTool.info("ActJump", "ActJumpA onDestroy ");
	}

	@Override
	protected void onPause() {
		super.onPause();
		DebugTool.info("ActJump", "ActJumpA onPause ");
	}

	@Override
	protected void onResume() {
		super.onResume();
		DebugTool.info("ActJump", "ActJumpA onResume ");
	}

	@Override
	protected void onStop() {
		super.onStop();
		DebugTool.info("ActJump", "ActJumpA onStop ");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		DebugTool.info("ActJump", "ActJump onActivityResult " + requestCode + " -- " + resultCode);
	}

}
