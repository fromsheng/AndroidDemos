package com.artion.androiddemos.acts;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.EditText;
import android.widget.ScrollView;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.R.id;
import com.artion.androiddemos.R.layout;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.utils.DeviceTool;
import com.artion.androiddemos.view.ResizeLayout;
import com.artion.androiddemos.view.ResizeLayout.OnResizeListener;

public class EditTextDemo extends BaseActivity {
	private ScrollView scroll;
	private EditText edit;
	private static final int BIGGER = 1; 
	private static final int SMALLER = 2; 
	private static final int MSG_RESIZE = 1; 

	private static final int HEIGHT_THREADHOLD = 30;
	private InputHandler mHandler = new InputHandler(); 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_edittext);

		initLayout();
		initListener();
	}

	@Override
	protected void initLayout() {
		super.initLayout();
		edit = (EditText) findViewById(R.id.edit);
		edit.setHint(DeviceTool.getSpannedStrWithTextSize("123456", 8));
		scroll = (ScrollView) findViewById(R.id.scrollview);
		((ResizeLayout) findViewById(R.id.resize_layout)).setOnResizeListener(new OnResizeListener() {

			@Override
			public void OnResize(int w, int h, int oldw, int oldh) {
				int change = BIGGER; 
				if (h < oldh) { 
					change = SMALLER; 
				} 

				Message msg = new Message(); 
				msg.what = 1; 
				msg.arg1 = change; 
				mHandler.sendMessage(msg); 
			}
		});
	}

	@Override
	protected void initListener() {
		super.initListener();
//		listenerSoftInput();
		
		DeviceTool.setSoftInputListener(scroll);
		
		findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ToastUtils.showMessage(mAct, DeviceTool.getEditInputTextWithoutSeparator(edit));
			}
		});
		
		edit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				DeviceTool.setEditInputPhoneSeparator(edit);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	}
	
	private void listenerSoftInput() {
		final View activityRootView = findViewById(R.id.scrollview);
		activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						int heightDiff = activityRootView.getRootView()
								.getHeight() - activityRootView.getHeight();
						if (heightDiff > 100) { // 如果高度差超过100像素，就很有可能是有软键盘...
							scrollToBottom();
						} else {
						}
					}
				});
	}

		//scrollview滑到底部

		protected void scrollToBottom() {
			mHandler.postDelayed(new Runnable() {
		@Override
		public void run() {
			scroll.fullScroll(View.FOCUS_DOWN);
		}
		}, 100);
		}

	class InputHandler extends Handler { 
		@Override 
		public void handleMessage(Message msg) { 
			switch (msg.what) { 
			case MSG_RESIZE: { 
//				if (msg.arg1 == BIGGER) { 
//					scroll.scrollTo(0, 300);
//				} else { 
//					scroll.scrollTo(0, 0);
//				} 
			} 
			break; 

			default: 
				break; 
			} 
			super.handleMessage(msg); 
		} 
	} 
}
