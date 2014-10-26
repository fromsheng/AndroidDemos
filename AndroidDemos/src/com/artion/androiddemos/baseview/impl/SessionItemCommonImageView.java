package com.artion.androiddemos.baseview.impl;

import android.view.View;
import android.widget.ImageView;

import com.artion.androiddemos.R;
import com.artion.androiddemos.domain.SessionItem;

public class SessionItemCommonImageView extends SessionItemCommonView {

	private static SessionItemCommonImageView instance = null;

	public static SessionItemCommonImageView getInstance(View convertView, int childLayoutId) {
		if(instance == null) {
			instance = new SessionItemCommonImageView();
		}
		instance.initViewHolder(convertView);
		instance.checkChildView(childLayoutId);
		return instance;
	}

	private SessionItemCommonImageView() {}
	
	@Override
	public SessionItemCommonViewHolder returnNewViewHolder(View convertView) {
		// TODO Auto-generated method stub
		return new SessionItemCommonImageViewHolder(convertView);
	}

	@Override
	public void bindView(SessionItem data) {
		// TODO Auto-generated method stub
		super.bindView(data);
		
		((SessionItemCommonImageViewHolder) mHolder).ivPhoto.setImageResource(R.drawable.ic_launcher);
	}

	@Override
	public void initChildViews(View lldetails) {
		// TODO Auto-generated method stub
		super.initChildViews(lldetails);
		
		((SessionItemCommonImageViewHolder) mHolder).ivPhoto = (ImageView) lldetails.findViewById(R.id.session_item_common_image_iv);
	}

}
