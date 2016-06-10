package com.ktds.jgbaek.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnRegist;
    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = (EditText) findViewById(R.id.etEmail);

        btnRegist= (Button) findViewById(R.id.btnRegist);
        btnRegist.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, 1000); //  데이터를 돌려받을 때 너가 1000번으로 보낸 요청에 대한 응답이다 / 1000 : 요청에 대한 식별자

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//         // 선택코드 1000, 결과코드 result_ok, intent
//        Log.d("RESULT", requestCode+"");
//        Log.d("RESULT", resultCode+"");
//        Log.d("RESULT", data.getStringExtra("email"));

        if(requestCode == 1000 && resultCode == RESULT_OK){
            Toast.makeText(MainActivity.this, "회원가입을 완료했습니다.!", Toast.LENGTH_SHORT).show();
            etEmail.setText(data.getStringExtra("email"));
        }
    }
}
