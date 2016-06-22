package com.ktds.jgbaek.mymemo0620.db;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ktds.jgbaek.mymemo0620.HttpClient;
import com.ktds.jgbaek.mymemo0620.MainActivity;
import com.ktds.jgbaek.mymemo0620.vo.MemoVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by 206-006 on 2016-06-20.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public int LAST_UPDATE_TIME ;

    private Context context;
    private int insertId;


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    /**
     * DB가 존재하지 않을 때, 딱 한번 실행된다
     * DB를 생성하는 역할
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuffer sb = new StringBuffer();
        sb.append( " CREATE TABLE MEMO_TABLE ( ");
        sb.append( " _ID INTEGER PRIMARY KEY, ");
        sb.append( " TITLE TEXT, " );
        sb.append( " CONTENT TEXT, ");
        sb.append( " DATE TEXT); ");
        // SQL 실행
        db.execSQL(sb.toString());

        LAST_UPDATE_TIME = 0;

        Toast.makeText(context, "테이블 생성됨", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // test 용
    public void testDB(){
        SQLiteDatabase db = getReadableDatabase();
    }

    // DAO
    public void addMemo(MemoVO memo) {

        MemoInsert memoInsert = new MemoInsert();

        Map<String,String> params = new HashMap<String, String>();
        params.put("title",memo.getTitle());
        params.put("content",memo.getContent());
        params.put("date",memo.getDate());
        memoInsert.execute(params);



    }

    public List<MemoVO> getAllMemos(){
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT _ID, TITLE, CONTENT, DATE FROM MEMO_TABLE ");

        // 읽기전용 DB객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();

        // Select에 온다.
        Cursor cursor = db.rawQuery(sb.toString(), null);

        List<MemoVO> memos = new ArrayList<MemoVO>();

        MemoVO memo = null;

        while(cursor.moveToNext()){
            memo = new MemoVO();
            memo.set_id(cursor.getInt(0));
            memo.setTitle(cursor.getString(1));
            memo.setContent(cursor.getString(2));
            memo.setDate(convertTime(cursor.getString(3)));
            memos.add(memo);
        }


        return memos;
    }

    public MemoVO getMemoById(String id){
        StringBuffer sb = new StringBuffer();
        System.out.println("id");
        System.out.println(id);

        sb.append("SELECT _ID, TITLE, CONTENT, DATE FROM MEMO_TABLE WHERE _ID="+id);

        // 읽기전용 DB객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();

        // Select에 온다.
        Cursor cursor = db.rawQuery(sb.toString(), null);

        MemoVO memo = new MemoVO();

        while(cursor.moveToNext()){
            memo.set_id(cursor.getInt(0));
            memo.setTitle(cursor.getString(1));
            memo.setContent(cursor.getString(2));
            memo.setDate(cursor.getString(3));
        }
        return memo;
    }

    public void modifyMemoById(MemoVO memo){
        SQLiteDatabase db = getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        System.out.println("id");
        System.out.println(memo.get_id());

//        sb.append("UPDATE MEMO_TABLE " +
//                "SET    TITLE   = "+memo.getTitle()+
//                " , CONTENT = "+memo.getContent()+
//                "WHERE  _ID="+memo.get_id());
//
//        db.execSQL(sb.toString(),null);

        sb.append("UPDATE MEMO_TABLE ");
        sb.append("SET TITLE = ? ");
        sb.append(", CONTENT = ? ");
        sb.append("WHERE _ID = ?");
        db.execSQL(sb.toString(), new Object[]{
                memo.getTitle(), memo.getContent(), memo.get_id()
        });

        Toast.makeText(context, "수정완료", Toast.LENGTH_SHORT).show();
    }

    public void deleteMemoById(String id){
        SQLiteDatabase db = getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        System.out.println("id");
        System.out.println(id);

        sb.append("DELETE FROM MEMO_TABLE ");
        sb.append("WHERE _ID = ? ");

        db.execSQL(sb.toString(), new Object[]{id});

        MemoDelete memoDelete = new MemoDelete();

        Map<String,String> params = new HashMap<String, String>();
        params.put("id",id);
        memoDelete.execute(params);

        Toast.makeText(context, "삭제완료", Toast.LENGTH_SHORT).show();

    }

    public String convertTime(String time){
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat fm2 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat fm3 = new SimpleDateFormat("HH:mm");

        // 메모 시간을 받아온다.
        Date memoTime = null;
        try {
            memoTime =  fm.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String memoDate = fm2.format(memoTime);

        // 현재 시간을 받아온다.
        Calendar cal = Calendar.getInstance(Locale.KOREA);
        cal.setTime(new Date());
        String nowDate = fm2.format(cal.getTime());

        // 둘을 비교해서 날짜가 같으면 시간만 반환한다.
        if ( memoDate.equals(nowDate)){
            return fm3.format(memoTime);
        }
        // 아니면 메모 시간 그대로 반환한다.
        else{
            return time;
        }
    }

    public class MemoInsert extends AsyncTask<Map<String,String>, Integer, String> {

        @Override
        protected String doInBackground(Map<String, String>... maps) {
            // Map 파라미터는 NetworkTask2의 파라미터와 같다.
            // ... 은 가변배열 파라미터 이다.

            // post로 요청하겠다는 뜻
            // HTTP요청 준비작업
            HttpClient.Builder http = new HttpClient.Builder("POST", "http://10.225.152.167:8080//AndroidProject//insertMemo");

            // Parameter를 전송한다.
//            http.addOrReplace("test", "한글한글..");
            http.addAllParameters(maps[0]);

            // HTTP요청 전송
            HttpClient post = http.create();
            post.request();

            // 응답 코드 가져오기.
            int statusCode = post.getHttpStatusCode();

            // 응답 본문 가져오기
            String body = post.getBody();

            // 바로 onPostExecute로 감 ui작업을 해줌
            return body;
        }

        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson();
            MemoVO memo = gson.fromJson(s, MemoVO.class);
            Toast.makeText(context, memo.get_id()+"", Toast.LENGTH_SHORT).show();

            // 1. 쓰기 가능한 db객체를 가져온다.
            SQLiteDatabase db = getWritableDatabase();

            // 2. Person Data를 Insert한다.
            StringBuffer sb = new StringBuffer();
            sb.append("INSERT INTO MEMO_TABLE ( ");
            sb.append("_ID, TITLE, CONTENT, DATE)");
            sb.append("VALUES(?,?,?,?)");

            db.execSQL(sb.toString(), new Object[]{
                    memo.get_id()+"", memo.getTitle(), memo.getContent(), memo.getDate()
            });

            Toast.makeText(context, "Insert 완료", Toast.LENGTH_SHORT).show();

            Intent intent1 = new Intent(context, MainActivity.class);
            context.startActivity(intent1);

        }
    }
    public class MemoDelete extends AsyncTask<Map<String,String>, Integer, String> {

        @Override
        protected String doInBackground(Map<String, String>... maps) {
            HttpClient.Builder http = new HttpClient.Builder("POST", "http://10.225.152.167:8080//AndroidProject//deleteMemo");

            // Parameter를 전송한다.
//            http.addOrReplace("test", "한글한글..");
            http.addAllParameters(maps[0]);

            // HTTP요청 전송
            HttpClient post = http.create();
            post.request();

            // 응답 코드 가져오기.
            int statusCode = post.getHttpStatusCode();

            // 응답 본문 가져오기
            String body = post.getBody();

            // 바로 onPostExecute로 감 ui작업을 해줌
            return body;
        }
    }
}