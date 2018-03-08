package com.example.lenovo.appnews.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MinhVuong on 11/2/2017.
 */

public class DetailNewpaper implements Parcelable {
    private int mIdNewpaper;
    private String mLink;
    private int mSort;
    private String mNameCategory;
    private int mCountSelect;

    public DetailNewpaper() {
    }

    public DetailNewpaper(int mIdNewpaper, String mLink, int mSort, String mNameCategory, int mCountSelect) {
        this.mIdNewpaper = mIdNewpaper;
        this.mLink = mLink;
        this.mSort = mSort;
        this.mNameCategory = mNameCategory;
        this.mCountSelect = mCountSelect;
    }

    protected DetailNewpaper(Parcel in) {
        mIdNewpaper = in.readInt();
        mLink = in.readString();
        mSort = in.readInt();
        mNameCategory = in.readString();
        mCountSelect = in.readInt();
    }

    public static final Creator<DetailNewpaper> CREATOR = new Creator<DetailNewpaper>() {
        @Override
        public DetailNewpaper createFromParcel(Parcel in) {
            return new DetailNewpaper(in);
        }

        @Override
        public DetailNewpaper[] newArray(int size) {
            return new DetailNewpaper[size];
        }
    };

    public int getIdNewpaper() {
        return mIdNewpaper;
    }

    public void setIdNewpaper(int mIdNewpaper) {
        this.mIdNewpaper = mIdNewpaper;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }

    public int getSort() {
        return mSort;
    }

    public void setSort(int mSort) {
        this.mSort = mSort;
    }

    public String getNameCategory() {
        return mNameCategory;
    }

    public void setNameCategory(String mNameCategory) {
        this.mNameCategory = mNameCategory;
    }

    public int getCountSelect() {
        return mCountSelect;
    }

    public void setCountSelect(int mCountSelect) {
        this.mCountSelect = mCountSelect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIdNewpaper);
        dest.writeString(mLink);
        dest.writeInt(mSort);
        dest.writeString(mNameCategory);
        dest.writeInt(mCountSelect);
    }
}
