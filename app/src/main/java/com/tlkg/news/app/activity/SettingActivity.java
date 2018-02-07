package com.tlkg.news.app.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseSlidrActivity;
import com.tlkg.news.app.fragment.SettingFragment;

/**
 * 设置界面
 */
public class SettingActivity extends BaseSlidrActivity {


    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    protected void initViews() {
        initToolBar();
        setupFragment();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_setting_toolbar);
        toolbar.setTitle(R.string.my_setting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupFragment() {
        Fragment fragment = Fragment.instantiate(this, SettingFragment.class.getName());
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.activity_setting_container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
