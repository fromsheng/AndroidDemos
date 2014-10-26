package com.artion.androiddemos.adapter;

import java.util.List;

import com.artion.androiddemos.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ViewPopupWindowAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private List<String> mDatas;
	
	public ViewPopupWindowAdapter(Context context, List<String> datas) {
		this.mInflater = LayoutInflater.from(context);
		this.mDatas = datas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.biz_view_popupwindow_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.text.setText(mDatas.get(position));
		
		return convertView;
	}
	
	private class ViewHolder {
		TextView text;
		
		public ViewHolder(View convertView) {
			text = (TextView) convertView.findViewById(R.id.view_popupwindow_text);
		}
	}

}
