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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;


public class MenuMetaCalc extends AppCompatActivity implements View.OnClickListener{

    SeekBar sbGymTEA,sbAreoTEA, sbNEAT, sbGymTime, sbAreoTime;

    RadioButton rbWoman, rbMan;
    EditText etWeight, etHeight, etAge;
    TextView tvResult, tvGymTEA, tvAreoTEA, tvGymTime, tvAreoTime, tvTEAresult, tvAREOresult;

    Switch switchIsGym, switchIsAreo;

    String name, username, age, password, email, male, id, somatotypeS;

    int id1;

    ImageView ivNEAT;
    Button btCount;

    Double AGE, weight, height, RESULT, GymTEA, AreoTEA, GymEPOC, AreoEPOC, TEF;
    public double somatotype;
    /* Gettings date from the program */
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
        name = intent1.getStringExtra("name");
        username = intent1.getStringExtra("username");
        age = intent1.getStringExtra("age");
        male = intent1.getStringExtra("male");

        Log.e("somatotype w onCreate ",""+somatotype);
        Log.e("somatotype w onCreate ",""+somatotypeS);

        AGE = Double.parseDouble(age);
        etAge.setText(String.format("%.0f", AGE));

        System.out.println("My name is "+name+" username is "+username+" age is "+age+" and male "+male);

        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    boolean success = jsonObject.getBoolean("success");
//
//                    if (success) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MenuMetaCalc.this);
//                        builder.setMessage("username up to date")
//                                .setNegativeButton("OK", null)
//                                .create()
//                                .show();
//                    } else {
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                Log.e("StringResponse",""+response);
                String result;
                result = response.replaceAll("[\\p{Punct}&&[^@.]]"," ");
                String[] words = new String[]{};
                words = result.split("\\s+");
                String id = words[5];
                String username = words[7];
                Log.e("WYNIK",""+ Arrays.toString(words));
                Log.e("id",""+id);
                Log.e("username",""+username);
                id1 = Integer.parseInt(id);
                Log.e("IDID",""+id1);
            }
        };

        UpdateRequestTEST updateRequestTEST = new UpdateRequestTEST(username, responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(MenuMetaCalc.this);
        queue1.add(updateRequestTEST);

        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    boolean success = jsonObject.getBoolean("success");
//
//                    if (success) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MenuMetaCalc.this);
//                        builder.setMessage("username up to date")
//                                .setNegativeButton("OK", null)
//                                .create()
//                                .show();
//                    } else {
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                //TODO RIGHT HERE :)
                Log.e("HEHE RESPONSE",""+response);
                String result;
                result = response.replaceAll("[\\p{Punct}&&[^@.]]"," ");
                String[] words;
                words = result.split("\\s+");
                String username = words[4];
                String weight = words[6];
                String height = words[8];
                somatotypeS = words[10];
                somatotype = Double.parseDouble(String.valueOf(words[10]));
//                String somatotype = words[10];
                String date = words[12];

                Log.e("somatotype w response ",""+somatotype);
                Log.e("somatotype w response ",""+somatotypeS);

                Log.e("WYNIK",""+ Arrays.toString(words));
                Log.e("username"," "+username+" "+weight+" "+height+" "+somatotype+" "+date);

                etWeight.setText(weight);
                etHeight.setText(height);
            }
        };

        UpdateRequestShowUserInfo updateRequestShowUserInfo = new UpdateRequestShowUserInfo(username, responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(MenuMetaCalc.this);
        queue2.add(updateRequestShowUserInfo);





        switchIsGym.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    sbGymTEA.setVisibility(View.VISIBLE);
                    tvGymTime.setVisibility(View.VISIBLE);
                    sbGymTime.setVisibility(View.VISIBLE);
                    tvGymTEA.setVisibility(View.VISIBLE);
                }
                else {
                    sbGymTEA.setVisibility(View.INVISIBLE);
                    tvGymTime.setVisibility(View.INVISIBLE);
                    sbGymTime.setVisibility(View.INVISIBLE);
                    tvGymTEA.setVisibility(View.INVISIBLE);
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
                }
                else {
                    sbAreoTEA.setVisibility(View.INVISIBLE);
                    sbAreoTime.setVisibility(View.INVISIBLE);
                    tvAreoTime.setVisibility(View.INVISIBLE);
                    tvAreoTEA.setVisibility(View.INVISIBLE);
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

    private void AreoTEAresult() {

        AreoTEA = 5d;
        AreoEPOC = 5d;

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

        GymTEA = 7d;
        GymEPOC = 0.04d;
        GymEPOC = 4d;
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
                sbNEAT = findViewById(R.id.sbNEAT);
                Log.e("somatotype w seekbar ",""+somatotype);
                Log.e("somatotype w seekbar ",""+somatotypeS);

                sbNEAT.setProgress(4);



                sbNEAT.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    final int somatotypeINT = Integer.parseInt(somatotypeS);
                        Log.e("somatotype w seekbar ",""+somatotypeINT);
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
                        if (somatotype == 400.0) {
                            seekBar.setProgress(4);
                        }
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
            }
        },3000);


        sbGymTime = findViewById(R.id.sbGymTime);
        sbGymTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvGymTime.setText(""+(++i+29));
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
                tvAreoTime.setText(""+(++i+19));
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



                    UpdateRequestCalResult updateRequestCalResult = new UpdateRequestCalResult(id1, username, SResult, date, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MenuMetaCalc.this);
                    queue.add(updateRequestCalResult);





                    UpdateRequestUserInfo updateRequestUserInfo = new UpdateRequestUserInfo(id1, username, weight, height, somatotype, date, responseListener);
                    RequestQueue queue1 = Volley.newRequestQueue(MenuMetaCalc.this);
                    queue1.add(updateRequestUserInfo);

                    return;
                }
                else if (rbWoman.isChecked()){
                    weight = Double.valueOf(etWeight.getText().toString());
                    height = Double.valueOf(etHeight.getText().toString());
                    AGE = Double.valueOf(etAge.getText().toString());

                    double BMR = ((9.99 * weight) + (6.25 * height) - (4.92 * AGE)) - 161d;

                    double GymTime = Double.valueOf(tvGymTime.getText().toString());
                    double AreoTime = Double.valueOf(tvAreoTime.getText().toString());

                    TEF = BMR + (GymTime * GymTEA) + (AreoTime * AreoTEA) + (GymEPOC * BMR) + AreoEPOC + somatotype;

                    RESULT = TEF + (0.1d * TEF);

                    tvResult.setText(""+String.format("%.0f", RESULT));

                    tvTEAresult.setText("AreoEPOC "+GymEPOC);
                    tvAREOresult.setText("AreoTEA "+GymTEA);



                    return;
                }


                break;
        }

    }



}

/*
    private void bmrManCount() {
        etWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                weight = Integer.valueOf(etWeight.getText().toString());
                tvResult.setText("" + weight);
            }
        });
        etHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                height = Integer.valueOf(etHeight.getText().toString());
                int RESULT = weight + height;
                tvResult.setText("" + RESULT);
            }
        });
        etAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                AGE = Integer.valueOf(etAge.getText().toString());
                int RESULT = weight + height + AGE;
                tvResult.setText("" + RESULT);
            }
        });
        return;

    }

    private void bmrWomanCount(){
        etWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                weight = Integer.valueOf(etWeight.getText().toString());
                tvResult.setText("" + weight);
            }
        });
        etHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                height = Integer.valueOf(etHeight.getText().toString());
                int RESULT = weight * height;
                tvResult.setText("" + RESULT);
            }
        });
        etAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                AGE = Integer.valueOf(etAge.getText().toString());
                int RESULT = weight * height * AGE;
                tvResult.setText("" + RESULT);
            }
        });
    }

}


EditText searchTo = (EditText)findViewById(R.id.medittext);
searchTo.addTextChangedListener(new TextWatcher() {
    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        doSomething();
    }
});
 */