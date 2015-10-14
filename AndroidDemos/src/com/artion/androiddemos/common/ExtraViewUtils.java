package com.artion.androiddemos.common;

import android.app.Activity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import com.artion.androiddemos.R;

/**
 * 非Dialog实现仿LoadingDialog类,注意activity需要实现onKeyDown事件并处理{@link isNeedHandleKeyBack} 
 * @author jinsheng_cai
 * @since 2015-10-14
 */
public class ExtraViewUtils {

	/** 默认添加，以view形式，不接收返回事件等,可以不处理onKeyDown */
	public static final int EXTRA_VIEW_CANCEL_LEVEL_NONE = 0;
	/** 接收返回事件,注意activity需要实现onKeyDown事件并处理{@link isNeedHandleKeyBack} */
	public static final int EXTRA_VIEW_CANCEL_LEVEL_BACKABLE = 1;
	/** 屏蔽返回事件,注意activity需要实现onKeyDown事件并处理{@link isNeedHandleKeyBack} */
	public static final int EXTRA_VIEW_CANCEL_LEVEL_UNBACKABLE = 2;
	/** 接收返回事件及点击周围响应返回,注意activity需要实现onKeyDown事件并处理{@link isNeedHandleKeyBack} */
	public static final int EXTRA_VIEW_CANCEL_LEVEL_BACKABLE_OUTSIDE = 3;

	public interface ExtraViewListener {
		/** 显示时事件 */
		public void onDisplay(View v);
		/** 消失时事件 */
		public void onDismiss(View v);
	}

	/**
	 * 根据给出的layoutId初始化extraView
	 * @param activity
	 * @param view
	 * @param layoutId
	 * @param listener
	 * @param extra_level
	 * @return
	 */
	public static View initExtraView(Activity activity, View view, int layoutId, final ExtraViewListener listener,
			final int extra_level) {
		return initExtraView(activity, view, LayoutInflater.from(activity).inflate(layoutId, null), listener, extra_level);

	}
	
	/**
	 * 根据给出的layoutView初始化extraView
	 * @param activity
	 * @param view
	 * @param layoutView
	 * @param listener
	 * @param extra_level
	 * @return
	 */
	public static View initExtraView(Activity activity, View view, View layoutView, final ExtraViewListener listener,
			final int extra_level) {
		if(view == null) {
			if(layoutView == null) {
				layoutView = LayoutInflater.from(activity).inflate(R.layout.fag_extra_view, null);
			}
			view = layoutView;

			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
					android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
					android.widget.FrameLayout.LayoutParams.MATCH_PARENT);

			params.gravity = Gravity.TOP;

			switch (extra_level) {
			case EXTRA_VIEW_CANCEL_LEVEL_NONE:

				break;
			case EXTRA_VIEW_CANCEL_LEVEL_BACKABLE:
			case EXTRA_VIEW_CANCEL_LEVEL_UNBACKABLE:
			case EXTRA_VIEW_CANCEL_LEVEL_BACKABLE_OUTSIDE:
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if(isExtraViewLevelBackableOutside(v)) {
							ExtraViewUtils.dismissExtraView(v);
						}
					}
				});
				break;
			}

			view.setTag(R.id.extra_view_listener, listener);
			view.setTag(R.id.extra_view_cancel_level, extra_level);

			activity.addContentView(view, params);
			showExtraView(view);
		}
		return view;

	}
	
	/**
	 * 初始化默认extraView
	 * @param activity
	 * @param view
	 * @param listener
	 * @param extra_level
	 * @return
	 */
	public static View initExtraView(Activity activity, View view, final ExtraViewListener listener,
			final int extra_level) {
		return initExtraView(activity, view, R.layout.fag_extra_view, listener, extra_level);
	}

	public static boolean isExtraViewShowing(View view) {
		if(view != null) {
			return view.getVisibility() == View.VISIBLE;
		}
		return false;
	}

	public static int getExtraViewCancelLevel(View view) {
		if(view != null) {
			Integer level = (Integer) view.getTag(R.id.extra_view_cancel_level);
			if(level != null) {
				return level;
			}
		}
		return EXTRA_VIEW_CANCEL_LEVEL_NONE;
	}

	public static boolean isExtraViewLevelNone(View view) {
		return EXTRA_VIEW_CANCEL_LEVEL_NONE == getExtraViewCancelLevel(view);
	}

	public static boolean isExtraViewLevelBackable(View view) {
		return EXTRA_VIEW_CANCEL_LEVEL_BACKABLE == getExtraViewCancelLevel(view)
				|| EXTRA_VIEW_CANCEL_LEVEL_BACKABLE_OUTSIDE == getExtraViewCancelLevel(view);
	}

	public static boolean isExtraViewLevelUnBackable(View view) {
		return EXTRA_VIEW_CANCEL_LEVEL_UNBACKABLE == getExtraViewCancelLevel(view);
	}

	public static boolean isExtraViewLevelBackableOutside(View view) {
		return EXTRA_VIEW_CANCEL_LEVEL_BACKABLE_OUTSIDE == getExtraViewCancelLevel(view);
	}

	public static boolean isNeedHandleKeyBack(View view, int keyCode) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) { 
			if(isExtraViewLevelBackable(view)
					&& isExtraViewShowing(view)) {
				dismissExtraView(view);
				return true; 
			} else if(isExtraViewLevelUnBackable(view)) {
				return true;
			}
		}

		return false;
	}

	public static void showExtraView(View view) {
		if(view != null) {
			if(!isExtraViewShowing(view)) {
				view.setVisibility(View.VISIBLE);

				ExtraViewListener listener = (ExtraViewListener) view.getTag(R.id.extra_view_listener);
				if(listener != null) {
					listener.onDisplay(view);
				}
			}
		}

	}

	public static void dismissExtraView(View view) {
		if(view != null) {
			if(isExtraViewShowing(view)) {
				view.setVisibility(View.GONE);
				ExtraViewListener listener = (ExtraViewListener) view.getTag(R.id.extra_view_listener);
				if(listener != null) {
					listener.onDismiss(view);
				}
			}
		}
	}

}
