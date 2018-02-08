package com.tlkg.news.app.activity.setting;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.widget.TimePicker;

import com.tlkg.news.app.R;
import com.tlkg.news.app.util.CommonSettingUtil;

/**
 * Created by wuxiaoqi on 2018/2/8.
 */

public class AutoNightSwitchFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private String nightStartHour;

    private String nightStartMinute;

    private String dayStartHour;

    private String dayStartMinute;

    private Preference autoDay;

    private Preference autoNight;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_auto_night);
        setHasOptionsMenu(true);

        autoNight = findPreference("auto_night");
        autoDay = findPreference("auto_day");
        setSummary();
        autoNight.setOnPreferenceClickListener(this);
        autoDay.setOnPreferenceClickListener(this);
    }

    private void setSummary() {
        nightStartHour = CommonSettingUtil.getInstance().getNightStartHour();
        nightStartMinute = CommonSettingUtil.getInstance().getNightStartMinute();
        dayStartHour = CommonSettingUtil.getInstance().getDayStartHour();
        dayStartMinute = CommonSettingUtil.getInstance().getDayStartMinute();

        autoNight.setSummary(String.format("%s:%s", nightStartHour, nightStartMinute));
        autoDay.setSummary(String.format("%s:%s", dayStartHour, dayStartMinute));
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == autoNight) {
            setNightTime();
        } else if (preference == autoDay) {
            setDayTimer();
        }
        return true;
    }

    private void setNightTime() {
        TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                CommonSettingUtil.getInstance().setNightStartHour(hourOfDay > 9 ? hourOfDay + "" : "0" + hourOfDay);
                CommonSettingUtil.getInstance().setNightStartMinute(minute > 9 ? minute + "" : "0" + minute);
                setSummary();
            }
        }, Integer.parseInt(nightStartHour), Integer.parseInt(nightStartMinute), true);
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(R.string.complete);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(R.string.cancel);
    }

    private void setDayTimer() {
        TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                CommonSettingUtil.getInstance().setDayStartHour(hourOfDay > 9 ? hourOfDay + "" : "0" + hourOfDay);
                CommonSettingUtil.getInstance().setDayStartMinute(minute > 9 ? minute + "" : "0" + minute);
                setSummary();
            }
        }, Integer.parseInt(dayStartHour), Integer.parseInt(dayStartMinute), true);
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(R.string.complete);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(R.string.cancel);
    }
}
