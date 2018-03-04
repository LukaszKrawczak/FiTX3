package com.brus5.lukaszkrawczak.fitx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Backup.UpdateRequestTEST;
import com.brus5.lukaszkrawczak.fitx.Metacalc.MetacalcUserInfoShowRequest;
import com.brus5.lukaszkrawczak.fitx.Metacalc.MetacalcUserInsertUserinfoRequest;
import com.brus5.lukaszkrawczak.fitx.Metacalc.MetacalcUserUpdateKcalresRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;


public class MetacalcActivity extends AppCompatActivity implements View.OnClickListener{

    final private static String TAG = "MetacalcActivity";

    SeekBar sbGymTEA,sbAreoTEA, sbNEAT, sbGymTime, sbAreoTime;

    RadioButton rbWoman, rbMan;
    EditText etWeight, etHeight, etAge;
    TextView tvResult, tvGymTEA, tvAreoTEA, tvGymTime, tvAreoTime, tvTEAresult, tvAREOresult;

    Switch switchIsGym, switchIsAreo;

    String name, username, age, password, email, male, id, somatotypeS;

    int id1;

    ImageView ivNEAT;
    Button btCount;

    Double AGE, weight, height, RESULT, TEF;
    double AreoTEA = 5d;
    double AreoEPOC = 5d;
    double GymTEA = 7d;
    double GymEPOC = 0.04d;
    double GymTime = 0d;
    double AreoTime = 0d;
//    double GymEPOC = 0d;
//    double AreoTEA = 0d;
//    double AreoEPOC = 0d;
    double somatotype;
    /* Gettings date */
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String date = simpleDateFormat.format(c.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_meta_calc);

        init();
        GymTEAresult();
        AreoTEAresult();

        Intent intent1 = getIntent();
        id1 = intent1.getIntExtra("id",0);
        name = intent1.getStringExtra("name");
        username = intent1.getStringExtra("username");
        age = intent1.getStringExtra("age");
        male = intent1.getStringExtra("male");

        Log.e(TAG,"id1: "+id1);

        Log.e("somatotype w onCreate ",""+somatotype);
        Log.e("somatotype w onCreate ",""+somatotypeS);

        AGE = Double.parseDouble(age);
        etAge.setText(String.format("%.0f", AGE));

        System.out.println("My name is "+name+" username is "+username+" age is "+age+" and male "+male);

           Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("StringResponse",""+response);
                String result;
                result = response.replaceAll("[\\p{Punct}&&[^@.]]"," ");
                String[] words = new String[]{};
                words = result.split("\\s+");
                String id = words[5];
                String username = words[7];
                Log.e("WYNIK123",""+ Arrays.toString(words));
                Log.e("id",""+id);
                Log.e("username",""+username);
                id1 = Integer.parseInt(id);
                Log.e("IDID",""+id1);
            }
        };

        UpdateRequestTEST updateRequestTEST = new UpdateRequestTEST(username, responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(MetacalcActivity.this);
        queue1.add(updateRequestTEST);


        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    boolean noresult = jsonObject.getBoolean("noresult");

                    Toast.makeText(MetacalcActivity.this, "Nice! Now fill the empty fields, and let us calculate your result.", Toast.LENGTH_LONG).show();
                    


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray server_response = jsonObject.getJSONArray("server_response");

                Log.e("HEHE RESPONSE",""+response);
                String result;
                result = response.replaceAll("[\\p{Punct}&&[^@.]]"," ");
                String[] words;
                words = result.split("\\s+");
                String username = words[words.length-9];
                String weight = words[words.length-7];
                String height = words[words.length-5];
                somatotypeS = words[words.length-3];
                somatotype = Double.parseDouble(somatotypeS);
                String date = words[words.length-1];
                Log.e("last index of array ",""+words[words.length-1]);
                Log.e("somatotype w response ",""+somatotype);
                Log.e("somatotype w response ",""+somatotypeS);

                Log.e("WYNIK",""+ Arrays.toString(words));
                Log.e("username"," "+username+" "+weight+" "+height+" "+somatotype+" "+date);

                etWeight.setText(weight);
                etHeight.setText(height);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MetacalcUserInfoShowRequest metacalcUserInfoShow = new MetacalcUserInfoShowRequest(username, responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(MetacalcActivity.this);
        queue2.add(metacalcUserInfoShow);

        switchIsGym.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    sbGymTEA.setVisibility(View.VISIBLE);
                    tvGymTime.setVisibility(View.VISIBLE);
                    sbGymTime.setVisibility(View.VISIBLE);
                    tvGymTEA.setVisibility(View.VISIBLE);
                    GymTEA = 7d;
                    GymEPOC = 0.04d;
                    tvGymTime.setText("10");
                    System.out.println("CHECKING: "+GymTEA+" "+GymEPOC);
                }
                else if (!isChecked){
                    sbGymTEA.setVisibility(View.INVISIBLE);
                    tvGymTime.setVisibility(View.INVISIBLE);
                    sbGymTime.setVisibility(View.INVISIBLE);
                    tvGymTEA.setVisibility(View.INVISIBLE);
                    GymTEA = 0d;
                    GymEPOC = 0d;
                    tvGymTime.setText("0");
                    System.out.println("CHECKING: "+GymTEA+" "+GymEPOC);

                }
            }
        });
        switchIsAreo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    sbAreoTEA.setVisibility(View.VISIBLE);
                    tvAreoTime.setVisibility(View.VISIBLE);
                    sbAreoTime.setVisibility(View.VISIBLE);
                    tvAreoTEA.setVisibility(View.VISIBLE);
                    AreoTEA = 5d;
                    AreoEPOC = 5d;
                    tvAreoTime.setText("10");
                    System.out.println("CHECKING: "+AreoTEA+" "+AreoEPOC);
                }
                else if (!isChecked){
                    sbAreoTEA.setVisibility(View.INVISIBLE);
                    sbAreoTime.setVisibility(View.INVISIBLE);
                    tvAreoTime.setVisibility(View.INVISIBLE);
                    tvAreoTEA.setVisibility(View.INVISIBLE);
                    AreoTEA = 0d;
                    AreoEPOC = 0d;
                    tvAreoTime.setText("0");
                    System.out.println("CHECKING: "+AreoTEA+" "+AreoEPOC);
                }
            }
        });

        if (male.equals("m")){
            rbMan.setChecked(true);
            rbWoman.setChecked(false);
        }

        else if (male.equals("w")){
            rbWoman.setChecked(true);
            rbMan.setChecked(false);
        }
    }

    private void init2() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("somatotype przed ifami",""+somatotype);
                int somatotypeINT = Integer.valueOf((int) somatotype);

            }
        },2000);
    }

    private void AreoTEAresult() {
        sbAreoTEA = findViewById(R.id.sbAreoTEA);

        sbAreoTEA.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if (i==0){
                    AreoTEA = 5d;
                    AreoEPOC = 5d;
                    tvAreoTEA.setText("low intensity");
                }
                if (i==1){
                    AreoTEA = 7d;
                    AreoEPOC = 35d;
                    tvAreoTEA.setText("mid intensity");
                }
                if (i==2){
                    AreoTEA = 10d;
                    AreoEPOC = 180d;
                    tvAreoTEA.setText("high intensity");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void GymTEAresult(){

        sbGymTEA = findViewById(R.id.sbGymTEA);

        sbGymTEA.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i==0) {
                    GymTEA = 7d;
                    GymEPOC = 0.04d;
                    tvGymTEA.setText("low intensity");
                }
                if (i==1) {
                    GymTEA = 8d;
                    GymEPOC = 0.06d;
                    tvGymTEA.setText("mid intensity");
                }
                if (i==2) {
                    GymTEA = 9d;
                    GymEPOC = 0.07d;
                    tvGymTEA.setText("high intensity");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void init() {


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.e("somatotype w seekbar ",""+somatotype);
                Log.e("somatotype w seekbar ",""+somatotypeS);

//                sbNEAT.setProgress(4);


                sbNEAT = findViewById(R.id.sbNEAT);
                sbNEAT.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                    final int somatotypeINT = Integer.parseInt(somatotypeS);
//                        Log.e("somatotype w seekbar ",""+somatotypeINT);
                        if (i==0){
                            ivNEAT.setImageResource(R.drawable.ectomorph);
                            somatotype = 900d;
                        }
                        if (i==1){
                            ivNEAT.setImageResource(R.drawable.ectomorph);
                            somatotype = 800d;
                        }
                        if (i==2){
                            ivNEAT.setImageResource(R.drawable.ectomorph);
                            somatotype = 700d;
                        }
                        if (i==3){
                            ivNEAT.setImageResource(R.drawable.mezomorph);
                            somatotype = 500d;
                        }
                        if (i==4) {
                            ivNEAT.setImageResource(R.drawable.mezomorph);
                            somatotype = 400d;
                        }
                        if (i==5){
                            ivNEAT.setImageResource(R.drawable.endomorph);
                            somatotype = 300d;
                        }
                        if (i==6){
                            ivNEAT.setImageResource(R.drawable.endomorph);
                            somatotype = 200d;
                        }
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
                Log.e("somatotype przed ifami",""+somatotype);
                Log.e("somatotype INT",""+somatotype);
                if (somatotype==900){
                    ivNEAT.setImageResource(R.drawable.ectomorph);
                    sbNEAT.setProgress(0);
                }
                if (somatotype==800){
                    ivNEAT.setImageResource(R.drawable.ectomorph);
                    sbNEAT.setProgress(1);
                }
                if (somatotype==700){
                    ivNEAT.setImageResource(R.drawable.ectomorph);
                    sbNEAT.setProgress(2);
                }
                if (somatotype==500){
                    ivNEAT.setImageResource(R.drawable.mezomorph);
                    sbNEAT.setProgress(3);
                }
                if (somatotype==400){
                    ivNEAT.setImageResource(R.drawable.mezomorph);
                    sbNEAT.setProgress(4);
                }
                if (somatotype==300){
                    ivNEAT.setImageResource(R.drawable.endomorph);
                    sbNEAT.setProgress(5);
                }
                if (somatotype==200){
                    ivNEAT.setImageResource(R.drawable.endomorph);
                    sbNEAT.setProgress(6);
                }
            }
        },3000);


        sbGymTime = findViewById(R.id.sbGymTime);
        sbGymTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvGymTime.setText(""+(++i+10));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        sbAreoTime = findViewById(R.id.sbAreoTime);
        sbAreoTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvAreoTime.setText(""+(++i+10));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btCount = findViewById(R.id.btCount);
        btCount.setOnClickListener(this);
        rbWoman = findViewById(R.id.womanRadioButton);
        rbMan = findViewById(R.id.manRadioButton);
        etWeight = findViewById(R.id.weightEditText);
        etHeight = findViewById(R.id.heightEditText);
        etAge = findViewById(R.id.ageEditText);
        tvGymTEA = findViewById(R.id.tvGymTEA);
        tvAreoTEA = findViewById(R.id.tvAreoTEA);
        tvResult = findViewById(R.id.resultTextView);
        tvGymTime = findViewById(R.id.tvGymTime);
        tvAreoTime = findViewById(R.id.tvAreoTime);
        tvTEAresult = findViewById(R.id.tvTEAresult);
        tvAREOresult = findViewById(R.id.tvAREOresult);
        switchIsGym = findViewById(R.id.switchIsGym);
        switchIsAreo = findViewById(R.id.switchIsAreo);
        ivNEAT = findViewById(R.id.ivNEAT);

    }
    @SuppressLint("SetTextI18n")
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btCount:

                if (rbMan.isChecked()) {

                    weight = Double.valueOf(etWeight.getText().toString());
                    etWeight.setSelected(false);
                    height = Double.valueOf(etHeight.getText().toString());
                    etHeight.setSelected(false);
                    AGE = Double.valueOf(etAge.getText().toString());
                    etAge.setSelected(false);

                    double BMR = ((9.99 * weight) + (6.25 * height) - (4.92 * AGE)) + 5d;

                    double GymTime = Double.valueOf(tvGymTime.getText().toString());
                    double AreoTime = Double.valueOf(tvAreoTime.getText().toString());

                    System.out.println("GymTime:"+GymTime+"GymTEA:"+GymTEA+"AreoTime:"+AreoTime+"AreoTEA:"+AreoTEA+"GymEPOC:"+GymEPOC+"AreoEPOC:"+AreoEPOC+"somatotype:"+somatotype+"BMR:"+BMR);

                    TEF = BMR + (GymTime * GymTEA) + (AreoTime * AreoTEA) + (GymEPOC * BMR) + AreoEPOC + somatotype;
                    RESULT = TEF + (0.1d * TEF);
                    tvResult.setText(""+RESULT);
                    Log.e("RESULT2",""+RESULT);
                    tvTEAresult.setText("AreoEPOC "+GymEPOC);
                    tvAREOresult.setText("AreoTEA "+GymTEA);

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response",""+response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                jsonObject.getString("success");
                                boolean success = jsonObject.getBoolean("success");
                                if (success){
                                    Log.e("boolean",""+success);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.e("Response",""+response);
                        }
                    };

                    String SResult = String.format("%.0f",RESULT);
                    Log.e("MyStringID",""+id);
                    Log.e("MyINTid",""+id1);

                    MetacalcUserUpdateKcalresRequest metacalcUserUpdateKcalresRequest = new MetacalcUserUpdateKcalresRequest(id1, username, SResult, date, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MetacalcActivity.this);
                    queue.add(metacalcUserUpdateKcalresRequest);

                    MetacalcUserInsertUserinfoRequest metacalcUserInsertUserinfoRequest = new MetacalcUserInsertUserinfoRequest(id1, username, weight, height, somatotype, date, responseListener);
                    RequestQueue queue1 = Volley.newRequestQueue(MetacalcActivity.this);
                    queue1.add(metacalcUserInsertUserinfoRequest);

                    return;
                }


                else if (rbWoman.isChecked()){
                    weight = Double.valueOf(etWeight.getText().toString());
                    etWeight.setSelected(false);
                    height = Double.valueOf(etHeight.getText().toString());
                    etHeight.setSelected(false);
                    AGE = Double.valueOf(etAge.getText().toString());
                    etAge.setSelected(false);

                    double BMR = ((9.99 * weight) + (6.25 * height) - (4.92 * AGE)) - 161d;
                    double GymTime = Double.valueOf(tvGymTime.getText().toString());
                    double AreoTime = Double.valueOf(tvAreoTime.getText().toString());

                    TEF = BMR + (GymTime * GymTEA) + (AreoTime * AreoTEA) + (GymEPOC * BMR) + AreoEPOC + somatotype;

                    RESULT = TEF + (0.1d * TEF);

                    tvResult.setText(""+String.format("%.0f", RESULT));

                    System.out.println("GymTime:"+GymTime+"GymTEA:"+GymTEA+"AreoTime:"+AreoTime+"AreoTEA:"+AreoTEA+"GymEPOC:"+GymEPOC+"AreoEPOC:"+AreoEPOC+"somatotype:"+somatotype+"BMR:"+BMR+"TEF:"+TEF+"RESULT:"+RESULT);

                    tvTEAresult.setText("AreoEPOC "+GymEPOC);
                    tvAREOresult.setText("AreoTEA "+GymTEA);

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response",""+response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                jsonObject.getString("success");
                                boolean success = jsonObject.getBoolean("success");
                                if (success){
                                    Log.e("boolean",""+success);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.e("Response",""+response);
                        }
                    };

                    String SResult = String.format("%.0f",RESULT);
                    Log.e("MyStringID",""+id);
                    Log.e("MyINTid",""+id1);

                    MetacalcUserUpdateKcalresRequest metacalcUserUpdateKcalresRequest = new MetacalcUserUpdateKcalresRequest(id1, username, SResult, date, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MetacalcActivity.this);
                    queue.add(metacalcUserUpdateKcalresRequest);

                    MetacalcUserInsertUserinfoRequest metacalcUserInsertUserinfoRequest = new MetacalcUserInsertUserinfoRequest(id1, username, weight, height, somatotype, date, responseListener);
                    RequestQueue queue1 = Volley.newRequestQueue(MetacalcActivity.this);
                    queue1.add(metacalcUserInsertUserinfoRequest);

                    return;
                }
                break;
        }

    }



}