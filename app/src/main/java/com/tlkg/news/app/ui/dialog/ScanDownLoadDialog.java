package com.tlkg.news.app.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseMenuDialog;
import com.tlkg.news.app.util.DensityUtil;
import com.tlkg.news.app.util.ZxQRUtil;

import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/12/19.
 * 扫码下载Dialog
 */

public class ScanDownLoadDialog extends BaseMenuDialog {

    private static final String TAG = "ScanDownLoadDialog";

    @InjectView(R.id.menu_scan_down_qr_img)
    ImageView qrImg;

    Bitmap qrBitmap = null;

    public ScanDownLoadDialog(@NonNull Context context) {
        super(context);
        initQR();
    }

    private void initQR() {
        final String text = getContext().getString(R.string.scan_down_load_url);
        final int width = DensityUtil.dip2px(200);
        if (qrBitmap == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    qrBitmap = ZxQRUtil.createQR(text, width, width);
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
