package com.tlkg.news.app.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseRecyclerAdapter;
import com.tlkg.news.app.bean.HotMovieBean;
import com.tlkg.news.app.event.DoubanTop250CliekEvent;
import com.tlkg.news.app.event.MovieItemClickEvent;
import com.tlkg.news.app.util.PhoneUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/10/11.
 */

public class MovieRecyclerAdapter extends BaseRecyclerAdapter<HotMovieBean.SubjectsBean> {

    private static final String TAG = "MovieRecyclerAdapter";

    private static String headPic = PhoneUtil.randomPic();

    @Override
    public BaseRecyclerViewHolder getRecyclerViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(v);
    }

    public static class ViewHolder extends BaseRecyclerAdapter.BaseRecyclerViewHolder<HotMovieBean.SubjectsBean> {

        @InjectView(R.id.item_movie_root_ll)
        LinearLayout rootLinearLayout;

        @InjectView(R.id.item_movie_item_ll)
        LinearLayout itemLinearLayout;

        @InjectView(R.id.item_movie_photo_img)
        ImageView photoImg;//subjectsBean.images.large

        @InjectView(R.id.item_movie_title_tv)
        TextView titleTv;//subjectsBean.title

        @InjectView(R.id.item_movie_director_tv)
        TextView directorTv;//subjectsBean.directors

        @InjectView(R.id.item_movie_casts_tv)
        TextView castsTv;//subjectsBean.casts

        @InjectView(R.id.itme_movie_genres_tv)
        TextView genresTv;//subjectsBean.genres

        @InjectView(R.id.item_movie_rating_rate_tv)
        TextView ratingRateTv;//subjectsBean.rating.average

        @InjectView(R.id.item_movie_view_color)
        View lineView;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindViewHolder(HotMovieBean.SubjectsBean object, int position) {
            final HotMovieBean.SubjectsBean subjectsBean = object;
            Glide.with(itemView.getContext())
                    .load(subjectsBean.getImages().getLarge())
                    .into(photoImg);
            titleTv.setText(subjectsBean.getTitle());
            directorTv.setText(getDirectorsString(subjectsBean));
            castsTv.setText(getCastsString(subjectsBean));
            genresTv.setText(NewsClientApplication.getStringId(R.string.movie_type) + getGenersString(subjectsBean));
            ratingRateTv.setText(NewsClientApplication.getStringId(R.string.movie_score) + subjectsBean.getRating().getAverage());
            lineView.setBackgroundColor(PhoneUtil.randomColor());
            ViewHelper.setScaleX(itemView, 0.9f);
            ViewHelper.setScaleY(itemView, 0.7f);
            ViewPropertyAnimator.animate(itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator(0.8f)).start();
            ViewPropertyAnimator.animate(itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator(0.8f)).start();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new MovieItemClickEvent(subjectsBean));
                }
            });
        }
    }

    public static class HeadViewHoler extends BaseRecyclerAdapter.BaseRecyclerViewHolder<HotMovieBean.SubjectsBean> {

        @InjectView(R.id.item_movie_head_rl)
        RelativeLayout relativeLayout;

        @InjectView(R.id.item_movie_head_img)
        ImageView imageView;

        public HeadViewHoler(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindViewHolder(HotMovieBean.SubjectsBean object, int position) {
            Glide.with(itemView.getContext()).load(headPic)
                    .centerCrop()
                    .placeholder(R.drawable.ic_error_outline_white_48dp)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imageView);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new DoubanTop250CliekEvent());
                }
            });
        }
    }

    /**
     * 获取类型
     *
     * @param subjectsBean
     * @return
     */
    public static String getGenersString(HotMovieBean.SubjectsBean subjectsBean) {
        List<String> genres = subjectsBean.getGenres();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < genres.size(); i++) {
            if (i < genres.size() - 1) {
                stringBuilder.append(genres.get(i)).append(" / ");
            } else {
                stringBuilder.append(genres.get(i));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 获取主演名字
     *
     * @param subjectsBean
     * @return
     */
    public static String getCastsString(HotMovieBean.SubjectsBean subjectsBean) {
        List<HotMovieBean.SubjectsBean.CastsBean> casts = subjectsBean.getCasts();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < casts.size(); i++) {
            if (!TextUtils.isEmpty(casts.get(i).getName())) {
                if (i < casts.size() - 1) {
                    sb.append(casts.get(i).getName() + " / ");
                } else {
                    sb.append(casts.get(i).getName());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取导演名字
     *
     * @param subjectsBean
     * @return
     */
    public static String getDirectorsString(HotMovieBean.SubjectsBean subjectsBean) {
        List<HotMovieBean.SubjectsBean.DirectorsBean> directors = subjectsBean.getDirectors();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < directors.size(); i++) {
            if (!TextUtils.isEmpty(directors.get(i).getName())) {
                if (i < directors.size() - 1) {
                    sb.append(directors.get(i).getName() + " / ");
                } else {
                    sb.append(directors.get(i).getName());
                }
            }
        }
        return sb.toString();
    }
}
