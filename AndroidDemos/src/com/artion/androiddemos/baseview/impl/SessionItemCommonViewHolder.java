package com.artion.androiddemos.baseview.impl;

import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artion.androiddemos.R;
import com.artion.androiddemos.baseview.ViewHolder;

public class SessionItemCommonViewHolder extends ViewHolder {

	public TextView tvTime;
	public TextView tvUserName;
	public ImageView ivAvatar;
	public ViewStub vsDetails;
	public LinearLayout llDetails;
	
	public SessionItemCommonViewHolder(View convertView) {
		super(convertView);
		// TODO Auto-generated constructor stub
		
		tvTime = (TextView) convertView.findViewById(R.id.session_item_common_tv_time);
		tvUserName = (TextView) convertView.findViewById(R.id.session_item_common_tv_username);
		ivAvatar = (ImageView) convertView.findViewById(R.id.session_item_common_iv_avatar);
		vsDetails = (ViewStub) convertView.findViewById(R.id.session_item_common_vs_details);
		llDetails = (LinearLayout) convertView.findViewById(R.id.session_item_common_ll_details);
		
	}

}
