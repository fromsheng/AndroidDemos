package com.artion.androiddemos.baseview.impl;

import android.view.View;
import android.widget.TextView;

import com.artion.androiddemos.R;
import com.artion.androiddemos.domain.SessionItem;

public class SessionItemCommonShareView extends SessionItemCommonView {

	private static SessionItemCommonShareView instance = null;
	
	public static SessionItemCommonShareView getInstance(View convertView, int childLayoutId) {
		if(instance == null) {
			instance = new SessionItemCommonShareView();
		}
		instance.initViewHolder(convertView);
		instance.checkChildView(childLayoutId);
		return instance;
	}
	
	private SessionItemCommonShareView() {}
	
	@Override
	public SessionItemCommonViewHolder returnNewViewHolder(View convertView) {
		// TODO Auto-generated method stub
		return new SessionItemCommonShareViewHolder(convertView);
	}

	@Override
	public void bindView(SessionItem data) {
		// TODO Auto-generated method stub
		super.bindView(data);
		
		((SessionItemCommonShareViewHolder) mHolder).tvShareTitle.setText(data.shareTitle);
	}

	@Override
	public void initChildViews(View lldetails) {
		// TODO Auto-generated method stub
		super.initChildViews(lldetails);
		
		((SessionItemCommonShareViewHolder) mHolder).tvShareTitle = (TextView) lldetails.findViewById(R.id.session_item_common_share_tv_title);
	}

}
