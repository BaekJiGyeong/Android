package com.ktds.jgbaek.myapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPasswordConfirm;
    private EditText etPassword;
    private Button btnDone;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Pattern p = Pattern.compile("^[a-zA-X0-9]@[a-zA-Z0-9].[a-zA-Z0-9]");
                    Matcher m = p.matcher((etEmail).getText().toString());

                    if ( !m.matches()){
                        Toast.makeText(RegistActivity.this, "Email형식으로 입력하세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        etPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = etPassword.getText().toString();
                String confirm = etPasswordConfirm.getText().toString();

                if(password.equals(confirm)){
                    etPassword.setBackgroundColor(Color.GREEN);
                    etPasswordConfirm.setBackgroundColor(Color.GREEN);
                }
                else {
                    etPassword.setBackgroundColor(Color.RED);
                    etPasswordConfirm.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPasswordConfirm.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String password = etPassword.getText().toString();
                String confirm = etPasswordConfirm.getText().toString();

                if(password.equals(confirm)){
                    etPasswordConfirm.setBackgroundColor(Color.RED);
                }
                else {
                    etPasswordConfirm.setBackgroundColor(Color.GREEN);
                }

                return false;
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(etEmail.getText().toString().length()==0){
                    Toast.makeText(RegistActivity.this, "Email을 입력하세요", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }

                if(etPasswordConfirm.getText().toString().length()==0){
                    Toast.makeText(RegistActivity.this, "Email을 입력하세요", Toast.LENGTH_SHORT).show();
                    etPasswordConfirm.requestFocus();
                    return;
                }

                if(etPassword.getText().toString().length()==0){
                    Toast.makeText(RegistActivity.this, "비밀번호 입력하세요", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

                if(!etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())){
                    Toast.makeText(RegistActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etPasswordConfirm.setText("");
                    etPassword.requestFocus();
                    return;
                }

                Intent result = new Intent();
                result.putExtra("email",etEmail.getText().toString());

                setResult(RESULT_OK, result);
                finish();
            }
        });


    }
}
