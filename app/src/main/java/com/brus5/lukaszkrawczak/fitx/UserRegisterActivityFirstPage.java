package com.brus5.lukaszkrawczak.fitx;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class UserRegisterActivityFirstPage extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_first_page);

        // toolbar in xml file
        Toolbar toolbar10 = (Toolbar) findViewById(R.id.toolbar10);
        setSupportActionBar(toolbar10);

        // statusbarcolor
        getWindow().setStatusBarColor(ContextCompat.getColor(UserRegisterActivityFirstPage.this, R.color.color_main_activity_statusbar));

        Button buttonNextStep = findViewById(R.id.buttonNextStep);
        buttonNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserRegisterActivityFirstPage.this,UserRegisterActivitySecondPage.class);
                UserRegisterActivityFirstPage.this.startActivity(intent);
            }
        });





    }
}
