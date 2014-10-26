package com.artion.androiddemos.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.artion.androiddemos.R;

public class GridMenuAdapter extends BaseAdapter {
	
	private Context ctx;
	private LayoutInflater mInflater;
	private List<String> datas;
	private int mHidePosition = -1;

	public GridMenuAdapter(Context ctx, List<String> datas) {
		this.ctx = ctx;
		this.mInflater = LayoutInflater.from(ctx);
		this.datas = datas;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return datas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
//		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.act_footer_menu_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
		
		holder.text.setText(datas.get(position));
		
		if(position == mHidePosition){
			convertView.setVisibility(View.INVISIBLE);
		}
		
		return convertView;
	}
	
	/**
	 * 设置某项隐藏
	 * @param position
	 */
	public void setItemHide(int position){
		this.mHidePosition = position; 
		notifyDataSetChanged();
	}
	
	private class ViewHolder {
		ImageView icon;
		TextView text;
		
		public ViewHolder(View convertView) {
			icon = (ImageView) convertView.findViewById(R.id.footer_menu_item_icon);
			text = (TextView) convertView.findViewById(R.id.footer_menu_item_text);
		}
	}

}
