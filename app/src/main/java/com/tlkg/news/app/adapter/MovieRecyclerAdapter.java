package com.tlkg.news.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.bean.HotMovieBean;
import com.tlkg.news.app.util.PhoneUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiaoqi on 2017/10/11.
 */

public class MovieRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MovieRecyclerAdapter";

    public static final int TYPE_HEAD_VIEW = 0x0001;

    public static final int TYPE_GENERAL_VIEW = 0x0002;

    private List<HotMovieBean.SubjectsBean> mData;

    private View mHeadView;

    private String headPic = PhoneUtil.randomPic();

    public MovieRecyclerAdapter(List<HotMovieBean.SubjectsBean> data) {
        this.mData = new ArrayList<>();
        if (data != null) mData.addAll(data);
    }

    public void updateMovieItem(List<HotMovieBean.SubjectsBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addHeadView(View v) {
        mHeadView = v;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD_VIEW) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_head, parent, false);
            return new HeadViewHoler(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieRecyclerAdapter.ViewHolder) {
            ViewHolder holder1 = (ViewHolder) holder;
            HotMovieBean.SubjectsBean subjectsBean = mData.get(mHeadView == null ? position : position - 1);
            Glide.with(holder.itemView.getContext())
                    .load(subjectsBean.getImages().getLarge())
                    .into(holder1.photoImg);
            holder1.titleTv.setText(subjectsBean.getTitle());
            holder1.directorTv.setText(getDirectorsString(subjectsBean));
            holder1.castsTv.setText(getCastsString(subjectsBean));
            holder1.genresTv.setText(NewsClientApplication.getStringId(R.string.movie_type) + getGenersString(subjectsBean));
            holder1.ratingRateTv.setText(NewsClientApplication.getStringId(R.string.movie_score) + subjectsBean.getRating().getAverage());
            holder1.lineView.setBackgroundColor(PhoneUtil.randomColor());
            View itemView = holder1.itemView;
            ViewHelper.setScaleX(itemView, 0.9f);
            ViewHelper.setScaleY(itemView, 0.7f);
            ViewPropertyAnimator.animate(itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator(0.8f)).start();
            ViewPropertyAnimator.animate(itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator(0.8f)).start();
        } else {
            HeadViewHoler headViewHoler = (HeadViewHoler) holder;
            Glide.with(holder.itemView.getContext()).load(headPic)
                    .centerCrop()
                    .into(headViewHoler.imageView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeadView == null) return super.getItemViewType(position);
        if (position == 0)
            return TYPE_HEAD_VIEW;
        return TYPE_GENERAL_VIEW;
    }

    @Override
    public int getItemCount() {
        if (mHeadView == null) {
            if (mData == null) return 0;
            return mData.size();
        }
        return mData.size() + 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout rootLinearLayout;
        LinearLayout itemLinearLayout;
        ImageView photoImg;//subjectsBean.images.large
        TextView titleTv;//subjectsBean.title
        TextView directorTv;//subjectsBean.directors
        TextView castsTv;//subjectsBean.casts
        TextView genresTv;//subjectsBean.genres
        TextView ratingRateTv;//subjectsBean.rating.average
        View lineView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootLinearLayout = (LinearLayout) itemView.findViewById(R.id.item_movie_root_ll);
            itemLinearLayout = (LinearLayout) itemView.findViewById(R.id.item_movie_item_ll);
            photoImg = (ImageView) itemView.findViewById(R.id.item_movie_photo_img);
            titleTv = (TextView) itemView.findViewById(R.id.item_movie_title_tv);
            directorTv = (TextView) itemView.findViewById(R.id.item_movie_director_tv);
            castsTv = (TextView) itemView.findViewById(R.id.item_movie_casts_tv);
            genresTv = (TextView) itemView.findViewById(R.id.itme_movie_genres_tv);
            ratingRateTv = (TextView) itemView.findViewById(R.id.item_movie_rating_rate_tv);
            lineView = itemView.findViewById(R.id.item_movie_view_color);
        }
    }

    public static class HeadViewHoler extends RecyclerView.ViewHolder {
        ImageView imageView;

        public HeadViewHoler(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_movie_head_img);
        }
    }

    /**
     * 获取类型
     *
     * @param subjectsBean
     * @return
     */
    public String getGenersString(HotMovieBean.SubjectsBean subjectsBean) {
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
    private String getCastsString(HotMovieBean.SubjectsBean subjectsBean) {
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
    private String getDirectorsString(HotMovieBean.SubjectsBean subjectsBean) {
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
