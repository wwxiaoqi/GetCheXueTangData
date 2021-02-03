package com.wwxiaoqi.get.chexuetang;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.util.Log;
import java.util.List;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;
import android.content.Intent;


public class MyAccessibilityService extends AccessibilityService {
  //  final String TAG = "MyAccessibilityService";
    
    /**
     * 当服务启动的时候会被调用
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
     //   Log.d(TAG, "connected");
    }

    /**
     * 监听窗口变化的回调
     */
    public String receive_show = "Float_window_show";

    
     
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
     //   Log.d(TAG, event.getPackageName() + "");
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            
            List<AccessibilityNodeInfo> nodeInfoList = getRootInActiveWindow().findAccessibilityNodeInfosByViewId("cn.com.drivedu.chexuetang:id/skillContent");
            
            String data;
            if (nodeInfoList != null && nodeInfoList.size() > 0) {
                data = nodeInfoList.get(0).getText().toString();
               // Log.d(TAG, data);
                Intent intent_Receiver = new Intent("com.application.mac.RECEIVER");
                intent_Receiver.putExtra(receive_show, data);
                sendBroadcast(intent_Receiver);
                // Toast.makeText(getApplication(), data, Toast.LENGTH_LONG).show();
            }
        }

    }
    
    
    /**
     * 中断服务的回调
     */
    @Override
    public void onInterrupt() {
    //    Log.d(TAG, "onInterrupt");
    }
    
}
