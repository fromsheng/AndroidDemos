package com.artion.androiddemos.baseview;

import java.lang.reflect.Constructor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 基本View加载的Holder模板类的工具类，实例化convertView对应的ViewHolder
 * @author jinsheng_cai
 * @since 2015-05-11
 */
public class BaseHolderHepler {
	
	public static <T> T instanceBaseViewHolder(Context context, Class<? extends T> clazz, View convertView) {
		try {
			T t = (T) convertView.getTag(convertView.getId());
			if(t == null) {
				Class<?>[] parameterTypes = new Class<?>[1];  
				parameterTypes[0] = View.class;  

				t = newInstance(context, clazz, parameterTypes, convertView);
				convertView.setTag(convertView.getId(), t);
			}
			return t;
		} catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
	
	private static <T> T newInstance(Context context, Class clazz, Class[] constructorSig,
            Object... arguments) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(constructorSig);
            constructor.setAccessible(true);  
            return (T) constructor.newInstance(arguments);
        } catch (Exception e) {
        }
        return null;
    }
}
