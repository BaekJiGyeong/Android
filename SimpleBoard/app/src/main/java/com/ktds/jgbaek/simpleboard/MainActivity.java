package com.ktds.jgbaek.simpleboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ktds.jgbaek.simpleboard.db.SimpleDB;
import com.ktds.jgbaek.simpleboard.vo.ArticleVO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareSimpleDB();

        LinearLayout ll= (LinearLayout) findViewById(R.id.itemList);

        //반복시작
        for(int i=0; i<SimpleDB.getIndexes().size(); i++){
            Button button = new AppCompatButton(this);
            button.setText(SimpleDB.getIndexes().get(i));

            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String buttonText = (String) ((Button)v).getText();

                    Intent intent = new Intent(v.getContext(), DetailActivity.class);

                    intent.putExtra("key", buttonText);
                    startActivity(intent);
                }
            });
            ll.addView(button);
        }
        //반복끝
    }

    private void prepareSimpleDB() {
        for(int i=1; i<100; i++){
            SimpleDB.addArticle(i+"번글", new ArticleVO(i,i+"번글 입니다.", i+"번글 입니다. 내용입니다.", "내가 씀"));
        }

    }

    private long pressedTime;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        // Alert를 띄워서 종료시키기
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("종료 알림").setMessage("정말 종료하시겠습니까?").setPositiveButton("종료합니다.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNeutralButton("취소합니다.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "취소했습니다.", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "종료하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }).create().show();



        /*
        // 뒤로가기 두번 누르면 종료되기
        if( pressedTime == 0 ){
            Toast.makeText(MainActivity.this, "한번 더 누르면 종료합니다.", Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int)((System.currentTimeMillis() - pressedTime));
            if(seconds > 2000 ) {
                Toast.makeText(MainActivity.this, "한번 더 누르면 종료합니다.", Toast.LENGTH_LONG).show();
                pressedTime = 0;
            }
            else {
                finish(); // 앱종료
            }
        }*/


    }
}
