package com.artion.androiddemos.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.artion.androiddemos.R;
import com.artion.androiddemos.adapter.holder.TabMenuTopHolder;

public class TabMenuTopAdapter extends
		TabMenuAdapter<TabMenuItem, TabMenuTopHolder> {

	public TabMenuTopAdapter(
			Context context,
			List<com.artion.androiddemos.adapter.TabMenuItem> models) {
		super(context, models);
	}

	@Override
	public int getLayoutId() {
		return R.layout.tab_menu_top_item;
	}

	@Override
	public TabMenuTopHolder newHolder(View convertView) {
		return new TabMenuTopHolder(convertView);
	}

	@Override
	public void bindView(
			com.artion.androiddemos.adapter.TabMenuItem model,
			TabMenuTopHolder holder) {
		holder.tvTitle.setText(model.titleId);
		if(model.hasTextColor()) {
			holder.tvTitle.setTextColor(model.isSelected ?
					mContext.getResources().getColor(model.textColorDownId)
					: mContext.getResources().getColor(model.textColorNormalId));
		}
		holder.icon.setBackgroundResource(model.isSelected ? model.iconDownId : model.iconNormalId);
		holder.downLine.setVisibility(model.isSelected ? View.VISIBLE : View.INVISIBLE);
	}

	@Override
	public com.artion.androiddemos.adapter.TabMenuItem selectModel(
			com.artion.androiddemos.adapter.TabMenuItem model,
			boolean isSelected) {
		model.isSelected = isSelected;
		return model;
	}

}
