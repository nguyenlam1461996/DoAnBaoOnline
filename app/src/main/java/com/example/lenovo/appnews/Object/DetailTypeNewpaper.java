package com.example.lenovo.appnews.Object;

import java.util.List;

/**
 * Created by MinhVuong on 10/28/2017.
 */

public class DetailTypeNewpaper {
    private int mSort;
    private TypeNewpaper mTypeNewpaper;
    private List<Newpaper> mListNewPaper;

    public DetailTypeNewpaper() {
    }

    public DetailTypeNewpaper(int mSort, TypeNewpaper mTypeNewpaper, List<Newpaper> mListNewPaper) {
        this.mSort = mSort;
        this.mTypeNewpaper = mTypeNewpaper;
        this.mListNewPaper = mListNewPaper;
    }

    public int getSort() {
        return mSort;
    }

    public void setSort(int mSort) {
        this.mSort = mSort;
    }

    public List<Newpaper> getListNewPaper() {
        return mListNewPaper;
    }

    public void setListNewPaper(List<Newpaper> mListNewPaper) {
        this.mListNewPaper = mListNewPaper;
    }

    public TypeNewpaper getTypeNewpaper() {
        return mTypeNewpaper;
    }

    public void setTypeNewpaper(TypeNewpaper mTypeNewpaper) {
        this.mTypeNewpaper = mTypeNewpaper;
    }
}
