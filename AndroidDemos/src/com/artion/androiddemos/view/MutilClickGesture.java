package com.artion.androiddemos.view;

import android.content.Context;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MutilClickGesture extends GestureDetector {
	
	private ClickEvent mClickEvent = null;
	
	public void setClickEvent(ClickEvent event) {
		this.mClickEvent = event;
	}

	public MutilClickGesture(Context context, OnGestureListener listener,
			Handler handler, boolean unused) {
		super(context, listener, handler, unused);
		// TODO Auto-generated constructor stub
		
		setListener();
	}

	public MutilClickGesture(Context context, OnGestureListener listener,
			Handler handler) {
		super(context, listener, handler);
		// TODO Auto-generated constructor stub
		
		setListener();
	}

	public MutilClickGesture(Context context, OnGestureListener listener) {
		super(context, listener);
		// TODO Auto-generated constructor stub
		
		setListener();
	}
	
	private void setListener() {
		setOnDoubleTapListener(new OnDoubleTapListener() {
			
			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				
				if(mClickEvent != null) {
					mClickEvent.onSingleTap();
				}
				
				return false;
			}
			
			@Override
			public boolean onDoubleTapEvent(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onDoubleTap(MotionEvent e) {
				// TODO Auto-generated method stub
				
				if(mClickEvent != null) {
					mClickEvent.onDoubleTap();
				}
				
				return false;
			}
		});
	}
	
	public interface ClickEvent {
		public void onSingleTap();
		public void onDoubleTap();
	}


}
