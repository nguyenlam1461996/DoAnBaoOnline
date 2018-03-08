package com.example.lenovo.appnews.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lenovo.appnews.Object.DetailNewpaper;
import com.example.lenovo.appnews.Object.Newpaper;
import com.example.lenovo.appnews.Untils.Constans;
import com.example.lenovo.appnews.View.Fragments.NewThreadFragment;

import java.util.List;

/**
 * Created by MinhVuong on 11/2/2017.
 */

public class DetailNewpaperAdapter extends FragmentStatePagerAdapter {
    public static final String KEY_DETAIL_NEWPAPER = "KEY_DETAIL_NEWPAPER";
    private List<DetailNewpaper> mListDetailNewpapers;
    private Newpaper mNewpaper;

    public DetailNewpaperAdapter(FragmentManager fm, List<DetailNewpaper> mListDetailNewpapers, Newpaper mNewpaper) {
        super(fm);
        this.mListDetailNewpapers = mListDetailNewpapers;
        this.mNewpaper = mNewpaper;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DETAIL_NEWPAPER, mListDetailNewpapers.get(position));
        bundle.putParcelable(Constans.KEY_NEWPAPER, mNewpaper);
        Fragment currentFragment = NewThreadFragment.newpaperFragment(bundle);
        return currentFragment;
    }

    @Override
    public int getCount() {
        return mListDetailNewpapers.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mListDetailNewpapers.get(position).getNameCategory();
    }
}
