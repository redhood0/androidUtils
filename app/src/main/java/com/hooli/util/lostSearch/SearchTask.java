package com.hooli.util.lostSearch;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.hooli.util.CameraUtil;
import com.hooli.util.MainActivity;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchTask extends AsyncTask<TextView, String, Boolean> {
    TextView mtv;


    @Override
    protected void onPreExecute() {
        Log.i("onPreExecute()","这里是预处理");
    }

    //子线程中执行，如果在此处需要更新UI操作，需调用publishProgress(line)方法将要传递的参
    // 数，传递到onProgressUpdate()方法中
    @Override
    protected Boolean doInBackground(TextView... params) {
        mtv = params[0];
        try {
            String json = LikePIcUtil.searchLost(CameraUtil.currentPhotoPath);
            publishProgress(json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        publishProgress("error");
        return false;
    }

    //调用publishProgress()方法后，该方法会很快执行，利用方法中携带的参数进行对界面进行刷新
    @Override
    protected void onProgressUpdate(String... values) {
        for (int i = 0; i < values.length; i++) {
            mtv.setText(values[i]);
        }
    }

    //后台任务执行完毕后，可以通过此方法进行提示
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        //mtv.setText("sb");
        if(aBoolean == true)
        Toast.makeText(MainActivity.MainActivityContext, "查询成功了", Toast.LENGTH_SHORT).show();
    }
}
