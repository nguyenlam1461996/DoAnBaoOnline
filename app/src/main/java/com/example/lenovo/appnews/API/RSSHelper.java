package com.example.lenovo.appnews.API;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.appnews.Object.NewFeedCatogery;
import com.example.lenovo.appnews.Object.NewFeedItem;
import com.example.lenovo.appnews.Object.RSSItems;
import com.example.lenovo.appnews.Object.XMLDOMParser;
import com.example.lenovo.appnews.Untils.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by B350M on 10/6/2017.
 */

public class RSSHelper extends Application {
    private static final String TAG = RSSHelper.class.getSimpleName();
    private static RSSHelper mInstance;
    private Logger mLogger = new Logger(TAG);
    private RequestQueue mRequestQueue;

    public static synchronized RSSHelper getInstance() {
        if (mInstance == null) {
            mInstance = new RSSHelper();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void getContentXML(final IRSSCallBack rssCallBack, String url) {
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                rssCallBack.onSuccess(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mLogger.d(error.toString());
                rssCallBack.onFail(error.toString());
            }
        });

// Adding request to request queue
        RSSHelper.getInstance().addToRequestQueue(strReq, "getContentXML");
    }

    public void getContentXMLByUrlConnection(final IRSSCallBack rssCallBack, String... url) {
        ReadData readData = new ReadData();
        readData.execute(rssCallBack, url);
    }

    public void getNewsFeedByInternet(final IRSSCallBackWithNewFeed rssCallBack,Context context, List<NewFeedCatogery> newFeedCatogeries) {
        ReadDataNewFeedCatogery readDataNewFeedCatogery = new ReadDataNewFeedCatogery();
        readDataNewFeedCatogery.execute(rssCallBack,context, newFeedCatogeries);
    }

    public String readRssFromUrl(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String responseBody = "";
        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = "";
        try {
            while ((s = buffer.readLine()) != null)
                responseBody += s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("", "WSCAlls response body is:" + responseBody);
        return responseBody;
    }

    public interface IRSSCallBackWithNewFeed {
        void onSuccess(List<NewFeedCatogery> newFeedCatogeries);
        void onFail(String error);
    }

    public interface IWorker {
        void doWork();
        void loadFinish(List<NewFeedCatogery> newFeedCatogeries);
    }

    private IWorker worker;

    private Runnable runnableWorker = new Runnable() {
        @Override
        public void run() {
            worker.doWork();
        }
    };

    public IWorker getWorker() {
        return worker;
    }

    public void setWorker(IWorker worker) {
        this.worker = worker;
    }

    public void executeLoadNewFeedData(IWorker iWorker) {
        this.worker = iWorker;
        new Thread(runnableWorker).start();
    }

    public RSSItems readRssFromUrl(final NewFeedItem newFeedItem) {
        String responseBody = "";
        okhttp3.Response response = null;
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(newFeedItem.getLink())
                .build();

        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful())
                responseBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RSSItems rssItems = XMLDOMParser.getInstance().parseXMLToRSSItems(responseBody, newFeedItem.getNewpaper());
        return rssItems;
    }

    class ReadDataNewFeedCatogery extends AsyncTask<List<NewFeedCatogery>, Integer, List<NewFeedCatogery>> {
        private IRSSCallBackWithNewFeed mRssCallBack;
        private Context mContext;
        private boolean isLoading = true;
        public RSSItems readRssFromUrl(final NewFeedItem newFeedItem) {
            String responseBody = "";
            okhttp3.Response response = null;
            OkHttpClient client = new OkHttpClient();
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(newFeedItem.getLink())
                    .build();

            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful())
                    responseBody = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            RSSItems rssItems = XMLDOMParser.getInstance().parseXMLToRSSItems(responseBody, newFeedItem.getNewpaper());
            return rssItems;
        }


        public void execute(IRSSCallBackWithNewFeed rssCallBack,Context context, List<NewFeedCatogery> newFeedCatogeries) {
            mRssCallBack = rssCallBack;
            mContext = context;
            execute(newFeedCatogeries);
        }

        @Override
        protected List<NewFeedCatogery> doInBackground(List<NewFeedCatogery>[] lists) {
            List<NewFeedCatogery> newFeedCatogeries = lists[0];
            for (int i = 0 ; i < newFeedCatogeries.size(); i ++) {
                NewFeedCatogery currentNewFeedCatogery = newFeedCatogeries.get(i);
                RSSItems rssItems = new RSSItems();
                for (int j = 0; j < currentNewFeedCatogery.getNewFeedItems().size(); j++) {
                    NewFeedItem currentNewFeedItem = currentNewFeedCatogery.getNewFeedItems().get(j);
                    RSSItems receiverRss = readRssFromUrl(currentNewFeedItem);
                    if (receiverRss != null) {
                        rssItems.addAll(receiverRss);
                    }
                }
//                Collections.sort(rssItems.getRssItems());
                currentNewFeedCatogery.setRssItems(rssItems);
            }
            return newFeedCatogeries;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<NewFeedCatogery> newFeedCatogeries) {
            super.onPostExecute(newFeedCatogeries);
            mRssCallBack.onSuccess(newFeedCatogeries);
        }
    }


    class ReadData extends AsyncTask<String, Integer, String> {
        private IRSSCallBack mRssCallBack;

        public String readRssFromUrl(String url) {
            String responseBody = "";
            okhttp3.Response response = null;
            OkHttpClient client = new OkHttpClient();
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .build();

            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful())
                    responseBody = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseBody;
        }



        @Override
        protected String doInBackground(String... params) {
            String content = "";
            for (int i = 0; i < params.length; i ++) {
                content += readRssFromUrl(params[0]);
            }
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            mRssCallBack.onSuccess(s);
            super.onPostExecute(s);

        }

        public void execute(IRSSCallBack rssCallBack, String... url) {
            mRssCallBack = rssCallBack;
            execute(url);
        }
    }

    public void executePoccessUrl(final String url, final IRSSCallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                readRssFromUrlByOkhttp(url, callBack);
            }
        };
        new Thread(runnable).start();
    }

    public void readRssFromUrlByOkhttp(String url, IRSSCallBack callBack) {
        String responseBody = "";
        okhttp3.Response response = null;
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful())
                responseBody = response.body().string();
        } catch (IOException e) {
            callBack.onFail(e.toString());
        }
        callBack.onSuccess(responseBody);
    }
}
