package com.example.lenovo.appnews.Untils;

import android.os.Environment;

import java.io.File;

/**
 * Created by MinhVuong on 11/20/2017.
 */

public class Constans {
    public static final String FOLDER_NAME = "Vivu";
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + FOLDER_NAME + File.separator;
    /*key from fragment*/
    public static final String KEY_RSS = "KEY_RSS";
    public static final String KEY_NEWPAPER = "KEY_NEWPAPER";
    public static final int ID_PAPER_TINTHETHAO = 12;
    public static final int REQUEST_CODE_FAVORITE = 111;
}
