package com.artion.androiddemos;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.artion.androiddemos.utils.LoadingDialog;
import com.artion.androiddemos.utils.LoadingDialog.ProgressListener;
import com.artion.androiddemos.utils.ToastUtils;

public class LoadingDialogDemo extends BaseActivity {
	
	private Button btn1, btn2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_button);
		
		initLayout();
		initListener();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		
		findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LoadingDialog.getInstance().showLoading(mAct, "没有取消事件", true, false);
			}
		});
		
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LoadingDialog.getInstance().showLoading(mAct, "有取消事件", false, new ProgressListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						ToastUtils.showMessage(mAct, "你取消了哦");
					}
				});
			}
		});
		
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LoadingDialog.getInstance().showLoading(mAct, "有取消事件", true, new ProgressListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						ToastUtils.showMessage(mAct, "你取消了哦333");
					}
				});
			}
		});
	}

}
