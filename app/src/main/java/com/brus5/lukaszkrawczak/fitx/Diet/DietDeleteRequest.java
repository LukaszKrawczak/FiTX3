package com.brus5.lukaszkrawczak.fitx.Diet;


import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszkrawczak on 01.12.2017.
 */

public class DietDeleteRequest extends StringRequest{
    private static final String UPDATE_REQUEST_URL = "http://justfitx.xyz/Diet/DeleteRequest.php";
    private Map<String,String> params;
    public DietDeleteRequest(int id, String weight, String username, String date, Response.Listener<String> listener){

        super(Method.POST,UPDATE_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("id", id+"");
        params.put("weight", weight);
        params.put("username", username);
        params.put("date", date);
    }

    @Override
    public Map<String, String> getParams() {
        Log.e("params","params "+params);
        return params;
    }

}
