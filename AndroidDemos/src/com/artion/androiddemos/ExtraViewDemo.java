package com.artion.androiddemos;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.artion.androiddemos.common.ExtraViewUtils;
import com.artion.androiddemos.common.ExtraViewUtils.ExtraViewListener;
import com.artion.androiddemos.common.ToastUtils;

public class ExtraViewDemo extends BaseActivity {
	
	private Button btn1, btn2, btn3;
	private View extraView = null;
	private View layoutView = null;
	private TextView layoutText = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_button);
		
		initLayout();
		initListener();
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		
		layoutView = LayoutInflater.from(mAct).inflate(R.layout.fag_extra_view, null);
		layoutText = (TextView) layoutView.findViewById(R.id.extra_view_text);
		layoutText.setText("请稍候.");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(ExtraViewUtils.isNeedHandleKeyBack(extraView, keyCode)) {
			return false;
		}
		return super.onKeyDown(keyCode, event); 
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ExtraViewUtils.showExtraView(extraView);
			}
		});
		
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ExtraViewUtils.dismissExtraView(extraView);
			}
		});
		
		btn3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				extraView = ExtraViewUtils.initExtraView(mAct, extraView, layoutView, new ExtraViewListener() {

					@Override
					public void onDisplay(View v) {
					}

					@Override
					public void onDismiss(View v) {
					}
				}, ExtraViewUtils.EXTRA_VIEW_CANCEL_LEVEL_BACKABLE, true);
				
				layoutText.setText(layoutText.getText().toString() + ".");
			}
		});
	}

}
