package com.artion.androiddemos;

import java.util.List;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.accessibility.AccessibilityManager;

public class LuckySettingDemo extends CommonBtnDemo {

	
	@Override
	protected void initLayout() {
		super.initLayout();
		
		btn1.setText("进入系统打开辅助");
		btn2.setText("检查状态");
	}

	@Override
	protected void initListener() {
		super.initListener();
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				updateServiceStatus();
			}
		});
	}
	
	@SuppressLint("NewApi")
	private void updateServiceStatus() {
        boolean serviceEnabled = false;

        AccessibilityManager accessibilityManager =
                (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> accessibilityServices =
                accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            if (info.getId().equals(getPackageName() + "/.service.LuckyService")) {
                serviceEnabled = true;
            }
        }
        btn3.setText(serviceEnabled ? "辅助服务已开启" : "辅助服务已关闭");

    }

}
