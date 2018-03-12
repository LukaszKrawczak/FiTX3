package com.brus5.lukaszkrawczak.fitx.Metacalc;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszkrawczak on 21.12.2017.
 */

public class MetacalcUserUpdateKcalresRequest extends StringRequest {

        private static final String UPDATE_REQUEST_URL = "http://justfitx.xyz/MetabolicCalc/MetacalcUserUpdateKcalresRequest.php";
        private Map<String,String> params;
        public MetacalcUserUpdateKcalresRequest(int id1, String username, String RESULT, String date, Response.Listener<String> listener){

            super(Method.POST,UPDATE_REQUEST_URL,listener,null);
            params = new HashMap<>();
            params.put("id",id1+"");
            params.put("username", username);
            params.put("RESULT", RESULT);
            params.put("date", date);
        }
        @Override
        public Map<String, String> getParams() {
            return params;
        }

    }

