package com.artion.androiddemos.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class TabMenuAdapter<MODEL, HOLDER> extends MyBaseAdapter<MODEL, HOLDER> {
	
	protected int mCurSeletion = -1;
	
	public TabMenuAdapter(Context context, List<MODEL> models) {
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
	
	public abstract int getLayoutId();
	
	public abstract HOLDER newHolder(View convertView);
	

}
