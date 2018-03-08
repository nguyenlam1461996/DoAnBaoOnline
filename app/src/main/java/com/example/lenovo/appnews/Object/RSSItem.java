package com.example.lenovo.appnews.Object;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.example.lenovo.appnews.Untils.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RSSItem implements Parcelable, Comparable<RSSItem> {
    public static final Creator<RSSItem> CREATOR = new Creator<RSSItem>() {
        @Override
        public RSSItem createFromParcel(Parcel in) {
            return new RSSItem(in);
        }

        @Override
        public RSSItem[] newArray(int size) {
            return new RSSItem[size];
        }
    };
    protected String Title;
    protected String Link;
    protected String Description;
    protected String ImgUrl;
    protected String mDate;
    protected Newpaper mNewpaper;
    protected long timeStamp = 0;
    private Logger logger = new Logger(getClass().getSimpleName());

    protected RSSItem(Parcel in) {
        Title = in.readString();
        Link = in.readString();
        Description = in.readString();
        ImgUrl = in.readString();
        mDate = in.readString();
        mNewpaper = in.readParcelable(Newpaper.class.getClassLoader());
        timeStamp = in.readLong();
    }

    public RSSItem(String title, String link, String description, String imgUrl, String mDate, Newpaper mNewpaper, long timeStamp) {
        Title = title;
        Link = link;
        Description = description;
        ImgUrl = imgUrl;
        this.mDate = mDate;
        this.mNewpaper = mNewpaper;
        this.timeStamp = timeStamp;
    }

    public RSSItem(String title, String link, String description, String imgUrl) {

        Title = title;
        Link = link;
        Description = description;
        ImgUrl = imgUrl;
    }

    public RSSItem() {
        Title = "";
        Link = "";
        Description = "";
    }

    public RSSItem(String title, String link, String description) {
        Title = title;
        Link = link;
        Description = description;
    }

    public boolean isDuplicate(RSSItem rssItem) {
        if (Title.equals(rssItem.getTitle())) {
            return true;
        } else {
            return false;
        }
    }


    public Newpaper getNewpaper() {
        return mNewpaper;
    }

    public void setNewpaper(Newpaper mNewpaper) {
        this.mNewpaper = mNewpaper;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getTitle() {
        return Title.trim();
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        long timeSecondAfter = 0;
        String timeConvert = null;
        Date date = new Date(timeStamp);
        timeSecondAfter = (System.currentTimeMillis() - date.getTime()) / 1000;
        Date currentTime = Calendar.getInstance().getTime();
        if (currentTime.getDate() == date.getDate()) {
            int seconds = (int) ((timeSecondAfter) % 60);
            int minutes = (int) ((timeSecondAfter / 60) % 60);
            int hours = (int) ((timeSecondAfter / 60 / 60) % 60);
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
        logger.d("pubDate : " + getPubDate() + "   Date : " + date);
        logger.d("pubDate : " + getPubDate() + "   timeConvert : " + timeConvert);
        if (timeConvert != null) {
            return timeConvert;
        }
        return formatter.format(date);
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getPubDate() {
        return mDate;
    }

    @Override
    public int compareTo(@NonNull RSSItem o) {
        if (timeStamp > o.timeStamp) {
            return -1;
        } else if (timeStamp < o.timeStamp) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Title);
        dest.writeString(Link);
        dest.writeString(Description);
        dest.writeString(ImgUrl);
        dest.writeString(mDate);
        dest.writeParcelable(mNewpaper, flags);
        dest.writeLong(timeStamp);
    }
}
