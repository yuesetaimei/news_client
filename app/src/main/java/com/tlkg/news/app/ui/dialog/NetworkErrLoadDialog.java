package com.tlkg.news.app.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseDialog;
import com.tlkg.news.app.event.NetAgainLoadEvent;
import com.tlkg.news.app.util.PhoneUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/10/10.
 */

public class NetworkErrLoadDialog extends BaseDialog {

    private int mPosition = -1;

    @InjectView(R.id.network_err_load_img)
    ImageView errImg;

    @InjectView(R.id.network_err_load_tv)
    TextView errTv;

    public NetworkErrLoadDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.network_err_load_view);
//        setCancelable(false);
//        setCanceledOnTouchOutside(false);
        initView();
        initEvent();
    }

    private void initView() {
        if (PhoneUtil.isNetworkConnect(getContext())) {
            errImg.setImageResource(R.drawable.ic_sentiment_dissatisfied_white_48dp);
            errTv.setText(R.string.network_err);
        } else {
            errImg.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_white_48dp);
            errTv.setText(R.string.network_disable);
        }
    }

    private void initEvent() {
        errImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        errTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        if (PhoneUtil.isNetworkConnect(getContext())) {
            EventBus.getDefault().post(new NetAgainLoadEvent(mPosition));
        } else {
            //跳转到设置网络界面
            getContext().startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            return;
        }
        super.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            super.dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }
}
