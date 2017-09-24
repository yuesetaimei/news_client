package com.tlkg.news.app.fragment;

import android.os.Bundle;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseFragment;
import com.tlkg.news.app.bean.BannerBean;
import com.tlkg.news.app.presenter.NewestPresenter;

/**
 * Created by wuxiaoqi on 2017/9/24.
 * 最新推荐Fragment
 */

public class NewestFragment extends BaseFragment implements NewestPresenter.INewestView {

    private NewestPresenter presenter;

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_newest;
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void setBannerData(BannerBean bannerBean) {

    }
}
