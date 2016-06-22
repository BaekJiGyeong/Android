package com.ktds.jgbaek.mymemo0620;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ktds.jgbaek.mymemo0620.db.DBHelper;
import com.ktds.jgbaek.mymemo0620.vo.MemoVO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WriteActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    private EditText etTitle;
    private EditText etContent;
    private Button btnDone;

    private String date;
    private MemoVO memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        if(dbHelper==null){
            dbHelper = new DBHelper(WriteActivity.this, "MEMO", null, dbHelper.DB_VERSION);
        }

        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance(Locale.KOREA);
        cal.setTime(new Date());

        memo = new MemoVO();
        date = fm.format(cal.getTime());
        System.out.println("날짜");
        System.out.println(date);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        btnDone = (Button) findViewById(R.id.btnDone);

        Intent intent = getIntent();

        if( intent != null) {
            final String id = intent.getStringExtra("id");
            System.out.println(id);
            memo = dbHelper.getMemoById(id);
            etTitle.setText(memo.getTitle());
            etContent.setText(memo.getContent());
        }

        final int memoId = memo.get_id();



        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MemoVO memoVO = new MemoVO();
                memoVO.setTitle(etTitle.getText().toString());
                memoVO.setContent(etContent.getText().toString());
                memoVO.setDate(date);

                if(memoId >0){
                    memo.set_id(memoId);
                    dbHelper.modifyMemoById(memo);
                    setResult(RESULT_OK);
                    Intent intent1 = new Intent(WriteActivity.this, MainActivity.class);
                    startActivity(intent1);
                }
                else {
                    dbHelper.addMemo(memoVO);
                }

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                dbHelper.deleteMemoById(memoId+"");
                Intent intent1 = new Intent(WriteActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_write, menu);
        return true;
    }
}
