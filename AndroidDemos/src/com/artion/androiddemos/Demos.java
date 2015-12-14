package com.artion.androiddemos;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Demos extends BaseActivity {
	
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
		actNames.add("MainDemos");
		actNames.add("AnimationDemo");
		actNames.add("StartDoDemo");
		actNames.add("MyDrawViewAct");
		actNames.add("EditTextDemo");
		actNames.add("ImageCompressDemo");
		actNames.add("MoveViewDemo");
		actNames.add("ImagePressedDemo");
		actNames.add("ExtraViewDemo");
		actNames.add("NettyDemo");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_demos);
		
		initLayout();
		initListener();
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		listView = (ListView) findViewById(R.id.listview);
		initActNames();
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, actNames));
		
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String className = mAct.getPackageName() + "." + actNames.get(arg2);
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
