package com.brus5.lukaszkrawczak.fitx;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class UserRegisterActivityThirdPage extends AppCompatActivity {

    private static final String TAG = "UserRegisterActivityThi";

    String userName, userFirstName, userBirthday, userPassword, userEmail, userMale, userID;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_third_page);
        Log.e(TAG, "onCreate: started");
        // toolbar in xml file
        Toolbar toolbar10 = (Toolbar) findViewById(R.id.toolbar10);
        setSupportActionBar(toolbar10);

        // statusbarcolor
        getWindow().setStatusBarColor(ContextCompat.getColor(UserRegisterActivityThirdPage.this, R.color.color_main_activity_statusbar));


        Intent intent1 = getIntent();
        userFirstName = intent1.getStringExtra("userFirstName");
        userName = intent1.getStringExtra("userName");
        userBirthday = intent1.getStringExtra("userBirthday");
        userPassword = intent1.getStringExtra("userPassword");
        userEmail = intent1.getStringExtra("userEmail");
        userMale = intent1.getStringExtra("userMale");
        Log.e(TAG,"informacje"+userFirstName+" "+userName+" "+userBirthday+" "+userPassword+" "+userEmail+" "+userMale);








    }
}
