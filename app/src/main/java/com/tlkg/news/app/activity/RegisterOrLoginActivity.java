package com.tlkg.news.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseActivity;

/**
 * Created by wuxiaoqi on 2017/12/26.
 */

public class RegisterOrLoginActivity extends BaseActivity {

    private static final String TAG = "RegisterOrLoginActivity";

    public static void startActivity(Activity activity) {
        Intent i = new Intent(activity, RegisterOrLoginActivity.class);
        activity.startActivity(i);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_register_or_login;
    }

    @Override
    public void initEvent() {

    }
}
