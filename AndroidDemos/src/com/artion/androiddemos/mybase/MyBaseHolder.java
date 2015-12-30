package com.artion.androiddemos.mybase;

import android.view.View;

public abstract class MyBaseHolder<MODEL> {

	public MyBaseHolder(View convertView) {
		
	}
	
	public abstract void bindView(MODEL model);
}
