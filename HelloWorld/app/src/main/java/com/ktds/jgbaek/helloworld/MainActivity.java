package com.ktds.jgbaek.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button1 = (Button) findViewById(R.id.btn1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //view 도 button1
                //Toast.makeText(v.getContext(), button1.getText(), Toast.LENGTH_SHORT).show();
                Toast.makeText(v.getContext(), ((Button) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void call(View view) {
        int id = view.getId();

        if( id == R.id.button) {
            Toast.makeText(view.getContext(), "버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-4310-0000"));
            startActivity(intent);
        }
        else if ( id == R.id.textView){
            Toast.makeText(view.getContext(), "텍스트를 눌렀습니다.", Toast.LENGTH_SHORT).show();
        }

    }
}
