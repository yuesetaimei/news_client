package com.tlkg.news.app.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.tlkg.news.app.R;

/**
 * Created by wuxiaoqi on 2017/11/20.
 * 配置推荐页显示的tab
 */

public class ConfigShowTabPopWindow extends PopupWindow {

    private static final String TAG = "ConfigShowTabPopWindow";

    private RecyclerView recyclerView;

    private ImageView closeImg;

    private Context mContext;

    public ConfigShowTabPopWindow(Context context) {
        this.mContext = context;
    }

    public void show(View parent) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_configshowtab, null);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(contentView);
        setAnimationStyle(R.style.popwin_anim_style);
        showAtLocation(parent, Gravity.TOP, 0, 0);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.view_configshowtab_recyclerview);
        closeImg = (ImageView) contentView.findViewById(R.id.view_configshowtab_close_img);
        closeImg.animate().rotation(0).setDuration(0).start();
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeImg.clearAnimation();
                closeImg.animate().rotation(120).setDuration(100).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        dismiss();
                    }
                }).start();
            }
        });
    }
}
