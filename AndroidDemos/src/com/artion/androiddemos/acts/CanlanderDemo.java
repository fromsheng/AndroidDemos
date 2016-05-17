package com.artion.androiddemos.acts;

import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.view.View;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.common.CalendarUtils;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.utils.DebugTool;

public class CanlanderDemo extends BaseActivity {
	//Android2.2版本以后的URL，之前的就不写了 
    private static String calanderURL = "content://com.android.calendar/calendars";
    private static String calanderEventURL = "content://com.android.calendar/events";
    private static String calanderRemiderURL = "content://com.android.calendar/reminders";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_canlander);
        
    }

    public void onClick(View v) {
        if (v.getId() == R.id.readUserButton) {  //读取系统日历账户，如果为0的话先添加
            Cursor userCursor = getContentResolver().query(Uri.parse(calanderURL), null, null, null, null);
            
            System.out.println("Count: " + userCursor.getCount());            
            ToastUtils.showMessage(mAct, "Count: " + userCursor.getCount());
            
            for (userCursor.moveToFirst(); !userCursor.isAfterLast(); userCursor.moveToNext()) {
                System.out.println("name: " + userCursor.getString(userCursor.getColumnIndex("ACCOUNT_NAME")));
                
                
                String userName1 = userCursor.getString(userCursor.getColumnIndex("name"));
                String userName0 = userCursor.getString(userCursor.getColumnIndex("ACCOUNT_NAME"));
                ToastUtils.showMessage(mAct, "NAME: " + userName1 + " -- ACCOUNT_NAME: " + userName0);
            }
        }
        else if (v.getId() == R.id.inputaccount) {        //添加日历账户
//            initCalendars();
        	CalendarUtils.checkCalendarAccount(mAct);
        }
        else if (v.getId() == R.id.delEventButton) {  //删除事件
    
            int rownum = getContentResolver().delete(Uri.parse(calanderURL), "_id!=-1", null);  //注意：会全部删除所有账户，新添加的账户一般从id=1开始，
                                                                                                  //可以令_id=你添加账户的id，以此删除你添加的账户    
            ToastUtils.showMessage(mAct, "删除了: " + rownum);
        }
        else if (v.getId() == R.id.readEventButton) {  //读取事件
            Cursor eventCursor = getContentResolver().query(Uri.parse(calanderEventURL), null, null, null, null);
            if (eventCursor.getCount() > 0) {
                eventCursor.moveToLast();             //注意：这里与添加事件时的账户相对应，都是向最后一个账户添加
                String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
                String eventExDate = eventCursor.getString(eventCursor.getColumnIndex(CalendarContract.Events.EXDATE));
//                Date date = new Date(Long.parseLong(eventExDate));
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD HH：mm:ss", Locale.ENGLISH);
//                DebugTool.info("Calendars", "eventExDate===" + sdf.format(date));
                ToastUtils.showMessage(mAct, eventTitle);
                
                for(int i = 0; i < eventCursor.getColumnCount(); i ++) {
                	DebugTool.info("Calendars", eventCursor.getColumnName(i) + "===" + eventCursor.getString(i));
                }
            }
        }
        else if (v.getId() == R.id.writeEventButton) {
        	CalendarUtils.updateCalendarReturnId(mAct);
        } else if(v.getId() == R.id.openCalendar) {
        	CalendarUtils.gotoCalendar(mAct);
        } else if(v.getId() == R.id.newCalendar) {
        	CalendarUtils.gotoCreateCalendar(mAct);
        } else if(v.getId() == R.id.settingdetail) {
        	Intent mIntent = new Intent();
        	mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        	mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//        	mIntent.setData(Uri.fromParts("package", "com.kdweibo.client",null));
        	ComponentName comp = new ComponentName("com.android.settings", 
        	"com.android.settings.applications.ManageApplications");
        	mIntent.setComponent(comp);
        	mIntent.putExtra("com.android.settings.ApplicationPkgName", "com.kdweibo.client");
        	mIntent.setAction("android.intent.action.VIEW");
        	startActivity(mIntent);
        }
    }
    

}
