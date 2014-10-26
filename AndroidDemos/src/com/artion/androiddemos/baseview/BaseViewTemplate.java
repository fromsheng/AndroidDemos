package com.artion.androiddemos.baseview;

import android.view.View;

import com.artion.androiddemos.utils.DebugTool;

/**
 * 基本View加载的模板类，子类继承实现{@link getViewHolder}及{@link bindView}，并实现单例
 * @author jinsheng_cai
 * @since 2014-01-26
 * @param <T> 数据对象
 * @param <Holder> view对应的ViewHolder
 */
public abstract class BaseViewTemplate<T, Holder> {
	
	protected Holder mHolder;
	
//	protected BaseViewTemplate(View convertView){
//		initViewHolder(convertView);
//	}
	
	/**
	 * 初始化ViewHolder
	 * @param convertView
	 */
	public void initViewHolder(View convertView) {
		long t1 = System.currentTimeMillis();
		mHolder = (Holder) convertView.getTag(convertView.getId());//R.id.tag_view_template
		if(mHolder == null) {
			DebugTool.info("Template", "mHolder == null需要重组 == " + convertView.toString());
			mHolder = returnNewViewHolder(convertView);
			convertView.setTag(convertView.getId(), mHolder);
		} else {
			DebugTool.info("Template", "mHolder可重用 == " + convertView.toString());
		}
		DebugTool.info("Template", "initViewHolder 耗时 == " + (System.currentTimeMillis() - t1));
	}
	
	/**
	 * 子类继承实现对应的ViewHolder实现
	 * @param convertView
	 * @return
	 */
	public abstract Holder returnNewViewHolder(View convertView);
	/**
	 * 子类继承实现对应的数据对象更新View
	 * @param data 数据对象
	 */
	public abstract void bindView(T data);
}
