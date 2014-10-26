package com.artion.androiddemos.view;

import com.artion.androiddemos.R;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public abstract class BasePopupWindowManager {
	
	public enum POSITION {
		UP, NORMAL, DOWN
	}

	protected PopupWindow viewPopupWindow;
	protected Activity mAct;
	protected LifecycleListener mLifecycleListener = null;
	
	public BasePopupWindowManager(Activity activity) {
		this.mAct = activity;
		
		initPopupWindow();
	}
	
	public abstract View createContentView();
	
	public void setContentView(View view) {
		if(viewPopupWindow != null) {
			viewPopupWindow.setContentView(view);
		}
	}
	
	public View getContentView() {
		return viewPopupWindow.getContentView();
	}
	
	public void dismiss() {
		if(viewPopupWindow != null) {
			viewPopupWindow.dismiss();
		}
	}
	
	public void setOutsideTouchable(boolean touchable) {
		if(viewPopupWindow != null) {
			viewPopupWindow.setOutsideTouchable(touchable);
			viewPopupWindow.setFocusable(touchable);
		}
	}
	
	protected void setAnimationStyle(int style) {
		if(viewPopupWindow != null) {
			viewPopupWindow.setAnimationStyle(style);
		}
	}
	
	protected void setWidth(int width) {
		if(viewPopupWindow != null) {
			viewPopupWindow.setWidth(width);
		}
	}
	
	protected void setHeight(int height) {
		if(viewPopupWindow != null) {
			viewPopupWindow.setHeight(height);
		}
	}
	
	public void initPopupWindow() {
		viewPopupWindow = new PopupWindow(mAct);
		viewPopupWindow.setBackgroundDrawable(new BitmapDrawable()); //这个要有的，没有的话会出现奇怪的效果，嘿嘿，大家可以试下的
		viewPopupWindow.setAnimationStyle(R.style.view_popupwindow_anim);
		
		//popwindow的长和宽的，必须要设置的，不然无法显示的
		viewPopupWindow.setWidth(LayoutParams.MATCH_PARENT);
		viewPopupWindow.setHeight(LayoutParams.WRAP_CONTENT);//设置WRAP_CONTENT在取得高度时无法取得，最好设置固定高度

		viewPopupWindow.setOutsideTouchable(true);
		viewPopupWindow.setFocusable(true);
		
		viewPopupWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				if(mLifecycleListener != null) {
					mLifecycleListener.onRemoved();
				}
			}
		});
		
		viewPopupWindow.setContentView(createContentView());
	}
	
	public void showPopupWindowByPos(View parentView, POSITION pos) {
		if(viewPopupWindow == null) {
			return;
		}
		
		if(viewPopupWindow.isShowing()) {
			viewPopupWindow.dismiss();
		}
		
		int yOff = 0;
		if(POSITION.DOWN == pos) {
			yOff = parentView.getHeight();
		} else if(POSITION.UP == pos) {
			yOff = -viewPopupWindow.getHeight();
		} else {
			yOff = 0;
		}
		
		int[] location = new int[2];  
		parentView.getLocationOnScreen(location); 
		viewPopupWindow.showAtLocation(parentView, Gravity.TOP | Gravity.CENTER_HORIZONTAL, location[0], location[1] + yOff);

		if(mLifecycleListener != null) {
			mLifecycleListener.onDisplayed();
		}
	}
	
	public void setLifecycleListener(LifecycleListener lifecycleListener) {
		this.mLifecycleListener = lifecycleListener;
	}
	
	/** Provides callback methods on major lifecycle events of a {@link PopupWindow}. */
	public interface LifecycleListener {
	  /** Will be called when your Crouton has been displayed. */
	  public void onDisplayed();

	  /** Will be called when your {@link PopupWindow} has been removed. */
	  public void onRemoved();

	}
}
