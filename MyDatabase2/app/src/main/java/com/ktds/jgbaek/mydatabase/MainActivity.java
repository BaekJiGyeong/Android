package com.ktds.jgbaek.mydatabase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ktds.jgbaek.mydatabase.db.DBHelper;

public class MainActivity extends AppCompatActivity {

    private Button btnCreateDatabase;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateDatabase = (Button) findViewById(R.id.btnCreateDatabase);
        btnCreateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText etDBName = new EditText(MainActivity.this);
                etDBName.setHint("DB명을 입력하세요");

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Database 이름을 ")
                        .setMessage(("Database 이름을 입력하세요"))
                        .setView(etDBName)
                        .setPositiveButton("생성", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(etDBName.getText().toString().length()>0){
                                    dbHelper = new DBHelper(MainActivity.this,
                                            etDBName.getText().toString(),
                                            null, 1);
                                }
                                dbHelper.testDB();

                                // Toast.makeText(MainActivity.this, etDBName.getText(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();

            }
        });
    }
}
