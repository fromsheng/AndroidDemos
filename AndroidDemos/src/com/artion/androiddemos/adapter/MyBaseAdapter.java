package com.artion.androiddemos.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<MODEL, HOLDER> extends BaseAdapter {

	protected List<MODEL> mModels = null;
	protected Context mContext = null;
	protected LayoutInflater mInflater = null;
	
	public MyBaseAdapter(Context context, List<MODEL> models) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		this.mModels = models;
	}
	
	@Override
	public int getCount() {
		return mModels.size();
	}

	@Override
	public MODEL getItem(int position) {
		return mModels.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HOLDER holder = null;
		if(convertView == null) {
			convertView = mInflater.inflate(getLayoutId(), null);
			holder = newHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (HOLDER) convertView.getTag();
		}
		
		bindView(getItem(position), holder);
		
		return convertView;
	}
	
	public abstract int getLayoutId();
	
	public abstract HOLDER newHolder(View convertView);
	
	public abstract void bindView(MODEL model, HOLDER holder);

}
