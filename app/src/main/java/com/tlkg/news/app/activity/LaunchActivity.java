package com.tlkg.news.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.bumptech.glide.Glide;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseActivity;
import com.tlkg.news.app.presenter.LaunchPresenter;

/**
 * Created by wuxiaoqi on 2017/9/18.
 * 启动页
 */

public class LaunchActivity extends BaseActivity implements LaunchPresenter.ILaunchView {

    private final String TAG = "LaunchActivity";

    private LaunchPresenter presenter;

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
        presenter = new LaunchPresenter(this);
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (setAdData) {
                        AdActivity.startActivity(LaunchActivity.this, mBingPicUrl, mTtitle);
                    } else {
                        MainActivity.startActivity(LaunchActivity.this);
                    }
                    finish();
                    overridePendingTransition(0, 0);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            handler.removeMessages(1);
            handler.removeCallbacksAndMessages(null);
        }
        return super.onKeyDown(keyCode, event);
    }
}
