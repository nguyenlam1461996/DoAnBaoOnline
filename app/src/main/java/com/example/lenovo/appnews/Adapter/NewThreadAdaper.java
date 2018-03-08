package com.example.lenovo.appnews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.lenovo.appnews.GlideApp;
import com.example.lenovo.appnews.R;
import com.example.lenovo.appnews.DataBase.DatabaseConstans;
import com.example.lenovo.appnews.Object.RSSItem;
import com.example.lenovo.appnews.Object.RSSItems;
import com.example.lenovo.appnews.Untils.Logger;
import com.example.lenovo.appnews.View.Activity.ShowNewThreadAct;


public class NewThreadAdaper extends RecyclerView.Adapter<NewThreadAdaper.NewFeedHolder> {
    private RSSItems mRssItems;
    private Context mContext;
    private LayoutInflater mInflater;
    private Logger mLogger = new Logger(NewThreadAdaper.class.getSimpleName());

    public NewThreadAdaper(Context context, RSSItems mRssItems) {
        this.mRssItems = mRssItems;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public NewFeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_thread, parent, false);
        NewFeedHolder newFeedHolder = new NewFeedHolder(itemView);
        return newFeedHolder;
    }

    @Override
    public void onBindViewHolder(final NewFeedHolder newFeedHolder, int position) {
        final RSSItem currentRSSItem = mRssItems.getItem(position);
        mLogger.d(currentRSSItem.getImgUrl());
        GlideApp.with(mContext)
                .load(currentRSSItem.getImgUrl()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                newFeedHolder.mProgressBarWaitingImage.setVisibility(View.GONE);
                newFeedHolder.mIvImage.setVisibility(View.GONE);
                newFeedHolder.mIvNoImage.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                newFeedHolder.mProgressBarWaitingImage.setVisibility(View.GONE);
                newFeedHolder.mIvImage.setVisibility(View.VISIBLE);
                newFeedHolder.mIvNoImage.setVisibility(View.GONE);
                return false;
            }
        }).into(newFeedHolder.mIvImage);
        newFeedHolder.mTvTitle.setText(currentRSSItem.getTitle());
        newFeedHolder.mTvTime.setText(currentRSSItem.getDate());
        newFeedHolder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, ShowNewThreadAct.class);
                intent.putExtra(DatabaseConstans.KEY_RSS, currentRSSItem);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRssItems.getSize();
    }

    class NewFeedHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        ImageView mIvImage;
        ImageView mIvNoImage;
        ProgressBar mProgressBarWaitingImage;
        TextView mTvTime;
        View mItemView;

        NewFeedHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mIvImage = itemView.findViewById(R.id.iv_image);
            mIvNoImage = itemView.findViewById(R.id.iv_no_image);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mProgressBarWaitingImage = itemView.findViewById(R.id.progress_waiting_load_image);
        }
    }
}
