package com.example.lenovo.appnews.Object;

import java.util.ArrayList;

/**
 * Created by nguye on 12/30/2016.
 */

public class RSSItems {



    private ArrayList<RSSItem> rssItems;

    public RSSItems() {
        this.rssItems = new ArrayList<>();
    }

    public ArrayList<RSSItem> getRssItems() {
        return rssItems;
    }

    public void setRssItems(ArrayList<RSSItem> rssItems) {
        this.rssItems = rssItems;
    }

    public void addRSSItem(RSSItem rssItem){
        rssItems.add(rssItem);
    }

    public int getSize() {
        return rssItems.size();
    }

    public RSSItem getItem(int position) {
        return rssItems.get(position);
    }

    public void clearAllItem() {
        rssItems.clear();
    }

    public void addAll(RSSItems rssItems){
        for (RSSItem rssItem : rssItems.getRssItems()) {
            if (!checkIsEsxit(rssItem)) {
                this.rssItems.add(rssItem);
            }
        }
    }

    private boolean checkIsEsxit(RSSItem rssItem) {
        return this.rssItems.contains(rssItem);
    }

}
