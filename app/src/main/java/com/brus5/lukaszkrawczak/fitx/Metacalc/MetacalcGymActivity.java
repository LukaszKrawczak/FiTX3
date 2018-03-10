package com.brus5.lukaszkrawczak.fitx.Metacalc;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.brus5.lukaszkrawczak.fitx.R;

public class MetacalcGymActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metacalc_gym);

        getWindow().setStatusBarColor(ContextCompat.getColor(MetacalcGymActivity.this,R.color.color_meta_calc_gym_statusbar));

    }
}
