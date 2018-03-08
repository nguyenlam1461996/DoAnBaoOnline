package com.example.lenovo.appnews.Object;

/**
 * Created by MinhVuong on 12/5/2017.
 */

public class NewFeedItem {
    private Newpaper mNewpaper;
    private String mLink;
    private String mRssItems;
    public Newpaper getNewpaper() {
        return mNewpaper;
    }

    public void setNewpaper(Newpaper mNewpaper) {
        this.mNewpaper = mNewpaper;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }

    public NewFeedItem(Newpaper mNewpaper, String mLink, String mRssItems) {
        this.mNewpaper = mNewpaper;
        this.mLink = mLink;
        this.mRssItems = mRssItems;
    }

    public NewFeedItem() {
    }

    public String getRssItems() {
        return mRssItems;
    }

    public void setRssItems(String mRssItems) {
        this.mRssItems = mRssItems;
    }
}
