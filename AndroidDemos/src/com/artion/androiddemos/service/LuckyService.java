package com.artion.androiddemos.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.RemoteViews;

import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.utils.DebugTool;

public class LuckyService extends AccessibilityService {
	
	private ArrayList<AccessibilityNodeInfo> mNodeInfoList = new ArrayList<AccessibilityNodeInfo>();
	private boolean mLuckyClicked;
    private boolean mContainsLucky;
    private boolean mContainsOpenLucky;
    private boolean mNeedBack;
    
//    private LruCache<String, AccessibilityNodeInfo> caches = new LruCache<String, AccessibilityNodeInfo>(50);
    private List<AccessibilityNodeInfo> listCaches = new ArrayList<AccessibilityNodeInfo>();
    
    @SuppressLint("NewApi")
	private boolean isInCache(AccessibilityNodeInfo info) {
    	if(info == null) {
    		return false;
    	}
    	
//    	return caches.get(info.toString()) != null;
//    	return listCaches.contains(info);
    	return false;
    }
    
    @SuppressLint("NewApi")
	private void putToCache(AccessibilityNodeInfo info) {
//    	if(info == null) {
//    		return ;
//    	}
////    	caches.put(cellNode.toString(), cellNode);
//    	listCaches.add(info);
//    	DebugTool.info("putToCache", info.toString());
//    	System.out.println("putToCache -> " + info);
//    	if(listCaches.size() > 50) {
//    		listCaches.remove(0);
//    	}
    }

	@SuppressLint("NewApi")
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		int eventType = event.getEventType();
		String eventText = "";

		switch (eventType) {
		case AccessibilityEvent.TYPE_VIEW_CLICKED://点击
			eventText = "TYPE_VIEW_CLICKED";
			break;
		case AccessibilityEvent.TYPE_VIEW_FOCUSED:
			eventText = "TYPE_VIEW_FOCUSED";
			break;
		case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED://长按
			eventText = "TYPE_VIEW_LONG_CLICKED";
			break;
		case AccessibilityEvent.TYPE_VIEW_SELECTED:
			eventText = "TYPE_VIEW_SELECTED";
			break;
		case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
			eventText = "TYPE_VIEW_TEXT_CHANGED";
			break;
		case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
			eventText = "TYPE_WINDOW_STATE_CHANGED";
			
            handleStateChanged(event.getSource(), true);
			
			break;
		case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED://通知栏
			unlockScreen();
            mLuckyClicked = false;
			eventText = "TYPE_NOTIFICATION_STATE_CHANGED";
			
			Notification notification = (Notification) event.getParcelableData();
            List<String> textList = getText(notification);
            if (null != textList && textList.size() > 0) {
                for (String text : textList) {
                    if (!TextUtils.isEmpty(text) && text.contains("微信红包")) {
                        final PendingIntent pendingIntent = notification.contentIntent;
                        try {
                            pendingIntent.send();
                        } catch (PendingIntent.CanceledException e) {
                        }
                        break;
                    }
                }
            }
			
			break;
		case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
			eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_END";
			break;
		case AccessibilityEvent.TYPE_ANNOUNCEMENT:
			eventText = "TYPE_ANNOUNCEMENT";
			break;
		case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
			eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_START";
			break;
		case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
			eventText = "TYPE_VIEW_HOVER_ENTER";
			break;
		case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
			eventText = "TYPE_VIEW_HOVER_EXIT";
			break;
		case AccessibilityEvent.TYPE_VIEW_SCROLLED:
			eventText = "TYPE_VIEW_SCROLLED";
			break;
		case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
			eventText = "TYPE_VIEW_TEXT_SELECTION_CHANGED";
			break;
		case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED://内容
			eventText = "TYPE_WINDOW_CONTENT_CHANGED";
			
            handleStateChanged(event.getSource(), false);
			break;
		}

		DebugTool.info("onAccessibilityEvent", eventText.toLowerCase());
	}

	@SuppressLint("NewApi")
	private void handleStateChanged(AccessibilityNodeInfo nodeInfo, boolean ifNeedBack) {
		if (null != nodeInfo) {
		    mNodeInfoList.clear();
		    traverseNode(nodeInfo);
		    if (mContainsLucky && !mLuckyClicked) {
		        int size = mNodeInfoList.size();
		        if (size > 0) {
		            /** step1: get the last hongbao cell to fire click action */
		            AccessibilityNodeInfo cellNode = mNodeInfoList.get(size - 1);
		            if(!isInCache(cellNode)) {
		            	cellNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
		            	putToCache(cellNode);
		            }
		            mContainsLucky = false;
		            mLuckyClicked = true;
		        }
		    }
		    if (mContainsOpenLucky) {
		        int size = mNodeInfoList.size();
		        if (size > 0) {
		            /** step2: when hongbao clicked we need to open it, so fire click action */
		            AccessibilityNodeInfo cellNode = mNodeInfoList.get(size - 1);
		            if(!isInCache(cellNode)) {
		            	cellNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
		            	putToCache(cellNode);
		            }
		            mContainsOpenLucky = false;
		        }
		    }
		    
		    if (ifNeedBack && mNeedBack) {
		        try {
		            Thread.sleep(500);
		            performGlobalAction(GLOBAL_ACTION_BACK);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		        mNeedBack = false;
		    }
		}
	}

	@Override
	public void onInterrupt() {
		ToastUtils.showMessage(getApplicationContext(), "onInterrupt");
	}

	@Override
	protected void onServiceConnected() {
		super.onServiceConnected();
		
		ToastUtils.showMessage(getApplicationContext(), "onServiceConnected");
	}
	
	private void unlockScreen() {
//        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
//        final KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("MyKeyguardLock");
//        keyguardLock.disableKeyguard();
//
//        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
//                | PowerManager.ACQUIRE_CAUSES_WAKEUP
//                | PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
//
//        wakeLock.acquire();
    }

    @SuppressLint("NewApi")
	private void traverseNode(AccessibilityNodeInfo node) {
        if (null == node) return;

        final int count = node.getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                AccessibilityNodeInfo childNode = node.getChild(i);
                traverseNode(childNode);
            }
        } else {
            CharSequence text = node.getText();
            if (null != text && text.length() > 0) {
                String str = text.toString();
                if (str.contains("领取红包")) {
                    mContainsLucky = true;
                    mLuckyClicked = false;
                    AccessibilityNodeInfo cellNode = node.getParent();
                    if (null != cellNode) mNodeInfoList.add(cellNode);
                }

                if (str.contains("拆红包")) {
                    mContainsOpenLucky = true;
                    mNodeInfoList.add(node);
                }
                
                if (str.contains("手慢了") || str.contains("红包已失效") || str.contains("红包详情")) {
                	mNeedBack = true;
                }
            }
        }
    }

    @SuppressLint("NewApi")
	public List<String> getText(Notification notification) {
        if (null == notification) return null;
        
        if(Build.VERSION.SDK_INT >= 18) {
        	List<String> textList = new ArrayList<String>();
        	String tickerText = notification.tickerText.toString();
        	if (!TextUtils.isEmpty(tickerText)) textList.add(tickerText);
        	return textList;
        }

        RemoteViews views = notification.bigContentView;
        if (views == null) views = notification.contentView;
        if (views == null) return null;

        // Use reflection to examine the m_actions member of the given RemoteViews object.
        // It's not pretty, but it works.
        List<String> text = new ArrayList<String>();
        try {
            Field field = views.getClass().getDeclaredField("mActions");
            field.setAccessible(true);

            @SuppressWarnings("unchecked")
            ArrayList<Parcelable> actions = (ArrayList<Parcelable>) field.get(views);

            // Find the setText() and setTime() reflection actions
            for (Parcelable p : actions) {
                Parcel parcel = Parcel.obtain();
                p.writeToParcel(parcel, 0);
                parcel.setDataPosition(0);

                // The tag tells which type of action it is (2 is ReflectionAction, from the source)
                int tag = parcel.readInt();
                if (tag != 2) continue;

                // View ID
                parcel.readInt();

                String methodName = parcel.readString();
                if (null == methodName) {
                    continue;
                } else if (methodName.equals("setText")) {
                    // Parameter type (10 = Character Sequence)
                    parcel.readInt();

                    // Store the actual string
                    String t = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel).toString().trim();
                    text.add(t);
                }
                parcel.recycle();
            }
        } catch (Exception e) {
        }

        return text;
    }

}
