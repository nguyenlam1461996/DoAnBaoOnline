package com.example.lenovo.appnews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LENOVO on 1/26/2018.
 */

public class FirstFragment extends Fragment {
    private View mRootView; //De anh xa cac view con nam trong giao dien ve sau
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView =inflater.inflate(R.layout.fragment_first,container,false);
        return mRootView;
    }
}
