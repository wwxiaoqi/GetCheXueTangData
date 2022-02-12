package com.wwxiaoqi.get.chexuetang.shortcuts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import com.wwxiaoqi.get.chexuetang.RootUtils;
import java.util.ArrayList;

public class GotoTopic extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
			boolean isRoot = new ExecuteAsRoot().execute();
			Toast.makeText(this, "Root 返回结果：" + isRoot, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
        finish();
    }
    
	private static class ExecuteAsRoot extends RootUtils {
        @Override
        protected ArrayList<String> getCommandsToExecute() {
            ArrayList<String> list = new ArrayList<String>();
            list.add("am start -n cn.com.drivedu.chexuetang/cn.lanzhi.cxtsdk.vip.ui.GuessQuestionActivity");
            return list;
        }
    }
}
