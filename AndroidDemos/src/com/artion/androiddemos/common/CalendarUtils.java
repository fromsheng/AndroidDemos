package com.artion.androiddemos.common;

import java.util.Calendar;
import java.util.TimeZone;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;

import com.artion.androiddemos.R;
import com.artion.androiddemos.utils.DebugTool;

public class CalendarUtils {
	public final static String calanderURL = "content://com.android.calendar/calendars";  
    public final static String calanderEventURL = "content://com.android.calendar/events";  
    public final static String calanderRemiderURL = "content://com.android.calendar/reminders"; 
    
    public static boolean isCanUseCalendar(Context context) {
    	return Build.VERSION.SDK_INT >= 14;
    }

    
    public static void checkCalendarAccount(Context context) {
    	if(hasCalendarAccount(context, "jinsheng_cai@kingdee.com")) {
    		return;
    	}
    	
    	initCalendars(context, "jinsheng_cai@kingdee.com", "Artion", "jinsheng_cai@kingdee.com", "Artion", R.color.highlighttext); 
    }
    
    public static void initCalendars(Context context, String owner_account, String name, 
    		String account_name, String calendar_display_name, int calendar_color) {
    	TimeZone timeZone = TimeZone.getDefault();  
        ContentValues value = new ContentValues();  
  
        value.put(Calendars.OWNER_ACCOUNT, owner_account);  
        value.put(Calendars.NAME, name);  
          
        value.put(Calendars.ACCOUNT_NAME, account_name);  
        value.put(Calendars.ACCOUNT_TYPE, "com.android.exchange");   
        value.put(Calendars.CALENDAR_DISPLAY_NAME, calendar_display_name);  
        value.put(Calendars.VISIBLE, 1);  
        value.put(Calendars.CALENDAR_COLOR, calendar_color);  
        value.put(Calendars.CALENDAR_ACCESS_LEVEL, Calendars.CAL_ACCESS_OWNER);  
        value.put(Calendars.SYNC_EVENTS, 1);   
        value.put(Calendars.CALENDAR_TIME_ZONE, timeZone.getID());  
        value.put(Calendars.CAN_ORGANIZER_RESPOND,0);  
          
        Uri calendarUri = Uri.parse(calanderURL);  
        calendarUri = calendarUri.buildUpon()  
            .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true" )  
            .appendQueryParameter(Calendars.ACCOUNT_NAME, account_name)  
            .appendQueryParameter(Calendars.ACCOUNT_TYPE, "com.android.exchange" ) //CalendarContract.ACCOUNT_TYPE_LOCAL )  
            .build();  
          
        Uri newEvent = context.getContentResolver().insert(calendarUri, value); 
        DebugTool.info("Calendars", "newEvent.getLastPathSegment() == " + newEvent.getLastPathSegment());
    }
    
    public static String getCalendarAccountId(Context context, String owner_account) {
    	Cursor cursor = null;
    	String selection = null;
    	String[] args = null;
    	if(owner_account != null) {
    		selection = Calendars.OWNER_ACCOUNT + "=?";
    		
    	}
    	
    	String result = null;
    	try {
			cursor = context.getContentResolver().query(Uri.parse(calanderURL), null, selection, args, null);
			if(cursor.moveToFirst()) {
				result = cursor.getString(cursor.getColumnIndex("_id"));
			}
			DebugTool.info("Calendars", "是否存在 == " + result);
			return result; 
		} catch (Exception e) {
			
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
    	
    	return result;
    }
    
    public static boolean hasCalendarAccount(Context context, String owner_account) {
    	Cursor cursor = null;
    	String selection = Calendars.OWNER_ACCOUNT + "=?";
    	String[] args = new String[] {
    			owner_account
    	};
    	try {
			cursor = context.getContentResolver().query(Uri.parse(calanderURL), null, selection, args, null);
			boolean has = cursor.moveToFirst(); 
			DebugTool.info("Calendars", "是否存在 == " + has);
			return has; 
		} catch (Exception e) {
			
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
    	
    	return false;
    }
    
    public static String updateCalendarReturnId(Context context) {
    	String calId = getCalendarAccountId(context, null);
        if(calId == null) {
        	ToastUtils.showMessage(context, "没有账户，请先添加账户");
            return null;
        }
        
        ContentValues event = new ContentValues();
        event.put("title", "这是一个日程活动");
        event.put("description", "Frankie受空姐邀请,今天晚上10点以后将在Sheraton动作交流.lol~");
        // 插入账户
        event.put("calendar_id", calId);
        event.put("eventLocation", "地球-华夏");   
        
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, 11);
        mCalendar.set(Calendar.MINUTE, 45);
        long start = mCalendar.getTime().getTime();
        mCalendar.set(Calendar.HOUR_OF_DAY, 12);
        long end = mCalendar.getTime().getTime();

        event.put("dtstart", start);
        event.put("dtend", end);
        event.put("hasAlarm", 1);

        event.put(Events.EVENT_TIMEZONE, "Asia/Shanghai");  //这个是时区，必须有，
        //添加事件
        Uri newEvent = context.getContentResolver().insert(Uri.parse(calanderEventURL), event);        
        //事件提醒的设定
        long id = Long.parseLong(newEvent.getLastPathSegment());
        DebugTool.info("Calendars", "newEvent.getLastPathSegment() == " + id);
        ContentValues values = new ContentValues();
        values.put("event_id", id);
        // 提前10分钟有提醒
        values.put("minutes", 10);
        context.getContentResolver().insert(Uri.parse(calanderRemiderURL), values);
        
        ToastUtils.showMessage(context, "插入事件成功!!!");
        return newEvent.getLastPathSegment();
    }
    
    public static int deleleCalendar(Context context) {
    	int rows = context.getContentResolver().delete(Uri.parse(calanderURL), "_id!=-1", null);  //注意：会全部删除所有账户，新添加的账户一般从id=1开始，
        //可以令_id=你添加账户的id以此删除你添加的账户   
    	return rows;
    }
    
    public static void gotoCalendar(Context context) {
    	Intent intent = new Intent();  
//      intent.setComponent(new ComponentName("com.htc.calendar",  
//              "com.htc.calendar.LaunchActivity"));  
      intent.setComponent(new ComponentName("com.android.calendar",  
      		"com.android.calendar.LaunchActivity"));  
      context.startActivity(intent);
    }
    
    public static void gotoCreateCalendar(Context context) {
    	Intent intent = new Intent(Intent.ACTION_INSERT);
    	intent.setData(Uri.parse(CalendarUtils.calanderEventURL)); 
//    	intent.setComponent(new ComponentName("com.htc.calendar",  
//                "com.htc.calendar.EditEvent")); 
    	intent.putExtra("title", "这是一个日程活动");
    	intent.putExtra("description", "Frankie受空姐邀请,今天晚上10点以后将在Sheraton动作交流.lol~");
        // 插入账户
    	intent.putExtra("calendar_id", 2);
        intent.putExtra("eventLocation", "地球-华夏");   
        
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, 13);
        mCalendar.set(Calendar.MINUTE, 49);
        long start = mCalendar.getTime().getTime();
        mCalendar.set(Calendar.HOUR_OF_DAY, 15);
        long end = mCalendar.getTime().getTime();
        intent.putExtra("beginTime", start);
        intent.putExtra("endTime", end);
        intent.putExtra("hasAlarm", false);
//        intent.putExtra("minutes", 30);
//        intent.putExtra("reminders", ReminderEntry);
        intent.putExtra("allDay", false);
        
        

        intent.putExtra("timeZone", "Asia/Hongkong");  //这个是时区，必须有，
        context.startActivity(intent); 
    }
}
