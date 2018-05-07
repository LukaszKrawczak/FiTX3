package com.brus5.lukaszkrawczak.fitx.User;


import android.annotation.SuppressLint;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszkrawczak on 01.12.2017.
 */

public class UserRegister1Request extends StringRequest{
    private static final String UPDATE_REQUEST_URL = "http://justfitx.xyz/User/UserRegister1Request.php";
    private Map<String,String> params;
    final private static String TAG = "UserRegister1Request";
    public UserRegister1Request(String userName, String userFirstName, String userPassword, String userBirthday, String email, String userMale, String weight, String height,  String somatotype, Double proteinsratio, Double fatsratio, Double carbsratio, String date, Response.Listener<String> listener){

        super(Method.POST,UPDATE_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("name", userFirstName);
        params.put("username", userName);
        params.put("birthday", userBirthday);
        params.put("password", userPassword);
        params.put("email", email);
        params.put("male", userMale);
        params.put("weight", weight);
        params.put("height", height);
        params.put("somatotype", somatotype);
        params.put("proteinsratio", proteinsratio+"");
        params.put("fatsratio", fatsratio+"");
        params.put("carbsratio", carbsratio+"");
        params.put("date", date);
    }

    @SuppressLint("LongLogTag")
    @Override
    public Map<String, String> getParams() {
        Log.e(TAG,"params: "+params);
        return params;
    }
}
