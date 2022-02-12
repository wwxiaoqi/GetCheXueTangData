package com.wwxiaoqi.get.chexuetang;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

public class MonitorAccessibilityService extends AccessibilityService {

    @TargetApi(Build.VERSION_CODES.N)
	@Override
    public void onInterrupt() {
		disableSelf();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

	private void intentReceiver(String data) {
		Intent intent_Receiver = new Intent("com.application.mac.RECEIVER");
		intent_Receiver.putExtra("window_show", data);
		sendBroadcast(intent_Receiver);
	}

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		if (prefs.getBoolean("isServiceEnabled", true)) {
			List<AccessibilityNodeInfo> nodeInfoList = getRootInActiveWindow().findAccessibilityNodeInfosByViewId("cn.com.drivedu.chexuetang:id/skillContent");
			String data;
			if (nodeInfoList != null && nodeInfoList.size() > 0) {
				data = nodeInfoList.get(0).getText().toString();
				intentReceiver(data);
			}
		}
    }
}
