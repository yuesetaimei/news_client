package com.tlkg.news.app.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseActivity;
import com.tlkg.news.app.bean.MultiNewsArticleDataBean;
import com.tlkg.news.app.presenter.NewsVideoPresenter;
import com.tlkg.news.app.ui.view.MyJCVideoPlayerStandard;
import com.tlkg.news.app.util.CommonSettingUtil;
import com.tlkg.news.app.util.ImageLoader;
import com.tlkg.news.app.util.StatusBarFullUtil;

import butterknife.InjectView;
import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoContentActivity extends BaseActivity implements NewsVideoPresenter.INewsVideoView {

    private static final String TAG = "VideoContentActivity";

    @InjectView(R.id.jc_video)
    MyJCVideoPlayerStandard jcVideo;

    private String videoId;
    private int currentAction;

    public static void startActivity(MultiNewsArticleDataBean bean) {
        NewsClientApplication.getAppContext().startActivity(new Intent(
                NewsClientApplication.getAppContext(), VideoContentActivity.class
        ).putExtra(TAG, bean)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public int getContentView() {
        return R.layout.activity_video_content;
    }

    @Override
    public void initData(Bundle bundle) {
        try {
            MultiNewsArticleDataBean dataBean = bundle.getParcelable(TAG);
            if (dataBean != null && dataBean.getVideo_detail_info() != null) {
                if (null != dataBean.getVideo_detail_info().getDetail_video_large_image()) {
                    String image = dataBean.getVideo_detail_info().getDetail_video_large_image().getUrl();
                    if (!TextUtils.isEmpty(image)) {
                        ImageLoader.loadCenterCrop(this, image, jcVideo.thumbImageView, R.color.viewBackground, R.drawable.ic_error_outline_white_48dp);
                    }
                }
            }
            if (dataBean != null) {
                this.videoId = dataBean.getVideo_id();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initWindow() {
        StatusBarFullUtil.setScreenFull(this);
    }

    @Override
    protected void initViews() {
        NewsVideoPresenter presenter = new NewsVideoPresenter(this);
        presenter.load();
    }

    @Override
    public void initEvent() {
        MyJCVideoPlayerStandard.setOnClickFullScreenListener(new MyJCVideoPlayerStandard.onClickFullScreenListener() {
            @Override
            public void onClickFullScreen() {
                if (currentAction == JCUserAction.ON_ENTER_FULLSCREEN) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });
        jcVideo.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onLoadErr(String msg) {

    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public void onSetVidoeUrl(String url) {
        jcVideo.setUp(url, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL);
        if (CommonSettingUtil.getInstance().getWifiAutoPlayVidoe()) {
            jcVideo.startButton.performClick();
        }
        if (CommonSettingUtil.getInstance().getIsVideoForceLandscape()) {
            currentAction = JCUserAction.ON_ENTER_FULLSCREEN;
            jcVideo.fullscreenButton.performClick();
        }
        // 设置监听事件 判断是否进入全屏
        JCVideoPlayer.setJcUserAction(new JCUserAction() {
            @Override
            public void onEvent(int type, String url, int screen, Object... objects) {
//                if (type == JCUserActionStandard.ON_CLICK_START_THUMB ||
//                        type == JCUserAction.ON_CLICK_START_ICON ||
//                        type == JCUserAction.ON_CLICK_RESUME ||
//                        type == JCUserAction.ON_CLICK_START_AUTO_COMPLETE) {
//                }

//                if (type == JCUserAction.ON_CLICK_PAUSE || type == JCUserAction.ON_AUTO_COMPLETE) {
//                }
                if (type == JCUserAction.ON_ENTER_FULLSCREEN) {
                    currentAction = JCUserAction.ON_ENTER_FULLSCREEN;
                }
                if (type == JCUserAction.ON_QUIT_FULLSCREEN) {
                    currentAction = JCUserAction.ON_QUIT_FULLSCREEN;
                }
            }
        });
    }

    @Override
    public String getVideoid() {
        return videoId;
    }

    @Override
    public void onHideLoading() {

    }

    @Override
    public void onShowNetError() {

    }
}
