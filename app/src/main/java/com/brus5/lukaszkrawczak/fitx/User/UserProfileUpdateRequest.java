package com.brus5.lukaszkrawczak.fitx.User;


import android.annotation.SuppressLint;
import android.util.Log;

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
    final private static String TAG = "UserProfileUpdateRequest";
    public UserProfileUpdateRequest(String updatename, String username, String updateusername, String updatebirthday, String updatepassword, String updateemail, Double updateheight, Double updateweight, Double updatesomatotype, Double updateproteinsratio, Double updatefatsratio, Double updatecarbsratio, String date, Response.Listener<String> listener){

        super(Request.Method.POST,UPDATE_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("updatename", updatename);
        params.put("username", username);
        params.put("updateusername", updateusername);
        params.put("updatebirthday",updatebirthday);
        params.put("updatepassword", updatepassword);
        params.put("updateemail", updateemail);
        params.put("updateheight", updateheight+"");
        params.put("updateweight", updateweight+"");
        params.put("updatesomatotype", updatesomatotype+"");
        params.put("updateproteinsratio", updateproteinsratio+"");
        params.put("updatefatsratio", updatefatsratio+"");
        params.put("updatecarbsratio", updatecarbsratio+"");
        params.put("date", date);
    }

    @SuppressLint("LongLogTag")
    @Override
    public Map<String, String> getParams() {
        Log.e(TAG,"params: "+params);
        return params;
    }

}
