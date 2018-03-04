package com.brus5.lukaszkrawczak.fitx.Training;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszkrawczak on 01.12.2017.
 */

public class TrainingInsertRequest extends StringRequest{
    private static final String UPDATE_REQUEST_URL = "http://justfitx.xyz/Training/TrainingInsertRequest.php";
    private Map<String,String> params;
    public TrainingInsertRequest(int id, String username, String date, String description, Response.Listener<String> listener){

        super(Method.POST,UPDATE_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("id",id+"");
        params.put("username", username);
        params.put("date", date);
        params.put("description", description);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
