package com.artion.androiddemos.dialog;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artion.androiddemos.R;

public class DialogBottomAdapter extends BaseAdapter {

	private Context mContext;
	private List<String> itemStrs;
	
	public DialogBottomAdapter(Context mContext, List<String> itemStrs) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.itemStrs = itemStrs;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemStrs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itemStrs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_bottom_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tvItem = (TextView) convertView.findViewById(R.id.dialog_bottom_list_item_tv);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.tvItem.setText(itemStrs.get(position));
		
		return convertView;
	}

	private class ViewHolder {
		TextView tvItem;
	}
}
