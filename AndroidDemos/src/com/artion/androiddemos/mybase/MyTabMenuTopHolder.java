package com.artion.androiddemos.mybase;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artion.androiddemos.R;
import com.artion.androiddemos.adapter.TabMenuItem;

public class MyTabMenuTopHolder extends MyBaseHolder<TabMenuItem> {
	
	public TextView tvTitle;
	public ImageView icon;
	public View downLine;
	
	public MyTabMenuTopHolder(View convertView) {
		super(convertView);

		tvTitle = (TextView) convertView.findViewById(R.id.tab_menu_top_item_title);
		icon = (ImageView) convertView.findViewById(R.id.tab_menu_top_item_icon);
		downLine = convertView.findViewById(R.id.tab_menu_top_item_line);
	}

	@Override
	public void bindView(TabMenuItem model) {
		tvTitle.setText(model.titleId);
		if(model.hasTextColor()) {
			tvTitle.setTextColor(model.isSelected ?
					tvTitle.getContext().getResources().getColor(model.textColorDownId)
					: tvTitle.getContext().getResources().getColor(model.textColorNormalId));
		}
		icon.setBackgroundResource(model.isSelected ? model.iconDownId : model.iconNormalId);
		downLine.setVisibility(model.isSelected ? View.VISIBLE : View.INVISIBLE);
	}

}
