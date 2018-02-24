package com.tlkg.news.app.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wuxiaoqi on 2017/9/22.
 * 自定义的ViewPager
 */

public class ChoiceScrollViewPager extends ViewPager {
    public ChoiceScrollViewPager(Context context) {
        super(context);
    }

    public ChoiceScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
