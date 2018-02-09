package com.tlkg.news.app.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tlkg.news.app.R;
import com.tlkg.news.app.activity.RegisterOrLoginActivity;
import com.tlkg.news.app.activity.setting.SettingActivity;
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

public class MyFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "MyFragment";

    @InjectView(R.id.fragment_my_head_rl)
    RelativeLayout headRl;

    @InjectView(R.id.fragment_my_circle_img)
    CircleImageView circleImageView;

    @InjectView(R.id.fragment_my_regiter_or_login_tv)
    TextView registerOrLoginTv;

    @InjectView(R.id.fragment_my_theme)
    TextView themeTv;

    @InjectView(R.id.fragment_my_setting)
    TextView settingTv;

    @InjectView(R.id.fragment_my_version)
    TextView versionTv;

    @InjectView(R.id.fragment_my_clear)
    TextView clearTv;

    public static Fragment getInstance() {
        return new MyFragment();
    }

    @Override
    protected void initData(Bundle bundle) {
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView(View view) {
        versionTv.append(getVersionName());
        registerOrLoginTv.setOnClickListener(this);
        themeTv.setOnClickListener(this);
        settingTv.setOnClickListener(this);
        versionTv.setOnClickListener(this);
        clearTv.setOnClickListener(this);
    }

    private String getVersionName() {
        String versionName = "";
        PackageManager packageManager = getActivity().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
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
        registerOrLoginTv.setOnClickListener(this);
    }

    @Subscribe
    public void onEvent(BaseEvent event) {
        if (event instanceof ShowMyFragmentCircleEvent) {
            circleImageView.setVisibility(View.VISIBLE);
            registerOrLoginTv.clearAnimation();
            registerOrLoginTv.animate().alpha(1).setDuration(200).start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_my_regiter_or_login_tv:
                RegisterOrLoginActivity.startActivity(getActivity());
                break;
            case R.id.fragment_my_theme:

                break;
            case R.id.fragment_my_setting:
                SettingActivity.startActivity(getActivity());
                break;
            case R.id.fragment_my_version:
                Toast.makeText(getActivity(), getVersionName(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_my_clear:

                break;
            default:
                break;
        }
    }

    @Override
    protected void setThemeColor(int color) {
        headRl.setBackgroundColor(color);
    }
}
