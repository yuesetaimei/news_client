package com.tlkg.news.app.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.tlkg.news.app.R;
import com.tlkg.news.app.bean.BeautyBean;
import com.tlkg.news.app.util.GlideRoundTransform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuxiaoqi on 2017/9/29.
 */

public class WelfareRecyclerAdapter extends RecyclerView.Adapter<WelfareRecyclerAdapter.ViewHolder> {

    private List<BeautyBean.ResultsBean> mData;
    private Map<String, Integer> mapHeight = new HashMap<>();

    public WelfareRecyclerAdapter() {
        mData = new ArrayList<>();
    }

    public void updateBeauty(List<BeautyBean.ResultsBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void updateMoreBeauty(List<BeautyBean.ResultsBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_welfare_img, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        BeautyBean.ResultsBean resultsBean = mData.get(position);
        ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, -2);
        }
        Integer integer = mapHeight.get(position + "");
        if (integer != null && integer.intValue() > 0) {
            Log.i("wxq", integer.intValue() + "");
            layoutParams.height = integer.intValue();
        } else {
            layoutParams.height = -2;
        }
        holder.imageView.setLayoutParams(layoutParams);


        if (resultsBean == null || TextUtils.isEmpty(resultsBean.getUrl())) return;
        holder.cardView.setVisibility(View.INVISIBLE);
        Glide.with(holder.itemView.getContext())
                .load(resultsBean.getUrl())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontTransform()
                .centerCrop()
                .transform(new GlideRoundTransform(holder.itemView.getContext(), 10))
                .into(new GlideDrawableImageViewTarget(holder.imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        holder.cardView.setVisibility(View.VISIBLE);
                        holder.imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                mapHeight.put(position + "", holder.imageView.getHeight());
                            }
                        });
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_beauty_img);
            cardView = (CardView) itemView.findViewById(R.id.item_beauty_cardview);
        }
    }
}
