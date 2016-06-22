package com.ktds.jgbaek.mymemo0620;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ktds.jgbaek.mymemo0620.db.DBHelper;
import com.ktds.jgbaek.mymemo0620.vo.MemoVO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ListView lvMomos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Toast.makeText(MainActivity.this, "새 글 등록 버튼 클릭", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, WriteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, 1000);
            }
        });

        if(dbHelper == null) {
            dbHelper = new DBHelper(MainActivity.this, "MEMO", null, dbHelper.DB_VERSION);
            dbHelper.testDB();
        }

        lvMomos = (ListView) findViewById(R.id.lvMemos);
        List<MemoVO> memos = dbHelper.getAllMemos();
        System.out.println("출력");
        for(MemoVO memo: memos){
               System.out.println( memo.get_id());
        }

        // ListView에 Person 데이터를 모두 가져온다.

        lvMomos.setAdapter(new MemoListAdapter(memos, MainActivity.this));

    }

    private class MemoListAdapter extends BaseAdapter {

        private List<MemoVO> memos;
        private Context context;
        private MemoVO memo;

        public MemoListAdapter(List<MemoVO> memos, Context context){
            this.memos = memos;
            this.context = context;
        }

        @Override
        public int getCount() {
            return this.memos.size();
        }

        @Override
        public Object getItem(int position) {
            return this.memos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder = null;

            if(convertView==null){
                convertView = new LinearLayout(context);
                ((LinearLayout)convertView).setOrientation(LinearLayout.VERTICAL);

                TextView tvId = new TextView(context);
                TextView tvTitle = new TextView(context);
                tvTitle.setTextSize(30);
                TextView tvContent = new TextView(context);
                TextView tvDate = new TextView(context);


//                ((LinearLayout)convertView).addView(tvId);
                ((LinearLayout)convertView).addView(tvTitle);
//                ((LinearLayout)convertView).addView(tvContent);
                ((LinearLayout)convertView).addView(tvDate);

                holder = new Holder();
                holder.tvId = tvId;
                holder.tvTitle = tvTitle;
                holder.tvContent = tvContent;
                holder.tvDate = tvDate;

                convertView.setTag(holder);

            }
            else{
                holder = (Holder) convertView.getTag();
            }

            memo = (MemoVO) getItem(position);
            holder.tvId.setText(memo.get_id()+"");
            holder.tvTitle.setText(memo.getTitle());
            holder.tvContent.setText(memo.getContent());
            holder.tvDate.setText(memo.getDate());


            final Holder finalHolder = holder;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, finalHolder.tvId.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                    intent.putExtra("id", finalHolder.tvId.getText().toString());
                    startActivity(intent);
                }
            });
            return convertView;

        }

    }

    private class Holder {
        public TextView tvId;
        public TextView tvTitle;
        public TextView tvContent;
        public TextView tvDate;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == 1000 && resultCode == RESULT_OK ) {
            lvMomos = (ListView) findViewById(R.id.lvMemos);
            List<MemoVO> memos = dbHelper.getAllMemos();
            lvMomos.setAdapter(new MemoListAdapter(memos, MainActivity.this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if ( id == R.id.newPost ) {
            Toast.makeText(MainActivity.this, "새 글 등록 버튼 클릭", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, WriteActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, 1000);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
