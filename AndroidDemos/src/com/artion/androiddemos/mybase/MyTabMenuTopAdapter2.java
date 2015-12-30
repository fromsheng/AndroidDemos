package com.artion.androiddemos.mybase;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.artion.androiddemos.R;
import com.artion.androiddemos.adapter.TabMenuItem;

public class MyTabMenuTopAdapter2 extends MyTabMenuAdapter2<TabMenuItem> {

	public MyTabMenuTopAdapter2(Context context, List<TabMenuItem> models) {
		super(context, models);
	}

	@Override
	public TabMenuItem selectModel(TabMenuItem model, boolean isSelected) {
		model.isSelected = isSelected;
		return model;
	}

	@Override
	public int getLayoutId() {
		return R.layout.tab_menu_top_item;
	}

	@Override
	public MyBaseHolder<TabMenuItem> newHolder(View convertView) {
		return new MyTabMenuTopHolder(convertView);
	}

	@Override
	public void bindView(TabMenuItem model, MyBaseHolder<TabMenuItem> holder) {
		super.bindView(model, holder);
		//这里还可以继续实现控件的赋值及相关事件处理或者都放在holder也可以
	}

}
