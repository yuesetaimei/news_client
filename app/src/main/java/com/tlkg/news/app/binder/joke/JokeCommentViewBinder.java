package com.tlkg.news.app.binder.joke;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tlkg.news.app.R;
import com.tlkg.news.app.bean.JokeCommentBean;
import com.tlkg.news.app.util.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by wuxiaoqi on 2017/12/14.
 */

public class JokeCommentViewBinder extends ItemViewBinder<JokeCommentBean.DataBean.RecentCommentsBean, JokeCommentViewBinder.ViewHolder> {
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_joke_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull JokeCommentBean.DataBean.RecentCommentsBean item) {
        try {
            final Context context = holder.itemView.getContext();
            String iv_avatar = item.getUser_profile_image_url();
            String tv_username = item.getUser_name();
            String tv_text = item.getText();
            String tv_likes = item.getDigg_count() + "赞";

            ImageLoader.loadCenterCrop(context, iv_avatar, holder.iv_avatar, R.color.viewBackground);
            holder.tv_username.setText(tv_username);
            holder.tv_text.setText(tv_text);
            holder.tv_likes.setText(tv_likes);
        } catch (Exception e) {

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView iv_avatar;
        private TextView tv_username;
        private TextView tv_text;
        private TextView tv_likes;

        ViewHolder(View itemView) {
            super(itemView);
            this.iv_avatar = (CircleImageView) itemView.findViewById(R.id.iv_avatar);
            this.tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            this.tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            this.tv_likes = (TextView) itemView.findViewById(R.id.tv_likes);
        }
    }
}
