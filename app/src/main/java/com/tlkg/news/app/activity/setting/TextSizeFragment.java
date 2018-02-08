package com.tlkg.news.app.activity.setting;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaygoo.widget.RangeSeekBar;
import com.tlkg.news.app.R;
import com.tlkg.news.app.event.TextSizeEvent;
import com.tlkg.news.app.util.CommonSettingUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;

/**
 * Created by wuxiaoqi on 2018/2/8.
 */

public class TextSizeFragment extends Fragment {

    private TextView textTv;

    private RangeSeekBar seekBar;

    private DecimalFormat df = new DecimalFormat("0");

    private int currentSize = -1;

    private int oldTextSize = CommonSettingUtil.getInstance().getTextSize();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_textsize, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        textTv = (TextView) view.findViewById(R.id.text);
        seekBar = (RangeSeekBar) view.findViewById(R.id.seekbar);

        textTv.setTextSize(CommonSettingUtil.getInstance().getTextSize());
        seekBar.setValue(CommonSettingUtil.getInstance().getTextSize() - 14);
        seekBar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                if (isFromUser) {
                    int size = Integer.parseInt(df.format(min));
                    if (currentSize != size) {
                        setText(size);
                        currentSize = size;
                    }
                }
            }
        });
    }

    private void setText(int size) {
        size += 14;
        textTv.setTextSize(size);
        CommonSettingUtil.getInstance().setTextSize(size);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (oldTextSize != currentSize)
            EventBus.getDefault().post(new TextSizeEvent(currentSize));
    }
}
