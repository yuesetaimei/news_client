package com.wuxiaoqi.news.app.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by wuxiaoqi on 2017/9/18.
 * Activity基类，所有Activity都应该继承BaseActivity
 */

public abstract class BaseActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    protected Bundle saveInstanceState;

    protected ViewDataBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.saveInstanceState = savedInstanceState;
        initData(getIntent().getExtras());
        if (enableBindView()) {
            binding = DataBindingUtil.setContentView(this, getContentView());
        } else {
            setContentView(getContentView());
            ButterKnife.inject(this);
        }
        EventBus.getDefault().register(this);
        initViews();
        initEvent();
    }

    protected boolean enableBindView() {
        return false;
    }

    public abstract void initData(Bundle bundle);

    protected void initViews() {
    }

    @LayoutRes
    public abstract int getContentView();

    public abstract void initEvent();

    @Subscribe
    public void onEventBus(BaseEvent event){
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
