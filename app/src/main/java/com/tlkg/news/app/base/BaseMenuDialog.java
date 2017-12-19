package com.tlkg.news.app.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.tlkg.news.app.R;

import butterknife.ButterKnife;

/**
 * Created by wuxiaoqi on 2017/12/15.
 */

public abstract class BaseMenuDialog extends Dialog {

    protected  Context mContext;

    public BaseMenuDialog(@NonNull Context context) {
        this(context, R.style.menu_dialog);
    }

    public BaseMenuDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        setContentView(getContentView());
        ButterKnife.inject(this, this);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    @LayoutRes
    public abstract int getContentView();

}
