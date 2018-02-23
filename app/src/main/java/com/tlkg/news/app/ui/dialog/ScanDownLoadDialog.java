package com.tlkg.news.app.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.widget.ImageView;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseMenuDialog;
import com.tlkg.news.app.util.CommonSettingUtil;
import com.tlkg.news.app.util.DensityUtil;
import com.tlkg.news.app.util.ZxQRUtil;

import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/12/19.
 * 扫码下载Dialog
 */

public class ScanDownLoadDialog extends BaseMenuDialog {

//    private static final String TAG = "ScanDownLoadDialog";

    @InjectView(R.id.menu_scan_cardview)
    CardView cardView;

    @InjectView(R.id.menu_scan_down_qr_img)
    ImageView qrImg;

    private Bitmap qrBitmap = null;

    public ScanDownLoadDialog(@NonNull Context context) {
        super(context);
        initQR();
    }

    @Override
    public void show() {
        super.show();
        cardView.setCardBackgroundColor(CommonSettingUtil.getInstance().getThemeColor());
    }

    private void initQR() {
        final String text = getContext().getString(R.string.scan_down_load_url);
        if (qrBitmap == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    qrBitmap = ZxQRUtil.createQR(text, DensityUtil.dip2px(200), DensityUtil.dip2px(200));
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (qrBitmap != null) {
                                qrImg.setImageBitmap(qrBitmap);
                            }
                        }
                    });
                }
            }).start();
        }
    }

    @Override
    public int getContentView() {
        return R.layout.menu_scan_down;
    }
}
