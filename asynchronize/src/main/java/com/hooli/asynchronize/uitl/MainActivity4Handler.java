package com.hooli.asynchronize.uitl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hooli.asynchronize.R;

/**
 * 使用Handler消息传递机制；
 */
public class MainActivity4Handler extends AppCompatActivity {
    private TextView tv;

    //事件更新器
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x123) {
                //符合条件，更新控件
                tv.setText("更新后的TextView：" + (String) msg.obj);
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        new MyThread().start();
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            //异步处理事件
            //延迟两秒更新
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //传递消息(只传递一个数字)
            //handler.sendEmptyMessage(0x123);
            //传递一个message对象
            Message message = new Message();
            String x = "ssss";
            message.what = 0x123;
            message.obj = x;
            handler.sendMessage(message);
        }
    }
}
