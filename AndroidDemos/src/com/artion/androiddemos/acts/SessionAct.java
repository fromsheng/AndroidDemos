package com.artion.androiddemos.acts;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.R.id;
import com.artion.androiddemos.R.layout;
import com.artion.androiddemos.adapter.SessionAdapter;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.domain.SessionItem;

public class SessionAct extends BaseActivity {
	
	private ListView sessionListView;

	private SessionAdapter mAdapter;
	private List<SessionItem> mSessionList;
	
	private void initList(int size) {
		mSessionList = new ArrayList<SessionItem>();
		SessionItem item = null;
		for (int i = 0; i < size; i++) {
			item = new SessionItem();
			item.sessionTime = "time -> " + i;
			item.userName = i % 3 == 0 ? "Artion" :"名字 " + i;
			item.sessionType = i % 7 == 0 ? SessionItem.SESSION_TYPE_SHARE : SessionItem.SESSION_TYPE_TEXT;
			if(i % 11 == 0) {
				item.sessionType = SessionItem.SESSION_TYPE_NEWS;
			}
			item.shareTitle = "这是我分享的内容： " + i;
			item.sessionContent = "发表内容包含了: " + i;
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
		
		initList(50);
		mAdapter = new SessionAdapter(mAct, mSessionList);
		sessionListView.setAdapter(mAdapter);
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		
		sessionListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				SessionItem item = mSessionList.get(arg2);
				ToastUtils.showMessage(mAct, item.userName + " -- " + item.sessionContent);
			}
		});
	}

}
