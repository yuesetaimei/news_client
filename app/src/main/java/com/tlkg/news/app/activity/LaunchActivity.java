package com.tlkg.news.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.bumptech.glide.Glide;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseActivity;
import com.tlkg.news.app.presenter.LaunchPresenter;

import java.lang.ref.WeakReference;

/**
 * Created by wuxiaoqi on 2017/9/18.
 * 启动页
 */

public class LaunchActivity extends BaseActivity implements LaunchPresenter.ILaunchView {

//    private final String TAG = "LaunchActivity";

    private final LaunchPresenter presenter = new LaunchPresenter(this);

    private boolean setAdData = false;

    private String mBingPicUrl;

    private String mTtitle;

    @Override
    public void initData(Bundle bundle) {
    }

    @Override
    public int getContentView() {
        return R.layout.activity_launch;
    }

    @Override
    public void initEvent() {
        presenter.load();
        handler.sendEmptyMessageDelayed(1, 3000);
    }

    @Override
    public void setAdDataSuccess(String bingPicUrl, String title) {
        mBingPicUrl = bingPicUrl;
        Glide.with(this).load(mBingPicUrl).downloadOnly(720, 1280);
        mTtitle = title;
        setAdData = true;
    }

    private MyHandler handler = new MyHandler(this);

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            handler.removeMessages(1);
            handler.removeCallbacksAndMessages(null);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 跳转到广告Activity
     */
    private void toAdActivity() {
        AdActivity.startActivity(LaunchActivity.this, mBingPicUrl, mTtitle);
        finish();
        overridePendingTransition(0, 0);
    }

    /**
     * 跳转到MainActivity
     */
    private void toMainActivity() {
        MainActivity.startActivity(LaunchActivity.this);
        finish();
        overridePendingTransition(0, 0);
    }

    private static class MyHandler extends Handler {

        private final WeakReference<LaunchActivity> mActivity;

        private MyHandler(LaunchActivity activity) {
            this.mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LaunchActivity launchActivity = mActivity.get();
            switch (msg.what) {
                case 1:
                    if (launchActivity.setAdData) {
                        launchActivity.toAdActivity();
                    } else {
                        launchActivity.toMainActivity();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
