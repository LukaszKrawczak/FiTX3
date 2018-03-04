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

public class DietProductsSearchByName extends StringRequest{
    private static final String UPDATE_REQUEST_URL = "http://justfitx.xyz/Diet/DietProductsSearchByName.php";
    private Map<String,String> params;
    public DietProductsSearchByName(String name, Response.Listener<String> listener){
        super(Method.POST,UPDATE_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("name",name);
}
    @SuppressLint("LongLogTag")
    @Override
    public Map<String, String> getParams() {
        Log.e("DietProductsSearchByName","ParamsChecker"+params);
        return params;
    }
}
