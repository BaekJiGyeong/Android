package com.ktds.junho.mydrawablelayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.ktds.junho.mydrawablelayout.gallery.GalleryFragment1;
import com.ktds.junho.mydrawablelayout.gallery.GalleryFragment2;
import com.ktds.junho.mydrawablelayout.gallery.GalleryFragment3;
import com.ktds.junho.mydrawablelayout.gallery.GalleryFragment4;
import com.ktds.junho.mydrawablelayout.gallery.GalleryFragment5;
import com.ktds.junho.mydrawablelayout.gallery.GalleryFragment6;

public class GalleryFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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

/*    private Button btnFirstGallery;
    private Button btnSecondGallery;
    private Button btnThirdGallery;*/

    private ViewPager pager;

    private PagerSlidingTabStrip tabs;

    private Fragment galleryFragment1;
    private Fragment galleryFragment2;
    private Fragment galleryFragment3;
    private Fragment galleryFragment4;
    private Fragment galleryFragment5;
    private Fragment galleryFragment6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        galleryFragment1 = new GalleryFragment1();
        galleryFragment2 = new GalleryFragment2();
        galleryFragment3 = new GalleryFragment3();
        galleryFragment4 = new GalleryFragment4();
        galleryFragment5 = new GalleryFragment5();
        galleryFragment6 = new GalleryFragment6();

/*        btnFirstGallery = (Button) view.findViewById(R.id.btnFirstGallery);
        btnFirstGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
            }
        });
        btnSecondGallery = (Button) view.findViewById(R.id.btnSecondGallery);
        btnSecondGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
            }
        });
        btnThirdGallery = (Button) view.findViewById(R.id.btnThirdGallery);
        btnThirdGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
            }
        });*/

        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter( getChildFragmentManager() ) );
        pager.setOffscreenPageLimit(6);
        pager.setCurrentItem(0);

        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        getActivity().setTitle("Gallery Fragment");

        // Inflate the layout for this fragment
        return view;
    }

    private String[] pageTitle = {"Page 1", "Page 2", "Page 3", "Page 4", "Page 5", "Page 6"};

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitle[position];
        }

        /**
         * View Pager의 Fragment들은 각각 Index를 가진다
         * Android OS로 부터 요청된 Pager의 Index를 보내주면
         * 해당되는 Fragment를 리턴
         *
         * * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {

            if ( position == 0 ) {
                return galleryFragment1;
            }
            else if ( position == 1 ) {
                return galleryFragment2;
            }
            else if ( position == 2 ) {
                return galleryFragment3;
            }
            else if ( position == 3 ) {
                return galleryFragment4;
            }
            else if ( position == 4 ) {
                return galleryFragment5;
            }
            else {
                return galleryFragment6;
            }
        }

        /**
         * View Pager의 몇 개의 Fragment가 있는지 설정
         *
         * @return
         */
        @Override
        public int getCount() {
            return 6;
        }
    }
}
