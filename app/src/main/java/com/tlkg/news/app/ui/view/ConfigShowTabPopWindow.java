package com.tlkg.news.app.ui.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.tlkg.news.app.R;

/**
 * Created by wuxiaoqi on 2017/11/20.
 * 配置推荐页显示的tab
 */

public class ConfigShowTabPopWindow extends PopupWindow {

    private static final String TAG = "ConfigShowTabPopWindow";

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
    }
}
