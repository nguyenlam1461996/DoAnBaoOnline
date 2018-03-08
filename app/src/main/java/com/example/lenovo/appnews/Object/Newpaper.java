package com.example.lenovo.appnews.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MinhVuong on 10/28/2017.
 */

public class Newpaper implements Parcelable {
    public static final Creator<Newpaper> CREATOR = new Creator<Newpaper>() {
        @Override
        public Newpaper createFromParcel(Parcel in) {
            return new Newpaper(in);
        }

        @Override
        public Newpaper[] newArray(int size) {
            return new Newpaper[size];
        }
    };
    private int mIdNewpaper;
    private String mNamePaper;
    private String mNameImage;
    private int mSort;
    private int mIdType;


    public Newpaper() {
    }

    protected Newpaper(Parcel in) {
        mIdNewpaper = in.readInt();
        mNamePaper = in.readString();
        mNameImage = in.readString();
        mSort = in.readInt();
        mIdType = in.readInt();
    }

    public int getIdNewpaper() {
        return mIdNewpaper;
    }

    public void setIdNewpaper(int mId) {
        this.mIdNewpaper = mId;
    }

    public String getNamePaper() {
        return mNamePaper;
    }

    public void setNamePaper(String mNamePaper) {
        this.mNamePaper = mNamePaper;
    }

    public String getNameImage() {
        return mNameImage;
    }

    public void setNameImage(String mNameImage) {
        this.mNameImage = mNameImage;
    }

    public int getmSort() {
        return mSort;
    }

    public void setmSort(int mSort) {
        this.mSort = mSort;
    }

    public int getIdType() {
        return mIdType;
    }

    public void setIdType(int mIdType) {
        this.mIdType = mIdType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIdNewpaper);
        dest.writeString(mNamePaper);
        dest.writeString(mNameImage);
        dest.writeInt(mSort);
        dest.writeInt(mIdType);
    }

    @Override
    public String toString() {
        return "mIdNewpaper: " + mIdNewpaper
                + " | mNamePaper: " + mIdNewpaper == null ? "" : mIdNewpaper
                + " | mNameImage: " + mNameImage == null ? "" : mNameImage
                + " | mSort: " + mSort
                + " | mIdType: " + mIdType;
    }
}
