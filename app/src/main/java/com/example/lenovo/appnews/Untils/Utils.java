package com.example.lenovo.appnews.Untils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.example.lenovo.appnews.R;
import com.example.lenovo.appnews.Object.RSSItem;
import com.example.lenovo.appnews.Object.RSSItems;

/**
 * Created by MinhVuong on 10/31/2017.
 */

public class Utils {
    public static String[] libTitleRss;
    public static RSSItems libRssItem = new RSSItems();
    public static boolean isChangFavorite = false;

    public static void resetLibRss() {
        libRssItem = new RSSItems();
    }

    public static int getResourceIdByName(Context context, String nameResource) {
        int resId;
        try {
            resId = context.getResources().getIdentifier(nameResource, "drawable", context.getPackageName());
        } catch (Exception e) {
            return R.drawable.dantri;
        }
        return resId;
    }

    public static Drawable getDrawableResourceByName(Context context, String nameResource) {
        Drawable drawable;
        try {
            int resID = getResourceIdByName(context, nameResource);
            drawable = context.getResources().getDrawable(resID);
        } catch (Exception e) {
            drawable = context.getResources().getDrawable(R.drawable.dantri);
        }
        return drawable;
    }


    public static Bitmap getBitmapByPath(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }


    public static void addRssItemsToLibs(RSSItems rssItems) {

        libRssItem.addAll(rssItems);
    }

    public static String[] getLibTitleRssItem() {
        libTitleRss = new String[libRssItem.getRssItems().size()];
        int i = 0;
        for (RSSItem rssItem : libRssItem.getRssItems()) {
            libTitleRss[i] = rssItem.getTitle();
            i++;
        }
        return libTitleRss;
    }

    public static RSSItem filterRssByTitle(String title) {
        for (RSSItem rssItem : libRssItem.getRssItems()) {
            if (rssItem.getTitle().equals(title)) {
                return rssItem;
            }
        }
        return null;
    }

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
