package com.tlkg.news.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseActivity;
import com.tlkg.news.app.base.BaseEvent;
import com.tlkg.news.app.event.NetworkErrEvent;
import com.tlkg.news.app.fragment.LiteratureFragment;
import com.tlkg.news.app.fragment.MyFragment;
import com.tlkg.news.app.fragment.RecommentFragment;
import com.tlkg.news.app.fragment.WelfareFragment;
import com.tlkg.news.app.ui.view.NetworkErrLoadDialog;
import com.tlkg.news.app.ui.view.NetworkErrLoadView;
import com.tlkg.news.app.view.statusbar.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.activity_main_drawerlayout)
    DrawerLayout drawerLayout;

    @InjectView(R.id.activity_main_status_bar_view)
    View statusBarView;

    @InjectView(R.id.activity_main_nav_view)
    NavigationView navigationView;

    @InjectView(R.id.activity_main_coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @InjectView(R.id.activity_main_appbar_layout)
    AppBarLayout appBarLayout;

    @InjectView(R.id.activity_main_toolbar)
    Toolbar toolbar;

    @InjectView(R.id.activity_main_title_menu)
    FrameLayout titleMenu;

    @InjectView(R.id.activity_main_title)
    TextView title;

    @InjectView(R.id.activity_main_tool_bar_head_iv)
    CircleImageView toolBarHeadImg;

    @InjectView(R.id.activity_main_root_fl)
    FrameLayout rootView;

    @InjectView(R.id.activity_main_bottomBar)
    BottomBar bottomBar;

    private long onBackTime = 0;

    private NetworkErrLoadDialog netErrDialg;

    public static void startActivity(Activity activity) {
        Intent i = new Intent(activity, MainActivity.class);
        activity.startActivity(i);
    }

    @Override
    protected void initWindow() {
        StatusBarUtil.setTranslucentForDrawerLayout(this, drawerLayout, 0);
        ViewGroup.LayoutParams layoutParams = statusBarView.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight(this);
        statusBarView.requestLayout();
    }

    @Override
    public void initData(Bundle bundle) {
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        initFragment(FRAGMENT_ONE);
    }

    @Override
    protected int getContentFragmentId() {
        return rootView.getId();
    }

    @Override
    public void initEvent() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
        }
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                toolBarHeadImg.clearAnimation();
                toolBarHeadImg.animate().alpha(1 - slideOffset).setDuration(0).start();
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        initLeftMenu();
        titleMenu.setOnClickListener(this);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_recommend://推荐
                        setFragment(FRAGMENT_ONE);
                        break;
                    case R.id.tab_welfare://福利
                        setFragment(FRAGMENT_TWO);
                        break;
                    case R.id.tab_literature://文学
                        setFragment(FRAGMENT_THREE);
                        break;
                    case R.id.tab_watercress://我的
                        setFragment(FRAGMENT_FOUR);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initLeftMenu() {
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                onClickLeftMenu(item);
                return true;
            }
        });
    }

    private void onClickLeftMenu(final MenuItem item) {
        drawerLayout.closeDrawer(Gravity.START);
        drawerLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (item.getItemId()) {
                    case R.id.exit:
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    default:
                        break;
                }
            }
        }, 290);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
            return;
        }
        if (System.currentTimeMillis() - onBackTime > 2000) {
            Toast.makeText(this, R.string.press_the_exit_again, Toast.LENGTH_SHORT).show();
            onBackTime = System.currentTimeMillis();
        } else finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_title_menu:
                drawerLayout.openDrawer(Gravity.START);
                break;
            default:
                break;
        }
    }

    @Override
    protected Fragment findFragmentByIndex(int index) {
        Fragment fragment = null;
        switch (index) {
            case FRAGMENT_ONE:
                fragment = RecommentFragment.getInstance();
                break;
            case FRAGMENT_TWO:
                fragment = WelfareFragment.getInstance();
                break;
            case FRAGMENT_THREE:
                fragment = LiteratureFragment.getInstance();
                break;
            case FRAGMENT_FOUR:
                fragment = MyFragment.getInstance();
                break;
            default:
                break;
        }
        return fragment;
    }


    @Subscribe
    public void onEventBus(BaseEvent event) {
        if (event instanceof NetworkErrEvent) {
            Log.i("wxq", "收到事件");
            NetworkErrEvent networkErrEvent = (NetworkErrEvent) event;
            showNetErrDialog();
            netErrDialg.setPosition(networkErrEvent.mPosition);
        }
    }

    /**
     * 显示网络差dialog
     */
    private void showNetErrDialog() {
        if (netErrDialg == null) netErrDialg = new NetworkErrLoadDialog(this);
        if (!netErrDialg.isShowing()) netErrDialg.show();
        Log.i("wxq", "showDialog");
    }

    /**
     * 隐藏网络差dialog
     */
    private void dismissNetErrDialog() {
        if (netErrDialg == null) return;
        if (netErrDialg.isShowing()) netErrDialg.dismiss();
    }
}
