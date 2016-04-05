package com.artion.androiddemos.acts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.artion.androiddemos.utils.ActivityIntentTools;
import com.artion.androiddemos.utils.DebugTool;

public class ActJumpB extends CommonBtnDemo {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DebugTool.info("ActJump", "ActJumpB onCreate ");
//		setContentView(R.layout.act_fragment);
		
//		BaseFragment fm = new FragmentA();
//		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//		ft.replace(R.id.fragment_container, fm);
//		ft.commitAllowingStateLoss();
		
		btn1.setText("再跳一次");
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ActivityIntentTools.gotoActivityNotFinish(mAct, ActJumpB.class);
			}
		});
		
		btn2.setText("回A");
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mAct, ActJumpA.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		DebugTool.info("ActJump", "ActJumpB onDestroy ");
	}

	@Override
	protected void onPause() {
		super.onPause();
		DebugTool.info("ActJump", "ActJumpB onPause ");
	}

	@Override
	protected void onResume() {
		super.onResume();
		DebugTool.info("ActJump", "ActJumpB onResume ");
	}

	@Override
	protected void onStop() {
		super.onStop();
		DebugTool.info("ActJump", "ActJumpB onStop ");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		DebugTool.info("ActJump", "ActJumpB onActivityResult " + requestCode + " -- " + resultCode);
	}

}
