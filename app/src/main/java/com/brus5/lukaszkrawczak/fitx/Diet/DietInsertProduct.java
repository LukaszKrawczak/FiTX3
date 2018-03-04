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

public class DietInsertProduct extends StringRequest{
    private static final String UPDATE_REQUEST_URL = "http://justfitx.xyz/Diet/DietInsertProduct.php";
    private Map<String,String> params;
    public DietInsertProduct(String name, float proteins, float fats, float carbs, String date, String username, Response.Listener<String> listener){

        super(Method.POST,UPDATE_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("weight", 100+"");
        params.put("proteins", proteins+"");
        params.put("fats", fats+"");
        params.put("carbs", carbs+"");
        params.put("date", date);
        params.put("username", username);
    }

    @SuppressLint("LongLogTag")
    @Override
    public Map<String, String> getParams() {
        Log.e("UserProfileUpdateRequest","ParamsChecker"+params);
        return params;
    }

}
