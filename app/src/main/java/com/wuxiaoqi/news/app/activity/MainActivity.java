package com.wuxiaoqi.news.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.wuxiaoqi.news.app.R;
import com.wuxiaoqi.news.app.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private long onBackTime = 0;

    public static void startActivity(Activity activity) {
        Intent i = new Intent(activity, MainActivity.class);
        activity.startActivity(i);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initEvent() {

    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - onBackTime > 2000) {
            Toast.makeText(this, R.string.press_the_exit_again, Toast.LENGTH_SHORT).show();
            onBackTime = System.currentTimeMillis();
        } else super.onBackPressed();
    }
}
