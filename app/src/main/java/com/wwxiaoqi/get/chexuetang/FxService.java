package com.wwxiaoqi.get.chexuetang;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FxService extends Service {
    public String receive_show = "Float_window_show";

    //注册广播
    private MsgReceiver msgReceiver;

    //定义浮动窗口布局
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    WindowManager mWindowManager;

    Button mFloatView_Button;
    TextView mFloatView_textView;

    // private static final String TAG = "FxService";

    /**
     * 广播接收器
     *
     */
    public class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //此处从广播中取出数据，并写入你想要的函数
            String string = intent.getStringExtra(receive_show);
            mFloatView_textView.setText(string);
        }

    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
     //    Log.i(TAG, "on_create");
        createFloatView();

        //动态注册广播接收器
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.application.mac.RECEIVER");
        registerReceiver(msgReceiver, intentFilter);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO Auto-generated method stub
        return null;
    }

    private void createFloatView()
    {
        wmParams = new WindowManager.LayoutParams();
        //获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
       //  Log.i(TAG, "mWindowManager--->" + mWindowManager);
        //设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = 0;
        wmParams.y = 0;

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        /*// 设置悬浮窗口长宽数据
         wmParams.width = 200;
         wmParams.height = 80;*/

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);
        //浮动窗口按钮
        mFloatView_Button = mFloatLayout.findViewById(R.id.float_id);
        mFloatView_textView = mFloatLayout.findViewById(R.id.float_text);
        
        
        
        mFloatView_textView.setText("我是悬浮窗");

        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                                                              View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                             .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
       // Log.i(TAG, "Width/2--->" + mFloatView_Button.getMeasuredWidth()/2);
     //    Log.i(TAG, "Height/2--->" + mFloatView_Button.getMeasuredHeight()/2);
        //设置监听浮动窗口的触摸移动
        mFloatLayout.setOnTouchListener(new View.OnTouchListener()
            {

                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    // TODO Auto-generated method stub
                    //getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                    wmParams.x = (int) event.getRawX() - mFloatLayout.getMeasuredWidth()/2;
                 //   Log.i(TAG, "RawX" + event.getRawX());
                 //   Log.i(TAG, "X" + event.getX());
                    //减25为状态栏的高度
                    wmParams.y = (int) event.getRawY() - mFloatLayout.getMeasuredHeight()/2 - 25;
                //    Log.i(TAG, "RawY" + event.getRawY());
                  //  Log.i(TAG, "Y" + event.getY());
                    //刷新
                    mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                    return false;  //此处必须返回false，否则OnClickListener获取不到监听
                }
            });
/*
        mFloatView_Button.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    // TODO Auto-generated method stub
                    Toast.makeText(FxService.this, "onClick", Toast.LENGTH_SHORT).show();
                    mFloatView_textView.setText("我很丑\n但我很聪明");
                }
            });
            
            
            */
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        //注销广播
        unregisterReceiver(msgReceiver);

        if(mFloatLayout != null)
        {
            //移除悬浮窗口
            mWindowManager.removeView(mFloatLayout);
        }
    }
}
