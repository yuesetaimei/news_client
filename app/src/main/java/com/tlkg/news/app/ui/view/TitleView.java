package com.tlkg.news.app.ui.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tlkg.news.app.R;

/**
 * Created by wuxiaoqi on 2018/1/17.
 */

public class TitleView extends LinearLayout {

    private static final String TAG = "TitleView";

    private Context mContext;

    private ImageButton leftBtn, rightBtn;

    private ITitleOnClicenListener listener;

    private TextView titleTv;

    public void setListener(ITitleOnClicenListener listener) {
        this.listener = listener;
    }

    public TitleView(Context context) {
        super(context);
        init(context);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setOrientation(HORIZONTAL);
        LayoutInflater.from(mContext).inflate(R.layout.layout_title, this);

        leftBtn = (ImageButton) findViewById(R.id.layout_title_left_btn);
        titleTv = (TextView) findViewById(R.id.layout_title_text_tv);
        rightBtn = (ImageButton) findViewById(R.id.layout_title_right_btn);
        leftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickLeft();
                }
            }
        });
        rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickRight();
                }
            }
        });
    }

    public View getRightBtn() {
        return rightBtn;
    }

    public View getLeftBtn() {
        return leftBtn;
    }

    public void setTitle(String title) {
        titleTv.setVisibility(View.VISIBLE);
        titleTv.setText(title);
        titleTv.setSelected(true);
    }

    public interface ITitleOnClicenListener {
        void onClickLeft();

        void onClickRight();
    }
}
