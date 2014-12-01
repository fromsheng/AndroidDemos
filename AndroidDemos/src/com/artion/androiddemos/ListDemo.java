package com.artion.androiddemos;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.utils.DebugTool;

public class ListDemo extends ListActivity {
	
	private ListView lv;
	private MyAdapter adapter;
	private String arrays[] = {
			"A", "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB", "C", "D",
			"E", "R", "G", "H",
			"I", "J", "K", "L",
			"M", "N", "O", "P"
	};
	
	private List<String> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
//		lv = (ListView) findViewById(android.R.id.list);
		
		lv = getListView();
		View headerView = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_list_item, null);
		lv.addHeaderView(headerView, null, false);
		View footerView = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_list_item, null);
		lv.addFooterView(footerView, null, false);
		View emptyView = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_list_item, null);
		addContentView(emptyView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT)); 
		lv.setEmptyView(emptyView);
		
		lv.setSelector(R.drawable.selector_timeline_item);
		
		
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ToastUtils.showMessage(ListDemo.this, "点击了 " + adapter.getItem(arg2 - lv.getHeaderViewsCount()));
			}
		});
		
		list = getDefaultList();
		adapter = new MyAdapter(this, android.R.layout.simple_list_item_1, list);
		lv.setAdapter(adapter);
		
		System.out.println("lv.height == " + lv.getHeight());
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				list.clear();
				adapter.notifyDataSetChanged();
				
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						list.addAll(getDefaultList());
						adapter.notifyDataSetChanged();
						
						
					}
				}, 5000);
			}
		}, 5000);
	}
	
	private List<String> getDefaultList() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			list.add("item + " + i);
		}
		
		return list;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		System.out.println("onWindowFocusChanged adapter.getCount == " + adapter.getCount());
		System.out.println("onWindowFocusChanged lv.height == " + lv.getHeight());
		
		getLVItemsHeight();
	}

	/**
	 * 取列表所有item的高度，包含diveer
	 * @return
	 */
	private int getLVItemsHeight() {
		int result = 0;
		
		if (adapter != null) {
			for (int i = 0, len = adapter.getCount(); i < len; i++) {
				View listItem = lv.getChildAt(i);
				int childItemHeight = 0;
				if(listItem == null) {
					listItem = adapter.getView(i, null, lv);
					listItem.measure(0, 0); // 计算子项View 的宽高，
					//ListView的每个Item必须是LinearLayout，
					//不能是其他的，因为其他的Layout(如RelativeLayout)没有重写onMeasure()，
					//所以会在onMeasure()时抛出异常。
					childItemHeight = listItem.getMeasuredHeight()
							+ lv.getDividerHeight();
				}else{
					childItemHeight = listItem.getHeight();
				}
				listItem.measure(0, 0); // 计算子项View 的宽高，
										//ListView的每个Item必须是LinearLayout，
										//不能是其他的，因为其他的Layout(如RelativeLayout)没有重写onMeasure()，
										//所以会在onMeasure()时抛出异常。
				int list_child_item_height = listItem.getMeasuredHeight()
						+ lv.getDividerHeight();
//				DebugTool.info("height", "listItem == " + lv.getChildAt(i).getHeight());
				DebugTool.info("height", "list_child_item_height == " + childItemHeight);
				result += childItemHeight; // 统计所有子项的总高度
			}
		}
		
		DebugTool.info("height", "getLVItemsHeight == " + result);
		return result;
	}
	
	private class MyAdapter extends ArrayAdapter<String> {

		public MyAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}

		public MyAdapter(Context context, int resource, String[] objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return super.getCount();
		}

		@Override
		public String getItem(int position) {
			// TODO Auto-generated method stub
			return super.getItem(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return super.getItemId(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
//			DebugTool.info("Demo", "getView == " + position);
			return super.getView(position, convertView, parent);
		}
		
	}

}
