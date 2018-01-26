package com.brus5.lukaszkrawczak.fitx;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszkrawczak on 21.12.2017.
 */

public class UpdateRequestCalResultNewUser extends StringRequest {

        private static final String UPDATE_REQUEST_URL = "http://justfitx.xyz/UpdateRequestCalResult.php";
        private Map<String,String> params;
        public UpdateRequestCalResultNewUser(int id1, String username, String RESULT, String date, Response.Listener<String> listener){

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