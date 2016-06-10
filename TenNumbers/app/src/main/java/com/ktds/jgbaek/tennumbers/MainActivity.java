package com.ktds.jgbaek.tennumbers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button1 = (Button) findViewById(R.id.btn1);
        final Button button2 = (Button) findViewById(R.id.btn2);
        final Button button3 = (Button) findViewById(R.id.btn3);
        final Button button4 = (Button) findViewById(R.id.btn4);
        final Button button5 = (Button) findViewById(R.id.btn5);
        final Button button6 = (Button) findViewById(R.id.btn6);
        final Button button7 = (Button) findViewById(R.id.btn7);
        final Button button8 = (Button) findViewById(R.id.btn8);
        final Button button9 = (Button) findViewById(R.id.btn9);
        final Button button0 = (Button) findViewById(R.id.btn0);
        final Button buttonPlus = (Button) findViewById(R.id.plusBtn);
        final Button buttonEqual = (Button) findViewById(R.id.equalBtn);

        final TextView textView = (TextView) findViewById(R.id.textView2);
        final Map<String, String> numbers = new HashMap<String, String>();
        final TextView firstNumber = null;
        final TextView secondNumber = null;

        buttonPlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), ((Button) v).getText(), Toast.LENGTH_SHORT).show();
                String text = (String)textView.getText();
                firstNumber.setText(text);

                textView.setText("0");
            }
        });

        buttonEqual.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), ((Button) v).getText(), Toast.LENGTH_SHORT).show();

                int first = Integer.parseInt((String) firstNumber.getText());
                int second = Integer.parseInt((String)textView.getText());

                int result = first + second;

                String text = result+"";
                textView.setText(text);
            }
        });

        View.OnClickListener clickListener = new View.OnClickListener() {

            private int firstNumber=0;
            @Override
            public void onClick(View v) {

//                if( v.getId()==R.id.plusBtn){
//                    firstNumber = Integer.parseInt(textView.getText()+"");
//                    textView.setText("0");
//                }
//                else if(v.getId()==R.id.equalBtn){
//                    int secondNumber = Integer.parseInt(textView.getText()+"");
//                    textView.setText((firstNumber+secondNumber)+"");
//                }
                Toast.makeText(v.getContext(), ((Button) v).getText(), Toast.LENGTH_SHORT).show();

                String text = (String)textView.getText();
                text += ((Button)v).getText();
                textView.setText(Integer.parseInt(text)+"");
            }
        };



        button1.setOnClickListener(clickListener);
        button2.setOnClickListener(clickListener);
        button3.setOnClickListener(clickListener);
        button4.setOnClickListener(clickListener);
        button5.setOnClickListener(clickListener);
        button6.setOnClickListener(clickListener);
        button7.setOnClickListener(clickListener);
        button8.setOnClickListener(clickListener);
        button9.setOnClickListener(clickListener);
        button0.setOnClickListener(clickListener);




    }
}
