package com.artion.androiddemos.mybase;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.artion.androiddemos.adapter.TabMenuItem;

public abstract class MyTabMenuAdapter2<MODEL> extends MyBaseAdapterWithBaseHolder<MODEL> {

	protected int mCurSeletion = -1;
	
	public MyTabMenuAdapter2(Context context, List<MODEL> models) {
		super(context, models);
	}

	public void selectedPosition(int position) {
		if(mCurSeletion == position) {
			return;
		}
		mCurSeletion = position;
		MODEL model = null;
		for (int i = 0; i < getCount(); i++) {
			model = selectModel(getItem(i), i == position);
		}
		
		notifyDataSetChanged();
	}
	
	public abstract MODEL selectModel(MODEL model, boolean isSelected);
}
