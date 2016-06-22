package com.ktds.yjh.listview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.ktds.yjh.listview.facebook.Facebook;
import com.restfb.types.Post;

public class DetailActivity extends ActionBarActivity {

    private TextView tvDetail = null;
    private TextView tvDetailName = null;
    private TextView tvDetailLikes = null;

    private Facebook facebook;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvDetail = (TextView) findViewById(R.id.tvDetail);
        tvDetailName = (TextView) findViewById(R.id.tvDetailName);
        tvDetailLikes = (TextView) findViewById(R.id.tvDetailLikes);

        facebook =  new Facebook(this);

        handler = new Handler();


        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");

        facebook.auth(new Facebook.After() {
            @Override
            public void doAfter(Context context) {
                setDetail(id);
            }
        });



    }


    public void setDetail(String id) {

            facebook.getDetail( id, new Facebook.DetailData() {
                @Override
                public void data(final Post post) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvDetail.setText(post.getMessage());
                            tvDetailName.setText(post.getFrom().getName());
                            if(post.getLikes() == null ) {
                                tvDetailLikes.setText("0");
                            }else{
                                tvDetailLikes.setText(post.getLikes().getData().size()+"");
                            }
                        }
                    });
                }
            });

    }
}
