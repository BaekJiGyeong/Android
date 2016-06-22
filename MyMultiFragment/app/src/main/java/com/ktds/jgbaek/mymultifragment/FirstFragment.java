package com.ktds.jgbaek.mymultifragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class FirstFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NUMBER_1 = "param1";
    private static final String NUMBER_2 = "param2";
    private static final String NUMBER_3 = "param3";
    private static final String NUMBER_4 = "param4";
    private static final String NUMBER_5 = "param5";
    private static final String NUMBER_6 = "param6";

    private int number1;
    private int number2;
    private int number3;
    private int number4;
    private int number5;
    private int number6;

    public FirstFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(int number1, int number2, int number3, int number4, int number5, int number6) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt(NUMBER_1, number1);
        args.putInt(NUMBER_2, number2);
        args.putInt(NUMBER_3, number3);
        args.putInt(NUMBER_4, number4);
        args.putInt(NUMBER_5, number5);
        args.putInt(NUMBER_6, number6);


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            number1 = getArguments().getInt(NUMBER_1, 0);
            number2 = getArguments().getInt(NUMBER_2, 0);
            number3 = getArguments().getInt(NUMBER_3, 0);
            number4 = getArguments().getInt(NUMBER_4, 0);
            number5 = getArguments().getInt(NUMBER_5, 0);
            number6 = getArguments().getInt(NUMBER_6, 0);


        }
    }

    private Button nextFragment;

    private TextView tvNumber1;
    private TextView tvNumber2;
    private TextView tvNumber3;
    private TextView tvNumber4;
    private TextView tvNumber5;
    private TextView tvNumber6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        tvNumber1 = (TextView) view.findViewById(R.id.tvNumber1);
        tvNumber2 = (TextView) view.findViewById(R.id.tvNumber2);
        tvNumber3 = (TextView) view.findViewById(R.id.tvNumber3);
        tvNumber4 = (TextView) view.findViewById(R.id.tvNumber4);
        tvNumber5 = (TextView) view.findViewById(R.id.tvNumber5);
        tvNumber6 = (TextView) view.findViewById(R.id.tvNumber6);

        tvNumber1.setText(number1+"");
        tvNumber2.setText(number2+"");
        tvNumber3.setText(number3+"");
        tvNumber4.setText(number4+"");
        tvNumber5.setText(number5+"");
        tvNumber6.setText(number6+"");

        nextFragment = (Button) view.findViewById(R.id.newFragment);
        nextFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 안좋은 방법 : 클래스 캐스팅을 통한 방법
                //(( MainActivity)getActivity()).replaceFragment(2);

                // 좋은 방법 : 인터페이스를 통한 방법
                (( FragmentReplacable)getActivity()).replaceFragment(SecondFragment.newInstance(new Random().nextInt(100)+1,new Random().nextInt(100)+1,new Random().nextInt(100)+1));


            }
        });
        return view;
    }
}