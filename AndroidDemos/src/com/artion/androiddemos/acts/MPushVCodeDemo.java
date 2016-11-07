package com.artion.androiddemos.acts;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.artion.androiddemos.common.LogUtils;

public class MPushVCodeDemo extends CommonBtnDemo {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initListener() {
		super.initListener();
		btn1.setText("checkVCode");
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkVCode(mAct, listPushPackages(mAct));
			}
		});
		
	}
	
	public List<String> listPushPackages(Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        final Set<String> packageSets = new HashSet<String>();
        for (ResolveInfo info : pm.queryBroadcastReceivers(new Intent("android.mpushservice.action.notification.SHOW"), 0)) {
            packageSets.add(info.activityInfo.packageName);
        }

        List<String> packages = new ArrayList<String>();
        packages.addAll(packageSets);
        return packages;
    }
	
	public String getVCode(Context _context, String pkg) {
		String value = null;
        Context context;
        try {
            if (!TextUtils.isEmpty(pkg)) {
                context = _context.createPackageContext(pkg, Context.CONTEXT_IGNORE_SECURITY);
            } else {
                context = _context;
            }
            LogUtils.info(tag, "getFilePreferences context: " + context.getPackageName() + "<" + pkg + ">");
            String fileName = "mpush_version_preferences_file";
            File f = context.getFileStreamPath(fileName);
            //File f = new File(context.getFileStreamPath(fileName).getPath());
            if (f == null || !f.exists()) {
            	LogUtils.warn(tag, String.format("old sdk doesn't have preference file [%s, %s]", pkg, fileName));
                return null;
            }

            try {
                FileInputStream fis = context.openFileInput(fileName);
                byte[] b = new byte[fis.available()];//新建一个字节数组
                fis.read(b);//将文件中的内容读取到字节数组中
                fis.close();
                value = new String(b);//再将字节数组中的内容转化成字符串形式输出
                LogUtils.info(tag, "getFilePreferences value: " + value);
            } catch (Exception e) {
                LogUtils.error(tag, "read file err", e);
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.error(tag, "find package err", e);
        } catch (SecurityException e) {
            LogUtils.error(tag, "permission err", e);
        }
        return value;
	}
	
	public void checkVCode(Context ctx, List<String> packages) {
		for (String pkg : packages) {
            String v = getVCode(ctx, pkg);
            LogUtils.info(tag, "startNewerSdk pkg: " + pkg + "> v: " + v);
        }
	}

}
