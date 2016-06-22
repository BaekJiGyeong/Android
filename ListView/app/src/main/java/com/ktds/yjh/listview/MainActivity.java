package com.ktds.yjh.listview;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ktds.yjh.listview.facebook.Facebook;
import com.restfb.types.Post;

import java.util.List;
// sdk 버전 21은 ActionBarActivity을 사용
public class MainActivity extends ActionBarActivity {

    private ListView lvArticleList;
    private Facebook facebook;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();



        lvArticleList = (ListView) findViewById(R.id.lvArticleList);

        facebook = new Facebook(this);
        boolean isLogin = facebook.isLogin();
        facebook.auth(new Facebook.After() {
            @Override
            public void doAfter(Context context) {
                setTimeLine();
            }
        });



//        final List<ArticleVO> articleList = new ArrayList<ArticleVO>();
//        for(int i = 0 ; i < 300 ; i++) {
//            articleList.add(new ArticleVO("제목입니다..."+i , "글쓴이입니다.", new Random().nextInt(9999) +""));
//        }
//
//        lvArticleList.setAdapter(new ArticleListViewAdapter(this, articleList));
//        lvArticleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(), ShowText.class);
//                intent.putExtra("article", article );
//
//                startActivity(intent);
//            }
//        });

    }

    /**
     * Action Bar에 메뉴를 생성한다
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);

        // 검색 기능 활성화
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        // 검색 버튼 가져옴
        MenuItem searchButton = menu.findItem(R.id.searchButton);

        // 검색 버튼을 클릭했을 떄 SearchView를 가져온다.
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchButton);

        // 검색 힌트를 설정한다.
        searchView.setQueryHint("검색어를 입력하세요");

        // SearchView를 검색 가능한 위젯으로 설정한다.
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * 검색 버튼을 클릭했을 때 동작하는 이벤트
             * @param s
             * @return
             */
            @Override
            public boolean onQueryTextSubmit(String s) {

                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("query",s);

                startActivity(intent);

                return true;
            }

            /**
             * 검색어를 입력할 때 동작하는 이벤트
             * @param s
             * @return
             */
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });



        return true;
    }

    /**
     * Menu 아이템을 클릭했을 때 발생되는 이벤트
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ( id == R.id.newPost ) {
            Toast.makeText(MainActivity.this, "새 글 등록 버튼 클릭", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, WritePostActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, 1000);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == 1000 && resultCode == RESULT_OK ) {
            setTimeLine();
        }
    }

    public void setTimeLine() {
        if(facebook.isLogin()) {
            // ..timeLine 가져오기
            facebook.getTimeLine(new Facebook.TimelineSerializable() {
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

       /*     // 가장 간단한 방법
            // 사용자가 처음으로 Flicking을 할 때 , 아래쪽에 만들어지는 Cell은 Null이다.
            if(convertView == null) {
                // Item Cell 에 Layout을 적용시킬 inflater 객체
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                // Item Cell에 Layout을 적용시킨다.
                convertView = inflater.inflate(R.layout.list_item_message, parent, false);
            }
            //TextView을 계속 생성
            TextView tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
            TextView tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
            TextView tvHitCount = (TextView) convertView.findViewById(R.id.tvHitCount);

            ArticleVO article = (ArticleVO) getItem(position);
            tvSubject.setText(article.getSubject());
            tvAuthor.setText(article.getAuthor());
            tvHitCount.setText(article.getHitCount());

            return convertView;
            */

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
