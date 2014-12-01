package com.artion.androiddemos;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.common.ViewUtils;
import com.artion.androiddemos.common.ViewUtils.OnViewClickListener;
import com.artion.androiddemos.dialog.KdAlertBuilder;
import com.artion.androiddemos.dialog.KdAlertBuilder.KdAlertItemListener;
import com.artion.androiddemos.highlight.HighLightTextViewDemo;
import com.artion.androiddemos.utils.ActivityIntentTools;
import com.artion.androiddemos.utils.DebugTool;

public class MainActivity extends BaseActivity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Uri uri = Uri.parse("cloudhub://status?id=123456");
		ToastUtils.showMessage(this, uri.getQueryParameter("id"));
		
		findViewById(R.id.textView1).setOnClickListener(this);
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button3).setOnClickListener(this);
		findViewById(R.id.button4).setOnClickListener(this);
		findViewById(R.id.button5).setOnClickListener(this);
		findViewById(R.id.button6).setOnClickListener(this);
		findViewById(R.id.button7).setOnClickListener(this);
		findViewById(R.id.button8).setOnClickListener(this);
		findViewById(R.id.button9).setOnClickListener(this);
		findViewById(R.id.button10).setOnClickListener(this);
		findViewById(R.id.button11).setOnClickListener(this);
		findViewById(R.id.button12).setOnClickListener(this);
		findViewById(R.id.button13).setOnClickListener(this);
		findViewById(R.id.button14).setOnClickListener(this);
		
		int statusBarHeight = ActivityIntentTools.getWindowStatusBarHeight(MainActivity.this);
		int titleBarHeight = ActivityIntentTools.getTitleBarHeight(MainActivity.this);
		
		DebugTool.info("height", "statusBarHeight1 = " + statusBarHeight);
		DebugTool.info("height", "titleBarHeight1 = " + titleBarHeight);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int statusBarHeight = ActivityIntentTools.getWindowStatusBarHeight(MainActivity.this);
				int titleBarHeight = ActivityIntentTools.getTitleBarHeight(MainActivity.this);
				
				DebugTool.info("height", "statusBarHeight2 = " + statusBarHeight);
				DebugTool.info("height", "titleBarHeight2 = " + titleBarHeight);
			}
		}, 200);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		
		int statusBarHeight = ActivityIntentTools.getWindowStatusBarHeight(MainActivity.this);
		int titleBarHeight = ActivityIntentTools.getTitleBarHeight(MainActivity.this);
		
		DebugTool.info("height", "statusBarHeight3 = " + statusBarHeight);
		DebugTool.info("height", "titleBarHeight3 = " + titleBarHeight);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.textView1:
			ActivityIntentTools.gotoActivityNotFinish(MainActivity.this, Demos.class);
			break;
		case R.id.button1:
			ActivityIntentTools.gotoActivityNotFinish(MainActivity.this, ListDemo.class);
			break;
			
		case R.id.button2:
			ActivityIntentTools.gotoActivityNotFinish(MainActivity.this, SingleImageViewDemo.class);
			break;
			
		case R.id.button3:
			ActivityIntentTools.gotoActivityNotFinish(MainActivity.this, MultiImageViewDemo.class);
			break;
			
		case R.id.button4:
			ActivityIntentTools.gotoActivityNotFinish(MainActivity.this, ListDownDemo.class);
			break;
			
		case R.id.button5:
			List<String> items = new ArrayList<String>();
			items.add("111");
			items.add("222");
			items.add("333");
			AlertDialog mAlertDialog1 = new AlertDialog.Builder(MainActivity.this).create();
			AlertDialog mAlertDialog = new KdAlertBuilder(MainActivity.this).setDialogParams(mAlertDialog1).initItemDatas(items).initItemListener(new KdAlertItemListener() {
				
				@Override
				public void onItemClick(int position) {
					// TODO Auto-generated method stub
					ToastUtils.showMessage(MainActivity.this, "点击位置 " + position);
				}
			}).create();
			
//			Window window = mAlertDialog.getWindow();
////			window.setWindowAnimations(R.style.dialog_bottom);
//			window.setGravity(Gravity.BOTTOM);
//			
//			WindowManager wm = window.getWindowManager();
//			WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
//			// p.height = (int) (d.getHeight()); // 高度设置为屏幕的0.6
//			p.width = (int) (wm.getDefaultDisplay().getWidth()); // 宽度设置为屏幕的0.65
//			window.setAttributes(p);
			
			mAlertDialog.show();
			break;
		case R.id.button6:
			ActivityIntentTools.gotoActivityNotFinish(MainActivity.this, HighLightTextViewDemo.class);
			break;
		case R.id.button7:
			ActivityIntentTools.gotoActivityNotFinish(MainActivity.this, ImageViewSrcDemo.class);
			break;
		case R.id.button8:
			ActivityIntentTools.gotoActivityNotFinish(MainActivity.this, TranslateDemo.class);
			break;
		case R.id.button9:
			AlertDialog.Builder builder = new Builder(MainActivity.this);
			
			builder.setTitle("登录")
					.setMessage("这是什么东东？")
					.setPositiveButton("重试", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							ToastUtils.showMessage(MainActivity.this, "重试一下吧");
						}
					}).setNegativeButton("退出", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							ToastUtils.showMessage(MainActivity.this, "滚吧你");
							finish();
						}
					}).setCancelable(false).setOnCancelListener(new DialogInterface.OnCancelListener() {
						
						@Override
						public void onCancel(DialogInterface dialog) {
							// TODO Auto-generated method stub
							ToastUtils.showMessage(MainActivity.this, "取消了做什么");
						}
					}).create().show();
			
			break;
		case R.id.button10:
			ViewUtils.onViewClickTimes(v, 300, new OnViewClickListener() {
				
				@Override
				public void onClicked(View view, int clickTimes) {
					if(clickTimes == 5) {
						ToastUtils.showMessage(MainActivity.this, "好看哦");
					} else {
						ToastUtils.showMessage(MainActivity.this, "你点击次数为：" + clickTimes);
					}
				}

				@Override
				public void onClicking(View view, int clickTimes) {
					if(view instanceof TextView) {
						((TextView) view).setText("Click=" + clickTimes);
					}
				}
			});
			
//			if(DeviceTool.isOnViewClickCount(v, 5, 1000)) {
//				ToastUtils.showMessage(MainActivity.this, "好看哦");
//			}
			break;
		case R.id.button11:
			ActivityIntentTools.gotoActivityNotFinish(MainActivity.this, TextViewLongDemo.class);
			break;
		case R.id.button12:
			ActivityIntentTools.gotoActivityNotFinish(MainActivity.this, TextLongDemo.class);
			break;
		case R.id.button13:
			ActivityIntentTools.gotoActivityNotFinish(MainActivity.this, GridViewMenuDemo.class);
			break;
		case R.id.button14:
			ActivityIntentTools.gotoActivityNotFinish(MainActivity.this, OnGestureDemo.class);
			break;	
		default:
			break;
		}
		
	}

}
