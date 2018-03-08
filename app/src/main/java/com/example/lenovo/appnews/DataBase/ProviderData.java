package com.example.lenovo.appnews.DataBase;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.lenovo.appnews.Object.DetailTypeNewpaper;
import com.example.lenovo.appnews.Object.TypeNewpaper;
import com.example.lenovo.appnews.Object.Newpaper;
import com.example.lenovo.appnews.Object.DetailNewpaper;
import com.example.lenovo.appnews.Object.Bookmark;
import com.example.lenovo.appnews.Object.History;
import com.example.lenovo.appnews.Object.NewFeedItem;
import com.example.lenovo.appnews.Object.NewFeedCatogery;
import com.example.lenovo.appnews.Object.NewfeedSetting;

import com.example.lenovo.appnews.Untils.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 3/2/2018.
 */
//Cung cap du lieu
public class ProviderData {
    private static final String PATH = Environment.getDataDirectory().getPath() + "/data/" +
            "com.example.lenovo.appnews/DataBase";
    private static final String DATABASE_NAME = "appnewsdb.sqlite";
    private static final int VERSION_DATABSE = 17;
    private static final String KEY_VERSION_DATABSE = "KEY_VERSION_DATABSE";
    private static ProviderData mInstance;
    private SQLiteDatabase mReaderAndWriter;
    private Logger mLogger = new Logger(ProviderData.class.getSimpleName());

    public ProviderData(Context context) {
        copyDatabaseToInternal(context, PATH, DATABASE_NAME);
        openDB(PATH, DATABASE_NAME);

    }

    public static ProviderData getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ProviderData(context);
            mInstance.
                    openDB(PATH, DATABASE_NAME);

        }
        return mInstance;
    }

    public void copyDatabaseToInternal(Context context, String path, String databaseName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_VERSION_DATABSE, Context.MODE_PRIVATE);
        int versionDB = sharedPreferences.getInt(KEY_VERSION_DATABSE, -1);
        new File(path + databaseName);
        File file = new File(path + databaseName);
        if (file.exists()) {
            if (versionDB != VERSION_DATABSE) {
                file.delete();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_VERSION_DATABSE, VERSION_DATABSE);
                editor.apply();
            } else {
                return;
            }
        }
        try {
            DataInputStream dtInput = new DataInputStream(context.getAssets().open(databaseName));
            DataOutputStream dtOutput = new DataOutputStream(new FileOutputStream(path +
                    databaseName));
            byte[] buff = new byte[1024];
            int len = dtInput.read(buff);
            while (len != -1) {
                dtOutput.write(buff);
                len = dtInput.read(buff);
            }
            dtInput.close();
            dtOutput.close();
            sharedPreferences.edit().putInt(KEY_VERSION_DATABSE, VERSION_DATABSE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openDB(String path, String databaseName) {
        if (mReaderAndWriter == null || !mReaderAndWriter.isOpen()) {
            mReaderAndWriter = SQLiteDatabase.openDatabase(path + databaseName, null, SQLiteDatabase
                    .OPEN_READWRITE);
        }
    }

    public void closeDB() {
        if (mReaderAndWriter != null && mReaderAndWriter.isOpen()) {
            mReaderAndWriter.close();
        }
    }

    public List<DetailTypeNewpaper> getAllDetailTypeNewpaper() {
        List<TypeNewpaper> typeNewpapers = getAllTypeNewpaper();
        List<DetailTypeNewpaper> mListDetailTypeNewpapers = new ArrayList<>();
        Cursor cursor = null;
        for (TypeNewpaper typeNewpaper : typeNewpapers) {
            List<Newpaper> newpapers = new ArrayList<>();
            try {
                String selection = DatabaseConstans.NewPaperEntry.COLUMN_ID_TYPE + " = ?";
                String[] selectArgs = new String[]{typeNewpaper.getIdType() + ""};
                cursor = mReaderAndWriter.query(DatabaseConstans.NewPaperEntry.TABLE_NAME, null, selection, selectArgs, null, null, null);
                while (cursor.moveToNext()) {
                    Newpaper newpaper = new Newpaper();
                    int idNewpaper = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.NewPaperEntry.COLUMN_ID));
                    int sort = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.NewPaperEntry.COLUMN_SORT));
                    String image = cursor.getString(cursor.getColumnIndex(DatabaseConstans.NewPaperEntry.COLUMN_IMAGE));
                    String nameNewpaper = cursor.getString(cursor.getColumnIndex(DatabaseConstans.NewPaperEntry.COLUMN_NAME_NEWPAPER));
                    mLogger.d(nameNewpaper);
                    newpaper.setNamePaper(nameNewpaper);
                    newpaper.setIdNewpaper(idNewpaper);
                    newpaper.setIdType(typeNewpaper.getIdType());
                    newpaper.setmSort(sort);
                    newpaper.setNameImage(image);
                    newpapers.add(newpaper);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            DetailTypeNewpaper detailTypeNewpaper = new DetailTypeNewpaper();
            detailTypeNewpaper.setListNewPaper(newpapers);
            detailTypeNewpaper.setTypeNewpaper(typeNewpaper);
            mListDetailTypeNewpapers.add(detailTypeNewpaper);
        }
        return mListDetailTypeNewpapers;
    }


    public List<TypeNewpaper> getAllTypeNewpaper() {
        Cursor cursor = null;
        List<TypeNewpaper> typeNewpapers = new ArrayList<>();
        try {
            cursor = mReaderAndWriter.query(DatabaseConstans.TypeNewpaperEntry.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int idType = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.TypeNewpaperEntry.COLUMN_ID));
                int sort = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.TypeNewpaperEntry.COLUMN_SORT));
                String nameType = cursor.getString(cursor.getColumnIndex(DatabaseConstans.TypeNewpaperEntry.COLUMN_NAME));
                mLogger.d(nameType);
                typeNewpapers.add(new TypeNewpaper(idType, nameType, sort));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return typeNewpapers;
    }

    public List<DetailNewpaper> getListDetailNewpaperByIDNewpaper(int idNewpaper) {
        List<DetailNewpaper> mListDetailNewpaper = new ArrayList<>();
        Cursor cursor = null;
        try {
            String selection = DatabaseConstans.DetailNewpaperEntry.COLUMN_ID_NEWPAPER + " = ?";
            String[] selectArgs = new String[]{String.valueOf(idNewpaper)};
            cursor = mReaderAndWriter.query(DatabaseConstans.DetailNewpaperEntry.TABLE_NAME, null, selection, selectArgs, null, null, null);
            while (cursor.moveToNext()) {
                int mIdNewpaper = idNewpaper;
                String mLink = cursor.getString(cursor.getColumnIndex(DatabaseConstans.DetailNewpaperEntry.COLUMN_LINK));
                int mSort = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.DetailNewpaperEntry.COLUMN_SORT));
                String mNameCategory = cursor.getString(cursor.getColumnIndex(DatabaseConstans.DetailNewpaperEntry.COLUMN_NAME_CATEGORY));
                int mCountSelect = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.DetailNewpaperEntry.COLUMN_SORT));
                DetailNewpaper detailNewpaper = new DetailNewpaper(mIdNewpaper, mLink, mSort, mNameCategory, mCountSelect);
                mListDetailNewpaper.add(detailNewpaper);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return mListDetailNewpaper;
    }

    public List<Bookmark> getAllBookMark(int limit, int page) {
        List<Bookmark> listBookmarks = new ArrayList<>();
        Cursor cursor = null;
        try {
            String sql = "SELECT * FROM " + DatabaseConstans.BookMarkEntry.TABLE_NAME + " INNER JOIN " +
                    DatabaseConstans.NewPaperEntry.TABLE_NAME + " ON " +
                    DatabaseConstans.BookMarkEntry.TABLE_NAME + "." + DatabaseConstans.BookMarkEntry.COLUMN_ID_NEWPAPER +
                    " = " +
                    DatabaseConstans.NewPaperEntry.TABLE_NAME + "." + DatabaseConstans.NewPaperEntry.COLUMN_ID +
                    " ORDER BY " + DatabaseConstans.BookMarkEntry.COLUMN_DATE + " DESC " + (limit == 0 ? "" : " limit " + page * limit + ", limit");
            ;
            cursor = mReaderAndWriter.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                Bookmark bookmark = new Bookmark(cursor);
                listBookmarks.add(bookmark);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return listBookmarks;
    }


    public List<History> getListHistory(int limit, int page) {
        List<History> listHistories = new ArrayList<>();
        Cursor cursor = null;
        try {
            String sql = "SELECT * FROM " + DatabaseConstans.HistoryEntry.TABLE_NAME + " INNER JOIN " +
                    DatabaseConstans.NewPaperEntry.TABLE_NAME + " ON " +
                    DatabaseConstans.HistoryEntry.TABLE_NAME + "." + DatabaseConstans.HistoryEntry.COLUMN_ID_NEWPAPER +
                    " = " +
                    DatabaseConstans.NewPaperEntry.TABLE_NAME + "." + DatabaseConstans.NewPaperEntry.COLUMN_ID +
                    " ORDER BY " + DatabaseConstans.HistoryEntry.COLUMN_DATE + " DESC " + (limit == 0 ? "" : " limit " + page * limit + ", limit");
            cursor = mReaderAndWriter.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                History history = new History(cursor);
                listHistories.add(history);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return listHistories;
    }

    public List<NewFeedItem> getAllNewFeedItemByCategory(NewFeedCatogery newFeedCatogery, int limit, int page) {
        Cursor cursor = null;
        List<NewFeedItem> mNewFeedItems = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + DatabaseConstans.NewPaperEntry.TABLE_NAME + "," + DatabaseConstans.DetailNewpaperEntry.TABLE_NAME + "," + DatabaseConstans.NewFeedEntry.TABLE_NAME
                    + " WHERE " +
                    DatabaseConstans.DetailNewpaperEntry.TABLE_NAME + "." + DatabaseConstans.DetailNewpaperEntry.COLUMN_ID_NEWS_FEED + " = " +
                    DatabaseConstans.NewFeedEntry.TABLE_NAME + "." + DatabaseConstans.NewFeedEntry.COLUMN_ID + " AND " +
                    DatabaseConstans.DetailNewpaperEntry.TABLE_NAME + "." + DatabaseConstans.DetailNewpaperEntry.COLUMN_ID_NEWPAPER + " = " +
                    DatabaseConstans.NewPaperEntry.TABLE_NAME + "." + DatabaseConstans.NewPaperEntry.COLUMN_ID + " AND " +
                    DatabaseConstans.NewFeedEntry.TABLE_NAME + "." + DatabaseConstans.NewFeedEntry.COLUMN_NAME + " = '" + newFeedCatogery.getNameCategory() + "' " +
                    " ORDER BY " + DatabaseConstans.NewFeedEntry.TABLE_NAME + "." + DatabaseConstans.NewFeedEntry.COLUMN_SORT + " DESC " + (limit == 0 ? "" : " limit " + page * limit + ", limit");
            cursor = mReaderAndWriter.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String linkRss = cursor.getString(cursor.getColumnIndex(DatabaseConstans.DetailNewpaperEntry.TABLE_NAME + "." + DatabaseConstans.DetailNewpaperEntry.COLUMN_LINK));
                String mNameNewpaper = cursor.getString(cursor.getColumnIndex(DatabaseConstans.NewPaperEntry.TABLE_NAME + "." + DatabaseConstans.NewPaperEntry.COLUMN_NAME_NEWPAPER));
                String mNameImageNewpaper = cursor.getString(cursor.getColumnIndex(DatabaseConstans.NewPaperEntry.TABLE_NAME + "." + DatabaseConstans.NewPaperEntry.COLUMN_IMAGE));
                int mIdNewpaper = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.NewPaperEntry.TABLE_NAME + "." + DatabaseConstans.NewPaperEntry.COLUMN_ID));
                Newpaper newpaper = new Newpaper();
                newpaper.setNameImage(mNameImageNewpaper);
                newpaper.setNamePaper(mNameNewpaper);
                newpaper.setIdNewpaper(mIdNewpaper);
                NewFeedItem newFeedItem = new NewFeedItem();
                newFeedItem.setLink(linkRss);
                newFeedItem.setNewpaper(newpaper);
                mNewFeedItems.add(newFeedItem);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return mNewFeedItems;
    }

    public List<NewFeedCatogery> getListNewFeedCategories() {
        Cursor cursor = null;
        List<NewFeedCatogery> newFeedCatogeries = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + DatabaseConstans.NewFeedEntry.TABLE_NAME +
                    " WHERE " + DatabaseConstans.NewFeedEntry.COLUMN_IS_USED + " = 1" +
                    " ORDER BY " + DatabaseConstans.NewFeedEntry.COLUMN_SORT + " ASC ";
            cursor = mReaderAndWriter.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                int sort = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.NewFeedEntry.COLUMN_SORT));
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseConstans.NewFeedEntry.COLUMN_ID));
                String nameCategory = cursor.getString(cursor.getColumnIndex(DatabaseConstans.NewFeedEntry.COLUMN_NAME));
                NewFeedCatogery newFeedCatogery = new NewFeedCatogery();
                newFeedCatogery.setSort(sort);
                newFeedCatogery.setId(id);
                newFeedCatogery.setNameCategory(nameCategory);
                newFeedCatogeries.add(newFeedCatogery);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return newFeedCatogeries;
    }

    public List<NewFeedCatogery> getFullNewFeedCategories() {
        List<NewFeedCatogery> newFeedCatogeries = getListNewFeedCategories();
        for (NewFeedCatogery newFeedCatogery : newFeedCatogeries) {
            newFeedCatogery.setNewFeedItems(getAllNewFeedItemByCategory(newFeedCatogery, 0, 0));
        }
        return newFeedCatogeries;
    }


    public long saveBookmark(Bookmark bookmark) {
        if (!isAvailableBookmark(bookmark.getLink())) {
            long insert = mReaderAndWriter.insert(DatabaseConstans.BookMarkEntry.TABLE_NAME, null, bookmark.getContentValues());
            mLogger.d("insert bookmark: " + insert);
            return insert;

        } else {
            String whereClause = DatabaseConstans.BookMarkEntry.COLUMN_LINK + " = ?";
            String[] whereArgs = new String[]{bookmark.getRssItem().getLink()};
            int update = mReaderAndWriter.update(DatabaseConstans.BookMarkEntry.TABLE_NAME, bookmark.getContentValues(), whereClause, whereArgs);
            mLogger.d("update bookmark: " + update);
            return update;
        }
    }

    public long saveHistory(History history) {
        if (!isAvailableHistory(history)) {
            long insert = mReaderAndWriter.insert(DatabaseConstans.HistoryEntry.TABLE_NAME, null, history.getContentValues());
            mLogger.d("insert history: " + insert);
        } else {
            String whereClause = DatabaseConstans.HistoryEntry.COLUMN_LINK + " = ?";
            String[] whereArgs = new String[]{history.getRssItem().getLink()};
            mReaderAndWriter.update(DatabaseConstans.HistoryEntry.TABLE_NAME, history.getContentValues(), whereClause, whereArgs);
        }
        return -1;
    }

    public boolean isAvailableHistory(History history) {
        Cursor cursor = null;
        try {
            String sql = "SELECT * FROM " + DatabaseConstans.HistoryEntry.TABLE_NAME + " WHERE " + DatabaseConstans.HistoryEntry.COLUMN_LINK + " = '" + history.getRssItem().getLink() + "'";
            cursor = mReaderAndWriter.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                return true;
            } else {
                return false;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public boolean isAvailableBookmark(String link) {
        Cursor cursor = null;
        try {
            String sql = "SELECT * FROM " + DatabaseConstans.BookMarkEntry.TABLE_NAME + " WHERE " + DatabaseConstans.BookMarkEntry.COLUMN_LINK + " = '" + link + "'";
            cursor = mReaderAndWriter.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                return true;
            } else {
                return false;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public int deleteHistory(int... idHistorys) {
        String whereClause = "";
        String whereArgs[] = new String[idHistorys.length];
        for (int i = 0; i < idHistorys.length; i++) {
            whereClause += DatabaseConstans.HistoryEntry.COLUMN_ID + " = ? " + (i == idHistorys.length - 1 ? "" : "or");
            whereArgs[i] = idHistorys[i] + "";
        }
        return mReaderAndWriter.delete(DatabaseConstans.HistoryEntry.TABLE_NAME, whereClause, whereArgs);
    }

    public int deleteBookMark(String link) {
        String whereClause = DatabaseConstans.BookMarkEntry.COLUMN_LINK +"= ?";
        String whereArgs[] = new String[]{link};
        return mReaderAndWriter.delete(DatabaseConstans.BookMarkEntry.TABLE_NAME, whereClause, whereArgs);
    }

    public List<NewfeedSetting> getAllListNewfeedSetting() {
        List<NewfeedSetting> mNewfeedSettings = new ArrayList<>();
        Cursor cursor = null;
        try {
            String sql = "Select * from " + DatabaseConstans.NewFeedEntry.TABLE_NAME;
            cursor = mReaderAndWriter.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                NewfeedSetting newfeedSetting = new NewfeedSetting(cursor);
                mNewfeedSettings.add(newfeedSetting);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return mNewfeedSettings;
    }

    public int updateSetting(NewfeedSetting newfeedSetting) {
        String whereClause = DatabaseConstans.NewFeedEntry.COLUMN_ID + " = ?";
        String[] whereArgs = new String[]{newfeedSetting.getId() + ""};
        return mReaderAndWriter.update(DatabaseConstans.NewFeedEntry.TABLE_NAME, newfeedSetting.getContentValues(), whereClause, whereArgs);
    }
}
