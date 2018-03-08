package com.example.lenovo.appnews.DataBase;

/**
 * Created by MinhVuong on 11/14/2017.
 */

public class DatabaseConstans {
    public static final String KEY_RSS = "KEY_RSS";

    public interface BookMarkEntry { //Yeu thich
        String TABLE_NAME = "Bookmarks";
        String COLUMN_ID = "id_book_mark";
        String COLUMN_SORT = "sort"; //Chua dung
        String COLUMN_LINK = "link";
        String COLUMN_TITLE = "title";
        String COLUMN_PATH_IMAGE = "path_image";
        String COLUMN_URL_IMAGE = "url_image";
        String COLUMN_DATE = "date";
        String COLUMN_PUB_DATE = "pub_date";
        String COLUMN_ID_NEWPAPER = "id_new_paper";
    }

    public interface NewPaperEntry { //Ten tung trang bao (DanTri, VnEx)
        String COLUMN_ID = "id_new_paper";
        String COLUMN_ID_TYPE = "id_type";
        String TABLE_NAME = "New_Paper";
        String COLUMN_NAME_NEWPAPER = "name_paper";
        String COLUMN_IMAGE = "image";
        String COLUMN_SORT = "sort";
    }


    public interface TypeNewpaperEntry { //Kieu trang bao (Chinh thong, La cai,...) // Cai nay khong dung
        String TABLE_NAME = "Type_New_Paper";
        String COLUMN_ID = "id_type";
        String COLUMN_NAME = "name_type";
        String COLUMN_SORT = "sort";
    }


    public interface DetailNewpaperEntry { //Chi tiet trang bao //VD:DanTri-TheThao;DanTri-SucKhoe
        String COLUMN_ID = "id_detail";
        String COLUMN_ID_NEWPAPER = "id_new_paper";
        String COLUMN_LINK = "link";
        String COLUMN_NAME_CATEGORY = "name_category"; //Khong lam 1 trang category rieng vi k lap nhieu. Moi lan lai phai tra IDCategory
        String COLUMN_SORT = "sort";
        String COLUMN_COUNT_SELECT = "count_select";
        String COLUMN_ID_NEWS_FEED = "id_newfeed";
        String TABLE_NAME = "Detail_New_Paper";
    }

    public interface HistoryEntry { //Lich su...OK
        String TABLE_NAME = "History";
        String COLUMN_ID = "id_history";
        String COLUMN_ID_NEWPAPER = "id_new_paper";
        String COLUMN_LINK = "link";
        String COLUMN_DATE = "date_save";
        String COLUMN_TITLE = "title";
        String COLUMN_URL_IMAGE = "url_image";
        String COLUMN_PUB_DATE = "pub_date";
    }

    public interface NewFeedEntry { //Tin moi
        String TABLE_NAME = "News_feed";
        String COLUMN_ID = "id";
        String COLUMN_NAME = "name";
        String COLUMN_IS_USED = "is_used"; //Co hien thi hay khong?
        String COLUMN_IMAGE = "image";
        String COLUMN_SORT = "sort";
    }


}
