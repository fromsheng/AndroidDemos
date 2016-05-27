package com.artion.androiddemos.acts;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.utils.DeviceTool;

public class ClipboardDemo extends CommonBtnDemo {
	ClipboardManager cb = null;
	
	@SuppressLint("NewApi")
	@Override
	protected void initListener() {
		super.initListener();
		
		cb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		cb.setPrimaryClip(ClipData.newPlainText("", ""));
		cb.addPrimaryClipChangedListener(new OnPrimaryClipChangedListener() {
			
			@Override
			public void onPrimaryClipChanged() {
				// 具体实现
				ToastUtils.showMessage(mAct, "onPrimaryClipChanged");
			}
		});

		btn1.setText("copy");
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DeviceTool.copyToClipboard(mAct, "btn1", "111111111");
			}
		});
		btn2.setText("get");
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 //无数据时直接返回
		         if(!cb.hasPrimaryClip()){
		        	 ToastUtils.showMessage(mAct, "剪贴板中无数据");
		             return ;
		          } 
		         
		       //如果是文本信息
		         ClipData cdText = cb.getPrimaryClip();
		         Item item = cdText.getItemAt(0);
		         if (cb.getPrimaryClipDescription().hasMimeType(
		                  ClipDescription.MIMETYPE_TEXT_PLAIN)) {
		              //此处是TEXT文本信息
		             if(item.getText() == null){
		            	 ToastUtils.showMessage(mAct, "剪贴板中无内容");
		                  return ;
		              }else{
		            	  ToastUtils.showMessage(mAct, item.getText().toString());
		              }
		  
		          //如果是INTENT
		          } else if(cb.getPrimaryClipDescription().hasMimeType(
		                  ClipDescription.MIMETYPE_TEXT_INTENT)) {
		        	  Intent intent = item.getIntent();
		              startActivity(intent);
		          }
			}
		});
		btn3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mAct, AnimationDemo.class);
				DeviceTool.copyToClipboard(mAct, intent);
			}
		});
	}
}
