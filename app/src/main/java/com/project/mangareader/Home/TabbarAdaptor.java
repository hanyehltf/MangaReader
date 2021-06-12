package com.project.mangareader.Home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class TabbarAdaptor extends FragmentStatePagerAdapter {


    private int numOfTab;

    public TabbarAdaptor(FragmentManager fm, int NumOfTab) {
        super(fm);
        numOfTab = NumOfTab;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=new Fragment();

        switch (position) {
            case 0:

                fragment = new CategoryFragment();
                break;
            case 1:

                fragment = new CategoryFragment();
                break;

            default:

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return numOfTab;
    }
}