package com.ktds.jgbaek.project0609;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    private TextView tvResult;
    private Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        tvResult = (TextView) findViewById(R.id.tvResult);
        btnHome = (Button) findViewById(R.id.btnHome);

        Intent intent = getIntent();
        String first = intent.getStringExtra("first");
        String second = intent.getStringExtra("second");
        String operator = intent.getStringExtra("operator");

        Double firstNum = Double.parseDouble(first);
        Double secondNum = Double.parseDouble(second);
        Double result;

        if(operator.equals("+")){
            result = firstNum + secondNum;
        }
        else if(operator.equals("-")){
            result = firstNum + secondNum;
        }
        else if(operator.equals("*")){
            result = firstNum * secondNum;
        }
        else if(operator.equals("/")){
            result = firstNum / secondNum;
        }
        else {
            result = 0.0;
        }

        String stringResult = firstNum+" "+operator+" "+secondNum+" 의 결과는 "+result+" 입니다.";

        tvResult.setText(stringResult);

        btnHome.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra("result",tvResult.getText().toString());

                setResult(RESULT_OK, result);

                finish();
            }
        });

    }
}
