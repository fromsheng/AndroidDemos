package com.artion.androiddemos.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artion.androiddemos.R;

public class FooterMenuItemButton extends LinearLayout {
	
	private Context mContext;
	
	private LinearLayout llItemDetails;
	private ImageView icon;
	private TextView text;

	public FooterMenuItemButton(Context context) {
		super(context);
		
		this.mContext = context;
		LayoutInflater.from(context).inflate(R.layout.act_footer_menu_item, this);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		initLayout();
	}

	public FooterMenuItemButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.mContext = context;
		LayoutInflater.from(context).inflate(R.layout.act_footer_menu_item, this);
		initLayout();
	}

	private void initLayout() {
		llItemDetails = (LinearLayout) findViewById(R.id.footer_menu_item_ll_details);
		icon = (ImageView) findViewById(R.id.footer_menu_item_icon);
		text = (TextView) findViewById(R.id.footer_menu_item_text);
	}
	
	public LinearLayout getItemDetailsLayout() {
		return llItemDetails;
	}
	
	public ImageView getIconView() {
		return icon;
	}
	
	public void setIconResource(int resId) {
		icon.setImageResource(resId);
	}
	
	public void setText(int resId) {
		text.setText(resId);
	}
	
	public void setTextColor(int color) {
		text.setTextColor(color);
	}

}
