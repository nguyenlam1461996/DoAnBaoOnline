package com.example.lenovo.appnews.API;

/**
 * Created by B350M on 10/6/2017.
 */

public interface IRSSCallBack {
    void onSuccess(String response);
    void onFail(String error);
}
