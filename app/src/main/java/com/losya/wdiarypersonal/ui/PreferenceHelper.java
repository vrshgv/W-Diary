package com.losya.wdiarypersonal.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by dianlujitao on 17-6-4.
 */

public class PreferenceHelper {

    private static SharedPreferences mSharedPreferences;

    public static void init(Context context) {
        Context appContext = context.getApplicationContext();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    public static boolean isNightModeEnabled() {
        return mSharedPreferences.getBoolean("btheme", false);
    }
}