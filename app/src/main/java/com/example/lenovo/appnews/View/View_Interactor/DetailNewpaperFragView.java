package com.example.lenovo.appnews.View.View_Interactor;

import android.content.Context;

import com.example.lenovo.appnews.Object.RSSItems;

/**
 * Created by MinhVuong on 11/3/2017.
 */

public interface DetailNewpaperFragView {
    Context getContext();
    void onLoadData(RSSItems rssItems);
}
