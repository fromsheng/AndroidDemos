package com.artion.androiddemos.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.artion.androiddemos.R;

public class KdAlertBuilder extends Builder {
	
	private Context mContext;
	private View view;
	
	private ListView lv;
	private List<String> itemStrs;
	private DialogBottomAdapter mAdapter;
	private KdAlertItemListener mAlertItemListener;

	public KdAlertBuilder(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		this.mContext = context;
		
		setContentLayout(R.layout.dialog_bottom_list);
	}
	
	public KdAlertBuilder setDialogParams(AlertDialog dialog) {
		Window window = dialog.getWindow();
//		window.setWindowAnimations(R.style.dialog_bottom);
		window.setGravity(Gravity.BOTTOM);
		
		WindowManager wm = window.getWindowManager();
		WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
		// p.height = (int) (d.getHeight()); // 高度设置为屏幕的0.6
		p.width = (int) (wm.getDefaultDisplay().getWidth()); // 宽度设置为屏幕的0.65
		window.setAttributes(p);
		return this;
	}

	public KdAlertBuilder setContentLayout(int resId) {
		view = LayoutInflater.from(mContext).inflate(resId, null);
		setView(view);
		
		lv = (ListView) view.findViewById(R.id.dialog_lv);
		itemStrs = new ArrayList<String>();
		mAdapter = new DialogBottomAdapter(mContext, itemStrs);
		lv.setAdapter(mAdapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				dismiss();
				if(mAlertItemListener != null) {
					mAlertItemListener.onItemClick(arg2);
				}
			}
		});
		
		return this;
	}
	
	public KdAlertBuilder initItemDatas(List<String> items) {
		
		this.itemStrs.clear();
		this.itemStrs.addAll(items);
		mAdapter.notifyDataSetChanged();
		
		return this;
	}
	
	public KdAlertBuilder initItemListener(KdAlertItemListener mAlertItemListener) {
		this.mAlertItemListener = mAlertItemListener;
		
		return this;
	}
	
	public interface KdAlertItemListener {
		public void onItemClick(int position);
	}
}
