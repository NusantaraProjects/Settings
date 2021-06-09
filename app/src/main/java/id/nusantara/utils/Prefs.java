package id.nusantara.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by DELTALABS on 08,June,2021
 * DELTALABS STUDIO
 */
public class Prefs {

    public static SharedPreferences getPreferences() {
        return Tools.getContext().getSharedPreferences(getPrefName(false), Context.MODE_PRIVATE);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getPreferences().getBoolean(key, defaultValue);
    }

    public static boolean getBoolean(String key) {
        return getPreferences().getBoolean(key, false);
    }

    public static int getInt(String key, int defaultValue) {
        return getPreferences().getInt(key, defaultValue);
    }

    public static String getString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    public static SharedPreferences.Editor getEditor() {
        return getPreferences().edit();
    }

    public static void putBoolean(String key, boolean value) {
        getEditor().putBoolean(key, value).apply();
    }

    public static float getFloat(String key, float defaultValue) {
        return getPreferences().getFloat(key, defaultValue);
    }

    public static void putString(String key, String value) {
        getEditor().putString(key, value).apply();
    }

    public static void putInt(String key, int value) {
        getEditor().putInt(key, value).apply();
    }

    public static void remove(String key) {
        getEditor().remove(key).apply();
    }

    public static void clear() {
        getEditor().clear().commit();
    }

    public static void resetPrefs(String prefName, String key){
        SharedPreferences sharedPreferences = Tools.getContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public static SharedPreferences getPreferencesPriv() {
        return Tools.getContext().getSharedPreferences(getPrefName(true), Context.MODE_PRIVATE);
    }

    public static boolean getBooleanPriv(String key, boolean defaultValue) {
        return getPreferencesPriv().getBoolean(key, defaultValue);
    }

    public static int getIntPriv(String key, int defaultValue) {
        return getPreferencesPriv().getInt(key, defaultValue);
    }

    public static String getStringPriv(String key, String defaultValue) {
        return getPreferencesPriv().getString(key, defaultValue);
    }

    public static boolean getBooleanPriv(String key) {
        return getPreferencesPriv().getBoolean(key,false);
    }

    public static String getStringPriv(String key) {
        return getPreferencesPriv().getString(key, "");
    }

    public static SharedPreferences.Editor getEditorPriv() {
        return getPreferencesPriv().edit();
    }

    public static void putBooleanPriv(String key, boolean value) {
        getEditorPriv().putBoolean(key, value).apply();
    }

    public static float getFloatPriv(String key, float defaultValue) {
        return getPreferencesPriv().getFloat(key, defaultValue);
    }

    public static void putStringPriv(String key, String value) {
        getEditorPriv().putString(key, value).apply();
    }

    public static void putIntPriv(String key, int value) {
        getEditorPriv().putInt(key, value).apply();
    }

    public static void removePriv(String key) {
        getEditorPriv().remove(key).apply();
    }

    public static void clearPriv() {
        getEditorPriv().clear().commit();
    }

    public static ArrayList<Integer> getListInt(String key) {
        String[] myList = TextUtils.split(getPreferences().getString(key, ""), ",");
        ArrayList<String> arrayToList = new ArrayList<String>(Arrays.asList(myList));
        ArrayList<Integer> newList = new ArrayList<Integer>();

        for (String item : arrayToList)
            newList.add(Integer.parseInt(item));

        return newList;
    }

    public static ArrayList<String> getListString(String key) {
        return new ArrayList<String>(Arrays.asList(TextUtils.split(getPreferences().getString(key, ""), ",")));
    }

    public static void putListInt(String key, ArrayList<Integer> intList) {
        checkForNullKey(key);
        Integer[] myIntList = intList.toArray(new Integer[intList.size()]);
        getPreferences().edit().putString(key, TextUtils.join(",", myIntList)).apply();
    }

    public static void putListString(String key, ArrayList<String> stringList) {
        checkForNullKey(key);
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        getPreferences().edit().putString(key, TextUtils.join(",", myStringList)).apply();
    }

    public static void checkForNullKey(String key){
        if (key == null){
            throw new NullPointerException();
        }
    }

    public static boolean getDefaultBoolean(final Context context, final String s) {
        return context.getSharedPreferences(getDefaultPrefName(false), 0).getBoolean(s, false);
    }

    public static int getDefaultInt(final Context context, final String key, final int defaultValue) {
        return context.getSharedPreferences(getDefaultPrefName(false), 0).getInt(key, defaultValue);
    }

    public static String getRECENT(){
        return "recent_color";
    }

    public static String getPrefName(boolean isPrivate){
        String prefName = "YoWhatsApp";
        if(isPrivate){
            return "WhatsAppriv";
        }else {
            return prefName;
        }
    }

    public static String getDefaultPrefName(boolean isLight){
        String prefName = "com.yowhatsapp_preferences";
        if(isLight){
            return prefName+"_light";
        }else {
            return prefName;
        }
    }
}
