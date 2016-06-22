package com.ktds.yjh.listview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ktds.yjh.listview.facebook.Facebook;
import com.restfb.types.Post;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends ActionBarActivity {

    private final int SEPARATE = 500;

    private ListView lvArticleList;
    private Facebook facebook;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        handler = new Handler();
        facebook = new Facebook(this);
        lvArticleList = (ListView) findViewById(R.id.lvArticleList);

        // 튀로가기 버튼
        android.support.v7.app.ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        //검색어 받아오기
        Intent intent = getIntent();
        final String query = intent.getStringExtra("query");

        // 검색어를 ActionBar에 보여주기
        setTitle("검색어 : "+query);

        facebook.auth(new Facebook.After() {
            @Override
            public void doAfter(Context context) {
                setTimeLine(query);
            }
        });


    }

    public void setTimeLine(final String query) {
        if(facebook.isLogin()) {
            // ..timeLine 가져오기
            facebook.getTimeLine(new Facebook.TimelineSerializable() {
                @Override
                public void serialize(final List<Post> posts) {

                    int postSize = posts.size();
                    int threadCount = 0;

                    if ( postSize>SEPARATE ) {
                        threadCount = (int) Math.round(postSize/SEPARATE);
                    }

                    threadCount++;

                    final List<Post> searchPost = new ArrayList<Post>();

                    final BaseAdapter baseAdapter = new ArticleListViewAdapter(SearchActivity.this, searchPost);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            lvArticleList.setAdapter(baseAdapter);
                        }
                    });

                    for(int i=0; i<threadCount; i++){
                        final int startIndex = i*SEPARATE;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Post post = null;
                                for( int j = startIndex; j < ( startIndex + SEPARATE ); j++ ) {
                                    try {
                                        post = posts.get(j);
                                    }
                                    catch(RuntimeException re){
                                        break;
                                    }

                                    if(post.getMessage() != null && post.getMessage().length()>0){
                                        if(post.getMessage().toLowerCase().contains(query.toLowerCase())){
                                            setPost(post,searchPost,baseAdapter);
                                        }
                                    }
                                    else if(post.getStory() != null && post.getStory().length()>0){
                                        if(post.getStory().toLowerCase().contains(query.toLowerCase())){
                                            setPost(post,searchPost,baseAdapter);
                                        }
                                    }
                                    else if(post.getLink() != null && post.getLink().length()>0){
                                        if(post.getLink().toLowerCase().contains(query.toLowerCase())){
                                            setPost(post,searchPost,baseAdapter);
                                        }
                                    }
                                }
                            }
                        }).start();
                    }

//                    Post post = null;
//
//                    // 검색어와 관련이 없는 메시지는 제외시킨다.
//                    for(int i=posts.size()-1; i>=0 ; i--){
//
//                        post = posts.get(i);
//
//                        if(post.getMessage() != null && post.getMessage().length()>0){
//                            if(!post.getMessage().contains(query)){
//                                posts.remove(post);
//                            }
//                        }
//                        else if(post.getStory() != null && post.getStory().length()>0){
//                            if(!post.getStory().contains(query)){
//                                posts.remove(post);
//                            }
//                        }
//                        else if(post.getLink() != null && post.getLink().length()>0){
//                            if(!post.getLink().contains(query)){
//                                posts.remove(post);
//                            }
//                        }
//                    }

                }
            });
        }
    }

    private void setPost(Post post, final List<Post> searchPost, final BaseAdapter baseAdapter){
        searchPost.add(post);

        handler.post(new Runnable() {
            @Override
            public void run() {
                baseAdapter.notifyDataSetChanged();
            }
        });
    }

    private class ArticleListViewAdapter extends BaseAdapter {
        /**
         * ListView 에 Item을 세팅할 요청자에 객체가 들어감.
         */
        private Context context;
        /**
         * ListView에 셋팅할 Item 정보를...
         */
        private List<Post> articleList;

        private Post article;

        public ArticleListViewAdapter(Context context, List<Post> articleList) {
            this.context = context;
            this.articleList = articleList;
        }

        /**
         * Item 속성에 따라서 보여질 Item Layout 정해줌
         * @param position
         * @return
         */
        @Override
        public int getItemViewType(int position) {

            // Message가 Null이 아니면 list_item_message 보임
            // Story가 Null이 아니라면 list_item_stroy 보임
            // Link가 Null이 아니라면 list_item_link 보임

            article = (Post) getItem(position);
            if ( article.getMessage() != null && article.getMessage().length() > 0 ) {
                return 0;
            }
            else if ( article.getStory() != null && article.getStory().length() > 0 ) {
                return 1;
            }
            else if ( article.getLink() != null && article.getLink().length() > 0 ) {
                return 2;
            }

            return 0;
        }

        public int getLayoutType(int index) {
            if ( index == 0 ) {
                return R.layout.list_item_message;
            }
            else if ( index == 1 ) {
                return R.layout.list_item_story;
            }
            else if ( index == 2 ) {
                return R.layout.list_item_link;
            }

            return -1;
        }

        /**
         * Item Layout의 개수를 정한다
         * @return
         */
        @Override
        public int getViewTypeCount() {
            return 3;
        }

        /**
         * ListView에 셋팅할 아이템들의 개수
         * @return
         */
        @Override
        public int getCount() {
            return this.articleList.size();
        }

        /**
         * position번째 Item 정보를 가져옴.
         * @param position
         * @return
         */
        @Override
        public Object getItem(int position) {
            return this.articleList.get(position);
        }

        /**
         * Item Index을 가져옴
         * Item Index == position
         *
         * @param position
         * @return
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * ListView에 Item 들을 셋팅함
         *
         * @param position : 현재 보여질 Item의 Index 0부터 getcount까지 증가함
         * @param convertView : ListView의 ItemCell 객체를 가져옴.
         * @param parent : ListView
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ItemHolder holder = null;
            int layoutType = getItemViewType(position);
            article = (Post) getItem(position);
            // 가장 효율적인 방법
            if(convertView == null) {

                // Item Cell 에 Layout을 적용시킬 inflater 객체
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                // Item Cell에 Layout을 적용시킨다.
                convertView = inflater.inflate(getLayoutType(layoutType), parent, false);

                holder = new ItemHolder();

                if ( layoutType == 0 ) {
                    holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
                    holder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
                    holder.tvHitCount = (TextView) convertView.findViewById(R.id.tvHitCount);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(view.getContext(), article.getFrom().getName(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                            intent.putExtra("id", article.getId());
                            startActivity(intent);
                        }
                    });
                    //convertView.setTag(R.string.postId, );
                    // holder. id 집어넣는다.

                }
                else if ( layoutType == 1 ) {
                    holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
                }
                else if ( layoutType == 2 ) {
                    holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
                    holder.tvSubject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getLink()));
                            startActivity(intent);
                        }
                    });
                }

                // 항상 holder을 가짐으로서 TextView을 계속 생성해서 메모리낭비를 하지 않도록 한다.
                convertView.setTag(holder);
            } else {
                Log.d("RESULT", "NOT NULL");
                holder = (ItemHolder) convertView.getTag();
            }


            if ( layoutType == 0 ) {
                holder.tvSubject.setText(article.getMessage());
                holder.tvAuthor.setText(article.getFrom().getName());

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), article.getFrom().getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                        intent.putExtra("id", article.getId());
                        startActivity(intent);
                    }
                });

                if(article.getLikes() == null ) {
                    holder.tvHitCount.setText("0");
                }else{
                    holder.tvHitCount.setText(article.getLikes().getData().size()+"");
                }
            }
            else if ( layoutType == 1 ) {
                holder.tvSubject.setText(article.getStory());
            }
            else if ( layoutType == 2 ) {
                holder.tvSubject.setText(article.getLink());
            }
            return convertView;
        }

        private class ItemHolder {
            public TextView tvSubject;
            public TextView tvAuthor;
            public TextView tvHitCount;

        }


    }
}
