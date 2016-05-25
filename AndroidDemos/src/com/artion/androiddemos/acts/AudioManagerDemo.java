package com.artion.androiddemos.acts;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AudioManagerDemo extends CommonBtnDemo {
	
	private AudioManager am = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		am = (AudioManager) getSystemService(AUDIO_SERVICE);
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		
		btn1.setText("关闭麦克风");
		btn2.setText("打开麦克风");
		
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				am.setMicrophoneMute(true);
			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				am.setMicrophoneMute(false);
			}
		});
	}

}
