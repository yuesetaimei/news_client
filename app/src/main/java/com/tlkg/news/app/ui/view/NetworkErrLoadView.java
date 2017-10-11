package com.tlkg.news.app.ui.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.tlkg.news.app.R;

/**
 * Created by wuxiaoqi on 2017/10/10.
 * 网络状态出错显示View
 */

public class NetworkErrLoadView extends LinearLayout {
    private static final String TAG = "NetworkErrLoadView";

    public NetworkErrLoadView(Context context) {
        super(context);
        init();
    }

    public NetworkErrLoadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NetworkErrLoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NetworkErrLoadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(getContext()).inflate(R.layout.network_err_load_view, this, true);
    }
}
