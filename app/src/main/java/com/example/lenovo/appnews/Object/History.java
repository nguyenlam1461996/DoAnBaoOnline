package com.example.lenovo.appnews.Object;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.lenovo.appnews.DataBase.DatabaseConstans;
import com.example.lenovo.appnews.Untils.Logger;

/**
 * Created by MinhVuong on 11/27/2017.
 */

public class History {
    private Logger mLogger = new Logger(getClass().getSimpleName());
    private int mId;
    private String mLink;
    private String mDate;
    private String mTitle;
    private String mPubDate;
    private String mUrlImage;
    private Newpaper mNewpaper;
    private RSSItem mRssItem;

    public History() {
    }

    public History(Cursor cursor) {
        mNewpaper = new Newpaper();
        mRssItem = new RSSItem();
        mId = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.HistoryEntry.COLUMN_ID));
        mLink = cursor.getString(cursor.getColumnIndex(DatabaseConstans.HistoryEntry.COLUMN_LINK));
        mDate = cursor.getString(cursor.getColumnIndex(DatabaseConstans.HistoryEntry.COLUMN_DATE));
        mTitle = cursor.getString(cursor.getColumnIndex(DatabaseConstans.HistoryEntry.COLUMN_TITLE));
        mUrlImage = cursor.getString(cursor.getColumnIndex(DatabaseConstans.HistoryEntry.COLUMN_URL_IMAGE));
        mPubDate = cursor.getString(cursor.getColumnIndex(DatabaseConstans.HistoryEntry.COLUMN_PUB_DATE));

        /*set newpaper*/
        mNewpaper.setIdNewpaper(cursor.getInt(cursor.getColumnIndex(DatabaseConstans.NewPaperEntry.COLUMN_ID)));
        int idNewpaper = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.NewPaperEntry.COLUMN_ID));
        String image = cursor.getString(cursor.getColumnIndex(DatabaseConstans.NewPaperEntry.COLUMN_IMAGE));
        String nameNewpaper = cursor.getString(cursor.getColumnIndex(DatabaseConstans.NewPaperEntry.COLUMN_NAME_NEWPAPER));

        mNewpaper.setNamePaper(nameNewpaper);
        mNewpaper.setIdNewpaper(idNewpaper);
        mNewpaper.setNameImage(image);
          /*set new RSS*/
        mRssItem.setDate(mPubDate);
        mRssItem.setLink(mLink);
        mRssItem.setTitle(mTitle);
        mRssItem.setImgUrl(mUrlImage);
        mRssItem.setNewpaper(mNewpaper);



        mLogger.d("ID: " + mId + " | mLink :" + mLink + " | mDate: " + mDate + mNewpaper.toString());
    }


    public History(Logger mLogger, String mLink, int mId, String mDate, Newpaper mNewpaper) {
        this.mLogger = mLogger;
        this.mLink = mLink;
        this.mId = mId;
        this.mDate = mDate;
        this.mNewpaper = mNewpaper;
    }

    public Logger getLogger() {
        return mLogger;
    }

    public void setLogger(Logger mLogger) {
        this.mLogger = mLogger;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public Newpaper getNewpaper() {
        return mNewpaper;
    }

    public void setNewpaper(Newpaper mNewpaper) {
        this.mNewpaper = mNewpaper;
    }

    public RSSItem getRssItem() {
        return mRssItem;
    }

    public void setRssItem(RSSItem mRssItem) {
        this.mRssItem = mRssItem;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstans.HistoryEntry.COLUMN_LINK, mRssItem.getLink());
        contentValues.put(DatabaseConstans.HistoryEntry.COLUMN_URL_IMAGE, mRssItem.getImgUrl());
        contentValues.put(DatabaseConstans.HistoryEntry.COLUMN_PUB_DATE, mRssItem.getPubDate());
        contentValues.put(DatabaseConstans.HistoryEntry.COLUMN_TITLE, mRssItem.getTitle());
        contentValues.put(DatabaseConstans.HistoryEntry.COLUMN_DATE, System.currentTimeMillis());
        contentValues.put(DatabaseConstans.HistoryEntry.COLUMN_ID_NEWPAPER, mNewpaper.getIdNewpaper());
        return contentValues;
    }
}
