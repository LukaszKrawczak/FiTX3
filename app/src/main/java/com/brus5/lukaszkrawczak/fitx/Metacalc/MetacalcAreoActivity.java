package com.brus5.lukaszkrawczak.fitx.Metacalc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.MetacalcActivity3;
import com.brus5.lukaszkrawczak.fitx.R;

public class MetacalcAreoActivity extends AppCompatActivity implements View.OnClickListener {

    final private static String TAG = "MetacalcAreoActivity";

    SeekBar seekBarAreoTime, seekBarAreoIntensity;
    TextView textViewAreoActivityAreoTime, textViewAreoActivityAreoIntensity;
    Button buttonAreoAdd;

    double AreoTime = 0d;
    double AreoTea = 5d;
    double AreoEpoc = 5d;
    double GymTime = 0d;
    double GymTea = 7d;
    double GymEpoc = 0.04d;
    double Somatotype;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_metacalc_areo);

        getWindow().setStatusBarColor(ContextCompat.getColor(MetacalcAreoActivity.this,R.color.color_meta_calc_areo_statusbar));
        initialization();
        seekbars();
        Intent intent = getIntent();
        GymTime = intent.getDoubleExtra("calcGymTime",0);
        GymTea = intent.getDoubleExtra("calcGymTea",7d);
        GymEpoc = intent.getDoubleExtra("calcGymEpoc",0.04d);
        Log.e(TAG,"GymTime: "+GymTime);
        Log.e(TAG,"calcGymTea: "+GymTea);
        Log.e(TAG,"calcGymEpoc: "+GymEpoc);
    }

    private void initialization() {
        seekBarAreoTime = findViewById(R.id.seekBarAreoTime);
        textViewAreoActivityAreoTime = findViewById(R.id.textViewAreoActivityAreoTime);
        seekBarAreoIntensity = findViewById(R.id.seekBarAreoIntensity);
        textViewAreoActivityAreoIntensity = findViewById(R.id.textViewAreoActivityAreoIntensity);
        buttonAreoAdd = findViewById(R.id.buttonAreoAdd);
        buttonAreoAdd.setOnClickListener(this);

    }

    private void seekbars() {
        seekBarAreoTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewAreoActivityAreoTime.setText("" + (++i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        seekBarAreoIntensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i == 0) {
                    AreoTea = 5d;
                    AreoEpoc = 5d;
                    textViewAreoActivityAreoIntensity.setText("low");
                }
                if (i == 1) {
                    AreoTea = 7d;
                    AreoEpoc = 35d;
                    textViewAreoActivityAreoIntensity.setText("medium");
                }
                if (i == 2) {
                    AreoTea = 10d;
                    AreoEpoc = 180d;
                    textViewAreoActivityAreoIntensity.setText("high");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    public void onClick(View v) {

        if (Double.valueOf(textViewAreoActivityAreoTime.getText().toString()) == 0d){
            Log.e(TAG,"CANNOT BE 0");
            Toast.makeText(MetacalcAreoActivity.this,"Add Cardio time",Toast.LENGTH_SHORT).show();
        }
        else {
            switch (v.getId()) {
                case R.id.buttonAreoAdd:
                    Log.e(TAG, "buttonAreoAdd Clicked");
                    Intent intent = new Intent(MetacalcAreoActivity.this, MetacalcActivity3.class);
                    intent.putExtra("calcAreoTime",     Double.valueOf(textViewAreoActivityAreoTime.getText().toString()));
                    intent.putExtra("calcAreoTea",      AreoTea);
                    intent.putExtra("calcAreoEpoc",     AreoEpoc);

                    intent.putExtra("calcGymTime",      GymTime);
                    intent.putExtra("calcGymTea",       GymTea);
                    intent.putExtra("calcGymEpoc",      GymEpoc);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
            }
        }
    }
}
