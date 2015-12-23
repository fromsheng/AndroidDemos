package com.artion.androiddemos.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.artion.androiddemos.R;
import com.artion.androiddemos.adapter.holder.TabMenuBottomHolder;

public class TabMenuBottomAdapter extends
		TabMenuAdapter<TabMenuItem, TabMenuBottomHolder> {

	public TabMenuBottomAdapter(
			Context context,
			List<com.artion.androiddemos.adapter.TabMenuItem> models) {
		super(context, models);
	}

	@Override
	public com.artion.androiddemos.adapter.TabMenuItem selectModel(
			com.artion.androiddemos.adapter.TabMenuItem model,
			boolean isSelected) {
		model.isSelected = isSelected;
		return model;
	}

	@Override
	public int getLayoutId() {
		return R.layout.tab_menu_bottom_item;
	}

	@Override
	public TabMenuBottomHolder newHolder(View convertView) {
		return new TabMenuBottomHolder(convertView);
	}

	@Override
	public void bindView(
			com.artion.androiddemos.adapter.TabMenuItem model,
			TabMenuBottomHolder holder) {
		holder.tvTitle.setText(model.titleId);
		if(model.hasTextColor()) {
			holder.tvTitle.setTextColor(model.isSelected ?
					mContext.getResources().getColor(model.textColorDownId)
					: mContext.getResources().getColor(model.textColorNormalId));
		}
		holder.icon.setBackgroundResource(model.isSelected ? model.iconDownId : model.iconNormalId);
	}

}
