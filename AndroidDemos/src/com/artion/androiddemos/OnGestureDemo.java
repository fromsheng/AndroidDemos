package com.artion.androiddemos;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artion.androiddemos.utils.DebugTool;
import com.artion.androiddemos.view.MutilClickGesture;
import com.artion.androiddemos.view.MutilClickGesture.ClickEvent;

public class OnGestureDemo extends BaseActivity  implements OnGestureListener {
	private LinearLayout main;        
	  private TextView viewA;  
//	  private GestureDetector gestureScanner;  
	  
	  private MutilClickGesture mGesture;
	  @Override   
	  public void onCreate(Bundle savedInstanceState) {    
	    super.onCreate(savedInstanceState);    
//	    gestureScanner = new GestureDetector(this);    
//	    gestureScanner.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener(){   
//	      public boolean onDoubleTap(MotionEvent e) {   
//	        viewA.setText("-" + "onDoubleTap" + "-");   
//	        //双击时产生一次  
//	        Log.v("test", "onDoubleTap");  
//	        return false;   
//	      }  
//	      public boolean onDoubleTapEvent(MotionEvent e) {   
//	        //双击时产生两次  
//	        Log.v("test", "onDoubleTapEvent");  
//	        return false;  
//	      }   
//	      public boolean onSingleTapConfirmed(MotionEvent e) {   
//	        viewA.setText("-" + "onSingleTapConfirmed" + "-");   
//	        //短快的点击算一次单击  
//	        Log.v("test", "onSingleTapConfirmed");  
//	        return false;   
//	      }   
//	    });  
	    
	    mGesture = new MutilClickGesture(this, this);
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
	  
	    main = new LinearLayout(this);    
	    main.setBackgroundColor(Color.GRAY);  
	    main.setLayoutParams(new LinearLayout.LayoutParams(320,480));  
	    main.setOrientation(LinearLayout.VERTICAL);  
	    viewA = new TextView(this);  
	    viewA.setBackgroundColor(Color.YELLOW);  
	    viewA.setTextColor(Color.BLACK);    
	    viewA.setTextSize(16);  
	    viewA.setLayoutParams(new LinearLayout.LayoutParams(320,50));    
	    main.addView(viewA);  
	    main.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return mGesture.onTouchEvent(event);
			}
		});
	    
	    main.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DebugTool.info("Gesture", "onClick");
			}
		});
	    setContentView(main);    
	  }  
//	  public boolean onTouchEvent(MotionEvent me) {    
////	    return gestureScanner.onTouchEvent(me); 
//		  DebugTool.info("Gesture", "onTouchEvent");
//		  return mGesture.onTouchEvent(me);
//	  }    
	  public boolean onDown(MotionEvent e) {    
	    //viewA.setText("-" + "DOWN" + "-");    
	    return true;    
	  }    
	  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {    
	    //viewA.setText("-" + "FLING" + "- "+velocityX + "- "+velocityY);    
	    Log.v("test", "onFling "+e1.getX()+" "+e2.getX());  
	    return true;    
	  }    
	   
	  public void onLongPress(MotionEvent e) {    
	    //viewA.setText("-" + "LONG PRESS" + "-");    
	  }    
	  public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {    
	    //viewA.setText("-" + "SCROLL" + "- "+distanceX + "- "+distanceY);    
	    Log.v("test", "onScroll "+e1.getX()+" "+e2.getX());  
	    return true;    
	  }    
	  public void onShowPress(MotionEvent e) {    
	    //viewA.setText("-" + "SHOW PRESS" + "-");    
	  }  
	  public boolean onSingleTapUp(MotionEvent e) {    
	    Log.v("test", "onSingleTapUp");  
	    //viewA.setText("-" + "SINGLE TAP UP" + "-"+ i++);    
	    return true;    
	  }    
}
