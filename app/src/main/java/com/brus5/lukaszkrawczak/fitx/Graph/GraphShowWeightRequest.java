package com.brus5.lukaszkrawczak.fitx.Graph;


import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszkrawczak on 01.12.2017.
 */


public class GraphShowWeightRequest extends StringRequest{

    private static final String UPDATE_REQUEST_URL = "http://justfitx.xyz/Graph/GraphShowWeightRequest.php";
    private Map<String,String> params;
    public GraphShowWeightRequest(String username, Response.Listener<String> listener){

        super(Method.POST,UPDATE_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("username", username);
    }
    @Override
    public Map<String, String> getParams() {
        Log.e("GraphShowWeightRequest", "getParams: "+params );
        return params;
    }

}
