package com.example.lenovo.appnews.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.example.lenovo.appnews.R;
import com.example.lenovo.appnews.Adapter.DetailNewpaperAdapter;
import com.example.lenovo.appnews.DataBase.DatabaseConstans;
import com.example.lenovo.appnews.Object.DetailNewpaper;
import com.example.lenovo.appnews.Object.Newpaper;
import com.example.lenovo.appnews.Object.RSSItem;

import com.example.lenovo.appnews.Detail_Newpaper_Act.DetailNewpaperPresenterImpl;
import com.example.lenovo.appnews.Untils.Constans;
import com.example.lenovo.appnews.Untils.Utils;
import com.example.lenovo.appnews.View.View_Interactor.IDetailNewpaperActView;

import java.util.List;

public class DetaiNewpaperAct extends AppCompatActivity implements IDetailNewpaperActView {
    private Toolbar mToolbar;
    private ViewPager mVpDetaiNewpaper;
    private DetailNewpaperAdapter mDetailNewpaperAdapter;
    private TabLayout mTabDetailNewpaper;
    private DetailNewpaperPresenterImpl mDetailNewpaperPresenter;
    private Newpaper mNewpaper;
    private List<DetailNewpaper> mListDetailNewpapers;
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detai_newpaper);
        Utils.resetLibRss();
        init();
        initData();
        initView();
    }

    private void init() {
        mDetailNewpaperPresenter = new DetailNewpaperPresenterImpl(this);
        mNewpaper = getIntent().getParcelableExtra(Constans.KEY_NEWPAPER);


    }

    //1 Load Data theo ID
    private void initData() {
        mDetailNewpaperPresenter.loadData(mNewpaper.getIdNewpaper());
    }

    //3
    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mNewpaper.getNamePaper());
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //searchView = findViewById(R.id.search_view);
        //mTabDetailNewpaper = (TabLayout) findViewById(R.id.tab_detail_newpaper);
        //mVpDetaiNewpaper = (ViewPager) findViewById(R.id.vp_category_newpaper);
        mVpDetaiNewpaper.setAdapter(mDetailNewpaperAdapter);
        mTabDetailNewpaper.setupWithViewPager(mVpDetaiNewpaper);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchView.setSuggestions(Utils.getLibTitleRssItem());
                searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String title = (String) parent.getItemAtPosition(position);
                        RSSItem rssItem = Utils.filterRssByTitle(title);
                        searchView.closeSearch();
                        Intent intent = new Intent(getContext(), ShowNewThreadAct.class);
                        intent.putExtra(DatabaseConstans.KEY_RSS, rssItem);
                        getContext().startActivity(intent);
                    }
                });
                searchView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onSearchViewClosed() {
                searchView.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_toolbar_have_search, menu);
        //MenuItem item = menu.findItem(R.id.action_search);
        //searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public Context getContext() {
        return this;
    }


    //2
    @Override
    public void onLoadData(List<DetailNewpaper> detailNewpapers) {
        mListDetailNewpapers = detailNewpapers;
        mDetailNewpaperAdapter = new DetailNewpaperAdapter(getSupportFragmentManager(), mListDetailNewpapers, mNewpaper);
    }

}
