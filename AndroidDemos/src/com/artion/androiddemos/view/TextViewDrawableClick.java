package com.artion.androiddemos.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class TextViewDrawableClick extends TextView {
	
	private OnClickListener mLeftListener, mRightListener, mTopListener, mBottomListener;

    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;

	public TextViewDrawableClick(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public TextViewDrawableClick(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TextViewDrawableClick(Context context) {
		super(context);
	}
	
	
	public void setDrawableLeftListener(OnClickListener listener) {
        this.mLeftListener = listener;
    }

    public void setDrawableRightListener(OnClickListener listener) {
        this.mRightListener = listener;
    }
    
    public void setDrawableTopListener(OnClickListener listener) {
    	this.mTopListener = listener;
    }
    
    public void setDrawableBottomListener(OnClickListener listener) {
    	this.mBottomListener = listener;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            	float x = event.getX();
            	float y = event.getY();
            	if (mLeftListener != null) {
                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                    if (drawableLeft != null && x <= drawableLeft.getBounds().width()) {
                        mLeftListener.onClick(this);
                        setPressed(false);
                        return true;
                    }
                }
            	
            	if (mTopListener != null) {
                    Drawable drawableTop = getCompoundDrawables()[DRAWABLE_TOP];
                    if (drawableTop != null && y <= drawableTop.getBounds().height()) {
                    	mTopListener.onClick(this);
                    	setPressed(false);
                    	return true;
                    }
                }
            	
                if (mRightListener != null) {
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawableRight != null && x >= (getWidth() - drawableRight.getBounds().width())) {
                        mRightListener.onClick(this);
                        setPressed(false);
                        return true;
                    }
                }
                
                if (mBottomListener != null) {
                    Drawable drawableBottom = getCompoundDrawables()[DRAWABLE_BOTTOM];
                    if (drawableBottom != null && y >= (getHeight() - drawableBottom.getBounds().height())) {
                    	mBottomListener.onClick(this);
                    	setPressed(false);
                    	return true;
                    }
                }
                
                break;
        }
        return super.onTouchEvent(event);
    }

}
