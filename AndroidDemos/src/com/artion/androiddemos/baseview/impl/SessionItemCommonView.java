package com.artion.androiddemos.baseview.impl;

import android.view.LayoutInflater;
import android.view.View;

import com.artion.androiddemos.baseview.BaseViewTemplate;
import com.artion.androiddemos.domain.SessionItem;

public class SessionItemCommonView extends
		BaseViewTemplate<SessionItem, SessionItemCommonViewHolder> {

	@Override
	public SessionItemCommonViewHolder returnNewViewHolder(View convertView) {
		// TODO Auto-generated method stub
		return new SessionItemCommonViewHolder(convertView);
	}

	@Override
	public void bindView(SessionItem data) {
		// TODO Auto-generated method stub
		
		mHolder.tvTime.setText(data.sessionTime);
		mHolder.tvUserName.setText(data.userName);
		
	}
	
	public void checkChildView(int childLayoutId) {
		View childView = mHolder.llDetails.getChildAt(0);
		
		if(childView == null) {
//			mHolder.llDetails.addView(child);
			addChildView(childLayoutId);
		} else {
			Integer tagLayoutId = (Integer) childView.getTag();
			if(tagLayoutId == null || tagLayoutId.intValue() != childLayoutId) {
				addChildView(childLayoutId);
			}
		}
	}
	
	public void addChildView(int childLayoutId) {
		mHolder.llDetails.removeAllViews();
		View childView = LayoutInflater.from(mHolder.llDetails.getContext()).inflate(childLayoutId, null);
		mHolder.llDetails.addView(childView);
		
		initChildViews(mHolder.llDetails);
	}
	
	public void initChildViews(View lldetails) {
		
	}

}
