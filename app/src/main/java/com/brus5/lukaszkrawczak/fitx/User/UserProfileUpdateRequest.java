package com.brus5.lukaszkrawczak.fitx.User;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszkrawczak on 01.12.2017.
 */

public class UserProfileUpdateRequest extends StringRequest{
    private static final String UPDATE_REQUEST_URL = "http://justfitx.xyz/User/UserProfileUpdateRequest.php";
    private Map<String,String> params;
    public UserProfileUpdateRequest(String updatename, String username, String updateusername, int updateage, String updatepassword, String updateemail, Response.Listener<String> listener){

        super(Request.Method.POST,UPDATE_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("updatename", updatename);
        params.put("username", username);
        params.put("updateusername", updateusername);
        params.put("updateage",updateage+"");
        params.put("updatepassword", updatepassword);
        params.put("updateemail", updateemail);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
