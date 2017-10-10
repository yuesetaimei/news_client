package com.tlkg.news.app.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseActivity;
import com.tlkg.news.app.bean.BingPicBean;
import com.tlkg.news.app.constant.ConstantImageUrl;

import java.util.Random;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 显示图片广告的Activity
 */
public class AdActivity extends BaseActivity {

    private static final String TAG = "AdActivity";

    /**
     * 获取图片的key
     */
    public static final String PIC_URL = "pic_url";

    /**
     * 图片标题
     */
    public static final String PIC_TITLE = "pic_title";

    @InjectView(R.id.activity_ad_img)
    ImageView adImg;

    @InjectView(R.id.activity_ad_jump_btn)
    ImageView jumpBtn;

    @InjectView(R.id.activity_ad_text)
    TextView titleTv;

    private boolean isJump = false;

    private String pic_url = "";

    private String pic_title = "";

    public static void startActivity(Activity activity) {
        Intent i = new Intent(activity, AdActivity.class);
        activity.startActivity(i);
    }

    public static void startActivity(Activity activity, String picUrl, String picTitle) {
        Intent i = new Intent(activity, AdActivity.class);
        i.putExtra(PIC_URL, picUrl);
        i.putExtra(PIC_TITLE, picTitle);
        activity.startActivity(i);
    }

    @Override
    public void initData(Bundle bundle) {
        if (bundle != null) {
            pic_url = bundle.getString(PIC_URL);
            pic_title = bundle.getString(PIC_TITLE);
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_ad;
    }

    @Override
    public void initEvent() {
        if (TextUtils.isEmpty(pic_url)) {//如果没有传图片地址，随机加载固定的图片地址
            int i = new Random().nextInt(ConstantImageUrl.TRANSITION_URLS.length);
            pic_url = ConstantImageUrl.TRANSITION_URLS[i];
        }
        Glide.with(this).load(pic_url)
                .placeholder(R.mipmap.welcome)
                .error(R.mipmap.test_ad)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new GlideDrawableImageViewTarget(adImg) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        jumpBtn.setVisibility(View.VISIBLE);
                        if (!TextUtils.isEmpty(pic_title)) {
                            titleTv.setVisibility(View.VISIBLE);
                            titleTv.setAlpha(0);
                            titleTv.animate().alpha(1).setDuration(1000).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    titleTv.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            titleTv.clearAnimation();
                                            titleTv.animate().translationY(titleTv.getHeight()).setDuration(500).start();
                                        }
                                    });
                                }
                            }).start();
                            titleTv.setText(pic_title);
                        }
                        adImg.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (isJump) return;
                                MainActivity.startActivity(AdActivity.this);
                                finish();
                                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                            }
                        }, 3000);
                    }
                });
    }

    @OnClick({R.id.activity_ad_img, R.id.activity_ad_jump_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_ad_img:

                break;
            case R.id.activity_ad_jump_btn:
                isJump = true;
                MainActivity.startActivity(this);
                finish();
                overridePendingTransition(0, 0);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

