package com.example.lenovo.appnews.Object;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.lenovo.appnews.DataBase.DatabaseConstans;

/**
 * Created by MinhVuong on 12/18/2017.
 */

public class NewfeedSetting {
    private int mId;
    private String mName;
    private int mIsUsed;
    private int mSort;
    private String mImage;

    public NewfeedSetting() {
    }

    public NewfeedSetting(int mId, String mName, int mSort, String mImage) {
        this.mId = mId;
        this.mName = mName;
        this.mSort = mSort;
        this.mImage = mImage;
    }

    public NewfeedSetting(Cursor cursor) {
        mId = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.NewFeedEntry.COLUMN_ID));
        mName = cursor.getString(cursor.getColumnIndex(DatabaseConstans.NewFeedEntry.COLUMN_NAME));
        mIsUsed = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.NewFeedEntry.COLUMN_IS_USED));
        mSort = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.NewFeedEntry.COLUMN_SORT));
        mImage = cursor.getString(cursor.getColumnIndex(DatabaseConstans.NewFeedEntry.COLUMN_IMAGE));
    }



    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getSort() {
        return mSort;
    }

    public void setSort(int mSort) {
        this.mSort = mSort;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    public int getIsUsed() {
        return mIsUsed;
    }

    public void setIsUsed(int mIsUsed) {
        this.mIsUsed = mIsUsed;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstans.NewFeedEntry.COLUMN_IS_USED, mIsUsed);
        return contentValues;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            NewfeedSetting other = (NewfeedSetting) obj;
            if (mId == other.mId) {
                return true;
            }
        } catch (Exception e) {
            return super.equals(obj);
        }
        return super.equals(obj);
    }
}
