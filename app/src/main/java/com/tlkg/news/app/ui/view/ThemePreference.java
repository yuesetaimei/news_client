package com.tlkg.news.app.ui.view;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;

import com.afollestad.materialdialogs.color.CircleView;
import com.tlkg.news.app.R;
import com.tlkg.news.app.util.CommonSettingUtil;


/**
 * Created by wuxiaoqi on 2018/2/7.
 */

public class ThemePreference extends Preference {

    private CircleView circleImageView;

    public ThemePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWidgetLayoutResource(R.layout.item_theme_preference_preview);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        int color = CommonSettingUtil.getInstance().getThemeColor();
        circleImageView = (CircleView) view.findViewById(R.id.iv_preview);
        circleImageView.setBackgroundColor(color);
    }

    /**
     * 刷新颜色显示
     */
    public void updateColor() {
        circleImageView.setBackgroundColor(CommonSettingUtil.getInstance().getThemeColor());
    }
}
