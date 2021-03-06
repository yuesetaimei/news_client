package com.tlkg.news.app.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.tlkg.news.app.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


public class MyJCVideoPlayerStandard extends JCVideoPlayerStandard {

    public static onClickFullScreenListener onClickFullScreenListener;

    public MyJCVideoPlayerStandard(Context context) {
        super(context);
    }

    public MyJCVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static void setOnClickFullScreenListener(onClickFullScreenListener listener) {
        onClickFullScreenListener = listener;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.fullscreen) {
            if (onClickFullScreenListener != null) {
                onClickFullScreenListener.onClickFullScreen();
            }
        }
    }

    public interface onClickFullScreenListener {
        void onClickFullScreen();
    }
}
