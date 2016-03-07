package com.artion.androiddemos.acts;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.artion.androiddemos.R;
import com.artion.androiddemos.R.id;
import com.artion.androiddemos.R.layout;
import com.artion.androiddemos.frame.FrameActivity;

public class MainDemos extends FrameActivity {
	
	private ListView listView;
	private List<String> actNames;
	
	private void initActNames() {
		actNames = new ArrayList<String>();
		actNames.add("MainActivity");
		actNames.add("ListDemo");
		actNames.add("ListDownDemo");
		actNames.add("ImageViewSrcDemo");
		actNames.add("GridViewMenuDemo");
		actNames.add("SingleImageViewDemo");
		actNames.add("MultiImageViewDemo");
		actNames.add("OnGestureDemo");
		actNames.add("TextLongDemo");
		actNames.add("TextViewLongDemo");
		actNames.add("TranslateDemo");
		actNames.add("SessionAct");
		actNames.add("PopupWindowDemoActivity");
		actNames.add("SensorTestActivity");
		actNames.add("ViewPopupWindowDemo");
		actNames.add("CroutonDemo");
		actNames.add("TopPopupDemo");
		actNames.add("LoadingDialogDemo");
	}

	@Override
	public int getLayoutRid() {
		return R.layout.act_demos;
	}

	@Override
	public void initLayout() {
		listView = (ListView) findViewById(R.id.listview);
		initActNames();
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, actNames));
	}

	@Override
	public void initListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String className = mAct.getPackageName() + ".acts." + actNames.get(arg2);
				Class itemClass;
				try {
					itemClass = Class.forName(className);
					
					Intent intent = new Intent(mAct, itemClass);
					startActivity(intent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		});
	}

}
