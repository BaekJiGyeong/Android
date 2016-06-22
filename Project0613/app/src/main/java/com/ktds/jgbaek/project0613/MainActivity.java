package com.ktds.jgbaek.project0613;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new MyListAdapter(this));
    }

    private class MyListAdapter extends BaseAdapter {

        private List<String> list;
        private Context context;

        public MyListAdapter(Context context){
            this.context = context;

            list = new ArrayList<>();
            list.add("첫 번째");
            list.add("두 번째");
            list.add("세 번째");
            list.add("네 번째");
            list.add("다섯 번째");
            list.add("여섯 번째");
            list.add("일곱 번째");
            list.add("여덟 번째");
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                        context.LAYOUT_INFLATER_SERVICE
                );
                convertView = inflater.inflate(R.layout.list_item, parent, false);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.textView);
            textView.setText((String)getItem(position));

            textView.setOnClickListener(new TextView.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), ((TextView)v).getText() + "선택됨", Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
    }
}
