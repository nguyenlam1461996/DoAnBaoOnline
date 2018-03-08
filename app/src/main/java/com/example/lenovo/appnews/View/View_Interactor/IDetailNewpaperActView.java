package com.example.lenovo.appnews.View.View_Interactor;

import android.content.Context;

import com.example.lenovo.appnews.Object.DetailNewpaper;

import java.util.List;

/**
 * Created by MinhVuong on 11/2/2017.
 */

public interface IDetailNewpaperActView {
    Context getContext();
    void onLoadData(List<DetailNewpaper> detailNewpapers);
}
