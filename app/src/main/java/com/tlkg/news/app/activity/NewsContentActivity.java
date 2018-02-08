package com.tlkg.news.app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseSlidrActivity;
import com.tlkg.news.app.bean.MultiNewsArticleDataBean;
import com.tlkg.news.app.http.UrlConstant;
import com.tlkg.news.app.presenter.NewsContentPresenter;
import com.tlkg.news.app.ui.view.TitleView;
import com.tlkg.news.app.util.CommonSettingUtil;

import butterknife.InjectView;

public class NewsContentActivity extends BaseSlidrActivity implements NewsContentPresenter.INewsContentView {

    private static final String TAG = "NewsContentActivity";
    private static final String IMG = "img";

    @InjectView(R.id.title_view)
    TitleView titleView;

    @InjectView(R.id.webview)
    WebView mWebView;

    @InjectView(R.id.pb_progress)
    ContentLoadingProgressBar pb_progress;

    private MultiNewsArticleDataBean bean;
    private String imgUrl;
    private NewsContentPresenter presenter;


    public static void startActivity(MultiNewsArticleDataBean bean) {
        NewsClientApplication.getAppContext().startActivity(new Intent(NewsClientApplication.getAppContext(), NewsContentActivity.class)
                .putExtra(TAG, bean)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public static void startActivity(MultiNewsArticleDataBean bean, String imgUrl) {
        NewsClientApplication.getAppContext().startActivity(new Intent(NewsClientApplication.getAppContext(), NewsContentActivity.class)
                .putExtra(TAG, bean)
                .putExtra(IMG, imgUrl)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public int getContentView() {
        return R.layout.activity_news_content;
    }

    @Override
    public void initData(Bundle bundle) {
        try {
            bean = bundle.getParcelable(TAG);
            imgUrl = bundle.getString(IMG);
        } catch (Exception e) {
        }
        presenter = new NewsContentPresenter(this, bean);
    }

    @Override
    public void initEvent() {
        if (bean != null && !TextUtils.isEmpty(bean.getTitle())) {
            titleView.setTitle(bean.getTitle());
        }
        titleView.setListener(new TitleView.ITitleOnClicenListener() {
            @Override
            public void onClickLeft() {
                finish();
            }

            @Override
            public void onClickRight() {

            }
        });
    }

    @Override
    protected void initViews() {
        initWebClient();
        presenter.load();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebClient() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        settings.setBuiltInZoomControls(false);
//        settings.setSupportZoom(false);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(true);
        // 缓存
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启DOM storage API功能
        settings.setDomStorageEnabled(true);
        // 开启application Cache功能
        settings.setAppCacheEnabled(true);
        // 判断是否为无图模式
        settings.setBlockNetworkImage(CommonSettingUtil.getInstance().getIsNoPhotoMode());
        // 不调用第三方浏览器即可进行页面反应
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!TextUtils.isEmpty(url)) {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                onHideLoading();
                super.onPageFinished(view, url);
            }
        });

        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return false;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 90) {
                    onHideLoading();
                } else {
                    onShowLoading();
                }
            }
        });
    }

    private void onShowLoading() {
        pb_progress.show();
    }

    @Override
    public void onSetWebView(String url, boolean flag) {
        // 是否为头条的网站
        if (flag) {
            mWebView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        } else {
            /*
               ScrollView 嵌套 WebView, 导致部分网页无法正常加载
               如:https://temai.snssdk.com/article/feed/index/?id=11754971
               最佳做法是去掉 ScrollView, 或使用 NestedScrollWebView
             */
            String shareUrl = !TextUtils.isEmpty(bean.getShare_url()) ? bean.getShare_url() : bean.getDisplay_url();
            if (shareUrl.contains("temai.snssdk.com")) {
                mWebView.getSettings().setUserAgentString(UrlConstant.USER_AGENT_PC);
            }
            mWebView.loadUrl(shareUrl);
        }
    }

    @Override
    public void onHideLoading() {
        pb_progress.hide();
    }

    @Override
    public void onShowNetError() {

    }

    @Override
    public void onLoadErr(String msg) {

    }

    @Override
    public void onLoadStart() {

    }
}
