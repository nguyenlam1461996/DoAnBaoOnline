package com.example.lenovo.appnews.Object;

import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinhVuong on 12/5/2017.
 */

public class NewFeedCatogery implements Comparable<NewFeedCatogery> {
    public static final int LIMIT_NEWS_FEED = 5;
    private String mNameCategory;
    private int mSort;
    private List<NewFeedItem> mNewFeedItems;
    private RSSItems mRssItems;
    private int mLimitShowNewsFeed = LIMIT_NEWS_FEED;
    private int id;

    public NewFeedCatogery() {
        mNewFeedItems = new ArrayList<>();
        mRssItems = new RSSItems();
    }

    public NewFeedCatogery(Cursor cursor) {
    }

    public RSSItems getRssItems() {
        return mRssItems;
    }

    public void setRssItems(RSSItems mRssItems) {
        this.mRssItems = mRssItems;
    }

    public List<NewFeedItem> getNewFeedItems() {
        return mNewFeedItems;
    }

    public void setNewFeedItems(List<NewFeedItem> mNewFeedItems) {
        this.mNewFeedItems = mNewFeedItems;
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

    public int getLimitShowNewsFeed() {
        return mLimitShowNewsFeed;
    }

    public boolean upToLimitNewsFeed() {
        if (mLimitShowNewsFeed < mRssItems.getSize()) {
            mLimitShowNewsFeed += LIMIT_NEWS_FEED;
            return true;
        } else {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(@NonNull NewFeedCatogery o) {
        if (id > o.id) {
            return -1;
        } else if (id < o.id) {
            return 1;
        } else {
            return 0;
        }
    }
}
