package com.OM7753.Gold;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;

import id.nusantara.utils.Prefs;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        changeTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goSettings(View view){
        SettingsActivity.openSettings(this);
    }

    public static void changeTheme(){
        int theme = Integer.parseInt((String) Prefs.getString(Keys.KEY_PREF_THEME, "1"));
        AppCompatDelegate.setDefaultNightMode(theme);
    }

}