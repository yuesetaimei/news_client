package com.tlkg.news.app.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wuxiaoqi on 2017/12/19.
 */

@SuppressLint("AppCompatCustomView")
public class PrinterTextView extends TextView {

    private static final String TAG = "PrinterTextView";

    private final String DEFAULT_INTERVAL_CHAR = "_";

    private final int DEFAULT_TIME_DELAY = 80;

    private Timer mTimer;

    private String mPrintStr;

    private int intervalTime = DEFAULT_TIME_DELAY;

    private String intervalChar = DEFAULT_INTERVAL_CHAR;

    private int printProgress = 0;

    public PrinterTextView(Context context) {
        super(context);
    }

    public PrinterTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PrinterTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPrintText(String str) {
        setPrintText(str, DEFAULT_TIME_DELAY);
    }

    public void setPrintText(String str, int time) {
        setPrintText(str, time, DEFAULT_INTERVAL_CHAR);
    }

    public void setPrintText(String str, int time, String intervalChar) {
        if (TextUtils.isEmpty(str) || time == 0 || TextUtils.isEmpty(intervalChar)) {
            return;
        }
        this.mPrintStr = str;
        this.intervalTime = time;
        this.intervalChar = intervalChar;
    }

    public void startPrint() {
        if (TextUtils.isEmpty(mPrintStr)) {
            if (!TextUtils.isEmpty(getText().toString())) {
                this.mPrintStr = getText().toString();
            } else {
                return;
            }
        }
        setText("");
        stopPrint();
        printProgress = 0;
        mTimer = new Timer();
        mTimer.schedule(new PrinterTimeTask(), intervalTime, intervalTime);
    }

    public void stopPrint() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    class PrinterTimeTask extends TimerTask {

        @Override
        public void run() {
            post(new Runnable() {
                @Override
                public void run() {
                    if (printProgress < mPrintStr.length()) {
                        printProgress++;
                        setText(mPrintStr.substring(0, printProgress) + ((printProgress & 1) == 1 ? intervalChar : ""));
                    } else {
                        setText(mPrintStr);
                        stopPrint();
                    }
                }
            });
        }
    }
}
