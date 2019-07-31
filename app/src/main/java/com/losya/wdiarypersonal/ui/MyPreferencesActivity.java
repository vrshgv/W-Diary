package com.losya.wdiarypersonal.ui;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.*;
import com.losya.wdiarypersonal.R;
//import com.losya.wdiarypersonal.R;


public class MyPreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
      }

    }

    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.startActivity(this);
    }

    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        boolean lightPref = prefs.getBoolean("btheme", true);
        if (lightPref) {
            getListView().setBackgroundColor(Color.parseColor("#2b4055"));

        }
    }
}