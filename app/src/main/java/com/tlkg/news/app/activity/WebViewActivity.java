package com.tlkg.news.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseActivity;
import com.tlkg.news.app.util.PhoneUtil;
import com.tlkg.news.app.view.statusbar.StatusBarUtil;

import butterknife.InjectView;


public class WebViewActivity extends BaseActivity {

    private static final String TAG = "WebViewActivity";

    public static final String KEY_TITLE = "key_title";

    public static final String KEY_URL = "key_url";

    @InjectView(R.id.activity_web_toolbar)
    Toolbar toolbar;

    @InjectView(R.id.activity_web_webview)
    WebView webView;

    @InjectView(R.id.activity_web_progressbar)
    ProgressBar progressBar;

    @InjectView(R.id.activity_web_fullview_fl)
    FrameLayout fullViewFl;

    private String mTitle;

    private String mLoadUrl;

    private boolean isFistLoad = true;

    public static void startActivity(Activity activity, String loadUrl) {
        startActivity(activity, loadUrl, "");
    }

    public static void startActivity(Activity activity, String loadUrl, String title) {
        if (!PhoneUtil.isNetworkConnect(activity)) {
            Toast.makeText(NewsClientApplication.getAppContext(), R.string.checkout_network, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(activity, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_URL, loadUrl);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }


    @Override
    public void initData(Bundle bundle) {
        if (bundle == null) {
            finish();
            return;
        }
        mTitle = bundle.getString(KEY_TITLE);
        mLoadUrl = bundle.getString(KEY_URL);
    }

    @Override
    protected void initWindow() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public int getContentView() {
        return R.layout.activity_web;
    }

    @Override
    protected void initViews() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(mTitle);
        initWebView();
        webView.loadUrl(mLoadUrl);
    }

    private void setTitle(String title) {
        if (TextUtils.isEmpty(title))
            toolbar.setTitle(getString(R.string.app_name));
        else
            toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
    }

    private void initWebView() {
        progressBar.setVisibility(View.VISIBLE);
        WebSettings ws = webView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 缩放比例 1
        webView.setInitialScale(1);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        ws.setSupportMultipleWindows(true);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                isFistLoad = false;
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (isFistLoad) {
                    if (progressBar.getVisibility() != View.VISIBLE)
                        progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
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

    }
}
