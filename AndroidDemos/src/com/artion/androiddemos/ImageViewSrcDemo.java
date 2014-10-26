package com.artion.androiddemos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.artion.androiddemos.utils.DebugTool;

import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewSrcDemo extends BaseActivity {

	private ImageView iv;
	
	// 匹配网址
		public final static Pattern URL_PATTERN = Pattern
				.compile("http://([\\w-]+\\.)+[\\w-]+(/[\\w-\\./?%&=]*)?");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_imageview_src);
		
		initLayout();
		initListener();
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		iv = (ImageView) findViewById(R.id.iv);
		
		iv.setImageResource(R.drawable.customer_introduction1);
		
		String url = "http://www.baidu.com百度";
		Matcher urlMatcher = URL_PATTERN.matcher(url);
		while (urlMatcher.find()) {
			DebugTool.info(tag, "找到地址:" + urlMatcher.group(0));
		}
		
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if(iv != null) {
			DebugTool.info(tag, "iv != null");
			if(iv.getBackground() != null) {
				DebugTool.info(tag, "iv.getBackground() != null");
				iv.getBackground().setCallback(null);
			}else {
				DebugTool.info(tag, "iv.getBackground() == null");
			}
			
			if(iv.getDrawable() != null) {
				DebugTool.info(tag, "iv.getDrawable() != null");
//				iv.setBackgroundDrawable(null);
			}else {
				DebugTool.info(tag, "iv.getDrawable() == null");
			}
		}else {
			DebugTool.info(tag, "iv == null");
		}
	}

}
