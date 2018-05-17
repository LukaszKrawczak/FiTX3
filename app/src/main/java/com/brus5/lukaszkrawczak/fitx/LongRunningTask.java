package com.brus5.lukaszkrawczak.fitx;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by lukaszkrawczak on 07.05.2018.
 */

public class LongRunningTask extends AsyncTask<JSONObject, Void, JSONObject>{
    private static final String TAG = "LongRunningTask";

    private JSONObject jsonObject;
    private String username;
    private String date;

    @Override
    protected JSONObject doInBackground(JSONObject... jsonObjects) {
        Log.e(TAG, "doInBackground: "+jsonObjects );

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        Log.e(TAG, "onPostExecute: " +jsonObject);
        super.onPostExecute(jsonObject);
    }



}

