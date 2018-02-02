package com.tlkg.news.app.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tlkg.news.app.R;
import com.tlkg.news.app.adapter.BasePagerAdapter;
import com.tlkg.news.app.base.BaseEvent;
import com.tlkg.news.app.base.BaseFragment;
import com.tlkg.news.app.bean.NewsTableBean;
import com.tlkg.news.app.db.NewsTableDao;
import com.tlkg.news.app.event.ShowConfigTabEvent;
import com.tlkg.news.app.event.TabShowRefreshEvent;
import com.tlkg.news.app.ui.view.ChoiceScrollViewPager;
import com.tlkg.news.app.util.CommonSettingUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/9/21.
 * 推荐Fragment
 */

public class RecommentFragment extends BaseFragment {

    private static final String TAG = "RecommentFragment";

    @InjectView(R.id.fragment_recomment_refreshLayout)
    RelativeLayout rootLl;

    @InjectView(R.id.fragment_recomment_headtable_ll)
    LinearLayout headTableLl;

    @InjectView(R.id.fragment_recomment_tablayout)
    TabLayout tabLayout;

    @InjectView(R.id.fragment_recomment_add_iv)
    ImageView addIv;

    @InjectView(R.id.fragment_recomment_viewpager)
    ChoiceScrollViewPager viewPager;

    private List<Fragment> fragments;
    private List<String> titleList;

    private BasePagerAdapter mFragmentAdapter;

    private NewsTableDao dao = new NewsTableDao();

    private Map<String, Fragment> map = new HashMap<>();

    public static Fragment getInstance() {
        return new RecommentFragment();
    }

    @Override
    protected void initData(Bundle bundle) {
        initTabs();
    }

    private void initTabs() {
        fragments = new ArrayList<>();
        titleList = new ArrayList<>();
        List<NewsTableBean> newsTableBeanList = dao.query(1);
        if (newsTableBeanList.size() == 0) {
            dao.initNewsData();
            newsTableBeanList = dao.query(1);
        }

        for (NewsTableBean bean : newsTableBeanList) {
            BaseFragment fragment = null;
            String _id = bean._id;
            switch (_id) {
                case "essay_joke":
                    if (map.containsKey(_id)) {
                        fragments.add(map.get(_id));
                    } else {
                        fragment = JokeFragment.getInstance();
                        fragments.add(fragment);
                    }
                    break;
                case "question_and_answer"://问答
                    if (map.containsKey(_id)) {
                        fragments.add(map.get(_id));
                    } else {
                        fragment = QuestionAnswerFragment.getInstance();
                        fragments.add(fragment);
                    }
                    break;
                default:
                    if (map.containsKey(_id)) {
                        fragments.add(map.get(_id));
                    } else {
                        fragment = NewsArticleFragment.getInstance(_id);
                        fragments.add(fragment);
                    }
                    break;
            }
            titleList.add(bean.name);
            if (fragment != null) {
                map.put(_id, fragment);
            }
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_recomment;
    }

    @Override
    public void initView(View view) {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        headTableLl.setBackgroundColor(CommonSettingUtil.getInstance().getThemeColor());
        mFragmentAdapter = new BasePagerAdapter(getChildFragmentManager(), fragments, titleList);
        viewPager.setAdapter(mFragmentAdapter);
        viewPager.setOffscreenPageLimit(15);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void initEvent() {
        addIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ShowConfigTabEvent());
            }
        });
    }

    @Subscribe
    public void onEvent(BaseEvent event) {
        if (event instanceof TabShowRefreshEvent) {
            initTabs();
            mFragmentAdapter.recreateItems(fragments, titleList);
        }
    }
}
