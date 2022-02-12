package com.wwxiaoqi.get.chexuetang;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.text.TextUtils;
import java.util.ArrayList;
import android.preference.PreferenceManager;

public class AppUtils {
	
	public boolean isHideText(Context context) {
		SharedPreferences setting = context.getSharedPreferences("config", 0);
        return !setting.getBoolean("FIRST", true);
	}

	public void hideText(Context context) {
		SharedPreferences setting = context.getSharedPreferences("config", 0);
		setting.edit().putBoolean("FIRST", true).apply();
	}
	
	public void showText(Context context) {
		SharedPreferences setting = context.getSharedPreferences("config", 0);
		setting.edit().putBoolean("FIRST", false).apply();
	}
	
	public void controlAccessibility(Context context, boolean bool) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		if (bool) {
			prefs.edit().putBoolean("isServiceEnabled", false).apply();
			return;
		}
		prefs.edit().putBoolean("isServiceEnabled", true).apply();
	}
	
	public boolean isServiceRunning(Context context) {
		return isServiceRunning(context, "com.wwxiaoqi.get.chexuetang.WindowService");
	}
    
    public boolean isServiceRunning(Context context, String ServiceName) {
        if (TextUtils.isEmpty(ServiceName)) {
            return false;
        }
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(1000);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString().equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }
	
	public boolean isAccessibilitySettings(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = "com.wwxiaoqi.get.chexuetang/com.wwxiaoqi.get.chexuetang.MonitorAccessibilityService";
        try {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Exception ignored) {
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessabilityService = mStringColonSplitter.next();
                    if (accessabilityService.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
	
}
