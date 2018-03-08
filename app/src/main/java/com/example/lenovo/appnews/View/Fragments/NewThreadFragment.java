package com.example.lenovo.appnews.View.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.appnews.R;
import com.example.lenovo.appnews.Adapter.NewThreadAdaper;
import com.example.lenovo.appnews.Object.DetailNewpaper;
import com.example.lenovo.appnews.Object.Newpaper;
import com.example.lenovo.appnews.Object.RSSItems;
import com.example.lenovo.appnews.New_Thread_Frag.NewThreadPresenterImpl;
import com.example.lenovo.appnews.Untils.Constans;
import com.example.lenovo.appnews.Untils.Logger;
import com.example.lenovo.appnews.Untils.Utils;
import com.example.lenovo.appnews.Untils.SimpleDividerItemDecoration;
import com.example.lenovo.appnews.View.View_Interactor.DetailNewpaperFragView;

import static com.example.lenovo.appnews.Adapter.DetailNewpaperAdapter.KEY_DETAIL_NEWPAPER;
/**
 * Created by MinhVuong on 11/2/2017.
 */

public class NewThreadFragment extends BaseFragment implements DetailNewpaperFragView{
    private Logger mLogger = new Logger(NewThreadFragment.class.getSimpleName());
    private NewThreadAdaper mNewThreadAdaper;
    private RSSItems mRssItems;
    private RecyclerView mRcvNewFeed;
    private NewThreadPresenterImpl mNewFeedPresenter;
    private DetailNewpaper mDetailNewpaper;
    private Newpaper mNewpaper;
    public static NewThreadFragment newpaperFragment(Bundle bundle) {
        NewThreadFragment newThreadFragment = new NewThreadFragment();
        newThreadFragment.setArguments(bundle);
        return newThreadFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_first, container, false);//
        init();
        initData();
        initView();
        initEvent();
        return mRootView;
    }

    @Override
    protected void init() {
        mNewFeedPresenter = new NewThreadPresenterImpl(this);
        if (getArguments() != null) {
            mDetailNewpaper = getArguments().getParcelable(KEY_DETAIL_NEWPAPER);
            mLogger.d(mDetailNewpaper.getLink());
            mNewpaper = getArguments().getParcelable(Constans.KEY_NEWPAPER);
        }
    }

    @Override
    protected void initView() {
        // = mRootView.findViewById(R.id.rcv_list_new_thread);
        mRcvNewFeed.setHasFixedSize(true);
        mRcvNewFeed.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        SimpleDividerItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(getContext());
        mRcvNewFeed.addItemDecoration(dividerItemDecoration);
        mLogger.d("initView");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        mNewFeedPresenter.loadData(mDetailNewpaper.getLink(), mNewpaper);
        mLogger.d("initData");
    }

    @Override
    public void onLoadData(RSSItems rssItems) {
        if (getActivity() != null) {
            mRssItems = rssItems;
            Utils.addRssItemsToLibs(rssItems);
            mNewThreadAdaper = new NewThreadAdaper(getActivity(), mRssItems);
            mNewThreadAdaper.notifyDataSetChanged();
            mRcvNewFeed.setAdapter(mNewThreadAdaper);
            mLogger.d("onLoadData");
        }

    }
}
