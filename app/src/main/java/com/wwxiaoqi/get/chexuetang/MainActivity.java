package com.wwxiaoqi.get.chexuetang;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
	private boolean isOpenWindow = false;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		helpStatus();
		isServiceRunning(true);
		findViewById(R.id.btn_Status).setOnClickListener(this);
		findViewById(R.id.btn_OpenBarrier).setOnClickListener(this);
		findViewById(R.id.btn_Topic).setOnClickListener(this);
		findViewById(R.id.btn_kill).setOnClickListener(this);
		findViewById(R.id.text_help).setOnClickListener(this);
    }

	@Override
	protected void onStart() {
		super.onStart();
		helpStatus();
		isServiceRunning(true);
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (! Settings.canDrawOverlays(this)) {
				Toast.makeText(this, "软件悬浮窗权限获取失败.", Toast.LENGTH_SHORT).show();
				return;
            }
			btnRunClick();
        }
    }

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_Status:
				btnRunClick();
				break;
			case R.id.btn_Topic:
				openTopic();
				break;
			case R.id.text_help:
				help();
				break;
			case R.id.btn_kill:
				killApp();
				break;
			case R.id.btn_OpenBarrier:
				startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
				break;
		}
	}

	private void btnRunClick() {
		if (isShowLayer()) {
			if (!isOpenWindow) {
				if (isAccessibilitySettings()) {
					startServiceRunning();
					isOpenWindow = true;
					isServiceRunning(false);
				}
			} else {
				killServiceRunning();
				isOpenWindow = false;
				isServiceRunning(false);
			}
		}
	}

	private void killApp() {
		killServiceRunning();
		isOpenWindow = false;
		finishAndRemoveTask();
		System.exit(0);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	private void startServiceRunning() {
		new AppUtils().controlAccessibility(this, false);
		Intent intent = new Intent(this, WindowService.class);
		startService(intent);
	}

	private void openTopic() {
		try {
			boolean isRoot = new ExecuteAsRoot().execute();
			Toast.makeText(this, "Root 返回结果：" + isRoot, Toast.LENGTH_SHORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void help() {
		if (! new AppUtils().isHideText(this)) {
			new AppUtils().hideText(this);
		} else {
			new AppUtils().showText(this);
		}
		helpStatus();
	}

	private void helpStatus() {
		TextView help = findViewById(R.id.text_help);
		if (! new AppUtils().isHideText(this)) {
			help.setText(R.string.info_hide);
		} else {
			help.setText(R.string.info);
		}
	}

	private boolean isAccessibilitySettings() {
		if (! new AppUtils().isAccessibilitySettings(this)) {
			startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
			Toast.makeText(this, "请给予软件无障碍模式权限.....", Toast.LENGTH_SHORT);
			return false;
		}
		return true;
	}

	private boolean isShowLayer() {
		if (!Settings.canDrawOverlays(MainActivity.this)) {
			startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 1);
			Toast.makeText(MainActivity.this, "请给予软件悬浮窗权限......", Toast.LENGTH_SHORT);
			return false;
		}
		return true;
	}

	private void killServiceRunning() {
		new AppUtils().controlAccessibility(this, true);
		Intent intent = new Intent(this, WindowService.class);
		stopService(intent);
	}

	private void isServiceRunning(boolean isModifyisOpenWindow) {
		if (! new AppUtils().isServiceRunning(getApplicationContext())) {
			((TextView) findViewById(R.id.btn_Status)).setText("开启悬浮窗");
			if (isModifyisOpenWindow) isOpenWindow = false;
		} else {
			((TextView) findViewById(R.id.btn_Status)).setText("关闭悬浮窗");
			if (isModifyisOpenWindow) isOpenWindow = true;
		}
	}

	private class ExecuteAsRoot extends RootUtils {
        @Override
        protected ArrayList<String> getCommandsToExecute() {
            ArrayList<String> list = new ArrayList<String>();
            list.add("am start -n cn.com.drivedu.chexuetang/cn.lanzhi.cxtsdk.vip.ui.GuessQuestionActivity");
            return list;
        }
    }

}
