package com.tlkg.news.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseEvent;
import com.tlkg.news.app.base.BaseFragment;
import com.tlkg.news.app.event.AnimTranEvent;
import com.tlkg.news.app.event.ShowMyFragmentCircleEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wuxiaoqi on 2017/9/24.
 * 我的Fragment
 */

public class MyFragment extends BaseFragment {

    public static final String TAG = "MyFragment";

    private static MyFragment instance = null;

    @InjectView(R.id.fragment_my_circle_img)
    CircleImageView circleImageView;

    public static Fragment getInstance() {
        if (instance == null) instance = new MyFragment();
        return instance;
    }


    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_my;
    }

    @Override
    public void initEvent() {
        circleImageView.post(new Runnable() {
            @Override
            public void run() {
                int[] locations = new int[2];
                circleImageView.getLocationOnScreen(locations);
                EventBus.getDefault().post(new AnimTranEvent(locations[0], locations[1]));
            }
        });
    }


    @Subscribe
    public void onEvent(BaseEvent event) {
        if (event instanceof ShowMyFragmentCircleEvent) {
            circleImageView.setVisibility(View.VISIBLE);
        }
    }
}
