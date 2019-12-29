package com.company_name.wasl_project4.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class viewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments =new ArrayList<>();
    ArrayList<String> tabTitles=new ArrayList<>();


    public void addfragments (Fragment fragment, String tabTitle){

        this.fragments.add(fragment);
        this.tabTitles.add(tabTitle);

    }

    public viewPagerAdapter (FragmentManager fm){

        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }


}
