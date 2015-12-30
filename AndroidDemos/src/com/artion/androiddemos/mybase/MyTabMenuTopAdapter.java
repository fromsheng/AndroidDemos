package com.artion.androiddemos.mybase;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.artion.androiddemos.R;
import com.artion.androiddemos.adapter.TabMenuItem;

public class MyTabMenuTopAdapter extends
		MyTabMenuAdapter<TabMenuItem, MyTabMenuTopHolder> {

	public MyTabMenuTopAdapter(
			Context context,
			List<com.artion.androiddemos.adapter.TabMenuItem> models) {
		super(context, models);
	}

	@Override
	public int getLayoutId() {
		return R.layout.tab_menu_top_item;
	}

	@Override
	public MyTabMenuTopHolder newHolder(View convertView) {
		return new MyTabMenuTopHolder(convertView);
	}

	@Override
	public void bindView(
			com.artion.androiddemos.adapter.TabMenuItem model,
			MyTabMenuTopHolder holder) {
		
		holder.bindView(model);
		
	}

	@Override
	public com.artion.androiddemos.adapter.TabMenuItem selectModel(
			com.artion.androiddemos.adapter.TabMenuItem model,
			boolean isSelected) {
		model.isSelected = isSelected;
		return model;
	}

}
