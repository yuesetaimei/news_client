package com.tlkg.news.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;


public class BigImageViewActivity extends BaseActivity {

    private static final String TAG = "BigImageViewActivity";

    public static final String IMAGE_URLS = "image_urls";

    public static final String IMAGE_POSITION = "image_position";

    public static final String IMAGE_TYPE = "image_type";

    @InjectView(R.id.activity_big_image_viewpager)
    ViewPager viewPager;

    @InjectView(R.id.activity_big_image_index_tv)
    TextView indexTv;

    @InjectView(R.id.activity_big_image_setting_wallpaper_tv)
    TextView setWallpaperTv;

    @InjectView(R.id.activity_big_image_save_tv)
    TextView saveTv;

    @InjectView(R.id.activity_big_image_pb)
    ProgressBar progressBar;

    private int mType;

    private int mPosition;

    private List<String> imageUrls;

    public static void startActivity(Activity activity, ArrayList<String> list) {
        startActivity(activity, list, 0);
    }

    public static void startActivity(Activity activity, ArrayList<String> list, int position) {
        startActivity(activity, list, position, 0);
    }

    public static void startActivity(Activity activity, ArrayList<String> list, int position, int type) {
        Intent intent = new Intent(activity, BigImageViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(IMAGE_URLS, list);
        bundle.putInt(IMAGE_POSITION, position);
        bundle.putInt(IMAGE_TYPE, type);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    public void initData(Bundle bundle) {
        if (bundle == null) return;
        mType = bundle.getInt(IMAGE_TYPE);
        mPosition = bundle.getInt(IMAGE_POSITION);
        imageUrls = bundle.getStringArrayList(IMAGE_URLS);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_big_image_view;
    }

    @Override
    public void initEvent() {

    }
}
