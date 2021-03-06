package com.tlkg.news.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.color.CircleView;
import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.adapter.DoubanTopRecyclerAdapter;
import com.tlkg.news.app.base.BaseEvent;
import com.tlkg.news.app.base.BaseRecyclerAdapter;
import com.tlkg.news.app.base.BaseSlidrActivity;
import com.tlkg.news.app.bean.HotMovieBean;
import com.tlkg.news.app.event.MovieTopItemClickEvent;
import com.tlkg.news.app.presenter.DoubanTopPresenter;
import com.tlkg.news.app.ui.view.ChoiceSwipeRefreshLayout;
import com.tlkg.news.app.util.PhoneUtil;
import com.tlkg.news.app.view.statusbar.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;

/**
 * 豆瓣Top界面
 */
public class DoubanTopActivity extends BaseSlidrActivity implements DoubanTopPresenter.IDoubanTopView {

//    private static final String TAG = "DoubanTopActivity";

    @InjectView(R.id.activity_douban_top_toolbar)
    Toolbar toolbar;

    @InjectView(R.id.activity_douban_top_title_tv)
    TextView titleTv;

    @InjectView(R.id.activity_douban_top_refreshLayout)
    ChoiceSwipeRefreshLayout mRefreshLayout;

    @InjectView(R.id.activity_douban_top_recyclerview)
    RecyclerView mRecyclerView;

    private DoubanTopPresenter mPresenter;

    private DoubanTopRecyclerAdapter mAdapter;

    private int count = -1;

    public static void startActivity(Activity activity) {
        if (!PhoneUtil.isNetworkConnect(activity)) {
            Toast.makeText(NewsClientApplication.getAppContext(), R.string.checkout_network, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i = new Intent(activity, DoubanTopActivity.class);
        activity.startActivity(i);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    protected void initWindow() {
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void initViews() {
        mPresenter = new DoubanTopPresenter(this);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleTv.setText(getString(R.string.doubao_top));
        mRefreshLayout.init();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new DoubanTopRecyclerAdapter();
        mAdapter.setEnableLoadMore(true);
        mAdapter.setLoadMoreListener(new BaseRecyclerAdapter.ILoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mAdapter.getData().size() < count) {
                    mPresenter.loadMore(mAdapter.getData().size(), 0);
                } else {
                    mAdapter.setEnableLoadMore(false);
                    Toast.makeText(DoubanTopActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.load();
            }
        });
        mPresenter.load();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_douban_top;
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

    @Override
    public void onLoadErr(String msg) {
        mRefreshLayout.setRefreshing(false);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadStart() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onLoadComplete(HotMovieBean data) {
        if (isFinishing()) return;
        count = data.getTotal();
        mAdapter.refreshData(data.getSubjects());
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreComplete(HotMovieBean data) {
        if (isFinishing()) return;
        count = data.getTotal();
        mAdapter.refreshAddData(data.getSubjects());
        mRefreshLayout.setRefreshing(false);
    }

    @Subscribe
    public void onEventBus(BaseEvent event) {
        if (event instanceof MovieTopItemClickEvent) {
            MovieTopItemClickEvent movieTopItemClickEvent = (MovieTopItemClickEvent) event;
            DetailsPageActivity.startActivity(this, movieTopItemClickEvent.mBean);
        }
    }

    @Override
    protected void setThemeColor(int color) {
        mRefreshLayout.setColorSchemeColors(color);
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(CircleView.shiftColorDown(color));
    }
}
