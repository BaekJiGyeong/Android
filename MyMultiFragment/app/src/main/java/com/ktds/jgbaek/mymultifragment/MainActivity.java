package com.ktds.jgbaek.mymultifragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements FragmentReplacable{

    private Fragment firstFragment;
    private Fragment secondFragment;
    private Fragment thirdFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        firstFragment = new FirstFragment();
//        secondFragment = new SecondFragment();
//        thirdFragment = new ThirdFragment();

        setDefaultFragment();
    }

    /**
     * MainActivity가 처음 실행될 때
     * 최초로 보여질 Fragment를 세팅한다.
     */
    public void setDefaultFragment() {

        /**
         * 화면에 보여지는 Fragment를 관리한다.
         * FragmentManager : Fragment를 바꾸거나 추가하는 객체
         */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        // 첫번째 프래그 먼트를 생성하면서 데이터를 전달한다.
        firstFragment = FirstFragment.newInstance(new Random().nextInt(45)+1
                ,new Random().nextInt(45)+1,new Random().nextInt(45)+1,new Random().nextInt(45)+1
                ,new Random().nextInt(45)+1,new Random().nextInt(45)+1);

        /**
         * R.id.container(activity_main.xml)에 띄우겠다.
         * 첫번째로 보여지는 Fragment는 firstFragment로 설정한다.
         */
        transaction.add(R.id.container, firstFragment);

        /**
         * Fragment의 변경사항을 반영시킨다.
         */
        transaction.commit();
    }


    /**
     * @see FragmentReplacable
     * @param fragmentId
     */
    @Override
    public void replaceFragment (int fragmentId){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        /**
         * 다음으로 보여지는 firstFragment로 설정한다.
         */

        if(fragmentId==1){
            transaction.replace(R.id.container, firstFragment);
        }
        else if(fragmentId==2){
            transaction.replace(R.id.container, secondFragment);
        }
        else if(fragmentId==3){
            transaction.replace(R.id.container, thirdFragment);
        }

        // Back버튼 클릭시 이전 프래그먼트 이동시키도록 한다.
        transaction.addToBackStack(null);



        /**
         * Fragment의 변경사항을 반영시킨다.
         */
        transaction.commit();
    }

    @Override
    public void replaceFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        /**
         * 다음으로 보여지는 firstFragment로 설정한다.
         */

        if(fragment instanceof FirstFragment ){
            firstFragment = fragment;
            transaction.replace(R.id.container, firstFragment);
        }
        else if(fragment instanceof SecondFragment ){
            secondFragment = fragment;
            transaction.replace(R.id.container, secondFragment);
        }
        else if(fragment instanceof ThirdFragment ){
            thirdFragment = fragment;
            transaction.replace(R.id.container, thirdFragment);
        }

        // Back버튼 클릭시 이전 프래그먼트 이동시키도록 한다.
        transaction.addToBackStack(null);



        /**
         * Fragment의 변경사항을 반영시킨다.
         */
        transaction.commit();

    }
}