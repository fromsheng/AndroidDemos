package com.artion.androiddemos;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.view.MultiImageView;
import com.artion.androiddemos.view.MultiImageView.ItemClickLister;

public class MultiImageViewDemo extends Activity implements OnClickListener {
	
	private MultiImageView mMultiImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_multi_image_view);
		
		mMultiImageView = (MultiImageView) findViewById(R.id.multi_imageview);
		
		mMultiImageView.setOnItemClickListener(new ItemClickLister() {
			
			@Override
			public void onItemClickLister(int position) {
				ToastUtils.showMessage(MultiImageViewDemo.this, "点击了Item " + position);
			}
		});
		
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button3).setOnClickListener(this);
		findViewById(R.id.button4).setOnClickListener(this);
		findViewById(R.id.button5).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
//			mMultiImageView.setSingleImage();
			ArrayList<String> l1 = getDefaultList(1);
			mMultiImageView.loadMultiImages(l1, false, "12kb");
			break;
		case R.id.button2:
//			mMultiImageView.setSingleImageSize(300, 200);
			ArrayList<String> ll1 = getDefaultList(1);
			mMultiImageView.loadMultiImages(ll1, false, "12kb");
			break;
		case R.id.button3:
//			mMultiImageView.setMultiImageSize(3, 150, 150);
			ArrayList<String> l3 = getDefaultList(3);
			mMultiImageView.loadMultiImages(l3, true, "12kb");
			break;
		case R.id.button4:
//			mMultiImageView.setMultiImageSize(5, 100, 100);
			ArrayList<String> l5 = getDefaultList(5);
			mMultiImageView.loadMultiImages(l5, false, "12kb", 100, 100);
			break;
		case R.id.button5:
//			mMultiImageView.setMultiImageSize(9);
			ArrayList<String> l9 = getDefaultList(9);
			mMultiImageView.loadMultiImages(l9, true, "12kb");
			break;


		default:
			break;
		}
	}
	
	private ArrayList<String> getDefaultList(int size) {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			list.add("item " + i);
		}
		
		return list;
	}

}
