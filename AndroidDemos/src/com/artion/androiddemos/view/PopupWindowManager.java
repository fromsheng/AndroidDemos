package com.artion.androiddemos.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.artion.androiddemos.R;
import com.artion.androiddemos.adapter.ViewPopupWindowAdapter;
import com.artion.androiddemos.utils.ToastUtils;

public class PopupWindowManager {
	
	private final int MAS_SIZE = 5;
	
	private PopupWindow viewPopupWindow;
	
	private Context mContext;
	private View anchorView;
	
	private HorizontalListView hlv;
	private ViewPopupWindowAdapter mAdapter;
	private List<String> mDatas;
	
	private OnPopItemClickListener mListener = null;
	
	public PopupWindowManager(Context context, View anchor) {
		this.mContext = context;
		this.anchorView = anchor;
		
		initViewPopupWindow(mContext, anchorView);
	}
	
	private void initViewPopupWindow(Context context, View view) {
		if(viewPopupWindow == null) {
			View contentView = LayoutInflater.from(context).inflate(
					R.layout.biz_view_popupwindow, null);
			viewPopupWindow = new PopupWindow(context);
			viewPopupWindow.setBackgroundDrawable(new BitmapDrawable()); //这个要有的，没有的话会出现奇怪的效果，嘿嘿，大家可以试下的
			viewPopupWindow.setContentView(contentView);
			viewPopupWindow.setAnimationStyle(R.style.view_popupwindow_anim);

			hlv = (HorizontalListView) contentView.findViewById(R.id.pop_horizontallistview);
			
			hlv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long arg3) {
					// TODO Auto-generated method stub
					viewPopupWindow.dismiss();
					
					if(mListener != null) {
						mListener.onItemCllick(parent, view, position, arg3);
					}
				}
			});
			
			mDatas = new ArrayList<String>();
//			mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, mDatas);
			mAdapter = new ViewPopupWindowAdapter(mContext, mDatas);
			hlv.setAdapter(mAdapter);

			//popwindow的长和宽的，必须要设置的，不然无法显示的
			viewPopupWindow.setWidth(300);
			viewPopupWindow.setHeight(100);

			viewPopupWindow.setOutsideTouchable(true);
			viewPopupWindow.setFocusable(true);
			
			viewPopupWindow.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss() {
					if(mListener != null) {
						mListener.OnDismiss();
					}
				}
			});
			
		}
	}
	
	public void initListData(List<String> strs, OnPopItemClickListener listener) {
		if(strs == null || strs.size() == 0) {
			return;
		}
		
		this.mListener = listener;
		
		int size = strs.size() > MAS_SIZE ? MAS_SIZE : strs.size();
		viewPopupWindow.setWidth(size * 122);
		
		mDatas.clear();
		
		mDatas.addAll(strs);
		mAdapter.notifyDataSetChanged();
		
	}
	
	public void showPopupWindow() {
		int[] location = new int[2];  
        ((View) anchorView.getParent()).getLocationOnScreen(location); 
		viewPopupWindow.showAtLocation(anchorView, Gravity.TOP | Gravity.LEFT, location[0] + anchorView.getWidth()/2, location[1] + anchorView.getHeight()/2);
//		viewPopupWindow.showAsDropDown(anchorView);
		
		if(mListener != null) {
			mListener.onShowing();
		}
	}
	
	public void showTopPopupWindow(View contentView) {
//		View contentView = ((Activity) mContext).getWindow().findViewById(Window.ID_ANDROID_CONTENT);
		int[] location = new int[2];  
		contentView.getLocationOnScreen(location); 
		if(contentView != null) {
			viewPopupWindow.showAtLocation(contentView, Gravity.TOP, location[0], location[1]);
			
			if(mListener != null) {
				mListener.onShowing();
			}
		}
	}
	
	public void setOutsideTouchable(boolean touchable) {
		viewPopupWindow.setOutsideTouchable(touchable);
		viewPopupWindow.setFocusable(touchable);
	}
	
	public void setOnPopItemClickListener(OnPopItemClickListener listener) {
		this.mListener = listener;
	}
	
	public interface OnPopItemClickListener {
		public void onItemCllick(AdapterView<?> parent, View view, int position,
				long arg3);
		
		public void OnDismiss();
		
		public void onShowing();
	}
}
