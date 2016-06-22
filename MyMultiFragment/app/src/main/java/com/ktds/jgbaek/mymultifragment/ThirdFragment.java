package com.ktds.jgbaek.mymultifragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ThirdFragment extends Fragment {
    private static final String NUMBER_1 = "param1";
    private static final String NUMBER_2 = "param2";

    private int number1;
    private int number2;

    public ThirdFragment() {
        // Required empty public constructor
    }

    public static ThirdFragment newInstance(int number1, int number2) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putInt(NUMBER_1, number1);
        args.putInt(NUMBER_2, number2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            number1 = getArguments().getInt(NUMBER_1, 0);
            number2 = getArguments().getInt(NUMBER_2, 0);
        }
    }
    private TextView tvNumber1;
    private TextView tvNumber2;
    private Button nextFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        tvNumber1 = (TextView) view.findViewById(R.id.tvNumber1);
        tvNumber2 = (TextView) view.findViewById(R.id.tvNumber2);

        tvNumber1.setText(number1+"");
        tvNumber2.setText(number2+"");

        nextFragment = (Button) view.findViewById(R.id.newFragment2);
        nextFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 안좋은 방법 : 클래스 캐스팅을 통한 방법
                //(( MainActivity)getActivity()).replaceFragment(2);

                // 좋은 방법 : 인터페이스를 통한 방법
                (( FragmentReplacable)getActivity()).replaceFragment(1);

            }
        });
        return view;
    }



}
