package com.artion.androiddemos.adapter;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.artion.androiddemos.R;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.utils.DebugTool;
import com.artion.androiddemos.utils.DeviceTool;
import com.artion.androiddemos.utils.FileSaveManager;
import com.artion.androiddemos.view.BadgeView;
import com.artion.androiddemos.view.MutilClickGesture;
import com.artion.androiddemos.view.MutilClickGesture.ClickEvent;

public class GVMenuAdapter extends BaseAdapter {
	
	private Context ctx;
	private LayoutInflater mInflater;
	private List<GridMenuItem> datas;
	private int mHidePosition = -1;
	
	private MutilClickGesture mGesture;
	
	public static final String SAVE_FILE_NAME = "GridMenuList.data";

	public GVMenuAdapter(Context ctx, List<GridMenuItem> datas) {
		this.ctx = ctx;
		this.mInflater = LayoutInflater.from(ctx);
		this.datas = datas;
		
		mGesture = new MutilClickGesture(ctx, new OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		mGesture.setClickEvent(new ClickEvent() {
			
			@Override
			public void onSingleTap() {
				// TODO Auto-generated method stub
				DebugTool.info("Gesture", "onSingleTap");
			}
			
			@Override
			public void onDoubleTap() {
				// TODO Auto-generated method stub
				DebugTool.info("Gesture", "onDoubleTap");
			}
		});
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return datas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GridMenuItem item = datas.get(position);
		
		convertView = mInflater.inflate(R.layout.act_footer_menu_item, null);
		ImageView icon = (ImageView) convertView.findViewById(R.id.footer_menu_item_icon);
		TextView text = (TextView) convertView.findViewById(R.id.footer_menu_item_text);
		icon.setImageResource(item.itemSelected ? item.iconDownRid : item.iconNormalRid);
		text.setText(item.itemStrRid);
		DeviceTool.setImageTouchDark(icon);
		View layout = convertView.findViewById(R.id.footer_menu_item_ll_details);
		BadgeView badgeView = (BadgeView) layout.getTag();
		if(badgeView == null) {
			badgeView = new BadgeView(ctx, layout);
			badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
			layout.setTag(badgeView);
		}
		if(item.unReadCount > 0) {
			badgeView.setText(item.unReadCount >99 ? "99+" : String.valueOf(item.unReadCount));
			badgeView.showSmallNumber();
		} else {
			badgeView.hide();
		}
		
		if(position == mHidePosition){
//			mHidePosition = -1;
			convertView.setVisibility(View.INVISIBLE);
		}
		
		if(position == 0) {
			layout.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					return mGesture.onTouchEvent(event);
				}
			});
		}
		
		
		return convertView;
	}
	
	public int getCurrentSelection() {
		GridMenuItem item = null;
		for (int i = 0; i < datas.size(); i++) {
			item = datas.get(i);
			if(item.itemSelected) {
				return i;
			}
		}
		return 0;
	}
	
	public void setItemUnreadCount(int position, int unreadCount) {
		GridMenuItem item = null;
		for (int i = 0; i < getCount(); i++) {
			item = (GridMenuItem) getItem(i);
			if(position == i) {
				item.unReadCount = unreadCount;
			}
		}
		
		notifyDataSetChanged();
	}
	
	public void setSelectedIconRes(int downPosition) {
		GridMenuItem item = null;
		for (int i = 0; i < getCount(); i++) {
			item = (GridMenuItem) getItem(i);
			item.itemSelected = (downPosition == i);
		}
		
		setItemHide(-1);
	}
	
	public void onChange(int from, int to) {
		
		ToastUtils.showMessage(ctx, from + " -- > " + to);
		
		GridMenuItem temp = datas.get(from);
		
		//直接调换
//		datas.set(from, datas.get(to));
//		datas.set(to, temp);
		
		//这里的处理需要注意下
		if(from < to){
			for(int i=from; i<to; i++){
				Collections.swap(datas, i, i+1);
			}
		}else if(from > to){
			for(int i=from; i>to; i--){
				Collections.swap(datas, i, i-1);
			}
		}
		datas.set(to, temp);
		setItemHide(to);
	}
	
	public void initListDatas() {
		new AsyncTask<Void, Void, Object>() {

			@Override
			protected Object doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
				List<GridMenuItem> tmp = (List<GridMenuItem>) FileSaveManager.getInfosFromFile(ctx, SAVE_FILE_NAME);
				if(tmp != null && tmp.size() > 0) {
					datas.clear();
					datas.addAll(tmp);
				} else {
//					initMenuRes(0);
				}
				
				return null;
			}

			@Override
			protected void onPostExecute(Object result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				
				notifyDataSetChanged();
				
				ToastUtils.showMessage(ctx, "默认选中位置:" + getCurrentSelection());
			}
			
			
		}.execute();
	}
	
	/**
	 * 执行线程缓存当前的菜单列表
	 */
	public void saveListDatas() {
		if(datas != null && datas.size() > 0) {
			new AsyncTask<Void, Void, Object>() {

				@Override
				protected Object doInBackground(Void... params) {
					// TODO Auto-generated method stub
					
					FileSaveManager.saveInfosToFile(ctx, "GridMenuList.data", datas);
					
					return null;
				}
				
			}.execute();
		}
	}
	
	/**
	 * 设置某项隐藏
	 * @param position
	 */
	public void setItemHide(int position){
		this.mHidePosition = position; 
		notifyDataSetChanged();
	}

}
