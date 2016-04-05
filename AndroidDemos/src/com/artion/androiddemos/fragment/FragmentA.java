package com.artion.androiddemos.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.artion.androiddemos.R;
import com.artion.androiddemos.acts.ActJumpB;
import com.artion.androiddemos.utils.DebugTool;

public class FragmentA extends BaseFragment {
	protected Button btn1, btn2, btn3;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.act_button, container, false);
		
		initLayout(view);
		initListener();
		return view;
	}
	
	protected void initLayout(View view) {
		
		btn1 = (Button) view.findViewById(R.id.button1);
		btn2 = (Button) view.findViewById(R.id.button2);
		btn3 = (Button) view.findViewById(R.id.button3);
	}
	
	protected void initListener() {
		btn1.setText("Fragment启动");
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mAct, ActJumpB.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, 11);
			}
		});
		
		btn2.setText("view启动");
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mAct, ActJumpB.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				mAct.startActivityForResult(intent, 11);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		DebugTool.info("ActJump", "FragmentA onActivityResult " + requestCode + " -- " + resultCode);
	}

}
