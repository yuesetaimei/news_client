package com.tlkg.news.app.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.ui.view.photoview.PhotoView;
import com.tlkg.news.app.util.CommonSettingUtil;

import java.util.List;

/**
 * Created by wuxiaoqi on 2017/10/10.
 */

public class BigImageViewPagerAdapter extends PagerAdapter {

    private static final String TAG = "BigImageViewPagerAdapter";

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<String> mImageUrls;

    public BigImageViewPagerAdapter(Context context, List<String> imageUrls) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mImageUrls = imageUrls;
    }


    @Override
    public int getCount() {
        if (mImageUrls == null) return 0;
        return mImageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mLayoutInflater.inflate(R.layout.item_big_image_photoview, container, false);
        final PhotoView photoView = (PhotoView) view.findViewById(R.id.item_big_image_photoview);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.item_big_image_photoview_pb);

        String imageUrl = mImageUrls.get(position);
        progressBar.setVisibility(View.VISIBLE);
        int color = CommonSettingUtil.getInstance().getThemeColor();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        } else {
            Drawable wrapDrawable = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, color);
            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        }
        Glide.with(mContext).load(imageUrl)
                .crossFade(700)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(NewsClientApplication.getAppContext(), "资源加载异常", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    //这个用于监听图片是否加载完成
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        /**这里应该是加载成功后图片的高*/
                        int height = photoView.getHeight();
                        int wHeight = mContext.getResources().getDisplayMetrics().heightPixels;
                        if (height > wHeight) {
                            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        } else {
                            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        }
                        return false;
                    }
                }).into(photoView);

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
