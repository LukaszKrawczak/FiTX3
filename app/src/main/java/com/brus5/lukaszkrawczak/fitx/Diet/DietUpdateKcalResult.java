package com.brus5.lukaszkrawczak.fitx.Diet;


import android.annotation.SuppressLint;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszkrawczak on 01.12.2017.
 */

public class DietUpdateKcalResult extends StringRequest{
    private static final String UPDATE_REQUEST_URL = "http://justfitx.xyz/Diet/DietUpdateKcalResult.php";
    private Map<String,String> params;
    public DietUpdateKcalResult(int id, String updateresult, String username, String date, Response.Listener<String> listener){

        super(Method.POST,UPDATE_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("id", id+"");
        params.put("updateresult", updateresult);
        params.put("username", username);
        params.put("date", date);
    }

    @SuppressLint("LongLogTag")
    @Override
    public Map<String, String> getParams() {
        Log.e("DietUpdateKcalResult","ParamsChecker"+params);
        return params;
    }

}
