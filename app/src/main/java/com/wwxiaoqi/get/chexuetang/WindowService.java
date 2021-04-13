package com.wwxiaoqi.get.chexuetang;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WindowService extends Service {
	private WindowReceiver receiver;
	private LinearLayout mFloatLayout;
	private TextView mFloatView_textView;
    private WindowManager mWindowManager;
	private WindowManager.LayoutParams wmParams;

	@Override
    public IBinder onBind(Intent intent) {
        return null;
    }

	@Override
    public void onCreate() {
        super.onCreate();
        createFloatView();
        startMsgReceiver();
    }

	@Override
    public void onDestroy() {
        super.onDestroy();
        stopMsgReceiver();
        if (mFloatLayout != null) {
            mWindowManager.removeView(mFloatLayout);
        }
    }

	private void stopMsgReceiver() {
		unregisterReceiver(receiver);
	}

	private void startMsgReceiver() {
		receiver = new WindowReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.application.mac.RECEIVER");
        registerReceiver(receiver, intentFilter);
	}

    public class WindowReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra("window_show");
            mFloatView_textView.setText(result);
        }
    }

    private void createFloatView() {
        wmParams = new WindowManager.LayoutParams();
        mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        mWindowManager.addView(mFloatLayout, wmParams);
        mFloatView_textView = mFloatLayout.findViewById(R.id.float_text);
        mFloatView_textView.setText("我是悬浮窗");
        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mFloatLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    wmParams.x = (int) event.getRawX() - mFloatLayout.getMeasuredWidth() / 2;
                    wmParams.y = (int) event.getRawY() - mFloatLayout.getMeasuredHeight() / 2 - 25;
                    mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                    return false;
                }
            });
    }
}
