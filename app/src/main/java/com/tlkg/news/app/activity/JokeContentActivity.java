package com.tlkg.news.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseSlidrActivity;
import com.tlkg.news.app.bean.JokeCommentBean;
import com.tlkg.news.app.bean.JokeContentBean;
import com.tlkg.news.app.bean.LoadingBean;
import com.tlkg.news.app.bean.LoadingEndBean;
import com.tlkg.news.app.binder.LoadingEndViewBinder;
import com.tlkg.news.app.binder.LoadingViewBinder;
import com.tlkg.news.app.binder.joke.JokeCommentViewBinder;
import com.tlkg.news.app.presenter.JokeContentPresenter;
import com.tlkg.news.app.util.ImageLoader;
import com.tlkg.news.app.util.OnLoadMoreListener;
import com.tlkg.news.app.util.PhoneUtil;

import java.util.List;

import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 段子内容界面,带评论
 */
public class JokeContentActivity extends BaseSlidrActivity implements JokeContentPresenter.IJokeContentView {

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

    @InjectView(R.id.activity_joke_content_float_btn)
    FloatingActionButton shardBtn;

    @InjectView(R.id.activity_joke_content_recyclerview)
    RecyclerView recyclerView;

    @InjectView(R.id.activity_joke_content_no_comment)
    ImageView noCommentImg;

    @InjectView(R.id.activity_joke_progressbar)
    ProgressBar progressBar;

    private JokeContentBean.DataBean.GroupBean showData = null;

    private JokeContentPresenter presenter;

    private MultiTypeAdapter mAdapter;

    private boolean canLoadMore = false;

    private Items oldItems = new Items();

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
        presenter = new JokeContentPresenter(this, showData.getId() + "", showData.getComment_count());
        initToolbar();
        ImageLoader.loadCenterCrop(this, showData.getUser().getAvatar_url(), circleImageView, R.color.viewBackground);
        userNameTv.setText(showData.getUser().getName());
        contentTv.setText(showData.getText());
        registerAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    canLoadMore = false;
                    presenter.loadMore(10, 0);
                }
            }
        });
        presenter.load();
    }

    private void registerAdapter() {
        mAdapter = new MultiTypeAdapter(oldItems = new Items());
        mAdapter.register(JokeCommentBean.DataBean.RecentCommentsBean.class, new JokeCommentViewBinder());
        mAdapter.register(LoadingBean.class, new LoadingViewBinder());
        mAdapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
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
        shardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, showData.getText());
                startActivity(Intent.createChooser(shareIntent, "分享"));
            }
        });
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
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        if (oldItems.size() == 0) {
            noCommentImg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadStart() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSetAdapter(List<JokeCommentBean.DataBean.RecentCommentsBean> list) {
        progressBar.setVisibility(View.GONE);
        Items newItems = new Items();
        newItems.addAll(list);
        newItems.add(new LoadingBean());
        oldItems.clear();
        oldItems.addAll(newItems);
        mAdapter.notifyDataSetChanged();
        if (oldItems.size() == 0) {
            noCommentImg.setVisibility(View.VISIBLE);
        } else {
            noCommentImg.setVisibility(View.GONE);
        }
        canLoadMore = true;
    }

    @Override
    public void noShowMore() {
        progressBar.setVisibility(View.GONE);
        if (oldItems.size() > 0) {
            Items newItems = new Items(oldItems);
            newItems.remove(newItems.size() - 1);
            newItems.add(new LoadingEndBean());
            mAdapter.setItems(newItems);
            mAdapter.notifyDataSetChanged();
        } else if (oldItems.size() == 0) {
            oldItems.add(new LoadingEndBean());
            mAdapter.setItems(oldItems);
            mAdapter.notifyDataSetChanged();
        }
        if (oldItems.size() == 0) {
            noCommentImg.setVisibility(View.VISIBLE);
        } else {
            noCommentImg.setVisibility(View.GONE);
        }
        canLoadMore = false;
    }
}
