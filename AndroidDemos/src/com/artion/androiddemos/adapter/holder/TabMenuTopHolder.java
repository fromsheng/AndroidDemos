package com.artion.androiddemos.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artion.androiddemos.R;

public class TabMenuTopHolder {
	public TextView tvTitle;
	public ImageView icon;
	public View downLine;
	
	public TabMenuTopHolder(View convertView) {
		tvTitle = (TextView) convertView.findViewById(R.id.tab_menu_top_item_title);
		icon = (ImageView) convertView.findViewById(R.id.tab_menu_top_item_icon);
		downLine = convertView.findViewById(R.id.tab_menu_top_item_line);
	}
}
