package com.tlkg.news.app.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

    public static final int FRAGMENT_ONE = 1;
    public static final int FRAGMENT_TWO = 2;
    public static final int FRAGMENT_THREE = 3;
    public static final int FRAGMENT_FOUR = 4;
    public static final int FRAGMENT_FIVE = 5;
    public static final int FRAGMENT_SIX = 6;

    protected Bundle saveInstanceState;

    protected ViewDataBinding binding;

    protected Fragment mCurrentFragment;

    protected int mCurrentIndex;

    protected String mCurrentTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.saveInstanceState = savedInstanceState;
        if (enableBindView()) {
            binding = DataBindingUtil.setContentView(this, getContentView());
        } else {
            setContentView(getContentView());
            ButterKnife.inject(this);
        }
        EventBus.getDefault().register(this);
        initData(getIntent().getExtras());
        initViews();
        initWindow();
        initEvent();
    }

    protected boolean enableBindView() {
        return false;
    }

    public abstract void initData(Bundle bundle);

    protected void initWindow() {
    }

    protected void initViews() {
    }

    @LayoutRes
    public abstract int getContentView();

    public abstract void initEvent();

    @Subscribe
    public void onEventBus(BaseEvent event) {
    }

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


    public void initFragment(int currentIndex) {
        if (getContentFragmentId() == 0) {
            throw new IllegalArgumentException("No correct layout was returned id!");
        }
        this.mCurrentIndex = currentIndex;
        FragmentManager manager = getSupportFragmentManager();
        int backStackEntryCount = manager.getBackStackEntryCount();
        for (int i = 0; i < backStackEntryCount; i++) {
            manager.popBackStack(manager.getBackStackEntryAt(i).getId(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        if (manager.findFragmentById(getContentFragmentId()) != null) {
            manager.beginTransaction()
                    .remove(manager.findFragmentById(getContentFragmentId()))
                    .commit();
        }
//        setFragment(currentIndex);
    }

    @IdRes
    protected int getContentFragmentId() {
        return 0;
    }

    public void setFragment(int index) {
        if (getContentFragmentId() == 0) {
            throw new IllegalArgumentException("No correct layout was returned id!");
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        String tag = index + "";
        mCurrentIndex = index;
        if (mCurrentTag != null && !tag.equals(mCurrentTag)) {
            Fragment childFragment = fm.findFragmentByTag(tag);
            if (childFragment != null) {//Fragment栈已经存在
                ft.hide(mCurrentFragment)
                        .show(childFragment)
                        .commitAllowingStateLoss();
                mCurrentFragment = childFragment;
                mCurrentTag = tag;
            } else {//将fragment添加到栈中
                childFragment = findFragmentByIndex(index);
                if (childFragment == null) return;
                ft.addToBackStack(tag);
                ft.hide(mCurrentFragment)
                        .add(getContentFragmentId(), childFragment, tag)
                        .commitAllowingStateLoss();
                mCurrentFragment = childFragment;
                mCurrentTag = tag;
            }
        } else {//替换
            Fragment childFragment = findFragmentByIndex(index);
            if (childFragment == null) return;
            ft.replace(getContentFragmentId(), childFragment, tag)
                    .addToBackStack(tag)
                    .commit();
            mCurrentFragment = childFragment;
            mCurrentTag = tag;
        }
    }

    protected Fragment findFragmentByIndex(int index) {
        return null;
    }
}
