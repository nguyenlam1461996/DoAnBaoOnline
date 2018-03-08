package com.example.lenovo.appnews;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DayNightSwitch switcher;
    private View background_view;
    private SwitchCompat anthanh;

    private ViewPager mVpDemo;
    TabLayout tabLayout;

    private DetailNewpaperAdapter mDetailNewpaperAdapter;
    private DetailNewpaperPresenterImpl mDetailNewpaperPresenter;
    private Newpaper mNewpaper;
    private List<DetailNewpaper> mListDetailNewpapers;
    private MaterialSearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        Utils.resetLibRss();//
        mDetailNewpaperPresenter = new DetailNewpaperPresenterImpl((IDetailNewpaperActView) this);//
        mNewpaper = getIntent().getParcelableExtra(Constans.KEY_NEWPAPER);//
        mDetailNewpaperPresenter.loadData(mNewpaper.getIdNewpaper());//


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //searchView = findViewById(R.id.search_view);


        mVpDemo = (ViewPager)findViewById(R.id.vp_main);
        //mVpDemo.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mVpDemo.setAdapter(mDetailNewpaperAdapter);//
        tabLayout=(TabLayout)findViewById(R.id.tl_demo);
        tabLayout.setupWithViewPager(mVpDemo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(mNewpaper.getNamePaper());//
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));//

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu =navigationView.getMenu();
        MenuItem menuItem =menu.findItem(R.id.nav_chedodem);
        MenuItem menuAnthanh =menu.findItem(R.id.nav_anthanh);
        View actionView =MenuItemCompat.getActionView(menuItem);
        View actionViewAn =MenuItemCompat.getActionView(menuAnthanh);

        switcher = (DayNightSwitch) actionView.findViewById(R.id.switcher);
        anthanh =(SwitchCompat)actionViewAn.findViewById(R.id.switchbasic);
        background_view = findViewById(R.id.background_view);

        switcher.setDuration(450);

        switcher.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean isNight) {
                if(isNight)
                {
                    Toast.makeText(MainActivity.this,"Toi",Toast.LENGTH_SHORT).show();
                    background_view.setAlpha(1f);
                   /// AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                }
                else
                {
                    Toast.makeText(MainActivity.this,"Sang",Toast.LENGTH_SHORT).show();
                    background_view.setAlpha(0f);
                   /// AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }
            }
        });


       /*anthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, (anthanh.isChecked()) ? "is checked!!!" : "not checked!!!", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });*/
        anthanh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Toast.makeText(MainActivity.this,"OK",Toast.LENGTH_SHORT).show();
                    View decorView = getWindow().getDecorView();
                    int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
                    decorView.setSystemUiVisibility(uiOptions);
                    //ActionBar actionBar = getActionBar();
                   // actionBar.hide();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"OK Tiep",Toast.LENGTH_SHORT).show();
                    View decorView = getWindow().getDecorView();
                    int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE;
                    decorView.setSystemUiVisibility(uiOptions);
                }

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_timkiem) {
            // Handle the camera action
        } else if (id == R.id.nav_chuyenmuc) {

        } else if (id == R.id.nav_daluu) {

        } else if (id == R.id.nav_lichsu) {
            Toast.makeText(MainActivity.this,"OK OK",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_chedodem) {
            Toast.makeText(MainActivity.this,"OK",Toast.LENGTH_SHORT).show();
            //final Switch sw=(Switch)menu.find
        } else if (id == R.id.nav_thongbao) {

        } else if (id == R.id.nav_phatvideo) {

        } else if (id == R.id.nav_gopy) {

        } else if (id == R.id.nav_thongtintoasoan) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onResume() {
        super.onResume();
    }

}
