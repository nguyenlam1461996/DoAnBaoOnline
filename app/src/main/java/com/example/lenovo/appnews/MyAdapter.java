package com.example.lenovo.appnews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by LENOVO on 1/26/2018.
 */

public class MyAdapter extends FragmentStatePagerAdapter {
    private  String listTab[] = {"Thoi Su", "Giai Tri", "Du Lich"};
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;

    public MyAdapter(FragmentManager fm) {
        super(fm);
        firstFragment =new FirstFragment();
        secondFragment=new SecondFragment();
        thirdFragment=new ThirdFragment();

    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            return firstFragment;
        }else if (position==1)
        {
            return secondFragment;
        }
        else if (position==2)
        {
            return thirdFragment;
        }/*
        else if (position==3)
        {
            return secondFragment;
        }
        else if (position==4)
        {
            return firstFragment;
        }
        else if (position==5)
        {
            return secondFragment;
        }*/else {

        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}
