package com.artion.androiddemos.acts;

import java.util.Set;

import com.artion.androiddemos.R;
import com.artion.androiddemos.R.id;
import com.artion.androiddemos.R.layout;
import com.artion.androiddemos.utils.DebugTool;
import com.artion.androiddemos.view.SingleImageView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SingleImageViewDemo extends Activity implements OnClickListener {

	private SingleImageView singleImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.single_imageview);
		
		initDatas(getIntent());
		
		singleImageView = (SingleImageView) findViewById(R.id.single_imageview);
		
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button3).setOnClickListener(this);
		findViewById(R.id.button4).setOnClickListener(this);
		findViewById(R.id.button5).setOnClickListener(this);
		findViewById(R.id.button6).setOnClickListener(this);
	}

	private void initDatas(Intent intent) {
		if(intent == null || intent.getExtras() == null) {
			DebugTool.info("Key", "intent数据为空");
			return;
		}
		
		Set<String> keys = intent.getExtras().keySet();
		for(String key : keys) {
			DebugTool.info("Key", "key == " + key);
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			singleImageView.showPicOnly();
			break;
		case R.id.button2:
			singleImageView.showGif();
			break;
		case R.id.button3:
			singleImageView.showVideo("500kb");
			break;
		case R.id.button4:
			singleImageView.setLayoutParam(100, 100);
			break;
		case R.id.button5:
			singleImageView.setLayoutParam(200, 200);
			break;
		case R.id.button6:
			singleImageView.setLayoutParam(400, 400);
			break;
		default:
			break;
		}
	}

}
