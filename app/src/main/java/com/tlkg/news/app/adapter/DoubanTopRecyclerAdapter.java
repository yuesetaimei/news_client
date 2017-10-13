package com.tlkg.news.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseRecyclerAdapter;
import com.tlkg.news.app.bean.HotMovieBean;
import com.tlkg.news.app.event.MovieTopItemClickEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/10/12.
 */

public class DoubanTopRecyclerAdapter extends BaseRecyclerAdapter<HotMovieBean.SubjectsBean> {

    private static final String TAG = "DoubanTopRecyclerAdapter";

    @Override
    public BaseRecyclerViewHolder getRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_douban_top, parent, false);
        return new ViewHolder(view);
    }


    public static class ViewHolder extends BaseRecyclerAdapter.BaseRecyclerViewHolder<HotMovieBean.SubjectsBean> {

        @InjectView(R.id.item_douban_top_ll)
        LinearLayout linearLayout;

        @InjectView(R.id.item_douban_top_img)
        ImageView photoImg;

        @InjectView(R.id.item_douban_top_title_tv)
        TextView titleTv;

        @InjectView(R.id.item_douban_top_rating_tv)
        TextView ratingTv;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindViewHolder(final HotMovieBean.SubjectsBean object, int position) {
            HotMovieBean.SubjectsBean bean = object;
            Glide.with(itemView.getContext())
                    .load(bean.getImages().getLarge())
                    .placeholder(R.drawable.ic_movie_white_24dp)
                    .error(R.drawable.ic_movie_white_24dp)
                    .centerCrop()
                    .into(photoImg);
            titleTv.setText(bean.getTitle());
            ratingTv.setText(NewsClientApplication.getStringId(R.string.movie_score) + bean.getRating().getAverage());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new MovieTopItemClickEvent(object));
                }
            });
        }
    }
}

