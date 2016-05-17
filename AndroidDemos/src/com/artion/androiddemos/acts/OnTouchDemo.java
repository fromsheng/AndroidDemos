package com.artion.androiddemos.acts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnHoverListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.utils.DebugTool;

public class OnTouchDemo extends BaseActivity {
	protected Button btn1;
	private long firstDownTime = 0L;
	private View popLayout;
	
	private GestureDetector mGestureDetector;  
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_ontouch);
		
		initLayout();
		initListener();
		
		Window window = getWindow();
		window.getDecorView().addOnLayoutChangeListener(new OnLayoutChangeListener() {
			
			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				DebugTool.info(tag, "onLayoutChange new== " + left + "==" + top + "==" + right + "==" + bottom);
				DebugTool.info(tag, "onLayoutChange old== " + oldLeft + "==" + oldTop + "==" + oldRight + "==" + oldBottom);
			}
		});
		
		findViewById(R.id.ontouch_root).setOnHoverListener(new OnHoverListener() {
			
			@Override
			public boolean onHover(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					DebugTool.info(tag, "onHover ACTION_DOWN");
					break;
				case MotionEvent.ACTION_UP:
					DebugTool.info(tag, "onHover ACTION_UP");
					break;
				case MotionEvent.ACTION_MOVE:
					DebugTool.info(tag, "onHover ACTION_MOVE");
					break;
				case MotionEvent.ACTION_HOVER_ENTER:
					DebugTool.info(tag, "onHover ACTION_HOVER_ENTER");
					break;
				case MotionEvent.ACTION_HOVER_MOVE:
					DebugTool.info(tag, "onHover ACTION_HOVER_MOVE");
					break;
				case MotionEvent.ACTION_HOVER_EXIT:
					DebugTool.info(tag, "onHover ACTION_HOVER_EXIT");
					break;

				default:
					break;
				}
				return false;
			}
		});
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		btn1 = (Button) findViewById(R.id.button1);
		popLayout = findViewById(R.id.poplayout);
	}

	@Override
	protected void initListener() {
		super.initListener();
		mGestureDetector = new GestureDetector(new gestureListener()); //使用派生自OnGestureListener  
//		popLayout.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//					DebugTool.info(tag, "popLayout ACTION_DOWN");
//					break;
//				case MotionEvent.ACTION_MOVE:
//					DebugTool.info(tag, "popLayout ACTION_MOVE");
//					break;
//				case MotionEvent.ACTION_UP:
//					DebugTool.info(tag, "popLayout ACTION_UP");
//					break;
//				}
//				return true;
//			}
//		});
		/*btn1.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				boolean flag = mGestureDetector.onTouchEvent(event);
				if(flag) {
					return flag;
				} else {
					if(event.getAction() == MotionEvent.ACTION_UP){
						DebugTool.info(tag, "ACTION_UP");
						ToastUtils.showMessage(mAct, "ACTION_UP");
						return true;
					}
				}
				return true;
			}
		});*/
		btn1.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					firstDownTime = System.currentTimeMillis();
					DebugTool.info(tag, "ACTION_DOWN");
					break;
				case MotionEvent.ACTION_MOVE:
					if(firstDownTime != 0L && System.currentTimeMillis() - firstDownTime > 500) {
						firstDownTime = 0L;
						popLayout.setVisibility(View.VISIBLE);
					}
					DebugTool.info(tag, "ACTION_MOVE");
					break;
				case MotionEvent.ACTION_UP:
					DebugTool.info(tag, "ACTION_UP");
					ToastUtils.showMessage(mAct, "ACTION_UP");
					popLayout.setVisibility(View.GONE);
					break;
					
				default:
					break;
				}
				return false;
			}
		});
		
//		btn1.setOnClickListener(new OnClickListener() {
//			
//			@SuppressLint("NewApi")
//			@Override
//			public void onClick(View v) {
//				getWindow().getDecorView().setHovered(true);		
//			}
//		});

	}
	
	private class gestureListener implements GestureDetector.OnGestureListener{  
		  
        // 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发     
        public boolean onDown(MotionEvent e) {  
        	DebugTool.info(tag, "onDown");  
            ToastUtils.showMessage(mAct, "onDown");   
            return false;  
        }  
  
        /*   
         * 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发   
         * 注意和onDown()的区别，强调的是没有松开或者拖动的状态   
         *  
         * 而onDown也是由一个MotionEventACTION_DOWN触发的，但是他没有任何限制， 
         * 也就是说当用户点击的时候，首先MotionEventACTION_DOWN，onDown就会执行， 
         * 如果在按下的瞬间没有松开或者是拖动的时候onShowPress就会执行，如果是按下的时间超过瞬间 
         * （这块我也不太清楚瞬间的时间差是多少，一般情况下都会执行onShowPress），拖动了，就不执行onShowPress。 
         */  
        public void onShowPress(MotionEvent e) {  
        	DebugTool.info(tag, "onShowPress");  
            ToastUtils.showMessage(mAct, "onShowPress");   
        }  
  
        // 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发     
        ///轻击一下屏幕，立刻抬起来，才会有这个触发  
        //从名子也可以看出,一次单独的轻击抬起操作,当然,如果除了Down以外还有其它操作,那就不再算是Single操作了,所以这个事件 就不再响应  
        public boolean onSingleTapUp(MotionEvent e) {  
        	DebugTool.info(tag, "onSingleTapUp");  
            ToastUtils.showMessage(mAct, "onSingleTapUp");   
            return true;     
        }  
  
        // 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发     
        public boolean onScroll(MotionEvent e1, MotionEvent e2,  
                float distanceX, float distanceY) {  
        	DebugTool.info(tag, "onScroll");  
            ToastUtils.showMessage(mAct, "onScroll");   
              
            return true;     
        }  
  
        // 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发     
        public void onLongPress(MotionEvent e) {  
        	DebugTool.info(tag, "onLongPress");  
            ToastUtils.showMessage(mAct, "onLongPress");   
        }  
  
        // 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发     
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
                float velocityY) {  
        	DebugTool.info(tag, "onFling");  
            ToastUtils.showMessage(mAct, "onDown");   
            return true;  
        }  
    }; 

}
