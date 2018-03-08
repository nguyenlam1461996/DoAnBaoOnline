package com.example.lenovo.appnews.Object;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.lenovo.appnews.DataBase.DatabaseConstans;
import com.example.lenovo.appnews.Untils.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by MinhVuong on 11/14/2017.
 */

public class Bookmark {
    private Logger mLogger = new Logger(Bookmark.class.getSimpleName());
    private int mId;
    private int mSort;
    private String mLink;
    private String mTitle;
    private String mPath;
    private String mDate;
    private String mPubDate;
    private String mUrlImage;
    private Newpaper mNewpaper;
    private RSSItem mRssItem;

    public Bookmark() {
    }


    public Bookmark(Cursor cursor) {
        mNewpaper = new Newpaper();
        mRssItem = new RSSItem();
        mId = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.BookMarkEntry.COLUMN_ID));
        mSort = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.BookMarkEntry.COLUMN_SORT));
        mLink = cursor.getString(cursor.getColumnIndex(DatabaseConstans.BookMarkEntry.COLUMN_LINK));
        mTitle = cursor.getString(cursor.getColumnIndex(DatabaseConstans.BookMarkEntry.COLUMN_TITLE));
        mPath = cursor.getString(cursor.getColumnIndex(DatabaseConstans.BookMarkEntry.COLUMN_PATH_IMAGE));
        mUrlImage = cursor.getString(cursor.getColumnIndex(DatabaseConstans.BookMarkEntry.COLUMN_URL_IMAGE));
        mDate = cursor.getString(cursor.getColumnIndex(DatabaseConstans.BookMarkEntry.COLUMN_DATE));
        mPubDate = cursor.getString(cursor.getColumnIndex(DatabaseConstans.BookMarkEntry.COLUMN_PUB_DATE));
 /*set new Newpaper*/
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


        mLogger.d("ID: " + mId + " | mSort: " + mSort + " | mLink :" + mLink + " | mTitle: " + mTitle + " | mPath: " + mPath + " | mDate: " + mDate + " | newpaper: " + mNewpaper.toString());
    }

    public Logger getLogger() {
        return mLogger;
    }

    public void setLogger(Logger mLogger) {
        this.mLogger = mLogger;
    }

    public RSSItem getRssItem() {
        return mRssItem;
    }

    public void setRssItem(RSSItem mRssItem) {
        this.mRssItem = mRssItem;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public int getSort() {
        return mSort;
    }

    public void setSort(int mSort) {
        this.mSort = mSort;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String mPath) {
        this.mPath = mPath;
    }

    public String getUrlImage() {
        return mUrlImage;
    }

    public void setUrlImage(String mUrlImage) {
        this.mUrlImage = mUrlImage;
    }

    public String getDate() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        long timeSecondAfter = 0;
        String timeConvert = null;
        Date date = new Date(Long.parseLong(mDate));
        timeConvert = formatter.format(date);
        timeSecondAfter = (System.currentTimeMillis() - date.getTime()) / 1000;
        Date currentTime = Calendar.getInstance().getTime();
        if (currentTime.getDay() == date.getDay()) {
            int seconds = (int) ((timeSecondAfter) % 60);
            int minutes = (int) ((timeSecondAfter / 60 / 60) % 60);
            int hours = (int) ((timeSecondAfter / 60 / 60 / 24) % 60);
            if (hours == 0) {
                if (hours > 0) {
                    timeConvert = hours + " giờ trước";
                }
                if (minutes > 0 && hours <= 0) {
                    timeConvert = minutes + " phút trước";
                }
                if (minutes <= 0 && hours <= 0) {
                    timeConvert = seconds + " giây trước";
                }
            }
        }
        if (timeConvert != null) {
            return timeConvert;
        }
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

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstans.BookMarkEntry.COLUMN_LINK, mRssItem.getLink());
        contentValues.put(DatabaseConstans.BookMarkEntry.COLUMN_TITLE, mRssItem.getTitle());
        contentValues.put(DatabaseConstans.BookMarkEntry.COLUMN_PATH_IMAGE, mPath);
        contentValues.put(DatabaseConstans.BookMarkEntry.COLUMN_URL_IMAGE, mUrlImage);
        contentValues.put(DatabaseConstans.BookMarkEntry.COLUMN_DATE, System.currentTimeMillis());
        contentValues.put(DatabaseConstans.BookMarkEntry.COLUMN_PUB_DATE, mRssItem.getPubDate());
        contentValues.put(DatabaseConstans.BookMarkEntry.COLUMN_SORT, 0);
        contentValues.put(DatabaseConstans.BookMarkEntry.COLUMN_ID_NEWPAPER, mNewpaper.getIdNewpaper());
        return contentValues;
    }
}
