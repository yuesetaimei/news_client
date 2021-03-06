package com.tlkg.news.app.activity.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.tlkg.news.app.R;
import com.tlkg.news.app.activity.LaunchActivity;
import com.tlkg.news.app.ui.view.ThemePreference;
import com.tlkg.news.app.util.CacheDataManager;
import com.tlkg.news.app.util.LanguageUtil;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

/**
 * Created by wuxiaoqi on 2018/2/7.
 * 设置 Fragment
 */

public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private ThemePreference themePreference;

    private SettingActivity context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_setting);
        setHasOptionsMenu(true);

        context = (SettingActivity) getActivity();

        findPreference("auto_nightMode").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SettingActivity.startFragment(getActivity(), AutoNightSwitchFragment.class.getName(), null, getString(R.string.auto_switch_night_mode));
                return true;
            }
        });
        findPreference("text_size").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SettingActivity.startFragment(getActivity(), TextSizeFragment.class.getName(), null, getString(R.string.text_size));
                return true;
            }
        });
        themePreference = (ThemePreference) findPreference("theme_color");
        themePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new ColorChooserDialog.Builder(context, R.string.theme_color)
                        .backButton(R.string.back)
                        .cancelButton(R.string.cancel)
                        .doneButton(R.string.complete)
                        .customButton(R.string.custom)
                        .allowUserColorInputAlpha(false)
                        .show();
                return true;
            }
        });
        findPreference("clear_cache").setSummary(CacheDataManager.getTotalCacheSize());
        findPreference("clear_cache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showCacheDialog();
                return true;
            }
        });
        try {
            String versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            findPreference("version").setSummary("V " + versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        findPreference("licenses").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Notices notices = new Notices();
                notices.addNotice(new Notice("OkHttp", "https://github.com/square/okhttp", "Copyright 2016 Square, Inc.", new ApacheSoftwareLicense20()));
                notices.addNotice(new Notice("Gson", "https://github.com/google/gson", "Copyright 2008 Google Inc.", new ApacheSoftwareLicense20()));
                notices.addNotice(new Notice("Glide", "https://github.com/bumptech/glide", "Sam Judd - @sjudd on GitHub, @samajudd on Twitter", new ApacheSoftwareLicense20()));

                new LicensesDialog.Builder(context)
                        .setNotices(notices)
                        .setIncludeOwnLicense(true)
                        .build()
                        .show();
                return false;
            }
        });
        findPreference("source_code").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_url))));
                return false;
            }
        });
        findPreference("copy_right").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.copy_right)
                        .setMessage(R.string.only_study)
                        .setCancelable(true)
                        .show();
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        changeLanguageSummary();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if ("theme_color".equals(key)) {
            themePreference.updateColor();
        } else if ("slidable".equals(key)) {
            context.recreate();
        } else if ("language".equals(key)) {
            changeLanguage();
        }
    }

    /**
     * 更改显示语言
     */
    private void changeLanguage() {
        try {
            changeLanguageSummary();
            changeLanguageShow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeLanguageShow() {
        LanguageUtil.setChangeLanguage(context, getPreferenceManager().getSharedPreferences());
        Intent intent = new Intent(getActivity(), LaunchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void changeLanguageSummary() {
        String language = getPreferenceManager().getSharedPreferences().getString("language", "0");
        switch (Integer.parseInt(language)) {
            case 0:
                findPreference("language").setSummary("中文简体");
                break;
            case 1:
                findPreference("language").setSummary("中文繁體");
                break;
            case 2:
                findPreference("language").setSummary("English");
                break;
            case 3:
                findPreference("language").setSummary("日语");
                break;
            default:
                break;
        }
    }

    private void showCacheDialog() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.do_you_want_clear_cache)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CacheDataManager.clearAllCache();
                        findPreference("clear_cache").setSummary("0.0Byte");
                    }
                })
                .setNegativeButton(R.string.cancel, null).create();
        dialog.show();
    }
}
