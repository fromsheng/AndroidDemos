package com.artion.androiddemos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.artion.androiddemos.adapter.GVMenuAdapter;
import com.artion.androiddemos.adapter.GridMenuAdapter;
import com.artion.androiddemos.adapter.GridMenuItem;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.utils.DeviceTool;
import com.artion.androiddemos.utils.FileSaveManager;
import com.artion.androiddemos.view.BadgeView;
import com.artion.androiddemos.view.DragGridView;
import com.artion.androiddemos.view.DragGridView.OnChanageListener;

public class GridViewMenuDemo extends BaseActivity {

//	private GridView gridView;
	
	private DragGridView gridView;
//	private DragSortGridView gridView;
	private TextView tvText;
//	private GridMenuAdapter mAdapter;
//	private List<String> datas;
	
	private List<GridMenuItem> menuRes ;
	private GVMenuAdapter mMenuAdapter;
	
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
	
	private void initMenuRes(int defaultSelection) {
		if(menuRes == null) {
			menuRes = new ArrayList<GridMenuItem>();
		} else {
			menuRes.clear();
		}
		
		GridMenuItem item = null;
		for (int i = 0; i < 5; i++) {
			item = new GridMenuItem();
			item.iconNormalRid = ICON_RID_NORMAL[i];
			item.iconDownRid = ICON_RID_DOWN[i];
			item.itemStrRid = ITEM_STRS[i];
			item.itemSelected = (defaultSelection == i);
			menuRes.add(item);
		}
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.gridview_menu);
		
		initLayout();
		initListener();
		
//		initMenuRes(0);
		menuRes = new ArrayList<GridMenuItem>();
		mMenuAdapter = new GVMenuAdapter(mAct, menuRes);
//		gridView.setNumColumns(menuRes.size());
		gridView.setAdapter(mMenuAdapter);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		
		
		new AsyncTask<Void, Void, Object>() {

			@Override
			protected Object doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
				List<GridMenuItem> tmp = (List<GridMenuItem>) FileSaveManager.getInfosFromFile(mAct, GVMenuAdapter.SAVE_FILE_NAME);
				if(tmp != null && tmp.size() > 0) {
					menuRes.clear();
					menuRes.addAll(tmp);
				} else {
					initMenuRes(0);
				}
				
				return null;
			}

			@Override
			protected void onPostExecute(Object result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				
				mMenuAdapter.notifyDataSetChanged();
				
				ToastUtils.showMessage(mAct, "默认选中位置:" + mMenuAdapter.getCurrentSelection());
				
				tvText.setText(menuRes.get(mMenuAdapter.getCurrentSelection()).itemStrRid);
			}
			
			
		}.execute();
		
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if(mMenuAdapter != null) {
			mMenuAdapter.saveListDatas();
		}
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		gridView = (DragGridView) findViewById(R.id.gridview);
//		gridView = (DragSortGridView) findViewById(R.id.gridview);
		tvText = (TextView) findViewById(R.id.text);
		
//		datas = new ArrayList<String>();
//		for (int i = 0; i < 5; i++) {
//			datas.add("点" + i);
//		}
//		
//		mAdapter = new GridMenuAdapter(mAct, datas);
//		gridView.setAdapter(mAdapter);
		
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
//				ToastUtils.showMessage(mAct, "选中位置是: " + arg2);// + " --> " + datas.get(arg2)
				
				if(arg2 == mMenuAdapter.getCurrentSelection()) {
					if(DeviceTool.isOnViewDoubleClick(arg1, false)) {
//						ToastUtils.showMessage(mAct, "双击位置是: " + arg2);
					}
				} else {
				
				
				tvText.setText(menuRes.get(arg2).itemStrRid);
				
				if(mMenuAdapter != null) {
					mMenuAdapter.setSelectedIconRes(arg2);
				}
				}
				
			}
		});
		
//		gridView.setOnReorderingListener(new OnReorderingListener() {
//			
//			@Override
//			public void onReordering(int fromPosition, int toPosition) {
//				// TODO Auto-generated method stub
//				if(fromPosition != toPosition) {
//					ToastUtils.showMessage(mAct, fromPosition + "<-->" + toPosition);
//					
//					String from = datas.remove(fromPosition);
//					datas.add(toPosition, from);
//					mAdapter.notifyDataSetChanged();
//				}
//			}
//		});
		
		gridView.setOnChangeListener(new OnChanageListener() {
			
			@Override
			public void onChange(int from, int to) {
				// TODO Auto-generated method stub
				
				if(mMenuAdapter != null) {
					mMenuAdapter.onChange(from, to);
				}
				
//				GridMenuItem temp = menuRes.get(from);
//				//这里的处理需要注意下
//				if(from < to){
//					for(int i=from; i<to; i++){
//						Collections.swap(menuRes, i, i+1);
//					}
//				}else if(from > to){
//					for(int i=from; i>to; i--){
//						Collections.swap(menuRes, i, i-1);
//					}
//				}
//				menuRes.set(to, temp);
//				mMenuAdapter.setItemHide(to);
				
			}
		});
	}

}
