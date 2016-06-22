package com.ktds.jgbaek.simpleboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.ktds.jgbaek.simpleboard.db.SimpleDB;
import com.ktds.jgbaek.simpleboard.vo.ArticleVO;

public class DetailActivity extends AppCompatActivity {

    //textView 멤버변수를 적어준다.
    private TextView tvSubject;
    private TextView tvArticleNo;
    private TextView tvAuthor;
    private TextView tvDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvSubject = (TextView) findViewById(R.id.tvSubject);
        tvArticleNo = (TextView) findViewById(R.id.tvArticleNo);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvDescription = (TextView) findViewById(R.id.tvDescription);

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");

        ArticleVO articleVO = SimpleDB.getArticleVO(key);

        setTitle(articleVO.getSubject()); // 화면 name을 지정하는 방법법

       tvSubject.setText(articleVO.getSubject());
        tvArticleNo.setText(articleVO.getArticleNo()+"");
        tvAuthor.setText(articleVO.getAuthor());
        tvDescription.setText(articleVO.getDescription());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(DetailActivity.this, "엑티비티를 종료합니다.", Toast.LENGTH_SHORT).show();
    }
}
