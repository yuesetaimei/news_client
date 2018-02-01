package com.tlkg.news.app.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseSlidrActivity;
import com.tlkg.news.app.ui.view.TitleView;

import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wuxiaoqi on 2017/12/26.
 */

public class RegisterOrLoginActivity extends BaseSlidrActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    private static final String TAG = "RegisterOrLoginActivity";

    @InjectView(R.id.activity_register_or_login_titleview)
    TitleView titleView;

    @InjectView(R.id.activity_register_or_login_head_iv)
    CircleImageView headCircleImageView;

    @InjectView(R.id.username_login_et)
    EditText userNameEt;

    @InjectView(R.id.pwd_login_et)
    EditText userPwdEt;

    @InjectView(R.id.submit_login_btn)
    Button submitBtn;

    public static void startActivity(Activity activity) {
        Intent i = new Intent(activity, RegisterOrLoginActivity.class);
        activity.startActivity(i);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_register_or_login;
    }

    @Override
    protected void initViews() {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        titleView.setTitle(getString(R.string.login));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    @Override
    public void initEvent() {
        titleView.setListener(new TitleView.ITitleOnClicenListener() {
            @Override
            public void onClickLieft() {
                finish();
            }

            @Override
            public void onClickRight() {

            }
        });
    }

    @Override
    public void onGlobalLayout() {
        int screenHeight = headCircleImageView.getRootView().getHeight();//获取根布局高
        Rect keyRect = new Rect();
        headCircleImageView.getWindowVisibleDisplayFrame(keyRect);//获取当前窗口可视区域大小
        int keyShowHeight = screenHeight - keyRect.bottom;
        if (keyShowHeight > 0) {
            animShowOrHideHeadImg(false);
            Log.i("wxq", "显示");
        } else {
            animShowOrHideHeadImg(true);
            Log.i("wxq", "隐藏");
        }
    }

    private int headImgHeight = 0;
    private int headImgWidth = 0;

    private void animShowOrHideHeadImg(boolean show) {
        if (show) {//显示
            if (headCircleImageView.getTag() == null) {
                return;
            }
            final int height = headImgHeight;
            final int width = headImgWidth;
            headCircleImageView.setTag(null);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    ViewGroup.LayoutParams lp = headCircleImageView.getLayoutParams();
                    lp.height = (int) (height * value);
                    lp.width = (int) (width * value);
                    headCircleImageView.requestLayout();
                    headCircleImageView.setAlpha(value);
                }
            });
            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();
        } else {//隐藏
            if (headCircleImageView.getTag() != null) {
                return;
            }
            final int height = headCircleImageView.getHeight();
            final int width = headCircleImageView.getWidth();
            headImgHeight = height;
            headImgWidth = width;
            headCircleImageView.setTag(true);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    ViewGroup.LayoutParams lp = headCircleImageView.getLayoutParams();
                    lp.height = (int) (height * value);
                    lp.width = (int) (width * value);
                    headCircleImageView.requestLayout();
                    headCircleImageView.setAlpha(value);
                }
            });
            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();
        }
    }
}
