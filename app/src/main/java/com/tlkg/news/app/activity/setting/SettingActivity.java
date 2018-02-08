package com.tlkg.news.app.activity.setting;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseSlidrActivity;

/**
 * 设置界面
 */
public class SettingActivity extends BaseSlidrActivity implements ColorChooserDialog.ColorCallback{

    public static final String LAUNCH_FRAGMENT_NAME = "launch_fragment_name";

    public static final String LAUNCH_FRAGMENT_ARGS = "launch_fragment_args";

    public static final String LAUNCH_FRAGMENT_TITLE = "launch_fragment_title";

    private String launchFragmentName;

    private Bundle launchFragmentArgs;

    private String launchFragmentTitle;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }

    public static void startFragment(Activity activity, String fragmentName, Bundle args, String title) {
        Intent intent = new Intent(activity, SettingActivity.class);
        intent.putExtra(LAUNCH_FRAGMENT_NAME, fragmentName);
        intent.putExtra(LAUNCH_FRAGMENT_ARGS, args);
        intent.putExtra(LAUNCH_FRAGMENT_TITLE, title);
        activity.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    public void initData(Bundle bundle) {
        launchFragmentName = bundle.getString(LAUNCH_FRAGMENT_NAME);
        launchFragmentArgs = bundle.getBundle(LAUNCH_FRAGMENT_ARGS);
        launchFragmentTitle = bundle.getString(LAUNCH_FRAGMENT_TITLE);
    }

    @Override
    protected void initViews() {
        initToolBar();
        setupFragment();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_setting_toolbar);
        toolbar.setTitle(TextUtils.isEmpty(launchFragmentTitle) ? getString(R.string.my_setting) : launchFragmentTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupFragment() {
        Fragment fragment;
        if (TextUtils.isEmpty(launchFragmentName)) {
            fragment = Fragment.instantiate(this, SettingFragment.class.getName());
        } else {
            fragment = Fragment.instantiate(this, launchFragmentName, launchFragmentArgs);
        }
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

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, int selectedColor) {

    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {

    }
}
