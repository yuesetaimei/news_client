package com.tlkg.news.app.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.ContentProvider;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.tlkg.news.app.R;
import com.tlkg.news.app.activity.AdActivity;


public class ImageLoader {

    public static void loadCenterCrop(Context context, String url, ImageView view, int defaultResId) {
        if (CommonSettingUtil.getInstance().getIsNoPhotoMode()) {
            view.setImageResource(defaultResId);
        } else {
            Glide.with(context).load(url).crossFade().centerCrop().into(view);
        }
    }

    public static void load(Context context, String url,ImageView imgView, int defaultResId, int errorResId, GlideDrawableImageViewTarget listener) {
        if (CommonSettingUtil.getInstance().getIsNoPhotoMode()) {
            imgView.setImageResource(errorResId);
            listener.onLoadFailed(null,null);

        } else {
            Glide.with(context).load(url)
                    .placeholder(defaultResId)
                    .error(errorResId)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(listener);
        }
    }

    public static void load(Context context, String url, ImageView view, int defaultResId, int errorResId) {
        Glide.with(context).load(url)
                .placeholder(defaultResId)
                .error(errorResId)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    /**
     * 带加载异常图片
     */
    public static void loadCenterCrop(Context context, String url, ImageView view, int defaultResId, int errorResId) {
        if (CommonSettingUtil.getInstance().getIsNoPhotoMode()) {
            view.setImageResource(defaultResId);
        } else {
            Glide.with(context).load(url).crossFade().centerCrop().error(errorResId).into(view);
        }
    }

    /**
     * 带监听处理
     */
    public static void loadCenterCrop(Context context, String url, ImageView view, RequestListener listener) {
        Glide.with(context).load(url).crossFade().centerCrop().listener(listener).into(view);
    }
}
