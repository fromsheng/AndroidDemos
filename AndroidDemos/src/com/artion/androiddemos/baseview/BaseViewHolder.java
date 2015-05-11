package com.artion.androiddemos.baseview;

import android.view.View;

/**
 * 基本View加载的Holder模板类，子类继承仅需要实现{@link bindView}，通过BaseHolderHepler.instanceBaseViewHolder实例化
 * @author jinsheng_cai
 * @since 2015-05-11
 * @param <T> 数据对象
 */
public abstract class BaseViewHolder<T> {
	
	/**
	 * 构造方法，置为保护，外部不能直接调用实例化，改用BaseHolderHepler.instanceBaseViewHolder实例化
	 * @param convertView
	 */
	protected BaseViewHolder(View convertView) {
		
	}
	
	/**
	 * 构造相应的控件的value
	 * @param t 数据对象
	 */
	public abstract void bindView(T t);
}
