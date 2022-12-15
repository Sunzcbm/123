package com.example.http;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);
    }

    public void readContentFromWeb(View v){
        //开辟一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                String content = getUrl("https://www.yiketianqi.com/free/day?appid=92448623&appsecret=1MhhvmSc&unescape=1&city=");
                //Log.d("HHHH","read web content \n: " + content);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText(content);
                    }
                });
            }
        }).start();
    }

    private String getUrl(String webUrl) {
        //创建一个URL对象
        try {
            URL url= new URL(webUrl);

            //打开一个HttpURLConnection连续。
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //设置请求方式为GET请求，可以省略，默认就是GET请求。
            con.setRequestMethod("GET");
            //设置超时时间
            con.setReadTimeout(5 * 1000);
            con.setConnectTimeout(5 * 1000);
            //HttpURLConnection调用connect（）方法建立和服务器的连接
            con.connect();
            //对返回码进行判断，返回码为200则请求成功，否则请求失败。
            int responseCode = con.getResponseCode();
            if (responseCode == 200){
                //如果请求成功，将输入的流转换成字符串信息
                InputStream is = con.getInputStream();
                String content = convertInputStreamToString(is);
                return content;

            }else {
                return "UnKnow Error";
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "UnKnow Error";
    }

    private String convertInputStreamToString(InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "UnKnow";
    }
}