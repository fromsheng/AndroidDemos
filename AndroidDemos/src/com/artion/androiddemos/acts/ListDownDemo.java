package com.artion.androiddemos.acts;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.utils.DebugTool;

public class ListDownDemo extends BaseActivity {
	
	private Button btn;
	private TextView tv;
	private ListView lv;
	ArrayAdapter mAdapter;
	List<String> lists = new ArrayList<String>();
	private LinearLayout ll;
	
	private boolean isShowing = false;
	
	private String arrays[] = {
			"A", "B", "C", "D",
			"E", "R", "G", "H",
			"I", "J", "K", "L",
			"M", "N", "O", "P"
	};
	
	private Animation pushDown, pushUp;
	private Animation alphaIn, alphaOut;

	private static int lastPos, firstTop;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_list2);
		
		initLayout();
		initListener();
		
		WindowManager wm = getWindowManager();
		Window window = getWindow();
		window.getDecorView().addOnLayoutChangeListener(new OnLayoutChangeListener() {
			
			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				DebugTool.info(tag, "onLayoutChange new== " + left + "==" + top + "==" + right + "==" + bottom);
				DebugTool.info(tag, "onLayoutChange old== " + oldLeft + "==" + oldTop + "==" + oldRight + "==" + oldBottom);
			}
		});
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		DebugTool.info(tag, "onWindowFocusChanged == " + hasFocus);
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		btn = (Button) findViewById(R.id.btn);
		tv = (TextView) findViewById(R.id.tv);
		lv = (ListView) findViewById(R.id.listview);
		ll = (LinearLayout) findViewById(R.id.ll_out);
		
		lv.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 不滚动时保存当前滚动到的位置  
		        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) { 
		        	lastPos = lv.getFirstVisiblePosition();
		        	View firstView = lv.getChildAt(0);
		        	firstTop = (firstView == null) ? 0 : firstView.getTop();
		        } 
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
		});
		
//		mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrays);
		for (int i = 0; i < 20; i++) {
			lists.add("abcdefghijklmnopqrstuvwxyz" + i); 
		}
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lists);
		lv.setAdapter(mAdapter);
		
		pushDown = AnimationUtils.loadAnimation(this, R.anim.push_down);
		pushUp = AnimationUtils.loadAnimation(this, R.anim.push_up);
		alphaIn = AnimationUtils.loadAnimation(this, R.anim.alpha_in);
		alphaOut = AnimationUtils.loadAnimation(this, R.anim.alpha_out);
		
//		tv.setVisibility(View.GONE);
		ll.setVisibility(View.GONE);
		
//		startTranslate(tv, 0, -100, View.GONE);//如需tv也跟着走解注释
		lv.setSelectionFromTop(lastPos, firstTop);
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		
		ll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DebugTool.info("Demo", "点击我做什么 ");
			}
		});
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isShowing) {
					btn.setText("hide");
					//需要显示出来
				}else{
					btn.setText("show");
					//需要隐藏起来
				}
				ajustView(isShowing);
				isShowing = !isShowing;
				
				
			}
		});
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ToastUtils.showMessage(ListDownDemo.this, "点击了 " + lists.get(arg2));
			}
		});
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
//				deleteCell(arg1, arg2);
				delRightTranslate(arg1, arg2);
				return true;
			}
		});
	}
	
	private void deleteCell(final View v, final int index) {  
	    AnimationListener al = new AnimationListener() {  
	        @Override  
	        public void onAnimationEnd(Animation arg0) {  
	        	
	            lists.remove(index);
	            mAdapter.notifyDataSetChanged();  
	        }  
	        @Override public void onAnimationRepeat(Animation animation) {}  
	        @Override public void onAnimationStart(Animation animation) {}  
	    };  
	  
	    collapse(v, al);  
	}  
	  
	private void collapse(final View v, AnimationListener al) {  
	    final int initialHeight = v.getMeasuredHeight();  
	    final int initialWidth = v.getMeasuredWidth();  
	    final int paddingLeft = v.getPaddingLeft();
	    final int paddingTop = v.getPaddingTop();
	    final int paddingRight = v.getPaddingRight();
	    final int paddingBottom = v.getPaddingBottom();
	    
	  
	    Animation anim = new Animation() {  
	        @Override  
	        protected void applyTransformation(float interpolatedTime, Transformation t) {  
	            if (interpolatedTime == 1) {  
	                v.getLayoutParams().height = 0;
	                v.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
	                v.requestLayout();  
	            }  
	            else {  
//	                v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);  
//	                v.getLayoutParams().width = initialWidth - (int)(initialWidth * interpolatedTime); 
	            	
	                v.setPadding((int)(initialWidth * interpolatedTime), paddingTop, paddingRight, paddingBottom);
	                v.requestLayout();  
	            }  
	        }  
	  
	        @Override  
	        public boolean willChangeBounds() {  
	            return true;  
	        }  
	    };  
	  
	    if (al!=null) {  
	        anim.setAnimationListener(al);  
	    }  
	    anim.setDuration(500);  
	    v.startAnimation(anim);  
	}  
	
	private void ajustView(boolean isShowing) {
		if(isShowing){
//			tv.setVisibility(View.GONE);
//			tv.startAnimation(alphaOut);
//			DebugTool.info("height", "tv.getHeight() == " + tv.getHeight());
			startTranslate(lv, 0, -100, View.GONE);
//			startTranslate(tv, 0, -100, View.GONE);//如需tv也跟着走解注释
		}else{
//			tv.setVisibility(View.VISIBLE);
//			tv.startAnimation(alphaIn);
//			DebugTool.info("height", "tv.getHeight() == " + tv.getHeight());
			startTranslate(lv, 0, 100, View.VISIBLE);
//			startTranslate(tv, 0, 100, View.GONE);//如需tv也跟着走解注释
		}
	}
	
	private void delRightTranslate(final View view, final int index) {
		TranslateAnimation animation = new TranslateAnimation(0, view.getWidth(), 0, 0);
		animation.setDuration(500);
//		animation.setFillAfter(true);//只是将view移动到了目标位置，但是view绑定的点击事件还在原来位置
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				
				lists.remove(index);
	            mAdapter.notifyDataSetChanged();  				
				
			}
		});
//		view.setAnimation(animation);
		view.startAnimation(animation);
	}
	
	private void startTranslate(final View view, final int fromY, final int toY, final int status) {
		TranslateAnimation animation = new TranslateAnimation(0, 0, fromY, toY);
		animation.setDuration(500);
//		animation.setFillAfter(true);//只是将view移动到了目标位置，但是view绑定的点击事件还在原来位置
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				
				int l = view.getLeft();
				int width = view.getWidth();
				int height = view.getHeight();
				int t = view.getTop() + (toY - fromY);
				view.clearAnimation();
				
				view.layout(l, t, l + width, t + height);
				
				
			}
		});
//		view.setAnimation(animation);
		view.startAnimation(animation);
		
	}

}
