package com.tlkg.news.app.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.tlkg.news.app.R;
import com.tlkg.news.app.bean.BeautyBean;
import com.tlkg.news.app.event.WelfareClickEvent;
import com.tlkg.news.app.util.GlideRoundTransform;
import com.tlkg.news.app.util.StringListUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuxiaoqi on 2017/9/29.
 */

public class WelfareRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 内容布局标识
     */
    private static final int TYPE_POSITION_CONTENT = 0x0001;

    /**
     * 底部加载中布局标识
     */
    private static final int TYPE_POSITION_FOOTER = 0x0002;

    private List<BeautyBean.ResultsBean> mData;

    private Map<String, Integer> mapHeight = new HashMap<>();

    private boolean isFirstLoad = true;

    private ILoadMoreListener loadMoreListener;

    public void setLoadMoreListener(ILoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public WelfareRecyclerAdapter() {
        mData = new ArrayList<>();
    }

    public void updateBeauty(List<BeautyBean.ResultsBean> data) {
        isFirstLoad = true;
        mapHeight.clear();
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
        isFirstLoad = false;
    }

    public void updateMoreBeauty(List<BeautyBean.ResultsBean> data) {
        int startChange = mData.size();
        mData.addAll(data);
        notifyItemRangeChanged(startChange, data.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_POSITION_FOOTER)
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_footer, null));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_welfare_img, null));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FooterViewHolder) {
            if (isFirstLoad)
                ((FooterViewHolder) holder).itemView.setVisibility(View.GONE);
            else
                ((FooterViewHolder) holder).itemView.setVisibility(View.VISIBLE);

            if (!isFirstLoad && position == mData.size()) {
                if (loadMoreListener != null) {
                    loadMoreListener.onLoadMore();//加载更多
                }
            }
        }
        if (position >= mData.size()) return;

        final ViewHolder viewHolder = (ViewHolder) holder;
        final BeautyBean.ResultsBean resultsBean = mData.get(position % mData.size());
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
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
        viewHolder.itemView.setLayoutParams(layoutParams);

        if (resultsBean == null || TextUtils.isEmpty(resultsBean.getUrl())) return;
        viewHolder.textView.setVisibility(View.VISIBLE);
        Glide.with(holder.itemView.getContext())
                .load(resultsBean.getUrl())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontTransform()
                .centerCrop()
                .transform(new GlideRoundTransform(holder.itemView.getContext(), 10))
                .into(new GlideDrawableImageViewTarget(viewHolder.imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        viewHolder.textView.setVisibility(View.GONE);
                        viewHolder.imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                mapHeight.put(resultsBean.getUrl(), viewHolder.imageView.getHeight());
                            }
                        });
                        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //事件在WelfareFragment中处理
                                EventBus.getDefault().post(new WelfareClickEvent(StringListUtil.ResultsBeanToString(mData), position));
                            }
                        });
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size())
            return TYPE_POSITION_FOOTER;
        return TYPE_POSITION_CONTENT;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_beauty_img);
            cardView = (CardView) itemView.findViewById(R.id.item_beauty_cardview);
            textView = (TextView) itemView.findViewById(R.id.item_beauty_textview);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        TextView textView;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_footer_progressbar);
            textView = (TextView) itemView.findViewById(R.id.item_footer_textview);
        }
    }


    public interface ILoadMoreListener {
        void onLoadMore();
    }
}
