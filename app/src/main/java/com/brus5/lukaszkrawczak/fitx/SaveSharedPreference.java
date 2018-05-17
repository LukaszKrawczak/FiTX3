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
    static final String PREF_USER_FIRST_NAME= "userFirstName";
    static final String PREF_USER_BIRTHDAY= "userBirthday";
    static final String PREF_USER_EMAIL= "userEmail";
    static final String PREF_USER_GENDER= "userGender";
    static final String PREF_USER_ID = "userID";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static void setDefLogin(Context ctx, boolean defaultLogin) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(DEF_LOGIN, true);
        editor.commit();
    }

    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static Boolean getDefLogin(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(DEF_LOGIN, false);
    }

    public static void clearUserName(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }

    public static void setUserFirstName(Context ctx, String userFirstName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_FIRST_NAME, userFirstName);
        editor.commit();
    }

    public static String getUserFirstName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_FIRST_NAME, "");
    }

    public static void setUserBirthday(Context ctx, String userBirthday) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_BIRTHDAY, userBirthday);
        editor.commit();
    }

    public static String getUserBirthday(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_BIRTHDAY, "");
    }

    public static void setUserEmail(Context ctx, String userEmail) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_EMAIL, userEmail);
        editor.commit();
    }

    public static String getUserEmail(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_EMAIL, "");
    }

    public static void setUserGender(Context ctx, String userGender) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_GENDER, userGender);
        editor.commit();
    }

    public static String getUserGender(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_GENDER, "");
    }

    public static void setUserID(Context ctx, int userID) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_USER_ID, userID);
        editor.commit();
    }

    public static int getUserID(Context ctx) {
        return getSharedPreferences(ctx).getInt(PREF_USER_ID,0);
    }




}