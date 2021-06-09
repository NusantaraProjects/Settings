package com.OM7753.Gold;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import id.nusantara.utils.Prefs;
import id.nusantara.utils.Tools;

/**
 * Created by DELTALABS on 08,June,2021
 * DELTALABS STUDIO
 */
public class SettingsActivity extends AppCompatActivity implements MainFragment.Callback{

    private static final String TAG_NESTED = "TAG_NESTED";
    public static final String INTENT_PREFNAME = "INTENT_PREFNAME";
    public static final String INTENT_PREFKEY = "INTENT_PREFKEY";
    String getPrefname;
    Toolbar mToolbar;

    public static void openSettings(Activity activity){
        Intent i = new Intent(activity, SettingsActivity.class).putExtra(INTENT_PREFNAME, Prefs.getPrefName(false)).putExtra(INTENT_PREFKEY, Keys.PREF_MAIN);
        activity.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Tools.intLayout("activity_settings"));
        mToolbar = findViewById(Tools.intId("mToolbar"));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (savedInstanceState == null) {
            getPrefname = Prefs.getPrefName(false);
            int getKey = Keys.PREF_MAIN;
            if(getIntent() != null){
                getPrefname = getIntent().getStringExtra(INTENT_PREFNAME);
                getKey = getIntent().getIntExtra(INTENT_PREFKEY, Keys.PREF_MAIN);
            }
            getFragmentManager().beginTransaction()
                    .add(Tools.intId("mContent"), MainFragment.newInstance(getKey, getPrefname))
                    .commit();
        }
    }

    @Override
    public void onNestedPreferenceSelected(int key, String prefName) {
        getFragmentManager()
                .beginTransaction()
                .replace(Tools.intId("mContent"), SecondFragment.newInstance(key, prefName), TAG_NESTED)
                .addToBackStack(TAG_NESTED)
                .commit();
    }
}
