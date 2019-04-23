package com.hooli.asynchronize.uitl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.hooli.asynchronize.R;

public class MainActivity4Async extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        new Yibu().execute(tv);//执行异步
    }

    //泛型参数分别代表，传入，中间，返回值
    class Yibu extends AsyncTask<TextView, String, String>{

        //预处理，可以随意提示一些信息
        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity4Async.this, "这里是预处理", Toast.LENGTH_SHORT).show();
        }

        //调用publishProgress()方法后，该方法会很快执行，利用方法中携带的参数进行对界面进行刷新
        @Override
        protected void onProgressUpdate(String... values) {
            for (int i = 0; i < values.length; i++) {
                tv.setText(values[i]);
            }
        }

        //执行后台程序
        @Override
        protected String doInBackground(TextView... views) {
            //比如耗时多的联网操作等
            TextView tv = views[0];
            tv.setText("中途更新");

            publishProgress("string 字符串，可以是json格式，用来传递执行完成后的结果");
            return null;
        }

        //后台任务执行完毕后，可以通过此方法进行提示
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity4Async.this, "处理完成", Toast.LENGTH_SHORT).show();

        }
    }
}
