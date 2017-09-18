package com.wuxiaoqi.news.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wuxiaoqi.news.app.R;
import com.wuxiaoqi.news.app.base.BaseActivity;
import com.wuxiaoqi.news.app.base.BaseEvent;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 显示图片广告的Activity
 */
public class AdActivity extends BaseActivity {

    @InjectView(R.id.activity_ad_img)
    ImageView adImg;

    @InjectView(R.id.activity_ad_jump_btn)
    ImageView jumpBtn;

    private boolean isJump = false;

    public static void startActivity(Activity activity) {
        Intent i = new Intent(activity, AdActivity.class);
        activity.startActivity(i);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_ad;
    }

    @Override
    public void initEvent() {
        Glide.with(this).load(R.mipmap.test_ad).into(adImg);
        adImg.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isJump) return;
                MainActivity.startActivity(AdActivity.this);
                finish();
                overridePendingTransition(0, 0);
            }
        }, 3000);
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
}

