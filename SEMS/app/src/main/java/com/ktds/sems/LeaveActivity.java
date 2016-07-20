package com.ktds.sems;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LeaveActivity extends AppCompatActivity {

    private Button btnLeave;
    private Button btnNoLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        getSupportActionBar().hide();

        btnLeave = (Button) findViewById(R.id.btnLeave);
        btnNoLeave = (Button) findViewById(R.id.btnNoLeave);

        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnNoLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class LeaveTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient.Builder client = new HttpClient.Builder("POST", "http://192.168.43.142/m/leave");
            client.create().request();
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            LeaveActivity.this.finish();
        }
    }


}
