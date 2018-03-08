package com.example.lenovo.appnews.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.lenovo.appnews.R;
import com.example.lenovo.appnews.DataBase.ProviderData;
import com.example.lenovo.appnews.Object.Bookmark;
import com.example.lenovo.appnews.Object.History;
import com.example.lenovo.appnews.Object.Newpaper;
import com.example.lenovo.appnews.Object.RSSItem;
import com.example.lenovo.appnews.Untils.Constans;
import com.example.lenovo.appnews.Untils.SaveImageFromUrl;
import com.example.lenovo.appnews.Untils.Utils;

import java.io.File;

public class ShowNewThreadAct extends AppCompatActivity {
    public static final String KEY_URL_CONTENT = "KEY_URL_CONTENT";
    private WebView mWebViewContent;
    private String mUrlContent;
    private Toolbar mToolbar;
    private Newpaper mNewpaper;
    private RSSItem mRssItem;
    private ProviderData mProviderData;
    private Bookmark mBookmark;
    private History mHistory;
    private MenuItem btFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_thread);
        init();
        initView();
    }

    private void init() {
        mProviderData = new ProviderData(this);
    }

    private void initView() {
        Intent intent = getIntent();
        mRssItem = intent.getParcelableExtra(Constans.KEY_RSS);
        mNewpaper = mRssItem.getNewpaper();
        mHistory = new History();
        mHistory.setRssItem(mRssItem);
        mHistory.setNewpaper(mNewpaper);
        mProviderData.saveHistory(mHistory);
        mUrlContent = mRssItem.getLink();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mNewpaper.getNamePaper());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Hien thi UpButton
        mWebViewContent = (WebView) findViewById(R.id.wv_content);
        mWebViewContent.getSettings().setJavaScriptEnabled(true);
        mWebViewContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mWebViewContent.loadUrl("javascript:(function() { " +
                        "document.getElementsByTagName('header')[0].style.display=" + "none" + "; " +
                        "})()");
            }
        });
        mWebViewContent.loadUrl(mUrlContent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_save:
                if (mProviderData.isAvailableBookmark(mRssItem.getLink())) {
                    mProviderData.deleteBookMark(mRssItem.getLink());
                    btFavorite.setIcon(R.drawable.ic_is_not_favorite_24dp);
                    Utils.isChangFavorite = true;
                } else {
                    final String urlImage = mRssItem.getImgUrl();
                    new SaveImageFromUrl(this).start(urlImage, new SaveImageFromUrl.SaveImageCallBack() {
                        @Override
                        public void onSaveSuccess(String fileName) {
                            String path = Constans.PATH + File.separator + urlImage.substring(urlImage.lastIndexOf('/') + 1);
                            mBookmark = new Bookmark();
                            mBookmark.setUrlImage(urlImage);
                            mBookmark.setDate(mRssItem.getPubDate());
                            mBookmark.setLink(mUrlContent);
                            mBookmark.setPath(path);
                            mBookmark.setTitle(mRssItem.getTitle());
                            mBookmark.setNewpaper(mNewpaper);
                            mBookmark.setRssItem(mRssItem);
                            int saveSucces = (int) mProviderData.saveBookmark(mBookmark);
                            if (saveSucces != -1 && saveSucces != 0) {
                                Toast.makeText(ShowNewThreadAct.this, "Save success!", Toast.LENGTH_SHORT).show();
                                Utils.isChangFavorite = true;
                            }
                            btFavorite.setIcon(R.drawable.ic_favorite_24dp);
                        }

                        @Override
                        public void onSaveFail(String error) {
                            String path = Constans.PATH + File.separator + urlImage.substring(urlImage.lastIndexOf('/') + 1);
                            mBookmark = new Bookmark();
                            mBookmark.setUrlImage(urlImage);
                            mBookmark.setDate(mRssItem.getPubDate());
                            mBookmark.setLink(mUrlContent);
                            mBookmark.setPath("");
                            mBookmark.setTitle(mRssItem.getTitle());
                            mBookmark.setNewpaper(mNewpaper);
                            mBookmark.setRssItem(mRssItem);
                            int saveSucces = (int) mProviderData.saveBookmark(mBookmark);
                            if (saveSucces != -1 && saveSucces != 0) {
                                Toast.makeText(ShowNewThreadAct.this, "Save success!", Toast.LENGTH_SHORT).show();
                                Utils.isChangFavorite = true;
                            }
                            btFavorite.setIcon(R.drawable.ic_favorite_24dp);
                        }
                    });

                }
                break;
            case R.id.it_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = mRssItem.getTitle() + "\n" + mRssItem.getLink();
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            case android.R.id.home:
                if (Utils.isChangFavorite = true) {
                    setResult(RESULT_OK);
                }
                finish();
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar_show_content, menu);
        btFavorite = menu.findItem(R.id.it_save);
        if (mProviderData.isAvailableBookmark(mRssItem.getLink())) {
            btFavorite.setIcon(R.drawable.ic_favorite_24dp);
        } else {
            btFavorite.setIcon(R.drawable.ic_is_not_favorite_24dp);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (Utils.isChangFavorite = true) {
            setResult(RESULT_OK);
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
