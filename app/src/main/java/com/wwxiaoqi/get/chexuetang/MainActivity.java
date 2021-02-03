package com.wwxiaoqi.get.chexuetang;
 
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;




public class MainActivity extends Activity { 
   
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            
            findViewById(R.id.open_Floating).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,FxService.class);
                        startService(intent);
                    }
                });
                
                
            findViewById(R.id.stop_Floating).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,FxService.class);
                        stopService(intent);
                    }
                });

        }

        @Override
        protected void onDestroy() {
            Intent intent = new Intent(MainActivity.this,FxService.class);
            stopService(intent);
            super.onDestroy();
        }
    
    
    
    
} 
