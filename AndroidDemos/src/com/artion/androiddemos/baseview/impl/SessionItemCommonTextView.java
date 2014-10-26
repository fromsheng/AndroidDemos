package com.artion.androiddemos.baseview.impl;

import android.view.View;
import android.widget.TextView;

import com.artion.androiddemos.R;
import com.artion.androiddemos.domain.SessionItem;

public class SessionItemCommonTextView extends SessionItemCommonView {
	
	private static SessionItemCommonTextView instance = null;
	
	public static SessionItemCommonTextView getInstance(View convertView, int childLayoutId) {
		if(instance == null) {
			instance = new SessionItemCommonTextView();
		}
		instance.initViewHolder(convertView);
		instance.checkChildView(childLayoutId);
		return instance;
	}
	
	private SessionItemCommonTextView() {}

	@Override
	public SessionItemCommonViewHolder returnNewViewHolder(View convertView) {
		return new SessionItemCommonTextViewHolder(convertView);
	}

	@Override
	public void bindView(SessionItem data) {
		// TODO Auto-generated method stub
		super.bindView(data);
		
		((SessionItemCommonTextViewHolder) mHolder).tvContent.setText(data.sessionContent);
	}

	@Override
	public void initChildViews(View lldetails) {
		// TODO Auto-generated method stub
		super.initChildViews(lldetails);
		((SessionItemCommonTextViewHolder) mHolder).tvContent = (TextView) lldetails.findViewById(R.id.session_item_common_text_tv_content);
	}

}
