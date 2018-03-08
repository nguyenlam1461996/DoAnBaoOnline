package com.example.lenovo.appnews.Untils;

/**
 * Created by MinhVuong on 11/12/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.lenovo.appnews.Untils.Constans.PATH;

public class SaveImageFromUrl extends AsyncTask<String, String, Boolean> {
    private Context context;
    URL ImageUrl;
    Bitmap bmImg = null;
    private String mReportSaveImage;
    private SaveImageCallBack mSaveImageCallBack;
    public interface SaveImageCallBack {
        void onSaveSuccess(String fileName);
        void onSaveFail(String error);
    }

    public void start(String url, SaveImageCallBack saveImageCallBack){
        mSaveImageCallBack = saveImageCallBack;
        execute(url);
    }
    public SaveImageFromUrl(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub

        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(String... args) {
        // TODO Auto-generated method stub

        InputStream is = null;

        try {

            ImageUrl = new URL(args[0]);
            // myFileUrl1 = args[0];

            HttpURLConnection conn = (HttpURLConnection) ImageUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Config.RGB_565;
            bmImg = BitmapFactory.decodeStream(is, null, options);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            String path = ImageUrl.getPath();
            String idStr = path.substring(path.lastIndexOf('/') + 1);
            File dir = new File(PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = idStr;
            File file = new File(dir, fileName);
            if (file.exists()) {
                mReportSaveImage = fileName;
                return true;
            }

            FileOutputStream fos = new FileOutputStream(file);
            bmImg.compress(CompressFormat.JPEG, 75, fos);
            fos.flush();
            fos.close();

            File imageFile = file;
            MediaScannerConnection.scanFile(context,
                    new String[] { imageFile.getPath() },
                    new String[] { "image/jpeg" }, null);
            mReportSaveImage = fileName;
            return true;
        } catch (Exception e) {
            mReportSaveImage = e.toString();
            return false;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    mReportSaveImage = e.toString();
                    return false;
                }
            }
        }
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);
        if (isSuccess) {
            mSaveImageCallBack.onSaveSuccess(mReportSaveImage);
        } else {
            mSaveImageCallBack.onSaveFail(mReportSaveImage);
        }
    }

}