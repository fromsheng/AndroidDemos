package com.artion.androiddemos.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artion.androiddemos.R;

public class ImageLockHolder {
	public TextView text;
	public ImageView icon;
	
	public ImageLockHolder(View convertView) {
		text = (TextView) convertView.findViewById(R.id.text);
		icon = (ImageView) convertView.findViewById(R.id.icon);
	}
}
