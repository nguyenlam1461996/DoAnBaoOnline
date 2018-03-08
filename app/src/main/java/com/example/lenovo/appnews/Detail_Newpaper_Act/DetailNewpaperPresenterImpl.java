package com.example.lenovo.appnews.Detail_Newpaper_Act;

import com.example.lenovo.appnews.DataBase.ProviderData;
import com.example.lenovo.appnews.View.View_Interactor.IDetailNewpaperActView;


public class DetailNewpaperPresenterImpl implements IDetailNewpaperPresenter {
    private IDetailNewpaperActView mDetailNewpaperActView;

    public DetailNewpaperPresenterImpl(IDetailNewpaperActView mDetailNewpaperActView) {
        this.mDetailNewpaperActView = mDetailNewpaperActView;
    }

    @Override
    //Load Date theo ID cua trang bao
    public void loadData(int idNewpaper) {
        mDetailNewpaperActView.onLoadData(ProviderData.getInstance(mDetailNewpaperActView.getContext()).getListDetailNewpaperByIDNewpaper(idNewpaper));
    }
}
