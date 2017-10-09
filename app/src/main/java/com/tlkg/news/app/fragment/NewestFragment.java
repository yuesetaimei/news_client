package com.tlkg.news.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseFragment;
import com.tlkg.news.app.config.ClientConfig;
import com.tlkg.news.app.presenter.NewestPresenter;
import com.tlkg.news.app.ui.view.mzbanner.MZBannerView;
import com.tlkg.news.app.ui.view.mzbanner.holder.MZHolderCreator;
import com.tlkg.news.app.ui.view.mzbanner.holder.MZViewHolder;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/9/24.
 * 最新推荐Fragment
 */

public class NewestFragment extends BaseFragment implements NewestPresenter.INewestView {


    @InjectView(R.id.fragment_newest_banner)
    MZBannerView bannerView;

    @InjectView(R.id.fragment_newest_recyclerview)
    RecyclerView recyclerView;

    private NewestPresenter presenter;

    @Override
    protected void initData(Bundle bundle) {
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_newest;
    }


    @Override
    public void initView(View view) {
        presenter = new NewestPresenter(this);
        presenter.load();
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
        bannerView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        bannerView.pause();
    }

    @Override
    public void setBannerData(List<String> list) {
        // 设置数据
        bannerView.setDelayedTime(ClientConfig.BANNER_DELAY_TIME);
        bannerView.setIndicatorRes(R.drawable.indicator_rectangle_normal,R.drawable.indicator_rectang_selected);
        bannerView.setPages(list, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
    }


    public static class BannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定
            Glide.with(context).load(data).into(mImageView);
        }
    }
}
