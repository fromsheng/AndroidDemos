package com.artion.androiddemos.acts;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.adapter.ImageLockAdapter;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.utils.DebugTool;

public class ImageLockDemo extends BaseActivity {

	private ListView sessionListView;

	private ImageLockAdapter mAdapter;
	private List<String> mSessionList;
	
	private void initList(int size) {
		mSessionList = new ArrayList<String>();
		String item = null;
		for (int i = 0; i < size; i++) {
			item = "这是我分享的内容： " + i;
			mSessionList.add(item);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_session);
		
		initLayout();
		initListener();
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		sessionListView = (ListView) findViewById(R.id.session_listview);
		
		initList(100);
		mAdapter = new ImageLockAdapter(mAct, mSessionList);
		sessionListView.setAdapter(mAdapter);
	}

	@Override
	protected void initListener() {
		super.initListener();
		
		sessionListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ToastUtils.showMessage(mAct, mSessionList.get(arg2));
			}
		});
		
		sessionListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
	            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
	                // 手指触屏拉动准备滚动，只触发一次        顺序: 1
	            	mAdapter.setIsLocked(true);
	            	DebugTool.info(tag, "SCROLL_STATE_TOUCH_SCROLL");
	                break;
	            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
	                // 持续滚动开始，只触发一次                顺序: 2
	            	DebugTool.info(tag, "SCROLL_STATE_FLING");
	                break;
	            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
	                // 整个滚动事件结束，只触发一次            顺序: 4
	            	DebugTool.info(tag, "SCROLL_STATE_IDLE");
	            	mAdapter.setIsLocked(false);
					mAdapter.notifyDataSetChanged();
	                break;
	            default:
	                break;
	        }
//				mAdapter.setIsLocked(false);
//				mAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// 一直在滚动中，多次触发                          顺序: 3
//				DebugTool.info(tag, "onScroll");
//				mAdapter.setIsLocked(true);
			}
		});
	}

}
