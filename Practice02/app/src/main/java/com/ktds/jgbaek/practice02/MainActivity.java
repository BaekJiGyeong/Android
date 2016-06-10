package com.ktds.jgbaek.practice02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ktds.jgbaek.practice02.db.AddressDB;
import com.ktds.jgbaek.practice02.vo.AddressVO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareSimpleDB();

        LinearLayout ll= (LinearLayout) findViewById(R.id.itemList);

        //반복시작
        for(int i = 0; i< AddressDB.getNames().size(); i++){
            Button button = new AppCompatButton(this);
            button.setText(AddressDB.getNames().get(i));

            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String buttonText = (String) ((Button)v).getText();

                    Intent intent = new Intent(v.getContext(), DetailActivity.class);

                    intent.putExtra("name", buttonText);
                    startActivity(intent);
                }
            });
            ll.addView(button);
        }
        //반복끝

    }

    private void prepareSimpleDB() {

        AddressDB.addAddress("1", new AddressVO(1,"이기연", "010-1234-5678", "서울시 이대앞"));
        AddressDB.addAddress("2", new AddressVO(2,"김광민", "010-5678-1234", "서울시 분당앞"));
        AddressDB.addAddress("3", new AddressVO(3,"백지경", "010-1004-5008", "서울시 집앞"));


    }
}
