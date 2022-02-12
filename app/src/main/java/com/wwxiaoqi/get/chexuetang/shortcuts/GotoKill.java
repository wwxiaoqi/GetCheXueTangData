package com.wwxiaoqi.get.chexuetang.shortcuts;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import com.wwxiaoqi.get.chexuetang.MonitorAccessibilityService;
import com.wwxiaoqi.get.chexuetang.WindowService;
import com.wwxiaoqi.get.chexuetang.AppUtils;

public class GotoKill extends Activity {
    
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, WindowService.class);
		stopService(intent);
		new AppUtils().controlAccessibility(this, true);
		finishAndRemoveTask();
		System.exit(0);
		android.os.Process.killProcess(android.os.Process.myPid());
    }
    
}
