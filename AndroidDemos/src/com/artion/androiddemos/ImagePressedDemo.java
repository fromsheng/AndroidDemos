package com.artion.androiddemos;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;

import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.utils.DeviceTool;

public class ImagePressedDemo extends BaseActivity {
	
	private ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_image_pressed);
		
		initLayout();
		initListener();
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		image = (ImageView) findViewById(R.id.image_pressed_image);
		
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ToastUtils.showMessage(mAct, "你点击了我哦");
			}
		});
		
		image.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				ToastUtils.showMessage(mAct, "你长按了我");
				return true;
			}
		});
		
		DeviceTool.setImageTouchDark(image);
	}

}
