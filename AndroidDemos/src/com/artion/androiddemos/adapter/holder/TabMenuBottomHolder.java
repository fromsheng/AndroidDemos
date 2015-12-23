package com.artion.androiddemos.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artion.androiddemos.R;

public class TabMenuBottomHolder {
	public TextView tvTitle;
	public ImageView icon;
	
	public TabMenuBottomHolder(View convertView) {
		tvTitle = (TextView) convertView.findViewById(R.id.tab_menu_bottom_item_title);
		icon = (ImageView) convertView.findViewById(R.id.tab_menu_bottom_item_icon);
	}
}
