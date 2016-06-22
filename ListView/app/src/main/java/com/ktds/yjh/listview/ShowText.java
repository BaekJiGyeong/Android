package com.ktds.yjh.listview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class ShowText extends ActionBarActivity {

    TextView tvSubject = null;
    TextView tvAuthor = null;
    TextView tvHitCount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text);

        tvSubject = (TextView) findViewById(R.id.tvSubject);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvHitCount = (TextView) findViewById(R.id.tvHitCount);

        Intent intent = getIntent();
        ArticleVO articleVO = (ArticleVO) intent.getSerializableExtra("article");

        tvSubject.setText(articleVO.getSubject());
        tvAuthor.setText(articleVO.getAuthor());
        tvHitCount.setText(articleVO.getHitCount());
    }
}
