package com.hooli.util.lostSearch;

import android.util.Log;
import android.widget.Toast;

import com.baidu.aip.imagesearch.AipImageSearch;
import com.hooli.util.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LikePIcUtil {
    //设置APPID/AK/SK
    public static final String APP_ID = "16073411";
    public static final String API_KEY = "s7BPnvG3UTqkGPGdkMvC3mMz";
    public static final String SECRET_KEY = "GEoCtQv58H6mBLZhL9sGkPbtWkN4hW3U";


    public static String searchLost(String filePath) throws IOException, JSONException {
        // 初始化一个AipImageSearch
        AipImageSearch client = new AipImageSearch(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        // client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        // client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        //inputBasePic(client);
        String json = searchLikePic(client,filePath);
        return json;
    }
    public static String searchLikePic(AipImageSearch client,String filePath) throws IOException, JSONException {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
//        options.put("tags", "100,11");
//        options.put("tag_logic", "0");
//        options.put("pn", "100");
//        options.put("rn", "250");

         //参数为本地路径
        //String image = "res/pic5.jpg";
        //JSONObject res = client.similarSearch(filePath, options);
        JSONObject res = client.similarSearch(filePath, options);
        Log.i("Search",res.toString(2));
        return res.toString(2);
        //System.out.println(res.toString(2));

//         //参数为二进制数组
//
//        byte[] fileByte = FileUtil.processFileToByteArray(file.getName());;
//        JSONObject res = client.similarSearch(fileByte, options);
//        System.out.println(res.toString(2));

        // 相似图检索—检索, 图片参数为远程url图片
//        JSONObject res = client.similarSearchUrl(url, options);
//        System.out.println(res.toString(2));

    }
}
