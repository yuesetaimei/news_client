package com.tlkg.news.app.activity.setting;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.tlkg.news.app.R;

/**
 * Created by wuxiaoqi on 2018/2/7.
 */

public class SettingFragment extends PreferenceFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_setting);
        setHasOptionsMenu(true);

        findPreference("auto_nightMode").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SettingActivity.startFragment(getActivity(), AutoNightSwitchFragment.class.getName(), null, getString(R.string.auto_switch_night_mode));
                return true;
            }
        });
    }
}
