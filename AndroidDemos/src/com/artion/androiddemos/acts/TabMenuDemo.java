package com.artion.androiddemos.acts;

import java.util.ArrayList;
import java.util.List;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.R.color;
import com.artion.androiddemos.R.drawable;
import com.artion.androiddemos.R.id;
import com.artion.androiddemos.R.layout;
import com.artion.androiddemos.R.string;
import com.artion.androiddemos.adapter.GridMenuItem;
import com.artion.androiddemos.adapter.TabMenuBottomAdapter;
import com.artion.androiddemos.adapter.TabMenuItem;
import com.artion.androiddemos.adapter.TabMenuTopAdapter;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.mybase.MyTabMenuTopAdapter;
import com.artion.androiddemos.mybase.MyTabMenuTopAdapter2;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class TabMenuDemo extends BaseActivity {

	private GridView topGrid, bottomGrid;
//	private TabMenuTopAdapter topAdapter;
//	private MyTabMenuTopAdapter topAdapter;
	private MyTabMenuTopAdapter2 topAdapter;
	private TabMenuBottomAdapter bottomAdapter;
	private List<TabMenuItem> topItems, bottomItems;
	
	private final int[] ITEM_STRS = {
			R.string.footer_menu_message,
			R.string.footer_menu_app,
			R.string.footer_menu_discovery,
			R.string.footer_menu_college,
			R.string.footer_menu_work
	};
	
	private final int[] ICON_RID_NORMAL = {
			R.drawable.common_btn_message_normal,
			R.drawable.common_btn_app_normal,
			R.drawable.common_btn_discover_normal,
			R.drawable.common_btn_college_normal,
			R.drawable.common_btn_work_normal
		};
	
	private final int[] ICON_RID_DOWN = {
			R.drawable.common_btn_message_down,
			R.drawable.common_btn_app_down,
			R.drawable.common_btn_discover_down,
			R.drawable.common_btn_college_down,
			R.drawable.common_btn_work_down
		};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_tab_menu);
		
		initLayout();
		initListener();
	}
	
	private List<TabMenuItem> getItems(int size, int defaultSelection, boolean hasTextColor) {
		List<TabMenuItem> items = new ArrayList<TabMenuItem>();
		TabMenuItem item = null;
		for (int i = 0; i < size; i++) {
			item = new TabMenuItem();
			item.iconNormalId = ICON_RID_NORMAL[i];
			item.iconDownId = ICON_RID_DOWN[i];
			item.titleId = ITEM_STRS[i];
			item.isSelected = (defaultSelection == i);
			if(hasTextColor) {
				item.textColorNormalId = R.color.tab_menu_normal;
				item.textColorDownId = R.color.tab_menu_down;
			}
			items.add(item);
		}
		
		return items;
	}
	
	@Override
	protected void initLayout() {
		super.initLayout();
		
		topGrid = (GridView) findViewById(R.id.top_grid);
		bottomGrid = (GridView) findViewById(R.id.bottom_grid);
		topGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
		bottomGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
		topItems = getItems(3, 1, true);
//		topAdapter = new TabMenuTopAdapter(mAct, topItems);
//		topAdapter = new MyTabMenuTopAdapter(mAct, topItems);
		topAdapter = new MyTabMenuTopAdapter2(mAct, topItems);
		topGrid.setAdapter(topAdapter);
		
		bottomItems = getItems(5, 3, true);
		bottomAdapter = new TabMenuBottomAdapter(mAct, bottomItems);
		bottomGrid.setAdapter(bottomAdapter);
	}

	@Override
	protected void initListener() {
		
		super.initListener();
		
		topGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				topAdapter.selectedPosition(position);
				
				ToastUtils.showMessage(mAct, topAdapter.getItem(position).titleId);
			}
		});
		
		bottomGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				bottomAdapter.selectedPosition(position);
				ToastUtils.showMessage(mAct, bottomAdapter.getItem(position).titleId);
			}
		});
		
	}

}
