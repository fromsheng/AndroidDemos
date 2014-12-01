package com.artion.androiddemos.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.artion.androiddemos.R;
import com.artion.androiddemos.common.ToastUtils;

public class TopPopupWindowManager extends BasePopupWindowManager {

	public TopPopupWindowManager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View createContentView() {
		View contentView = LayoutInflater.from(mAct).inflate(
				R.layout.biz_session_mention, null);
		ImageView icon = (ImageView) contentView.findViewById(R.id.session_mention_icon);
//		setHeight(100);
		icon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ToastUtils.showMessage(mAct, "关闭");
				dismiss();
			}
		});
		return contentView;

	}


}
