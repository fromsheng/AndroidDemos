package com.artion.androiddemos.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.artion.androiddemos.R;
import com.artion.androiddemos.adapter.ViewPopupWindowAdapter;
import com.artion.androiddemos.view.PopupWindowManager.OnPopItemClickListener;

public class MenuPopupWindowManager extends BasePopupWindowManager {

	private final int MAS_SIZE = 5;
	
	private HorizontalListView hlv;
	private ViewPopupWindowAdapter mAdapter;
	private List<String> mDatas;
	
	private OnPopItemClickListener mListener = null;
	
	public MenuPopupWindowManager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View createContentView() {
		View contentView = LayoutInflater.from(mAct).inflate(
				R.layout.biz_view_popupwindow, null);
		
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
//		mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, mDatas);
		mAdapter = new ViewPopupWindowAdapter(mAct, mDatas);
		hlv.setAdapter(mAdapter);
		
		return contentView;
	}
	
	public void initListData(List<String> strs, OnPopItemClickListener listener) {
		if(strs == null || strs.size() == 0) {
			return;
		}
		
		this.mListener = listener;
		
		int size = strs.size() > MAS_SIZE ? MAS_SIZE : strs.size();
//		int itemWidth = mAct.getResources().getDimensionPixelOffset(R.dimen.view_popupwindow_item_width);
//		viewPopupWindow.setWidth(size * (itemWidth + hlv.getDividerWidth()));
		setWidth(size * 122);
		setHeight(100);
		
		mDatas.clear();
		
		mDatas.addAll(strs);
		mAdapter.notifyDataSetChanged();
		
	}

}
