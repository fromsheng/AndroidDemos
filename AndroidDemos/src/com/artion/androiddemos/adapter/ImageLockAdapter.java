package com.artion.androiddemos.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.artion.androiddemos.R;
import com.artion.androiddemos.adapter.holder.ImageLockHolder;

public class ImageLockAdapter extends TabMenuAdapter<String, ImageLockHolder> {
	
	protected boolean isLocked = false;
	
	public void setIsLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public ImageLockAdapter(Context context, List<String> models) {
		super(context, models);
	}

	@Override
	public String selectModel(String model, boolean isSelected) {
		return null;
	}

	@Override
	public int getLayoutId() {
		return R.layout.act_image_loader_lock_item;
	}

	@Override
	public ImageLockHolder newHolder(View convertView) {
		return new ImageLockHolder(convertView);
	}

	@Override
	public void bindView(String model, ImageLockHolder holder) {
		if(!isLocked) {
			holder.text.setText(model);
		}
	}

}
