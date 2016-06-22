package com.ktds.jgbaek.mydrawablelayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.ktds.jgbaek.mydrawablelayout.gallery.GalleryFragment1;
import com.ktds.jgbaek.mydrawablelayout.gallery.GalleryFragment2;
import com.ktds.jgbaek.mydrawablelayout.gallery.GalleryFragment3;


public class GalleryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

//    private Button btnFirstGallery;
//    private Button btnSecondGallery;
//    private Button btnThirdGallery;

    private ViewPager pager;

    private PagerSlidingTabStrip tabs;

    private Fragment galleryFragment1;
    private Fragment galleryFragment2;
    private Fragment galleryFragment3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        galleryFragment1 = new GalleryFragment1();
        galleryFragment2 = new GalleryFragment2();
        galleryFragment3 = new GalleryFragment3();

//        btnFirstGallery = (Button) view.findViewById(R.id.btnFirstGallery);
//        btnFirstGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pager.setCurrentItem(0);
//            }
//        });
//
//        btnSecondGallery = (Button) view.findViewById(R.id.btnSecondGallery);
//        btnSecondGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pager.setCurrentItem(1);
//            }
//        });
//
//        btnThirdGallery = (Button) view.findViewById(R.id.btnThirdGallery);
//        btnThirdGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pager.setCurrentItem(2);
//            }
//        });

        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter(getChildFragmentManager()));
        pager.setOffscreenPageLimit(3);
        pager.setCurrentItem(0);

        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        getActivity().setTitle("Gallery Fragment");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    private  String[] pageTitle = {"Page 1", "Page 2", "Page 3"};

    private class PagerAdapter extends FragmentPagerAdapter{

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitle[position];
        }

        /**
         * View Pager의 Fragment들은 가각의 Index를 가진다.
         * Android OS로 부터 요청된 Pager의 Index를 보내주면
         * 해당되는 Fragment들을 리턴시켜준다.
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {

            if(position==0){
                return galleryFragment1;
            }
            else if(position==1){
                return galleryFragment2;
            }
            else {
                return galleryFragment3;
            }
        }

        /**
         * View Pager에 몇개의 Fragment가 들어가는지 설정한다.
         * @return
         */
        @Override
        public int getCount() {
            return 3;
        }
    }

}
