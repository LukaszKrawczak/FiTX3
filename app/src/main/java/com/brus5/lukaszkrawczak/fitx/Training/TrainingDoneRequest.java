package com.brus5.lukaszkrawczak.fitx.Training;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszkrawczak on 01.12.2017.
 */

public class TrainingDoneRequest extends StringRequest{
    private static final String UPDATE_REQUEST_URL = "http://justfitx.xyz/Training/TrainingDoneRequest.php";
    private Map<String,String> params;
    public TrainingDoneRequest(String id, int done, Response.Listener<String> listener){

        super(Method.POST,UPDATE_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("id", id);
        params.put("done", done+"");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
