package com.cng.zhangtu.hmarkyun;

import android.app.Application;
import android.util.DisplayMetrics;

/**
 * Created by dongdz on 2016/8/29.
 */

public class MyApplication extends Application {

    public static int screenWidth = 480;
    public static int screenHeight = 854;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        if (dm != null) {
            screenHeight = dm.heightPixels;
            screenWidth = dm.widthPixels;
        }
    }
}
