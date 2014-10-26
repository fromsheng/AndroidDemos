package com.artion.androiddemos;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

import com.artion.androiddemos.utils.DebugTool;

public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
//		CrashHandler.getInstance().init(getApplicationContext());  
	}
	
	private static BaseApplication instance;

	//单例模式中获取唯一的MyApplication实例 
	public static BaseApplication getInstance() {
		return instance;             
	}

	private List<Activity> activityList = new LinkedList<Activity>(); 

	//添加Activity到容器中
	public void addActivity(Activity activity) {
		DebugTool.info("APP", "addActivity + " + activity);
		activityList.add(activity);
	}

	public void removeActivity(Activity activity) {
		DebugTool.info("APP", "removeActivity + " + activity);
		activity.finish();
		activityList.remove(activity);
		activity = null;
	}
	//遍历所有Activity并finish
	public void exit() {
		for(Activity activity : activityList){
			activity.finish();
			activity = null;
		}
		
		activityList.clear();
		System.exit(0);
	}
	
	
	public static List<String> getClassName(String packageName) {  
        String filePath = ClassLoader.getSystemResource("").getPath() + packageName.replace(".", "\\");  
        List<String> fileNames = getClassName(filePath, null);  
        return fileNames;  
    }  
  
    private static List<String> getClassName(String filePath, List<String> className) {  
        List<String> myClassName = new ArrayList<String>();  
        File file = new File(filePath);  
        File[] childFiles = file.listFiles();  
        for (File childFile : childFiles) {  
            if (childFile.isDirectory()) {  
                myClassName.addAll(getClassName(childFile.getPath(), myClassName));  
            } else {  
                String childFilePath = childFile.getPath();  
                childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));  
                childFilePath = childFilePath.replace("\\", ".");  
                myClassName.add(childFilePath);  
            }  
        }  
  
        return myClassName;  
    }  
	
}
