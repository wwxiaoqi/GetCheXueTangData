package com.wwxiaoqi.get.chexuetang;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends Activity { 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.open_Floating).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Settings.canDrawOverlays(MainActivity.this)) {
                        Toast.makeText(MainActivity.this, "当前无 [允许显示在其他应用程式上层] 权限, 请授权....", Toast.LENGTH_SHORT);
                        startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 1);
                    } else {
                        Intent intent = new Intent(MainActivity.this, FxService.class);
                        startService(intent);
                    }
                }
            });

        findViewById(R.id.open_a).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));

                }
            });

        findViewById(R.id.stop_a).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                    Intent intent = new Intent(MainActivity.this, FxService.class);
                    stopService(intent);
                    finishAffinity();
                    System.exit(0);
                    // Process.killProcess(Process.myPid());
                }
            });


        findViewById(R.id.stop_Floating).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, FxService.class);
                    stopService(intent);
                }
            });

    }

    @Override
    protected void onDestroy() {
        // Intent intent = new Intent(MainActivity.this,FxService.class);
        // stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权 [允许显示在其他应用程式上层] 失败!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "授权 [允许显示在其他应用程式上层] 成功!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, FxService.class);
                startService(intent);
            }
        }
    }

    

} 
