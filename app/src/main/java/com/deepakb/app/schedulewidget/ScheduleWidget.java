package com.deepakb.app.schedulewidget;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

public class ScheduleWidget extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
