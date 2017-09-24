package com.tlkg.news.app.ui.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wuxiaoqi on 2017/9/24.
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

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
