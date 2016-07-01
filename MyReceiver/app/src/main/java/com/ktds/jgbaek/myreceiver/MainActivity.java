package com.ktds.jgbaek.myreceiver;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionRequester.Builder requester = new PermissionRequester.Builder(this);
        requester.create().request(
                Manifest.permission.RECEIVE_SMS,
                10000,
                new PermissionRequester.OnClickDenyButtonListener() {
                    @Override
                    public void onClick(Activity activity) {

                    }
                });
    }
}
