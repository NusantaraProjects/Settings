package com.OM7753.Gold;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;

import id.nusantara.utils.Prefs;
import id.nusantara.utils.Tools;

/**
 * Created by DELTALABS on 08,June,2021
 * DELTALABS STUDIO
 */
public class MainFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private Callback mCallback;
    public SettingsActivity activity;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try{
            ListView mList = view.findViewById(android.R.id.list);
            mList.setDivider(Tools.colorDrawable(Tools.intColor("transparent"), Color.TRANSPARENT, PorterDuff.Mode.SRC_IN));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static MainFragment newInstance(int key, String prefName){
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(Keys.TAG_KEY, key);
        args.putString(Keys.TAG_PREF, prefName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof Callback) {
            mCallback = (Callback) activity;
        } else {
            throw new IllegalStateException("Owner must implement URLCallback interface");
        }
    }

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        activity = (SettingsActivity) getActivity();
        PreferenceManager manager = getPreferenceManager();
        manager.setSharedPreferencesName(getArguments().getString(Keys.TAG_PREF));
        xmlResource(getArguments().getInt(Keys.TAG_KEY));
    }

    private void xmlResource(int key) {
        switch (key) {
            case Keys.PREF_MAIN:
                addPreferencesFromResource(Tools.intXml("main_settings"));
                initPreference();
                break;
        }
    }

    private void initPreference(){
        Preference onePref = findPreference(Keys.KEY_PREF_ONE);
        Preference twoPref = findPreference(Keys.KEY_PREF_TWO);
        onePref.setOnPreferenceClickListener(this);
        twoPref.setOnPreferenceClickListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(Keys.KEY_PREF_THEME)){
            MainActivity.changeTheme();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(Tools.intLayout("list_fragment"), container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case Keys.KEY_PREF_ONE:
                mCallback.onNestedPreferenceSelected(Keys.PREF_ONE, Prefs.getPrefName(false));
                break;
            case Keys.KEY_PREF_TWO:
                mCallback.onNestedPreferenceSelected(Keys.PREF_TWO, Prefs.getPrefName(false));
                break;
        }
        return false;
    }

    public interface Callback {
        public void onNestedPreferenceSelected(int key, String prefName);
    }

}
