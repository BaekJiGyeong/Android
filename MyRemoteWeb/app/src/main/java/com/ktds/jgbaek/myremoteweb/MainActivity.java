package com.ktds.jgbaek.myremoteweb;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends ActionBarActivity {

    private TextView tvTitle;
    private TextView tvMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvMemo = (TextView) findViewById(R.id.tvMemo);

        NetworkTask2 networkTask2 = new NetworkTask2();

        Map<String,String> params = new HashMap<String, String>();
        params.put("title","메모입니다...");
        params.put("memo", "메모");

        networkTask2.execute(params);


    }

    public class NetworkTask2 extends AsyncTask<Map<String,String>, Integer, String>{
        // Task<Map<String,String> -> parameter ( onCreadte의 networkTask.execute("")와 같다. )
        // ( DoInBackground의 리턴타입 )

        // Integer -> progress ( 진행률 )

        // return ( DoInBackground의 리턴타입 )


        @Override
        protected String doInBackground(Map<String, String>... maps) {
            // Map 파라미터는 NetworkTask2의 파라미터와 같다.
            // ... 은 가변배열 파라미터 이다.

            // post로 요청하겠다는 뜻
            // HTTP요청 준비작업
            HttpClient.Builder http = new HttpClient.Builder("POST", "http://10.225.152.167:8080//AndroidProject//android3");

            // Parameter를 전송한다.
//            http.addOrReplace("test", "한글한글..");
           http.addAllParameters(maps[0]);

            // HTTP요청 전송
            HttpClient post = http.create();
            post.request();

            // 응답 코드 가져오기.
            int statusCode = post.getHttpStatusCode();

            // 응답 본문 가져오기
            String body = post.getBody();

            // 바로 onPostExecute로 감 ui작업을 해줌
            return body;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("JSON_RESULT", s);

            Gson gson = new Gson();
            Data data = gson.fromJson(s, Data.class);

            Log.d("JSON_RESULT", data.getData1());
            Log.d("JSON_RESULT", data.getData2());

            tvTitle.setText(data.getData1());
            tvMemo.setText(data.getData2());
        }
    }

    public class NetworkTask extends AsyncTask<String, String, String >{

        /**
         * doInBackground가 실행되기 이전에 동작된다.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 실제로 동작하는
         * on 작업을 Thread로 처리해준다.
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(String... params) {

            // post로 요청하겠다는 뜻
            // HTTP요청 준비작업
            HttpClient.Builder http = new HttpClient.Builder("POST", "http://10.225.152.167:8080//AndroidProject//android2");

          // Parameter를 전송한다.
            http.addOrReplace("test", "한글한글..");

           // HTTP요청 전송
            HttpClient post = http.create();
            post.request();

            // 응답 코드 가져오기.
            int statusCode = post.getHttpStatusCode();

            // 응답 본문 가져오기
            String body = post.getBody();

            // 바로 onPostExecute로 감 ui작업을 해줌
            return body;
        }

        /**
         * DoInBackground실행이 종료되면 동작한다.
         * @param s DoInBackground가 return한 값이 들어간다.
         */
        @Override
        protected void onPostExecute(String s) {
            Log.d("HTTP RESULT", s);
        }
    }
}
