package com.ktds.jgbaek.project0609;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber1;
    private EditText etNumber2;
    private TextView tvResult;

    private Button btnPlus;
    private Button btnSub;
    private Button btnMul;
    private Button btnDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber1 = (EditText) findViewById(R.id.etNumber1);
        etNumber2 = (EditText) findViewById(R.id.etNumber2);
        tvResult = (TextView) findViewById(R.id.tvResult);

        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnDiv = (Button) findViewById(R.id.btnDiv);

        View.OnClickListener on = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String operator = (String) ((Button)v).getText();
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                intent.putExtra("first",etNumber1.getText().toString());
                intent.putExtra("second",etNumber2.getText().toString());
                intent.putExtra("operator",operator);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent,1000);

            }
        };
        btnPlus.setOnClickListener(on);
        btnSub.setOnClickListener(on);
        btnMul.setOnClickListener(on);
        btnDiv.setOnClickListener(on);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == RESULT_OK){
            tvResult.setText(data.getStringExtra("result"));
        }
    }
}
