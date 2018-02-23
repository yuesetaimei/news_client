package com.tlkg.news.app.ui.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.widget.TextView;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseMenuDialog;
import com.tlkg.news.app.util.CommonSettingUtil;

import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/12/15.
 * 项目地址弹窗
 */
//https://github.com/wuxiaoqi123/news_client

public class ProjectAddressDialog extends BaseMenuDialog {

    @InjectView(R.id.menu_address_cardview)
    CardView cardView;

    @InjectView(R.id.menu_project_tv)
    TextView textTv;

    public ProjectAddressDialog(@NonNull Context context) {
        super(context);
        SpannableString spannableString = new SpannableString(getContext().getString(R.string.project_address_url));
        Linkify.addLinks(spannableString, Linkify.ALL);
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textTv.setText(spannableString);
        textTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void show() {
        super.show();
        cardView.setCardBackgroundColor(CommonSettingUtil.getInstance().getThemeColor());
    }

    @Override
    public int getContentView() {
        return R.layout.menu_project_address;
    }
}
