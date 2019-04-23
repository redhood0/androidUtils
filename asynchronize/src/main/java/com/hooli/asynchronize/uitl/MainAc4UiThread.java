package com.hooli.asynchronize.uitl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hooli.asynchronize.R;

public class MainAc4UiThread extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        new MyThread().start();//必须在Activity中实现
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            //写这个方法，表示新开线程
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        //延迟两秒更新
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tv.setText("更新后的TextView");
                }
            });
        }
    }
}
