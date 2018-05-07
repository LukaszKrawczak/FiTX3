package com.brus5.lukaszkrawczak.fitx;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by lukaszkrawczak on 07.05.2018.
 */

public class LongRunningTask extends AsyncTask<JSONObject, Void, JSONObject>{
    private static final String TAG = "LongRunningTask";

    @Override
    protected JSONObject doInBackground(JSONObject... jsonObjects) {
        Log.d(TAG, "doInBackground: "+ Arrays.toString(jsonObjects));
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(TAG, "onPreExecute: ");
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        Log.d(TAG, "onPostExecute: "+jsonObject);
    }
}

