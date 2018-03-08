package com.example.lenovo.appnews.New_Thread_Frag;

import android.os.Handler;
import android.util.Log;

import com.example.lenovo.appnews.API.IRSSCallBack;
import com.example.lenovo.appnews.API.RSSHelper;
import com.example.lenovo.appnews.Object.Newpaper;
import com.example.lenovo.appnews.Object.RSSItems;
import com.example.lenovo.appnews.Object.XMLDOMParser;
import com.example.lenovo.appnews.Untils.Logger;

import com.example.lenovo.appnews.View.View_Interactor.DetailNewpaperFragView;

/**
 * Created by MinhVuong on 11/3/2017.
 */

public class NewThreadPresenterImpl implements INewThreadPresenter {
    private DetailNewpaperFragView mDetailNewpaperFragView;
    private Logger mLogger = new Logger(NewThreadPresenterImpl.class.getSimpleName());

    public NewThreadPresenterImpl(DetailNewpaperFragView mDetailNewpaperFragView) {
        this.mDetailNewpaperFragView = mDetailNewpaperFragView;
    }

    @Override
    public void loadData(String urlRSS, final Newpaper newpaper) {
        RSSHelper.getInstance().executePoccessUrl(urlRSS, new IRSSCallBack() {
            @Override
            public void onSuccess(String response) {
                final RSSItems rssItems = XMLDOMParser.getInstance().parseXMLToRSSItems(response, newpaper);
                Handler handler = new Handler(mDetailNewpaperFragView.getContext().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mDetailNewpaperFragView.onLoadData(rssItems);
                    }
                });
                Log.d("loadData", response);
            }

            @Override
            public void onFail(String error) {
                mLogger.e(error);
            }
        });
    }
}
