package com.brus5.lukaszkrawczak.fitx;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.brus5.lukaszkrawczak.fitx.Metacalc.Calculator;
import com.brus5.lukaszkrawczak.fitx.Metacalc.MetacalcAreoActivity;
import com.brus5.lukaszkrawczak.fitx.Metacalc.MetacalcGymActivity;
import com.brus5.lukaszkrawczak.fitx.Metacalc.MetacalcUserInfoShowRequest;
import com.brus5.lukaszkrawczak.fitx.Metacalc.MetacalcUserUpdateKcalresRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MetacalcActivity3 extends AppCompatActivity implements View.OnClickListener {

    /*OLD VARIABLES*/
    SeekBar sbGymTEA, sbAreoTEA, sbNEAT, sbGymTime, sbAreoTime;
    RadioButton rbWoman, rbMan;
    EditText etWeight, etHeight, etAge;

    TextView tvResult, tvGymTEA, tvAreoTEA, tvGymTime, tvAreoTime, tvTEAresult, tvAREOresult;
    Switch switchGym, switchIsAreo;
    ImageView ivNEAT;
    /*OLD VARIABLES*/


    /*NEW VARIABLES*/
    TextView textViewWeight, textViewHeight, textViewAge, textViewGymTime, textViewGymIntensity, textViewAreoTime, textViewAreoIntensity,textViewFinalResult;

    TextView textViewNoGym, textView19, textView30, textView24, textViewNoAreo, textView27, textView34, textView28, textView16, textView17,textView22,textView29;

    FloatingActionButton buttonGym, buttonAreo;

    Double finalResult;

    ImageView imageViewSomatotype;

    Button buttonCalcAdd;
    // user variables
    int userID;
    int userAge;
    Double userHeight, userWeight;
    String userFirstName, userUserName, userMale;

    // calc variables
    double calcAreoTime = 0d;
    double calcAreoTea = 5d;
    double calcAreoEpoc = 5d;
    double calcGymTime = 0d;
    double calcGymTea = 7d;
    double calcGymEpoc = 0.04d;
    double calcSomatotype;

    ArrayList<String> userWeightArray = new ArrayList<>();
    ArrayList<String> userHeightArray = new ArrayList<>();
    ArrayList<String> userSomatotypeArray = new ArrayList<>();
    ArrayList<String> userDateArray = new ArrayList<>();

    Button buttonCount;
    final private static String TAG = "MetacalcActivity";

    /* Gettings date */
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String dateToday = simpleDateFormat.format(c.getTime());

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_meta_calc3);
        getWindow().setStatusBarColor(ContextCompat.getColor(MetacalcActivity3.this, R.color.color_meta_calc_statusbar));
        init();
        getUserPersonalData(); // getting userInfo by Intent method

        batchPersonalInformation(); // loading information about user from database
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initialize();        // initialize method affter look below

            }
        }, 2000);

        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                setTextViews(); //
                calculateFinalResukt();
                textViewFinalResult.setText(String.format("%.0f",finalResult));
            }
        }, 2500);
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },3000);
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "onStart");
        getUserActivityData(); // getting userActivity by Intent method
        super.onStart();
    }


    private void init() {
        buttonGym = findViewById(R.id.buttonGym);
        buttonGym.setOnClickListener(this);
        buttonAreo = findViewById(R.id.buttonAreo);
        buttonAreo.setOnClickListener(this);
        textViewWeight = findViewById(R.id.textViewWeight);
        textViewHeight = findViewById(R.id.textViewHeight);
        textViewAge = findViewById(R.id.textViewAge);
        textView17 = findViewById(R.id.textView17);
        textView16 = findViewById(R.id.textView16);
        textViewGymTime = findViewById(R.id.textViewGymTime);
        textViewGymIntensity = findViewById(R.id.textViewGymIntensity);
        textViewNoGym = findViewById(R.id.textViewNoGym);
        textView19 = findViewById(R.id.textView19);
        textView30 = findViewById(R.id.textView30);
        textView24 = findViewById(R.id.textView24);
        textViewAreoTime = findViewById(R.id.textViewAreoTime);
        textViewAreoIntensity = findViewById(R.id.textViewAreoIntensity);
        textViewNoAreo = findViewById(R.id.textViewNoAreo);
        textView27 = findViewById(R.id.textView27);
        textView28 = findViewById(R.id.textView28);
        textView34 = findViewById(R.id.textView34);
        buttonCalcAdd = findViewById(R.id.buttonCalcAdd);
        buttonCalcAdd.setOnClickListener(this);
        textView22 = findViewById(R.id.textView22);
        textView29 = findViewById(R.id.textView29);
        textViewFinalResult = findViewById(R.id.textViewFinalResult);
        imageViewSomatotype = findViewById(R.id.imageViewSomatotype);
    }

    private void initialize() {

    }

    private void getUserPersonalData() {
        Intent intent = getIntent();
        userID = intent.getIntExtra("id", 0);
        userFirstName = intent.getStringExtra("name");
        userUserName = intent.getStringExtra("username");
        userAge = intent.getIntExtra("age", 0);
        userMale = intent.getStringExtra("male");
    }

    private void getUserActivityData() {

    }

    private void batchPersonalInformation() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray server_response = jsonObject.getJSONArray("server_response");

                    String weight, height, somatotype, date;

                    if (server_response.length() > 0) {
                        for (int i = 0; i < server_response.length(); i++) {
                            JSONObject c = server_response.getJSONObject(i);

                            weight = c.getString("weight");
                            height = c.getString("height");
                            somatotype = c.getString("somatotype");
                            date = c.getString("date");

                            userWeightArray.add(weight);
                            userHeightArray.add(height);
                            userSomatotypeArray.add(somatotype);
                            userDateArray.add(date);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MetacalcUserInfoShowRequest metacalcUserInfoShow = new MetacalcUserInfoShowRequest(userUserName, responseListener);
        RequestQueue queue2 = Volley.newRequestQueue(MetacalcActivity3.this);
        queue2.add(metacalcUserInfoShow);

    }

    private void calculateFinalResukt() {
        Calculator calculator = new Calculator();
        calculator.setHeight(userHeight);
        calculator.setWeight(userWeight);
        calculator.setAge(userAge);
        calculator.setAreoTime(calcAreoTime);
        calculator.setAreoTea(calcAreoTea);
        calculator.setAreoEpoc(calcAreoEpoc);
        calculator.setGymTime(calcGymTime);
        calculator.setGymTea(calcGymTea);
        calculator.setGymEpoc(calcGymEpoc);
        calculator.setSomatotype(calcSomatotype);
        calculator.setBMR(userMale);
        calculator.setSomatotype(Double.valueOf(userSomatotypeArray.get(userSomatotypeArray.size() - 1)));
        Log.e(TAG, "countTEF(): " + calculator.countTEF());
        Log.e(TAG, "Double.valueOf(userSomatotypeArray.get(userSomatotypeArray.size()-1))" + Double.valueOf(userSomatotypeArray.get(userSomatotypeArray.size() - 1)));
        Log.e(TAG, "calculator.finalKcalResult()" + calculator.finalKcalResult());
        finalResult = calculator.finalKcalResult();
    }

    private void setTextViews() {
        userHeight = Double.valueOf(userHeightArray.get(userHeightArray.size() - 1));
        userWeight = Double.valueOf(userWeightArray.get(userWeightArray.size() - 1));
        textViewHeight.setText(String.valueOf(userHeight));
        textViewWeight.setText(String.valueOf(userWeight));
        textViewAge.setText(String.valueOf(userAge));
        calcSomatotype = Double.valueOf(userSomatotypeArray.get(userSomatotypeArray.size()-1));

        if (calcSomatotype == 200d){
            imageViewSomatotype.setImageResource(R.drawable.somatotype_man_endomorph_);
        }else if (calcSomatotype == 500d){
            imageViewSomatotype.setImageResource(R.drawable.somatotype_man_mezomorph_);
        }else if (calcSomatotype == 900d){
            imageViewSomatotype.setImageResource(R.drawable.somatotype_man_ectomorph_);
        }
        // set visibility of kg's and cm's value after loaded data
        textView16.setVisibility(View.VISIBLE);
        textView17.setVisibility(View.VISIBLE);

        // set visibility of "Your score is ... kCal"
        textView22.setVisibility(View.VISIBLE);
        textViewFinalResult.setVisibility(View.VISIBLE);
        textView29.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                calcGymTime = data.getDoubleExtra("calcGymTime", 0);
                calcGymTea = data.getDoubleExtra("calcGymTea", 7d);
                calcGymEpoc = data.getDoubleExtra("calcGymEpoc", 0.04d);

                calcAreoTime = data.getDoubleExtra("calcAreoTime", 0);
                calcAreoTea = data.getDoubleExtra("calcAreoTea", 5d);
                calcAreoEpoc = data.getDoubleExtra("calcAreoEpoc", 5d);

                textViewAreoTime.setText("" + String.format("%.0f", calcAreoTime));
                if (calcAreoTea == 5d) {
                    textViewAreoIntensity.setText("low");
                }
                if (calcAreoTea == 7d) {
                    textViewAreoIntensity.setText("medium");
                }
                if (calcAreoTea == 10d) {
                    textViewAreoIntensity.setText("high");
                }

                textViewGymTime.setText("" + String.format("%.0f", calcGymTime));
                if (calcGymTea == 7d) {
                    textViewGymIntensity.setText("low");
                }
                if (calcGymTea == 8d) {
                    textViewGymIntensity.setText("medium");
                }
                if (calcGymTea == 9d) {
                    textViewGymIntensity.setText("high");
                }

                if (calcGymTime > 0d) {
                    textViewNoGym.setVisibility(View.INVISIBLE);
                    textViewGymTime.setVisibility(View.VISIBLE);
                    textViewGymIntensity.setVisibility(View.VISIBLE);
                    textView19.setVisibility(View.VISIBLE);
                    textView24.setVisibility(View.VISIBLE);
                    textView30.setVisibility(View.VISIBLE);
                } else {
                    textViewNoGym.setVisibility(View.VISIBLE);
                    textViewGymTime.setVisibility(View.INVISIBLE);
                    textViewGymIntensity.setVisibility(View.INVISIBLE);
                    textView19.setVisibility(View.INVISIBLE);
                    textView24.setVisibility(View.INVISIBLE);
                    textView30.setVisibility(View.INVISIBLE);
                }

                if (calcAreoTime > 0d) {
                    textViewNoAreo.setVisibility(View.INVISIBLE);
                    textViewAreoTime.setVisibility(View.VISIBLE);
                    textViewAreoIntensity.setVisibility(View.VISIBLE);
                    textView27.setVisibility(View.VISIBLE);
                    textView34.setVisibility(View.VISIBLE);
                    textView28.setVisibility(View.VISIBLE);
                } else {
                    textViewNoAreo.setVisibility(View.VISIBLE);
                    textViewAreoTime.setVisibility(View.INVISIBLE);
                    textViewAreoIntensity.setVisibility(View.INVISIBLE);
                    textView27.setVisibility(View.INVISIBLE);
                    textView34.setVisibility(View.INVISIBLE);
                    textView28.setVisibility(View.INVISIBLE);
                }

                Calculator calculator1 = new Calculator();
                calculator1.setHeight(userHeight);
                calculator1.setWeight(userWeight);
                calculator1.setAge(userAge);
                calculator1.setAreoTime(calcAreoTime);
                calculator1.setAreoTea(calcAreoTea);
                calculator1.setAreoEpoc(calcAreoEpoc);
                calculator1.setGymTime(calcGymTime);
                calculator1.setGymTea(calcGymTea);
                calculator1.setGymEpoc(calcGymEpoc);
                calculator1.setSomatotype(calcSomatotype);
                calculator1.setBMR(userMale);
                calculator1.setSomatotype(Double.valueOf(userSomatotypeArray.get(userSomatotypeArray.size() - 1)));
                calculator1.countTEF();
                finalResult = calculator1.finalKcalResult();
                textViewFinalResult.setText(String.format("%.0f",finalResult));

                Log.e(TAG, "finalResult " + finalResult);
                Log.e(TAG, "calcGymTime " + calcGymTime);
                Log.e(TAG, "calcGymTea " + calcGymTea);
                Log.e(TAG, "calcGymEpoc " + calcGymEpoc);

                Log.e(TAG, "calcAreoTime " + calcAreoTime);
                Log.e(TAG, "calcAreoTea " + calcAreoTea);
                Log.e(TAG, "calcAreoEpoc " + calcAreoEpoc);


            }
        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.buttonGym:
                Log.e(TAG, "buttonGym Clicked");

                Intent intent = new Intent(MetacalcActivity3.this, MetacalcGymActivity.class);
                intent.putExtra("calcAreoTime", calcAreoTime);
                intent.putExtra("calcAreoTea", calcAreoTea);
                intent.putExtra("calcAreoEpoc", calcAreoEpoc);
                startActivityForResult(intent, 1);

                break;
            case R.id.buttonAreo:
                Log.e(TAG, "buttonAreo Clicked");
                Intent intent1 = new Intent(MetacalcActivity3.this, MetacalcAreoActivity.class);
                intent1.putExtra("calcGymTime", calcGymTime);
                intent1.putExtra("calcGymTea", calcGymTea);
                intent1.putExtra("calcGymEpoc", calcGymEpoc);
                startActivityForResult(intent1, 1);
                break;

            case R.id.buttonCalcAdd:
                Calculator calculator = new Calculator();
                calculator.setHeight(userHeight);
                calculator.setWeight(userWeight);
                calculator.setAge(userAge);
                calculator.setAreoTime(calcAreoTime);
                calculator.setAreoTea(calcAreoTea);
                calculator.setAreoEpoc(calcAreoEpoc);
                calculator.setGymTime(calcGymTime);
                calculator.setGymTea(calcGymTea);
                calculator.setGymEpoc(calcGymEpoc);
                calculator.setSomatotype(calcSomatotype);
                calculator.setBMR(userMale);
                calculator.setSomatotype(Double.valueOf(userSomatotypeArray.get(userSomatotypeArray.size() - 1)));
                Log.e(TAG, "countTEF(): " + calculator.countTEF());
                Log.e(TAG, "Double.valueOf(userSomatotypeArray.get(userSomatotypeArray.size()-1))" + Double.valueOf(userSomatotypeArray.get(userSomatotypeArray.size() - 1)));
                Log.e(TAG, "calculator.finalKcalResult()" + calculator.finalKcalResult());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response",""+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("row_in_database");
                            if (success){
                                Toast.makeText(MetacalcActivity3.this, "Uploaded previous score", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MetacalcActivity3.this, "Added new score", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                MetacalcUserUpdateKcalresRequest metacalcUserUpdateKcalresRequest = new MetacalcUserUpdateKcalresRequest(userID,userUserName,String.format("%.0f",calculator.finalKcalResult()),dateToday,responseListener);
                RequestQueue queue = Volley.newRequestQueue(MetacalcActivity3.this);
                queue.add(metacalcUserUpdateKcalresRequest);

                break;
        }

        if (userMale.equals("w")) {


        } else if (userMale.equals("m")) {

        }
    }
}
