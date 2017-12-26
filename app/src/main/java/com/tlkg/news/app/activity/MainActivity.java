package com.tlkg.news.app.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.EventLog;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseActivity;
import com.tlkg.news.app.base.BaseEvent;
import com.tlkg.news.app.event.AnimTranEvent;
import com.tlkg.news.app.event.NetworkErrEvent;
import com.tlkg.news.app.event.ShowConfigTabEvent;
import com.tlkg.news.app.event.ShowMyFragmentCircleEvent;
import com.tlkg.news.app.fragment.MovieFragment;
import com.tlkg.news.app.fragment.MyFragment;
import com.tlkg.news.app.fragment.RecommentFragment;
import com.tlkg.news.app.fragment.WalfareFragment;
import com.tlkg.news.app.ui.dialog.AboutDialog;
import com.tlkg.news.app.ui.dialog.NetworkErrLoadDialog;
import com.tlkg.news.app.ui.dialog.ProjectAddressDialog;
import com.tlkg.news.app.ui.dialog.ScanDownLoadDialog;
import com.tlkg.news.app.ui.view.ConfigShowTabPopWindow;
import com.tlkg.news.app.util.DensityUtil;
import com.tlkg.news.app.util.PhoneUtil;
import com.tlkg.news.app.view.statusbar.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
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

    @InjectView(R.id.activity_main_parent_rl)
    RelativeLayout parentRl;

    @InjectView(R.id.activity_main_root_fl)
    FrameLayout rootView;

    @InjectView(R.id.activity_main_bottomBar)
    BottomBar bottomBar;

    private long onBackTime = 0;

    private NetworkErrLoadDialog netErrDialg;

    private ConfigShowTabPopWindow configShowTabPopWindow;

    private CircleImageView myCircleImageView = null;

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
                        hideCircleView();
                        setFragment(FRAGMENT_ONE);
                        break;
                    case R.id.tab_welfare://福利
                        hideCircleView();
                        setFragment(FRAGMENT_TWO);
                        break;
                    case R.id.tab_literature://电影
                        hideCircleView();
                        setFragment(FRAGMENT_THREE);
                        break;
                    case R.id.tab_watercress://我的
                        showCircleView();
                        setFragment(FRAGMENT_FOUR);
                        break;
                    default:
                        break;
                }
                appBarLayout.setExpanded(true, false);
            }
        });
        toolBarHeadImg.post(new Runnable() {
            @Override
            public void run() {
                myCircleImageView = addMirrorView(parentRl, toolBarHeadImg);
                toolBarHeadImg.setVisibility(View.GONE);
            }
        });
    }

    private void hideCircleView() {
        if (myCircleImageView != null && myCircleImageView.getVisibility() == View.VISIBLE) {
            myCircleImageView.setVisibility(View.GONE);
        }
        if (toolBarHeadImg.getVisibility() != View.VISIBLE) {
            toolBarHeadImg.setVisibility(View.VISIBLE);
        }
    }

    private void showCircleView() {
        if (myCircleImageView != null && myCircleImageView.getVisibility() != View.VISIBLE) {
            myCircleImageView.setVisibility(View.VISIBLE);
        }
        if (toolBarHeadImg.getVisibility() == View.VISIBLE) {
            toolBarHeadImg.setVisibility(View.GONE);
        }
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

    private ProjectAddressDialog projectAddressDialog = null;

    private ScanDownLoadDialog scanDownLoadDialog = null;

    private AboutDialog aboutDialog = null;

    private void onClickLeftMenu(final MenuItem item) {
        drawerLayout.closeDrawer(Gravity.START);
        drawerLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (item.getItemId()) {
                    case R.id.project://项目地址
                        showProjectAddressDialog();
                        break;
                    case R.id.down://扫码下载
                        showScanDownLoadDialog();
                        break;
                    case R.id.share://分享好友
                        share();
                        break;
                    case R.id.guanyu://关于应用
                        showAboutDialog();
                        break;
                    case R.id.exit://退出应用
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    default:
                        break;
                }
            }
        }, 290);
    }

    private void showAboutDialog() {
        if (aboutDialog == null) {
            aboutDialog = new AboutDialog(this);
        }
        aboutDialog.show();
    }

    private void share() {
        Intent shareIntent = new Intent()
                .setAction(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_url));
        startActivity(Intent.createChooser(shareIntent, getString(R.string.action_share)));
    }

    private void showScanDownLoadDialog() {
        if (scanDownLoadDialog == null) {
            scanDownLoadDialog = new ScanDownLoadDialog(this);
        }
        scanDownLoadDialog.show();
    }

    private void showProjectAddressDialog() {
        if (projectAddressDialog == null) {
            projectAddressDialog = new ProjectAddressDialog(this);
        }
        projectAddressDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (configShowTabPopWindow != null && configShowTabPopWindow.isShowing()) {
            configShowTabPopWindow.dismiss();
            return;
        }
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
                fragment = WalfareFragment.getInstance();
                break;
            case FRAGMENT_THREE:
                fragment = MovieFragment.getInstance();
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
            NetworkErrEvent networkErrEvent = (NetworkErrEvent) event;
            showNetErrDialog();
            netErrDialg.setPosition(networkErrEvent.mPosition);
        } else if (event instanceof ShowConfigTabEvent) {
            if (configShowTabPopWindow == null)
                configShowTabPopWindow = new ConfigShowTabPopWindow(this);
            configShowTabPopWindow.show(drawerLayout);
        } else if (event instanceof AnimTranEvent) {
            AnimTranEvent animTranEvent = (AnimTranEvent) event;
            int x = animTranEvent.x + DensityUtil.dip2px(70) / 2;
            int y = animTranEvent.y + DensityUtil.dip2px(70) / 2;
            if (myCircleImageView != null) {
                PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("translationX", 0f, x - mirrorX);
                PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY", 0f, y - mirrorY);
                PropertyValuesHolder pvhSx = PropertyValuesHolder.ofFloat("scaleX", 0, 2.5f);
                PropertyValuesHolder pvhSy = PropertyValuesHolder.ofFloat("scaleY", 0, 2.5f);
//                myCircleImageView.animate().translationXBy(x - mirrorX).translationYBy(y - mirrorY).setDuration(1000).start();
                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(myCircleImageView, pvhX, pvhY, pvhSx, pvhSy).setDuration(1000);
                objectAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (myCircleImageView != null) {
                            myCircleImageView.setVisibility(View.GONE);
                        }
                        EventBus.getDefault().post(new ShowMyFragmentCircleEvent());
                    }
                });
                objectAnimator.start();
            }
        }
    }

    /**
     * 显示网络差dialog
     */
    private void showNetErrDialog() {
        if (netErrDialg == null) netErrDialg = new NetworkErrLoadDialog(this);
        if (!netErrDialg.isShowing()) netErrDialg.show();
    }

    /**
     * 隐藏网络差dialog
     */
    private void dismissNetErrDialog() {
        if (netErrDialg == null) return;
        if (netErrDialg.isShowing()) netErrDialg.dismiss();
    }

    private int mirrorX, mirrorY;

    private CircleImageView addMirrorView(ViewGroup parent, View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        final CircleImageView mirrorView = new CircleImageView(this);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
//        mirrorView.setImageBitmap(bitmap);
        mirrorView.setImageResource(R.drawable.iv_menu_head);
        view.setDrawingCacheEnabled(false);
        int[] locations = new int[2];
        view.getLocationOnScreen(locations);
        Log.i("wxq", "width:" + bitmap.getWidth());
        Log.i("wxq", "height:" + bitmap.getHeight());
        mirrorX = locations[0] + bitmap.getWidth() / 2;
        mirrorY = locations[1] + bitmap.getHeight() / 2;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        params.setMargins(locations[0], locations[1], 0, 0);
        parent.addView(mirrorView, params);
        return mirrorView;
    }
}
