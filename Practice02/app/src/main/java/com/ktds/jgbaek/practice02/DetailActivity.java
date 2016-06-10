package com.ktds.jgbaek.practice02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.ktds.jgbaek.practice02.db.AddressDB;
import com.ktds.jgbaek.practice02.vo.AddressVO;

public class DetailActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvId;
    private TextView tvPhoneNumber;
    private TextView tvAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvName = (TextView) findViewById(R.id.tvName);
        tvId = (TextView) findViewById(R.id.tvId);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        tvAddress = (TextView) findViewById(R.id.tvAddress);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        AddressVO addressVO = AddressDB.getAddress(name);

        tvName.setText(addressVO.getName());
        tvId.setText(addressVO.getId()+"");
        tvPhoneNumber.setText(addressVO.getPhoneNumber());
        tvAddress.setText(addressVO.getAddress());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(DetailActivity.this, "엑티비티를 종료합니다.", Toast.LENGTH_SHORT).show();
    }
}
