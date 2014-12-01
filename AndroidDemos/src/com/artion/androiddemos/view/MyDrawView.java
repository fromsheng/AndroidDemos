package com.artion.androiddemos.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.artion.androiddemos.R;
import com.artion.androiddemos.common.ToastUtils;

public class MyDrawView extends View implements OnClickListener {
	
	/**  
     * 屏幕的宽  
     */  
    private int width=400;   
       
    /**  
     * 屏幕的高  
     */  
    private int height=400;   
       
    /**  
     *  颜色区分区域  
     */  
    private int[] colors = new int[] { Color.BLACK, Color.BLUE, Color.CYAN,   
            Color.GREEN, Color.GRAY, Color.MAGENTA, Color.RED, Color.LTGRAY};   
    private String[] colorStrs = new String[] {   
            "黑色", "蓝色", "青绿色", "绿色", "灰色", "洋红色", "红色", "浅灰色"};   
       
    /**  
     * 大园半径  
     */  
    private float bigR;   
       
    /**  
     * 小圆半径  
     */  
    private float litterR;   
       
    /**  
     * 屏幕中间点的X坐标  
     */  
    private float centerX;   
       
    /**  
     * 屏幕中间点的Y坐标  
     */  
    private float centerY;   

	public MyDrawView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		// 设置两个圆的半径   
        bigR = (width - 20)/2;   
        litterR = bigR/2;   
           
        centerX = width/2;   
        centerY = height/2; 
	}

	public MyDrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		// 设置两个圆的半径   
        bigR = (width - 20)/2;   
        litterR = bigR/2;   
           
        centerX = width/2;   
        centerY = height/2; 
	}

	public MyDrawView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		// 设置两个圆的半径   
        bigR = (width - 20)/2;   
        litterR = bigR/2;   
           
        centerX = width/2;   
        centerY = height/2; 
	}

	private int count = 0;
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
//		// 首先定义一个paint   
//        Paint paint = new Paint();   
//
//        // 绘制矩形区域-实心矩形   
//        // 设置颜色   
//        paint.setColor(Color.BLUE);   
//        // 设置样式-填充   
//        paint.setStyle(Style.FILL);   
//        // 绘制一个矩形   
//        canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);   
//
//        // 绘空心矩形   
//        // 设置颜色   
//        paint.setColor(Color.RED);   
//        // 设置样式-空心矩形   
//        paint.setStyle(Style.STROKE);   
//        // 绘制一个矩形   
//        canvas.drawRect(new Rect(10, 10, 300, 200), paint);   
//
//        // 绘文字   
//        // 设置颜色   
//        paint.setColor(Color.GREEN);  
//        paint.setTextSize(18);
//        // 绘文字   
//        canvas.drawText("Hello", 10, 50, paint);   
//
//        // 绘图   
//        // 从资源文件中生成位图   
//        Bitmap bitmap = null;
//        if(count % 2 == 0) {
//        	bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_blue_normal);   
//        } else {
//        	bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_blue_down);   
//        }
//        // 绘图   
//        canvas.drawBitmap(bitmap, 10, 60, paint);   
//        
//        setOnClickListener(this);
        
		Paint p = new Paint();   
		// 绘制矩形区域-实心矩形   
		// 设置颜色   
		p.setColor(Color.BLUE);   
		// 设置样式-填充   
		p.setStyle(Style.FILL);
		canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), p);
     // 画背景颜色   
        Paint bg = new Paint();   
        bg.setColor(Color.WHITE);   
        Rect bgR = new Rect(0, 0, width, height);   
        canvas.drawRect(bgR, bg);   
           
        float start = 0F;   
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);   
        for(int i = 0; i < 4; i ++) {   
            //注意一定要先画大圆，再画小圆，不然看不到效果，小圆在下面会被大圆覆盖   
            // 画大圆   
            RectF bigOval = new RectF(centerX - bigR, centerY - bigR,    
                    centerX + bigR, centerY + bigR);   
            paint.setColor(colors[i]);   
            canvas.drawArc(bigOval, start, 90, true, paint);   
               
            // 画小圆   
            RectF litterOval = new RectF(centerX - litterR, centerY - litterR,    
                    centerX + litterR, centerY + litterR);   
            paint.setColor(colors[i+2]);   
            canvas.drawArc(litterOval, start, 90, true, paint);   
               
            start += 90F;   
        }   
        
        bg.setColor(Color.LTGRAY);   
        Rect bgR2 = new Rect(0, height, width, 2*height);   
        canvas.drawRect(bgR2, bg); 
        
        float start2 = 0F;   
        Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);   
        for(int i = 0; i < 2; i ++) {   
            //注意一定要先画大圆，再画小圆，不然看不到效果，小圆在下面会被大圆覆盖   
            // 画大圆   
            RectF bigOval = new RectF(centerX - bigR, centerY - bigR + height,    
                    centerX + bigR, centerY + bigR + height); 
            paint2.setColor(getColor(count, true));  
            canvas.drawArc(bigOval, start2, 180, true, paint2);   
               
            // 画小圆   
            RectF litterOval = new RectF(centerX - litterR, centerY - litterR + height,    
                    centerX + litterR, centerY + litterR + height);   
            paint2.setColor(getColor(count, false));  
            canvas.drawArc(litterOval, start2, 180, true, paint2);   
               
            start2 += 180F;   
        }   
        
        
	}

	private int smallColorNormal = Color.GRAY;
	private int smallColorPress = Color.BLUE;
	private int smallColorDown = Color.YELLOW;
	private int bigColorNormal = Color.RED;
	private int bigColorPress = Color.CYAN;
	private int bigColorDown = Color.MAGENTA;
	
	private int getColor(int count, boolean isBig) {
		int color = isBig ? bigColorNormal : smallColorNormal;
		switch (count % 3) {
		case 1:
			color = isBig ? bigColorDown : smallColorDown;
			break;
		case 2:
			color = isBig ? bigColorPress : smallColorPress;
			break;
		default:
			color = isBig ? bigColorNormal : smallColorNormal;
			break;
		}
		return color;
	}
	
	@Override
	public void onClick(View v) {
		count ++;
		ToastUtils.showMessage(getContext(), "onclick");
		invalidate();
	}
	 String tips = "";
	@Override  
    public boolean onTouchEvent(MotionEvent event) {   
        // 获取点击屏幕时的点的坐标   
        float x = event.getX();   
        float y = event.getY();   
//        whichCircle(x, y);  
       
        switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			count = 1;
			invalidate();
			tips = getWhichCircle(x, y);
			return !super.onTouchEvent(event);
//			break;
		case MotionEvent.ACTION_UP:
			count = 0;
			ToastUtils.showMessage(getContext(), tips);
			invalidate();
			break;

		default:
			break;
		}
        return super.onTouchEvent(event);   
    }   

	private String getWhichCircle(float x, float y) {
		// 将屏幕中的点转换成以屏幕中心为原点的坐标点   
        float mx = x - centerX;   
        float my = y - centerY;   
        float result = mx * mx + my * my;   
           
        StringBuilder tip = new StringBuilder();   
        tip.append("您点击了");   
        // 高中的解析几何   
        if(result <= litterR*litterR) {// 点击的点在小圆内   
            tip.append("小圆的");   
            tip.append(colorStrs[whichZone(mx, my)+2]);   
            tip.append("区域");   
        } else if(result <= bigR * bigR) {// 点击的点在大圆内   
            tip.append("大圆的");   
            tip.append(colorStrs[whichZone(mx, my)]);   
            tip.append("区域");   
        } else {// 点不在作作区域   
            tip.append("作用区域以外的区域");   
        }   
        
        return tip.toString();
	}
	
    /**  
     * 确定点击的点在哪个圆内  
     * @param x  
     * @param y  
     */  
    private void whichCircle(float x, float y) {   
        // 将屏幕中的点转换成以屏幕中心为原点的坐标点   
        float mx = x - centerX;   
        float my = y - centerY;   
        float result = mx * mx + my * my;   
           
        StringBuilder tip = new StringBuilder();   
        tip.append("您点击了");   
        // 高中的解析几何   
        if(result <= litterR*litterR) {// 点击的点在小圆内   
            tip.append("小圆的");   
            tip.append(colorStrs[whichZone(mx, my)+2]);   
            tip.append("区域");   
        } else if(result <= bigR * bigR) {// 点击的点在大圆内   
            tip.append("大圆的");   
            tip.append(colorStrs[whichZone(mx, my)]);   
            tip.append("区域");   
        } else {// 点不在作作区域   
            tip.append("作用区域以外的区域");   
        }   
        ToastUtils.showMessage(getContext(), tip.toString());  
    }   
       
    /**  
     * 判断点击了圆的哪个区域  
     * @param x  
     * @param y  
     * @return  
     */  
    private int whichZone(float x, float y) {   
        // 简单的象限点处理   
        // 第一象限在右下角，第二象限在左下角，代数里面的是逆时针，这里是顺时针   
        if(x > 0 && y > 0) {   
            return 0;   
        } else if(x > 0 && y < 0) {   
            return 3;   
        } else if(x < 0 && y < 0) {   
            return 2;   
        } else if(x < 0 && y > 0) {   
            return 1;   
        }   
           
        return -1;   
    }   
       
	
	

}
