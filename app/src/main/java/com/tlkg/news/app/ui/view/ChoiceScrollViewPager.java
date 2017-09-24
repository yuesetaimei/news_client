package com.tlkg.news.app.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wuxiaoqi on 2017/9/22.
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
        //TODO
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //TODO
        return super.onTouchEvent(ev);
    }
}
