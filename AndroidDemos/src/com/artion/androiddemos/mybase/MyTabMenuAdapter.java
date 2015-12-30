package com.artion.androiddemos.mybase;

import java.util.List;

import android.content.Context;

public abstract class MyTabMenuAdapter<MODEL, HOLDER> extends MyBaseAdapter<MODEL, HOLDER> {

	protected int mCurSeletion = -1;
	
	public MyTabMenuAdapter(Context context, List<MODEL> models) {
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
