package com.artion.androiddemos.adapter;

public class TabMenuItem {
	public int titleId;
	public int textColorNormalId = -1;
	public int textColorDownId = -1;
	public int iconNormalId;
	public int iconDownId;
	public boolean isSelected;
	
	public boolean hasTextColor() {
		return textColorNormalId != -1 && textColorDownId != -1;
	}
}