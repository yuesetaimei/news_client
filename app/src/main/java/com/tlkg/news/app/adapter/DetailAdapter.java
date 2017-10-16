package com.tlkg.news.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseRecyclerAdapter;
import com.tlkg.news.app.bean.PersonBean;
import com.tlkg.news.app.event.DetailPersonClienEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/10/13.
 */

public class DetailAdapter extends BaseRecyclerAdapter<PersonBean> {

    private static final String TAG = "DetailAdapter";

    @Override
    public BaseRecyclerViewHolder getRecyclerViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_detail_person, parent, false);
        return new ViewHolder(v);
    }

    public static class ViewHolder extends BaseRecyclerAdapter.BaseRecyclerViewHolder<PersonBean> {

        @InjectView(R.id.item_movie_detail_person_img)
        ImageView imageView;

        @InjectView(R.id.item_movie_detail_person_tv)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindViewHolder(final PersonBean object, int position) {
            Glide.with(itemView.getContext()).load(object.imageUrl)
                    .centerCrop()
                    .into(imageView);
            textView.setText(object.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new DetailPersonClienEvent(object.art, object.name));
                }
            });
        }
    }
}
