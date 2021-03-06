package com.tlkg.news.app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlkg.news.app.util.CommonSettingUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by wuxiaoqi on 2017/9/21.
 * 基类Fragment ,所有fragment都应继承BaseFragment
 */

public abstract class BaseFragment extends Fragment {

    public static final String TAG = "BaseFragment";

    private int oldThemeColor = -1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            initData(getActivity().getIntent().getExtras());
        }
    }

    protected abstract void initData(Bundle bundle);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @LayoutRes
    public abstract int getContentView();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initEvent();
        EventBus.getDefault().register(this);
    }

    public void initView(View view) {
    }

    public abstract void initEvent();

    @Subscribe
    public void onEvent(BaseEvent event) {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (oldThemeColor != CommonSettingUtil.getInstance().getThemeColor()) {
            oldThemeColor = CommonSettingUtil.getInstance().getThemeColor();
            setThemeColor(CommonSettingUtil.getInstance().getThemeColor());
        }
    }

    protected void setThemeColor(int color) {
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
