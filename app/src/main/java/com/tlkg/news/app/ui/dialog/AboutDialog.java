package com.tlkg.news.app.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseMenuDialog;
import com.tlkg.news.app.ui.view.PrinterTextView;
import com.tlkg.news.app.util.CommonSettingUtil;

import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/12/19.
 * 关于应用
 */

public class AboutDialog extends BaseMenuDialog {

//    private static final String TAG = "AboutDialog";

    @InjectView(R.id.menu_about_cardview)
    CardView cardView;

    @InjectView(R.id.menu_about_tv)
    PrinterTextView aboutTv;

    public AboutDialog(@NonNull Context context) {
        super(context);
        String aboutString = mContext.getString(R.string.about_app);
//        aboutTv.setText(mContext.getString(R.string.about_app));
        aboutTv.setPrintText(aboutString);
    }

    @Override
    public int getContentView() {
        return R.layout.menu_about;
    }

    @Override
    public void show() {
        super.show();
        cardView.setCardBackgroundColor(CommonSettingUtil.getInstance().getThemeColor());
        aboutTv.startPrint();
    }

    @Override
    public void dismiss() {
        aboutTv.stopPrint();
        super.dismiss();
    }
}
