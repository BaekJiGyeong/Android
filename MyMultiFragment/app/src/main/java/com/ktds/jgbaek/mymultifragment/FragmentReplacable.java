package com.ktds.jgbaek.mymultifragment;


import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by 206-008 on 2016-06-16.
 */
public interface FragmentReplacable extends Serializable {

    public void replaceFragment(int fragmentId);

    public void replaceFragment(Fragment fragment);
}
