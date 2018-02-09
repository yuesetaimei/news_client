package com.tlkg.news.app.base;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrInterface;
import com.tlkg.news.app.util.CommonSettingUtil;

/**
 * Created by wuxiaoqi on 2017/11/30.
 */

public abstract class BaseSlidrActivity extends BaseActivity {

    protected SlidrInterface slidrInterface;

    @Override
    protected void initSlidable() {
        int slidable = CommonSettingUtil.getInstance().getSlidable();
        if (slidable == 1) {
            SlidrConfig config = new SlidrConfig.Builder()
                    .edge(true)
                    .build();
            slidrInterface = Slidr.attach(this, config);
        }
    }
}
