package com.artion.androiddemos.dialog;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.artion.androiddemos.R;

public class PopListWindow {
	
	public class ListItem {
		public int iconRid;
		public String title;
		public View.OnClickListener clickListener;
	}
	
	private PopupWindow mPopWin;
	private Context mContext;
	private TextView tvExtra;
	
	public void PopListWindow(Context context) {
		this.mContext = context;
		
		initPopWind();
	}
	
	private void initPopWind() {
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);
		View popupWindow = layoutInflater.inflate(R.layout.pop_list, null);
		ListView lv = (ListView) popupWindow.findViewById(R.id.pop_listview);
		tvExtra = (TextView) popupWindow.findViewById(R.id.pop_list_extra);
		List<ListItem> list = new ArrayList<ListItem>();
		PopAdapter mAdapter = new PopAdapter(mContext, list);
		lv.setAdapter(mAdapter);

		mPopWin = new PopupWindow(popupWindow, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mPopWin.setOutsideTouchable(true);  
		mPopWin.update();  
		mPopWin.setTouchable(true);  
		mPopWin.setFocusable(true);  
	}
	
	private class PopAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private List<ListItem> mDatas;
		
		public PopAdapter(Context context, List<ListItem> list) {
			this.mInflater = LayoutInflater.from(context);
			this.mDatas = list;
		}

		@Override
		public int getCount() {
			return mDatas.size();
		}

		@Override
		public Object getItem(int position) {
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null) {
				convertView = mInflater.inflate(R.layout.pop_list_item, null);
			}
			
			ListItem item = mDatas.get(position);
			
			TextView title = (TextView) convertView.findViewById(R.id.pop_list_item_title);
			title.setText(item.title);
			
			return convertView;
		}
		
		
	}

}
