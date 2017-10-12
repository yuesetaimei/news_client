package com.tlkg.news.app.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseRecyclerAdapter;
import com.tlkg.news.app.bean.BeautyBean;
import com.tlkg.news.app.event.WelfareClickEvent;
import com.tlkg.news.app.util.GlideRoundTransform;
import com.tlkg.news.app.util.StringListUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/9/29.
 */

public class WelfareRecyclerAdapter extends BaseRecyclerAdapter<BeautyBean.ResultsBean> {

    private static final String TAG = "WelfareRecyclerAdapter";

    private Map<String, Integer> mapHeight = new HashMap<>();

    @Override
    public void refreshAddData(List<BeautyBean.ResultsBean> data) {
        super.refreshAddData(data);
    }

    @Override
    public void refreshData(List<BeautyBean.ResultsBean> data) {
        super.refreshData(data);
    }

    @Override
    public BaseRecyclerViewHolder getRecyclerViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_welfare_img, null));
    }

    public class ViewHolder extends BaseRecyclerAdapter.BaseRecyclerViewHolder<BeautyBean.ResultsBean> {

        @InjectView(R.id.item_beauty_cardview)
        CardView cardView;

        @InjectView(R.id.item_beauty_img)
        ImageView imageView;

        @InjectView(R.id.item_beauty_textview)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindViewHolder(BeautyBean.ResultsBean bean, final int position) {
            final BeautyBean.ResultsBean resultsBean = bean;
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new StaggeredGridLayoutManager.LayoutParams(-1, -2);
            }
            Integer integer = mapHeight.get(resultsBean.getUrl());
            if (integer != null && integer.intValue() > 0) {
                layoutParams.height = integer.intValue();
            } else {
                layoutParams.height = -2;
            }
            layoutParams.setMargins(5, 5, 5, 5);
            itemView.setLayoutParams(layoutParams);

            if (resultsBean == null || TextUtils.isEmpty(resultsBean.getUrl())) return;
            textView.setVisibility(View.VISIBLE);
            Glide.with(itemView.getContext())
                    .load(resultsBean.getUrl())
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontTransform()
                    .centerCrop()
                    .transform(new GlideRoundTransform(itemView.getContext(), 10))
                    .into(new GlideDrawableImageViewTarget(imageView) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                            textView.setVisibility(View.GONE);
                            imageView.post(new Runnable() {
                                @Override
                                public void run() {
                                    mapHeight.put(resultsBean.getUrl(), imageView.getHeight());
                                }
                            });
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //事件在WelfareFragment中处理
                                    EventBus.getDefault().post(new WelfareClickEvent(StringListUtil.ResultsBeanToString(getData()), position));
                                }
                            });
                        }
                    });
        }
    }
}
