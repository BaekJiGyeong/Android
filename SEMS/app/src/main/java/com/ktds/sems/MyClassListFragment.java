package com.ktds.sems;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MyClassListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyClassListFragment() {
        // Required empty public constructor
    }

    public static MyClassListFragment newInstance(String param1, String param2) {
        MyClassListFragment fragment = new MyClassListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private ListView tvClassList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_class_list, container, false);
        tvClassList = (ListView) view.findViewById(R.id.myClassList);

        // 1. 서버에서 강의 목록을 가져온다.
        GetClassListTask getClassListTask = new GetClassListTask();
        getClassListTask.execute("");

        return view;
    }

    private class GetClassListTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient.Builder client = new HttpClient.Builder("POST", "http://10.225.152.167:8080/m/myCourse");
            HttpClient post = client.create();

            post.request();

            return post.getBody();
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("REUSLT", s);

            Gson gson = new Gson();

            Type educationList = new TypeToken<EducationList>() {}.getType();

            EducationList eduList = gson.fromJson(s, educationList);
            Log.d("RESULT", eduList.getNowEduList().size() + "");

            // 2. 가져온 강의 목록을 ListView에 보여준다.
            tvClassList.setAdapter(new ListAdapter(eduList));
            // 3. 아이템을 클릭하면 출결이력을 보여준다.

            tvClassList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                  Toast.makeText(getActivity(), ((Holder) view.getTag()).educationId, Toast.LENGTH_SHORT).show();
                    FragmentTransaction transcation = getActivity().getSupportFragmentManager().beginTransaction();
                    transcation.replace(R.id.myContainer, AttendListFragment.newInstance( ( (Holder)view.getTag()).educationId ) );
                    transcation.addToBackStack(null);
                    transcation.commit();
                }
            });
        }
    }

    private class ListAdapter extends BaseAdapter {

        private List<Education> eduList;

        public ListAdapter(EducationList eduList) {
            this.eduList = new ArrayList<Education>();

            this.eduList.addAll( eduList.getNowEduList() );
            this.eduList.addAll( eduList.getPreEduList() );
        }

        @Override
        public int getCount() {
            return eduList.size();
        }

        @Override
        public Object getItem(int position) {
            return eduList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder = null;
            if( convertView == null ){
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.class_item, parent, false);

                holder = new Holder();
                holder.tvClassName = (TextView) convertView.findViewById(R.id.tvClassName);
                holder.tvClassOrg = (TextView) convertView.findViewById(R.id.tvClassOrg);
                holder.tvDuraTime = (TextView) convertView.findViewById(R.id.tvDuraTime);
                holder.tvEnterTime = (TextView) convertView.findViewById(R.id.tvEnterTime);
                holder.tvExitTime = (TextView) convertView.findViewById(R.id.tvExitTime);
                holder.tvClassName = (TextView) convertView.findViewById(R.id.tvClassName);

                convertView.setTag(holder);
            }  else {
                holder = (Holder) convertView.getTag();
            }

            Education education = (Education) getItem(position);
            holder.tvClassName.setText(education.getEducationTitle());
            holder.tvClassOrg.setText("ktds university");

            String educationId = education.getEducationId();
            String duration = education.getStartDate() + "~" + "~" + education.getEndDate();
            holder.educationId = educationId;

            holder.tvDuraTime.setText(duration);
            holder.tvEnterTime.setText("작업중..");
            holder.tvExitTime.setText("작업중..");

            return  convertView;
        }
    }

    private class Holder {

        public TextView tvClassName;
        public TextView tvClassOrg;
        public TextView tvDuraTime;
        public TextView tvEnterTime;
        public TextView tvExitTime;
        public String educationId;


    }


}
