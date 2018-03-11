package com.brus5.lukaszkrawczak.fitx.Metacalc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.MetacalcActivity3;
import com.brus5.lukaszkrawczak.fitx.R;

public class MetacalcGymActivity extends AppCompatActivity implements View.OnClickListener {

    final private static String TAG = "MetacalcGymActivity";

    SeekBar seekBarGymTime, seekBarGymIntensity;
    TextView textViewGymActivityGymTime, textViewGymActivityGymIntensity;
    Button buttonGymAdd;

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
        setContentView(R.layout.activity_menu_metacalc_gym);
        getWindow().setStatusBarColor(ContextCompat.getColor(MetacalcGymActivity.this, R.color.color_meta_calc_gym_statusbar));
        initialization();
        seekbars();
        Intent intent = getIntent();
        AreoTime = intent.getDoubleExtra("calcAreoTime",0);
        AreoTea = intent.getDoubleExtra("calcAreoTea",5d);
        AreoEpoc = intent.getDoubleExtra("calcAreoEpoc",5d);
        Log.e(TAG,"AreoTime: "       +AreoTime);
        Log.e(TAG,"AreoTea: "        +AreoTea);
        Log.e(TAG,"AreoEpoc: "       +AreoEpoc);
    }


    private void initialization() {
        seekBarGymTime = findViewById(R.id.seekBarGymTime);
        textViewGymActivityGymTime = findViewById(R.id.textViewGymActivityGymTime);
        seekBarGymIntensity = findViewById(R.id.seekBarGymIntensity);
        textViewGymActivityGymIntensity = findViewById(R.id.textViewGymActivityGymIntensity);
        buttonGymAdd = findViewById(R.id.buttonGymAdd);
        buttonGymAdd.setOnClickListener(this);

    }

    private void seekbars() {
        seekBarGymTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewGymActivityGymTime.setText("" + (++i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        seekBarGymIntensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i == 0) {
                    GymTea = 7d;
                    GymEpoc = 0.04d;
                    textViewGymActivityGymIntensity.setText("low");
                }
                if (i == 1) {
                    GymTea = 8d;
                    GymEpoc = 0.06d;
                    textViewGymActivityGymIntensity.setText("medium");
                }
                if (i == 2) {
                    GymTea = 9d;
                    GymEpoc = 0.07d;
                    textViewGymActivityGymIntensity.setText("high");
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

        if (Double.valueOf(textViewGymActivityGymTime.getText().toString()) == 0d){
            Log.e(TAG,"CANNOT BE 0");
            Toast.makeText(MetacalcGymActivity.this,"Add gym time",Toast.LENGTH_SHORT).show();
        }
        else {
            switch (v.getId()) {
                case R.id.buttonGymAdd:
                    Log.e(TAG, "buttonGymAdd Clicked");
                    Intent intent = new Intent(MetacalcGymActivity.this, MetacalcActivity3.class);
                    intent.putExtra("calcGymTime", Double.valueOf(textViewGymActivityGymTime.getText().toString()));
                    intent.putExtra("calcGymTea",       GymTea);
                    intent.putExtra("calcGymEpoc",      GymEpoc);

                    intent.putExtra("calcAreoTime",     AreoTime);
                    intent.putExtra("calcAreoTea",      AreoTea);
                    intent.putExtra("calcAreoEpoc",     AreoEpoc);


                    setResult(RESULT_OK, intent);
                    finish();
                    break;
            }
        }
    }
}

