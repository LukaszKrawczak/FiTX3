package com.brus5.lukaszkrawczak.fitx;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by lukaszkrawczak on 10.01.2018.
 */

public class App {
    public static void finishApp(AppCompatActivity appCompatActivity) {
        appCompatActivity.finish();
    }

    public static void refreshApp(AppCompatActivity appCompatActivity) {
        appCompatActivity.recreate();
    }
}
