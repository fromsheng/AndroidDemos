package com.artion.androiddemos.domain;

import java.util.Calendar;

import android.content.ContentValues;
import android.net.Uri;
import android.provider.CalendarContract.Events;

public class CalendarModel {
	public String title;
	public String description;
	public String calendar_id;
	public String eventLocation;
	public String eventTimezone;
	public long dtstart;
	public long dtend;
	public int hasAlarm;
	public String event_id;
	public int minutes;
	
	public static ContentValues getCalendarValues(CalendarModel model) {
		ContentValues values = new ContentValues();
		values.put("title", "与苍井空小姐动作交流");
		values.put("description", "Frankie受空姐邀请,今天晚上10点以后将在Sheraton动作交流.lol~");
        // 插入账户
//		values.put("calendar_id", calId);
		values.put("eventLocation", "地球-华夏");   
        
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, 11);
        mCalendar.set(Calendar.MINUTE, 45);
        long start = mCalendar.getTime().getTime();
        mCalendar.set(Calendar.HOUR_OF_DAY, 12);
        long end = mCalendar.getTime().getTime();

        values.put("dtstart", start);
        values.put("dtend", end);
        values.put("hasAlarm", 1);

        values.put(Events.EVENT_TIMEZONE, "Asia/Shanghai");  //这个是时区，必须有，
//        values.put("event_id", id);
        // 提前10分钟有提醒
        values.put("minutes", 10);
        return values;
	}
}
