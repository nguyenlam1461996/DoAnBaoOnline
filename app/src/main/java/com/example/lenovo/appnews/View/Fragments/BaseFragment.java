package com.example.lenovo.appnews.View.Fragments;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by MinhVuong on 11/2/2017.
 */

public abstract class BaseFragment extends Fragment {
    protected View mRootView;

    protected abstract void init();
    protected abstract void initView();
    protected abstract void initEvent();
    protected abstract void initData();


}
