package com.ktds.sems;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AttendListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttendListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;


    public AttendListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AttendListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AttendListFragment newInstance(String param1) {
        AttendListFragment fragment = new AttendListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    private TextView courseTitle;
    private ListView tvAttendList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_attend_list, container, false);

        courseTitle = (TextView) view.findViewById(R.id.courseTitle);
        tvAttendList = (ListView) view.findViewById(R.id.tvAttendList);

        GetAttendListTask getAttendListTask = new GetAttendListTask();
        getAttendListTask.execute(mParam1);

        return view;
    }

    private class GetAttendListTask extends AsyncTask<String, String, String >{

        @Override
        protected String doInBackground(String... params) {

            HttpClient.Builder client = new HttpClient.Builder("POST", "http://10.225.152.167:8080/m/getAttendList/" + params[0]);

            HttpClient post = client.create();
            post.request();

            return post.getBody();
        }

        @Override
        protected void onPostExecute(String s) {
//            Log.d("RESULT", s);

            Gson gson = new Gson();
            Type educationAttendList = new TypeToken<EducationAttend>() {}.getType();

            EducationAttend educationAttend = gson.fromJson(s, educationAttendList);

            courseTitle.setText(educationAttend.getEduInfo().getEducationTitle());
            tvAttendList.setAdapter(new AttendAdapter(educationAttend));
        }
    }

    private class AttendAdapter extends BaseAdapter {

        private Map<String , List<String>> attends;

        public AttendAdapter(EducationAttend educationAttend){
            this.attends = educationAttend.getAttends();
        }

        @Override
        public int getCount() {
            return attends.size();
        }

        @Override
        public Object getItem(int position) {

            int index = 0;

            Iterator<String> keys = attends.keySet().iterator();

            String key = "";
            while( keys.hasNext() ){
                key = keys.next();

                if(position == index) {
                    break;
                }
                index++;
            }

            return attends.get(key);
        }

        private String getDate(int position) {
            int index = 0;

            Iterator<String> keys = attends.keySet().iterator();

            String key = "";
            while( keys.hasNext() ){
                key = keys.next();

                if(position == index) {
                    break;
                }
                index++;
            }

            return key;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder =null;

            if(convertView == null ){
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.attend_item, parent, false);

                holder = new Holder();
                holder.tvAttendDate = (TextView) convertView.findViewById(R.id.tvAttendDate);
                holder.tvAttendTime = (TextView) convertView.findViewById(R.id.tvAttendTime);
                holder.tvAttendType = (TextView) convertView.findViewById(R.id.tvAttendType);

                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }

            String date = getDate(position);
            holder.tvAttendDate.setText(date);

            List<String> list = (List<String>) getItem(position);
            holder.tvAttendTime.setText(list.get(1));
            holder.tvAttendType.setText(list.get(0));


            return convertView;
        }

        private class Holder {
            public TextView tvAttendDate;
            public TextView tvAttendTime;
            public TextView tvAttendType;
        }


    }

}
