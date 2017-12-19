package com.tlkg.news.app.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import com.tlkg.news.app.R;

import butterknife.ButterKnife;

/**
 * Created by wuxiaoqi on 2017/10/10.
 */

public class BaseDialog extends Dialog {
    public BaseDialog(@NonNull Context context) {
        this(context, R.style.dialog);
    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        ButterKnife.inject(this,this);
    }
}
