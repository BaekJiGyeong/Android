package com.ktds.jgbaek.customlistview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ktds.jgbaek.customlistview.facebook.Facebook;
import com.restfb.types.Post;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    private ListView lvArticleList;
    private Facebook facebook ;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();


        lvArticleList = (ListView) findViewById(R.id.lvArticleList);

        Facebook facebook = new Facebook(this);
        facebook.auth(new Facebook.After() {
            @Override
            public void doAfter(Context context) {
                setTimeline();
            }
        });





//        final List<ArticleVO> articleList = new ArrayList<ArticleVO>();
//        for (int i = 0; i < 300; i++){
//            articleList.add(new ArticleVO("제목입니다."+i, "글쓴이입니다." , new Random().nextInt(9999)+""));
//        }
//        lvArticleList.setAdapter((new ArticleListViewAdapter(this, articleList)));
//        lvArticleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, id + "", Toast.LENGTH_SHORT).show();
//                ArticleVO article = articleList.get(position);
//                Toast.makeText(MainActivity.this, article.getSubject(), Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(""));
//                intent.putExtra("article", article);
//            }
//        });
    }


    /**
     * 옵션바에 메뉴를 관리한다.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return true;
    }

    /**
     * 메뉴 아이템들을 클릭했을 떄 발생되는 이벤트
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.newPost){
            Intent intent = new Intent(this, WritePostActivity.class);
            startActivityForResult(intent, 1000);
            //Toast.makeText(MainActivity.this, "새글 등록버튼을 클릭했습니다.", Toast.LENGTH_SHORT).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == 1000 && resultCode == RESULT_OK ) {
            setTimeline();
        }
    }

    public void setTimeline(){
        if( facebook.isLogin() ){
            // timeline 가져오기
            facebook.getTimeline(new Facebook.TimelineSerializable() {
                @Override
                public void serialize(final List<Post> posts) {

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            lvArticleList.setAdapter(new ArticleListViewAdapter(MainActivity.this, posts));
                        }
                    });
                }
            });
        }
    }

    private class ArticleListViewAdapter extends BaseAdapter {

        /**
         *  ListView에 Item을 셋팅할 요청자의 객체가 들어감
         */
        private Context context;

        /**
         * ListView 셋팅할 Item 정보들
         */
        private List<Post> articleList;

        private Post article;

        public ArticleListViewAdapter(Context context, List<Post> articleList) {
            this.context = context;
            this.articleList = articleList;
        }


        /**
         * 속성에 따라서 보여질 Item Layout을 저해준다.
         * @param position
         * @return
         */
        @Override
        public int getItemViewType(int position) {

            /**
             *  만약, Message가 Null이 아니라면 List Item message를 보여주고
             *  만약, Story가 Null이 아니라면 lit_item_story를 보여주고
             *  만약, List가 Null이 아니라면 list_item_link를 보여준다.
             */

            article = (Post) getItem(position);
            if(article.getMessage()!= null && article.getMessage().length()>0){
                return 0;
            }
            else if(article.getStory()!= null && article.getStory().length()>0){
                return 1;
            }
            else if(article.getLink()!= null && article.getLink().length()>0){
                return 2;
            }

            return 0;
        }

        public int getLayoutType(int index){
            if(index==0){
                return R.layout.list_item_message;
            }
            else if(index==1){
                return R.layout.list_item_story;
            }
            else if(index==2){
                return R.layout.list_item_link;
            }
            return -1;
        }


        /**
         * 아이템 레이아웃의 개수를 정한다.
         * @return
         */
        @Override
        public int getViewTypeCount() {
            return 3;
        }


        /**
         * ListView에 셋팅할 아이템의 개수
         * @return
         */
        @Override
        public int getCount() {
            return this.articleList.size();
        }

        /**
         * position 번째 Item정보를 가져옴
         * @param position
         * @return
         */
        @Override
        public Object getItem(int position) {
            return this.articleList.get(position);
        }

        /**
         * Item Index를 가져옴
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
         * ListView에 Item들을 셋팅함
         *
         * @param convertView - 현재 보여질 Item의 Index 0부터 getCount()를 실행함
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 가장 간단한 방법
            // 사용자가 처음으로 Flicking을 할때 아래쪽에 만들어지는 셀은 null이다.
//            if (convertView == null) {
//                // Item cell에 Layout을 적용 시킬 Inflater
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//                //  Item Cell에 Layout을 적용시킨다.
//                convertView = inflater.inflate(R.layout.list_item_message, parent, false);
//            }
//
//            TextView tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
//            TextView tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
//            TextView tvHitCount = (TextView) convertView.findViewById(R.id.tvHitCount);
//
//            ArticleVO article = (ArticleVO) getItem(position);
//            tvSubject.setText(article.getSubject());
//            tvAuthor.setText(article.getAuthor());
//            tvHitCount.setText(article.getHitCount());

            ItemHolder holder = null;
            int layoutType = getItemViewType(position);
            // 가장 효율적인 방법
            if (convertView == null) {

                // Item cell에 Layout을 적용 시킬 Inflater
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                //  Item Cell에 Layout을 적용시킨다.

                convertView = inflater.inflate(getLayoutType(layoutType), parent, false);

                holder = new ItemHolder();
                if (layoutType == 0) {
                    holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
                    holder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
                    holder.tvHitCount = (TextView) convertView.findViewById(R.id.tvHitCount);
                }
                if (layoutType == 1) {
                    holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
                } else if (layoutType == 2) {
                    holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
                    holder.tvSubject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getLink()));
                            startActivity(intent);
                        }
                    });
                }
                convertView.setTag(holder);
            }
            else {
                holder = (ItemHolder) convertView.getTag();
            }


            article = (Post) getItem(position);

            if(layoutType==0){
                holder.tvSubject.setText(article.getMessage());
                holder.tvAuthor.setText(article.getFrom().getName());

                if(article.getLikes()!=null){
                    holder.tvHitCount.setText(article.getLikes().getData().size()+"");
                }
                else
                    holder.tvHitCount.setText("0");
            }

            else if(layoutType==1){
                holder.tvSubject.setText(article.getStory());
            }
            else if(layoutType==2){
                holder.tvSubject.setText(article.getLink());
            }


            return convertView;

        }
    }

    private class ItemHolder {
        public TextView tvSubject;
        public TextView tvAuthor;
        public TextView tvHitCount;
    }

}
