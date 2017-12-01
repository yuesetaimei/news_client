package com.tlkg.news.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseSlidrActivity;
import com.tlkg.news.app.bean.JokeContentBean;
import com.tlkg.news.app.util.ImageLoader;
import com.tlkg.news.app.util.PhoneUtil;

import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 段子内容界面,带评论
 */
public class JokeContentActivity extends BaseSlidrActivity {

    private static final String TAG = "JokeContentActivity";

    public static final String DATA_KEY = "data_key";

    @InjectView(R.id.activity_joke_content_toolbar)
    Toolbar toolbar;

    @InjectView(R.id.activity_joke_content_iv_avatar)
    CircleImageView circleImageView;

    @InjectView(R.id.activity_joke_content_tv_username)
    TextView userNameTv;

    @InjectView(R.id.activity_joke_content_text_tv)
    TextView contentTv;

    private JokeContentBean.DataBean.GroupBean showData = null;

    public static void startActivity(Context activity, JokeContentBean.DataBean.GroupBean bean) {
        if (!PhoneUtil.isNetworkConnect(activity)) {
            Toast.makeText(NewsClientApplication.getAppContext(), R.string.checkout_network, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(activity, JokeContentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DATA_KEY, bean);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_joke_content;
    }

    @Override
    public void initData(Bundle bundle) {
        if (bundle == null) return;
        showData = bundle.getParcelable(DATA_KEY);
    }

    @Override
    protected void initViews() {
        initToolbar();
        ImageLoader.loadCenterCrop(this, showData.getUser().getAvatar_url(), circleImageView, R.color.viewBackground);
        userNameTv.setText(showData.getUser().getName());
        contentTv.setText(showData.getText());
    }

    private void initToolbar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
