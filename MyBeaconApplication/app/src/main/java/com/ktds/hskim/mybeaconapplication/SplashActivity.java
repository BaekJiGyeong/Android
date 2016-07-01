package com.ktds.hskim.mybeaconapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

       final String executeType = getIntent().getStringExtra("executeType");


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = null;
                if(executeType!= null && executeType.equals("beacon")){
                    intent = new Intent(SplashActivity.this, CouponActivity.class);
                }
                else{
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
