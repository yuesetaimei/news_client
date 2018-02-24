package com.tlkg.news.app.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

/**
 * Created by wuxiaoqi on 2017/9/24.
 * 自定义 SwipeRefreshLayout
 */

public class ChoiceSwipeRefreshLayout extends SwipeRefreshLayout {

    public ChoiceSwipeRefreshLayout(Context context) {
        super(context);
    }

    public ChoiceSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int mLastXIntercept = 0;

    private int mLastYIntercept = 0;

    public void init() {
        setProgressBackgroundColorSchemeResource(android.R.color.white);
        setColorSchemeResources(android.R.color.holo_blue_light,
//                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_blue_light);
        setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                intercepted = true;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercepted = false;
                } else {
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = true;
                break;
            default:
                break;
        }
        mLastXIntercept = x;
        mLastYIntercept = y;
        if (intercepted) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }
}
