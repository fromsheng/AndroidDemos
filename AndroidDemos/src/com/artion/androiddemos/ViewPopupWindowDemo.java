package com.artion.androiddemos;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;

import com.artion.androiddemos.utils.ToastUtils;
import com.artion.androiddemos.view.BasePopupWindowManager;
import com.artion.androiddemos.view.BasePopupWindowManager.POSITION;
import com.artion.androiddemos.view.MenuPopupWindowManager;
import com.artion.androiddemos.view.PopupWindowManager;
import com.artion.androiddemos.view.PopupWindowManager.OnPopItemClickListener;

public class ViewPopupWindowDemo extends BaseActivity {

	private Button btn;
	
	private List<String> datas;
	
	private PopupWindowManager pwm = null;
	
	private void initDatas() {
		datas = new ArrayList<String>();
		
		datas.add("hellohehellohellollo");
//		datas.add("android");
//		datas.add("world");
//		datas.add("!!!!");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_button);
		initDatas();
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
		
		btn = (Button) findViewById(R.id.button1);
		
	}
	

	private MenuPopupWindowManager mpwm = null;
	
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				initPop(mAct, btn, 200, 100);
				datas.add("world");
				
				if(mpwm == null) {
					mpwm = new MenuPopupWindowManager(mAct);
				}
				
				mpwm.initListData(datas, new OnPopItemClickListener() {
//					
					@Override
					public void onItemCllick(AdapterView<?> parent, View view, int position,
							long arg3) {
						ToastUtils.showMessage(mAct, datas.get(position));
					}

					@Override
					public void OnDismiss() {
						ToastUtils.showMessage(mAct, "OnDismiss");
						
						btn.setText("打开");
					}

					@Override
					public void onShowing() {
						ToastUtils.showMessage(mAct, "onShowing");
						btn.setText("关闭");
					}
				});
				
				mpwm.showPopupWindowByPos(btn, POSITION.UP);
				
				
				
//				if(pwm == null) {
//					pwm = new PopupWindowManager(mAct, btn);
//				}
//				
//				pwm.initListData(datas, new OnPopItemClickListener() {
//					
//					@Override
//					public void onItemCllick(AdapterView<?> parent, View view, int position,
//							long arg3) {
//						ToastUtils.showMessage(mAct, datas.get(position));
//					}
//
//					@Override
//					public void OnDismiss() {
//						ToastUtils.showMessage(mAct, "OnDismiss");
//						
//						btn.setText("打开");
//					}
//
//					@Override
//					public void onShowing() {
//						ToastUtils.showMessage(mAct, "onShowing");
//						btn.setText("关闭");
//					}
//				});
////				pwm.showPopupWindow();
//				pwm.setOutsideTouchable(false);
//				pwm.showTopPopupWindow(findViewById(R.id.layout));
			}
		});
	}

}
