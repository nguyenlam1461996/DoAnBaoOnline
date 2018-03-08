package com.example.lenovo.appnews.Object;

/**
 * Created by MinhVuong on 10/28/2017.
 */

public class TypeNewpaper { //Kieu trang bao (Chinh thong, La cai,...) // Cai nay khong dung
    private int mIdType;
    private String mNameType;
    private int mSort;

    public TypeNewpaper() {
    }

    public TypeNewpaper(int mIdType, String mNameType, int mSort) {
        this.mIdType = mIdType;
        this.mNameType = mNameType;
        this.mSort = mSort;
    }

    public int getIdType() {
        return mIdType;
    }

    public void setIdType(int mIdType) {
        this.mIdType = mIdType;
    }

    public String getNameType() {
        return mNameType;
    }

    public void setNameType(String mNameType) {
        this.mNameType = mNameType;
    }

    public int getSort() {
        return mSort;
    }

    public void setSort(int sort) {
        this.mSort = sort;
    }
}
