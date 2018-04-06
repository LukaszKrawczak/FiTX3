package com.brus5.lukaszkrawczak.fitx;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by lukaszkrawczak on 06.04.2018.
 */

public class SaveSharedPreference
{
    static final String PREF_USER_NAME= "username";
    static final String DEF_LOGIN= "defaultLogin";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static void setDefLogin(Context ctx, boolean defaultLogin)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(DEF_LOGIN, true);
        editor.commit();
    }


    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static Boolean getDefLogin(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(DEF_LOGIN, false);
    }
    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
}