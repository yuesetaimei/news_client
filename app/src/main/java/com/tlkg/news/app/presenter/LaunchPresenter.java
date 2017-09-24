package com.tlkg.news.app.presenter;

import android.os.SystemClock;

import com.tlkg.news.app.base.BasePresenter;

/**
 * Created by wuxiaoqi on 2017/9/18.
 */

public class LaunchPresenter implements BasePresenter {

    ILaunchView mView;

    public LaunchPresenter(ILaunchView view) {
        this.mView = view;
    }

    @Override
    public void start() {
        //TODO
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                mView.setAdDataSuccess();
            }
        }).start();
    }


    public interface ILaunchView {
        void setAdDataSuccess();
    }
}
