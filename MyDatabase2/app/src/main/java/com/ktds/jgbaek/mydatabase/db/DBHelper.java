package com.ktds.jgbaek.mydatabase.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 206-006 on 2016-06-20.
 */
public class DBHelper extends SQLiteOpenHelper {

    private Context context;


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
        sb.append( " CREATE TABLE TEST_TABLE ( ");
        sb.append( " _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append( " NAME TEXT, " );
        sb.append( " AGE INTEGER, ");
        sb.append( " PHONE TEXT); ");
        // SQL 실행
        db.execSQL(sb.toString());

        Toast.makeText(context, "테이블 생성됨", Toast.LENGTH_SHORT).show();
    }

    /**
     * Application의 버전이 올라가 Table 구조가 변경되었을 때 실행
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context, "Version 올라감", Toast.LENGTH_SHORT).show();
    }

    public void testDB(){
        SQLiteDatabase db = getReadableDatabase();
    }
}