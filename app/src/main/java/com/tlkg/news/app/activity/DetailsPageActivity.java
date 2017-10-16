package com.tlkg.news.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.adapter.DetailAdapter;
import com.tlkg.news.app.adapter.MovieRecyclerAdapter;
import com.tlkg.news.app.base.BaseActivity;
import com.tlkg.news.app.base.BaseEvent;
import com.tlkg.news.app.bean.HotMovieBean;
import com.tlkg.news.app.bean.MovieDetailBean;
import com.tlkg.news.app.bean.PersonBean;
import com.tlkg.news.app.event.DetailPersonClienEvent;
import com.tlkg.news.app.presenter.DetailsPresenter;
import com.tlkg.news.app.util.PhoneUtil;
import com.tlkg.news.app.view.statusbar.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import jp.wasabeef.glide.transformations.BlurTransformation;


public class DetailsPageActivity extends BaseActivity implements DetailsPresenter.IDetailsView {

    private static final String TAG = "DetailsPageActivity";

    public static final String DATA_KEY = "data_key";

    @InjectView(R.id.activity_details_page_appbar_layout)
    AppBarLayout appBarLayout;

    @InjectView(R.id.activity_details_page_toolbar)
    Toolbar toolbar;

    @InjectView(R.id.activity_details_head_ll)
    LinearLayout headLl;

    @InjectView(R.id.activity_details_head_image)
    ImageView headImageView;

    @InjectView(R.id.activity_details_head_photo_img)
    ImageView headPhotoImg;

    @InjectView(R.id.activity_details_director_tv)
    TextView headDirectorTv;

    @InjectView(R.id.activity_details_casts_tv)
    TextView headCastsTv;

    @InjectView(R.id.activity_details_genres_tv)
    TextView headGenresTv;

    @InjectView(R.id.activity_details_rating_rate_tv)
    TextView headRatingTv;

    @InjectView(R.id.activity_details_rating_number)
    TextView headNumberTv;

    @InjectView(R.id.activity_details_one_day_tv)
    TextView headOneDayTv;

    @InjectView(R.id.activity_details_city_tv)
    TextView headOneCityTv;

    @InjectView(R.id.activity_details_other_title_tv)
    TextView otherNameTv;

    @InjectView(R.id.activity_details_summary_tv)
    TextView summaryTv;

    @InjectView(R.id.activity_details_cardview)
    CardView cardView;

    @InjectView(R.id.activity_details_progress)
    ProgressBar progressBar;

    @InjectView(R.id.activity_details_recyclerview)
    RecyclerView recyclerView;

    @InjectView(R.id.activity_details_page_float_btn)
    FloatingActionButton floatingActionButton;

    private HotMovieBean.SubjectsBean mBean;

    private DetailsPresenter mPresenter;

    public static void startActivity(Activity activity, HotMovieBean.SubjectsBean bean) {
        if (!PhoneUtil.isNetworkConnect(activity)) {
            Toast.makeText(NewsClientApplication.getAppContext(), R.string.checkout_network, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(activity, DetailsPageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA_KEY, bean);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    public void initData(Bundle bundle) {
        if (bundle != null) mBean = (HotMovieBean.SubjectsBean) bundle.getSerializable(DATA_KEY);
        if (mBean == null) finish();
    }

    @Override
    protected void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.setTranslucent(this, 0);
        } else {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_details_page;
    }

    @Override
    protected void initViews() {
        mPresenter = new DetailsPresenter(this);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(mBean.getTitle());
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // 高斯模糊背景
        Glide.with(this).load(mBean.getImages().getLarge())
                .bitmapTransform(new BlurTransformation(this, 23, 4)).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                headImageView.setBackgroundColor(Color.TRANSPARENT);
//                headImageView.setImageAlpha(0);
                headImageView.setVisibility(View.VISIBLE);
                return false;
            }
        }).into(headImageView);
        Glide.with(this)
                .load(mBean.getImages().getLarge())
                .placeholder(R.drawable.ic_movie_white_24dp)
                .error(R.drawable.ic_movie_white_24dp)
                .crossFade()
                .into(headPhotoImg);
        headDirectorTv.setText(MovieRecyclerAdapter.getDirectorsString(mBean));
        headCastsTv.setText(MovieRecyclerAdapter.getCastsString(mBean));
        headGenresTv.setText(NewsClientApplication.getStringId(R.string.movie_type) + MovieRecyclerAdapter.getGenersString(mBean));
        headRatingTv.setText(NewsClientApplication.getStringId(R.string.movie_score) + mBean.getRating().getAverage());
        headNumberTv.setText(mBean.getCollect_count() + getString(R.string.person_score));
        mPresenter.load();
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
    public void initEvent() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Toast.makeText(DetailsPageActivity.this, R.string.movie_share, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLoadErr(String msg) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadStart() {
        if (progressBar.getVisibility() != View.VISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    while (i <= 900 && progressBar.getVisibility() == View.VISIBLE) {
                        i = progressBar.getProgress();
                        i += 15;
                        progressBar.setProgress(i);
                        SystemClock.sleep(20);
                    }
                }
            }).start();
        }
    }

    @Override
    public void onLoadComplete(MovieDetailBean data) {
        if(isFinishing()) return;
        progressBar.setProgress(1000);
        progressBar.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);
        headOneDayTv.setText(String.format(getString(R.string.movie_day), data.getYear()));
        headOneCityTv.setText(String.format(getString(R.string.movie_city), getFormatString(data.getCountries())));
        otherNameTv.setText(getFormatString(data.getAka()));
        summaryTv.setText(data.getSummary());
        transformData(data);
    }

    /**
     * 异步线程转换数据
     */
    private void transformData(final MovieDetailBean movieDetailBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<PersonBean> personBeanList = new ArrayList<PersonBean>();
                PersonBean bean;
                for (int i = 0; i < movieDetailBean.getDirectors().size(); i++) {
                    try {
                        bean = new PersonBean(movieDetailBean.getDirectors().get(i).getName(),
                                movieDetailBean.getDirectors().get(i).getAvatars().getLarge(),
                                movieDetailBean.getDirectors().get(i).getAlt());
                        personBeanList.add(bean);
                    } catch (Exception e) {
                    }
                }
                for (int i = 0; i < movieDetailBean.getCasts().size(); i++) {
                    try {
                        bean = new PersonBean(movieDetailBean.getCasts().get(i).getName(),
                                movieDetailBean.getCasts().get(i).getAvatars().getLarge(),
                                movieDetailBean.getCasts().get(i).getAlt());
                        personBeanList.add(bean);
                    } catch (Exception e) {
                    }
                }

                DetailsPageActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter(personBeanList);
                    }
                });
            }
        }).start();
    }

    private void setAdapter(List<PersonBean> data) {
        LinearLayoutManager ll = new LinearLayoutManager(this);
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(ll);

        DetailAdapter adapter = new DetailAdapter();
        recyclerView.setAdapter(adapter);
        adapter.refreshAddData(data);
    }

    @NonNull
    private String getFormatString(List<String> countries) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < countries.size(); i++) {
            if (i < countries.size() - 1) {
                stringBuilder.append(countries.get(i)).append(" / ");
            } else {
                stringBuilder.append(countries.get(i));
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String getMovieId() {
        if (mBean == null) return "";
        return mBean.getId();
    }

    @Subscribe
    public void onEventBus(BaseEvent event) {
        if (event instanceof DetailPersonClienEvent) {
            DetailPersonClienEvent detailPersonClienEvent = (DetailPersonClienEvent) event;
            WebViewActivity.startActivity(this, detailPersonClienEvent.mLoadUrl, detailPersonClienEvent.mTitle);
        }
    }
}
