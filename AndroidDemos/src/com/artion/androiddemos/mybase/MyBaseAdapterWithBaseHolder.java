package com.artion.androiddemos.mybase;

import java.util.List;

import android.content.Context;

public abstract class MyBaseAdapterWithBaseHolder<MODEL> extends
		MyBaseAdapter<MODEL, com.artion.androiddemos.mybase.MyBaseHolder<MODEL>> {

	public MyBaseAdapterWithBaseHolder(Context context, List<MODEL> models) {
		super(context, models);
	}

	@Override
	public void bindView(MODEL model, MyBaseHolder<MODEL> holder) {
		holder.bindView(model);
		//这里还可以继续重写来实现控件的赋值及相关事件处理或者都放在holder也可以
	}

}
