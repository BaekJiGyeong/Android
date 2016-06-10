package com.ktds.jgbaek.practice01;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnTel = (Button) findViewById(R.id.btnTel);
        btnTel.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse("tel:010-2737-2604"));
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-2737-2604"));
                startActivity(intent);
            }
        });
    }
}
