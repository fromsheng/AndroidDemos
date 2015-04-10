package com.artion.androiddemos;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.common.ViewUtils;
import com.artion.androiddemos.common.ViewUtils.OnViewClickListener;
import com.artion.androiddemos.utils.ActivityIntentTools;

public class StartDoDemo extends BaseActivity {

	protected Button btn1, btn2, btn3;

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

		btn1.setText("Start");
		btn2.setText("Share");
		btn3.setText("Chat");
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				ViewUtils.onViewClickTimes(v, 300, new OnViewClickListener() {
					
					@Override
					public void onClicked(View view, int clickTimes) {
						String schemeUri = null;
						if(clickTimes % 3 == 0) {
							schemeUri = "cloudhub://start?count=0";
						} else if(clickTimes % 3 == 1) {
							schemeUri = "cloudhub://start?count=0&token=90c95af05833a665dd736e43e8782070|29feece36ac1e3eb9e19beee6c6c9e4&networkId=383cee68-cea3-4818-87ae-24fb46e081b1";
						} else if(clickTimes % 3 == 2) {
//							schemeUri = "cloudhub://start?token=fcc0685ade65bdfec5f5398223fa3f9|245cdfa617a38612ab9aff9b98536ed3";
							schemeUri = "cloudhub://start?token=90c95af05833a665dd736e43e8782070|29feece36ac1e3eb9e19beee6c6c9e4&networkId=383cee68-cea3-4818-87ae-24fb46e081b1";
							}
						try {
							ActivityIntentTools.gotoTargetActivityByScheme(mAct,
									schemeUri);
						} catch (Exception e) {
							ToastUtils.showMessage(mAct, "你没装这个app");
						}
					}

					@Override
					public void onClicking(View view, int clickTimes) {
						if(view instanceof TextView) {
							((TextView) view).setText("Start=" + clickTimes);
						}
					}
				});
				
//				try {
//					String startSchemeUri = "cloudhub://start?count=0&token=?";
//					ActivityIntentTools.gotoTargetActivityByScheme(mAct,
//							startSchemeUri);
//				} catch (Exception e) {
//					ToastUtils.showMessage(mAct, "你没装这个app");
//				}
			}
		});

		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ViewUtils.onViewClickTimes(v, 300, new OnViewClickListener() {
					
					@Override
					public void onClicked(View view, int clickTimes) {
						String schemeUri = null;
						if(clickTimes % 2 == 0) {
							schemeUri = "cloudhub://share?appId=10101&appName=Do&shareType=3&title=yunzhijia&content=DO&webpageUrl=(schema)";
						} else {
							schemeUri = "cloudhub://share?appId=10101&appName=Do&shareType=3&title=yunzhijia&content=DO&webpageUrl=(schema)&token=fcc0685ade65bdfec5f5398223fa3f9|245cdfa617a38612ab9aff9b98536ed3";
						}
						try {
							ActivityIntentTools.gotoTargetActivityByScheme(mAct,
									schemeUri);
						} catch (Exception e) {
							ToastUtils.showMessage(mAct, "你没装这个app");
						}
					}

					@Override
					public void onClicking(View view, int clickTimes) {
						if(view instanceof TextView) {
							((TextView) view).setText("Share=" + clickTimes);
						}
					}
				});
				
//				try {
//					String startSchemeUri = "cloudhub://share?appId=?&appName=?&shareType=3&title=?&content=?&thumbData=?&webpageUrl=(schema)&token=?";
//					ActivityIntentTools.gotoTargetActivityByScheme(mAct,
//							startSchemeUri);
//				} catch (Exception e) {
//					ToastUtils.showMessage(mAct, "你没装这个app");
//				}
			}
		});
		
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				ViewUtils.onViewClickTimes(v, 300, new OnViewClickListener() {
					
					@Override
					public void onClicked(View view, int clickTimes) {
						String schemeUri = null;
						if(clickTimes % 2 == 0) {
							schemeUri = "cloudhub://chat?userId=2f1cae89-eb8e-11e3-b562-648e9a59efa3";
						} else {
							schemeUri = "cloudhub://chat?userId=2f1cae89-eb8e-11e3-b562-648e9a59efa3&token=fcc0685ade65bdfec5f5398223fa3f9|245cdfa617a38612ab9aff9b98536ed3";
						}
						try {
							ActivityIntentTools.gotoTargetActivityByScheme(mAct,
									schemeUri);
						} catch (Exception e) {
							ToastUtils.showMessage(mAct, "你没装这个app");
						}
					}

					@Override
					public void onClicking(View view, int clickTimes) {
						if(view instanceof TextView) {
							((TextView) view).setText("Chat=" + clickTimes);
						}
					}
				});
				
//				try {
//					String startSchemeUri = "cloudhub://chat?userId=2f1cae89-eb8e-11e3-b562-648e9a59efa3&token=?";
//					ActivityIntentTools.gotoTargetActivityByScheme(mAct,
//							startSchemeUri);
//				} catch (Exception e) {
//					ToastUtils.showMessage(mAct, "你没装这个app");
//				}
			}
		});
	}

}
