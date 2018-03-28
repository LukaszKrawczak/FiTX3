package com.brus5.lukaszkrawczak.fitx;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

    final private static String TAG = "MetacalcActivity";

    // textview
    TextView textViewNoGym, textView19, textView30, textView24, textViewNoAreo, textView27, textView34, textView28, textView16, textView17, textView22, textView29;

    // textview with userinfo
    TextView textViewWeight, textViewHeight, textViewAge;

    // textview with training invo
    TextView textViewGymTime, textViewGymIntensity, textViewAreoTime, textViewAreoIntensity, textViewFinalResult, tvSomatotype;

    // buttons
    ProgressBar progressBarLoad1, progressBarLoad2;
    FloatingActionButton buttonGym, buttonAreo;
    Button buttonCalcAdd;

    // image of somatotype
    ImageView imageViewSomatotype;

    // user info variables
    String userName, userFirstName, userBirthday, userPassword, userEmail, userMale;
    int userIDint, userAgeint;

    double calcSomatotype=0d;
    Double finalResult=0d,userHeight=0d, userWeight=0d, userSomatotype=0d;
    // calc variables
    double calcAreoTime = 0d;
    double calcAreoTea = 5d;
    double calcAreoEpoc = 5d;
    double calcGymTime = 0d;
    double calcGymTea = 7d;
    double calcGymEpoc = 0.04d;

    ArrayList<String> userWeightArray = new ArrayList<>();
    ArrayList<String> userHeightArray = new ArrayList<>();
    ArrayList<String> userSomatotypeArray = new ArrayList<>();
    ArrayList<String> userDateArray = new ArrayList<>();



    // getting dateToday
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String dateToday = simpleDateFormat.format(c.getTime());

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_meta_calc3);

        // toolbar in xml file
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);

        // statusbarcolor
        getWindow().setStatusBarColor(ContextCompat.getColor(MetacalcActivity3.this, R.color.color_main_activity_statusbar));

        // getting userinfo from previous activity
        Intent intent1 = getIntent();
        userIDint = intent1.getIntExtra("userIDint",0);
        userFirstName = intent1.getStringExtra("userFirstName");
        userName = intent1.getStringExtra("userName");
        userBirthday = intent1.getStringExtra("userBirthday");
        userAgeint = intent1.getIntExtra("userAgeint",0);
        userPassword = intent1.getStringExtra("userPassword");
        userEmail = intent1.getStringExtra("userEmail");
        userMale = intent1.getStringExtra("userMale");
        Log.d(TAG,"userInfo: "+"ID: "+userIDint+", userFirstName: "+userFirstName+" userName: "+userName+" userBirthday: "+userBirthday+" userAgeint:"+userAgeint+" userPassword: userPassword: "+userPassword+" userEmail: "+userEmail+" userMale: "+userMale);

        // initializating items from XML file
        initItems();

        new LongRunningTask().execute();
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {


            }
        }, 2500);
    }


    private class LongRunningTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");

            return null;
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: ");
            // loading information about user from database
            getUserInfo();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "onPostExecute: ");

            super.onPostExecute(aVoid);
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    // initializating items from XML file
    private void initItems() {
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
        tvSomatotype = findViewById(R.id.tvSomatotype);
    }


    // getting last userinfo - weight, heigh, somatotype
    private void getUserInfo() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response "+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray server_response = jsonObject.getJSONArray("server_response");

                    String weight="", height="", somatotype="", date;

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
//                        userHeight = Double.valueOf(userHeightArray.get(userHeightArray.size() - 1));
//                        userWeight = Double.valueOf(userWeightArray.get(userWeightArray.size() - 1));

                        userHeight = Double.valueOf(height);
                        userWeight = Double.valueOf(weight);
                        userSomatotype = Double.valueOf(somatotype);
                        Log.e(TAG, "onResponse: userHeight+userWeight+somatotype"+userHeight+" "+userWeight+" "+somatotype);


                        // setting items in XML file
                        setTextViews();


                    }
                } catch (JSONException e) {
                    Toast.makeText(MetacalcActivity3.this, "Connection problems", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
            }
        }
        };
        MetacalcUserInfoShowRequest metacalcUserInfoShow = new MetacalcUserInfoShowRequest(userName, responseListener);
        RequestQueue queue2 = Volley.newRequestQueue(MetacalcActivity3.this);
        queue2.add(metacalcUserInfoShow);
    }

    // setting items in XML file
    private void setTextViews() {

        textViewHeight.setText(String.valueOf(userHeight));
        textViewWeight.setText(String.valueOf(userWeight));
        textViewAge.setText(String.valueOf(userAgeint));
        calcSomatotype = userSomatotype;

        calculateFinalResukt();
        textViewFinalResult.setText(String.format("%.0f",finalResult));

//        calcSomatotype = Double.valueOf(userSomatotypeArray.get(userSomatotypeArray.size()-1));

        if (calcSomatotype == 200d){
            tvSomatotype.setText("Endomorph");
            imageViewSomatotype.setImageResource(R.drawable.somatotype_man_endomorph_);
        }else if (calcSomatotype == 500d){
            tvSomatotype.setText("Mezomorph");
            imageViewSomatotype.setImageResource(R.drawable.somatotype_man_mezomorph_);
        }else if (calcSomatotype == 900d){
            tvSomatotype.setText("Ectomorph");
            imageViewSomatotype.setImageResource(R.drawable.somatotype_man_ectomorph_);
        }

        // set visibility of kg's and cm's value after loaded data
        textView16.setVisibility(View.VISIBLE);
        textView17.setVisibility(View.VISIBLE);
        progressBarLoad1 = findViewById(R.id.progressBarLoad1);
        progressBarLoad1.setVisibility(View.INVISIBLE);
        progressBarLoad2 = findViewById(R.id.progressBarLoad2);
        progressBarLoad2.setVisibility(View.INVISIBLE);

        // set visibility of "Your score is ... kCal"
        textView22.setVisibility(View.VISIBLE);
        textViewFinalResult.setVisibility(View.VISIBLE);
        textView29.setVisibility(View.VISIBLE);
    }

    // caculating kcal result
    private void calculateFinalResukt() {
        Calculator calculator = new Calculator();
        calculator.setHeight(userHeight);
        calculator.setWeight(userWeight);
        calculator.setAge(userAgeint);
        calculator.setAreoTime(calcAreoTime);
        calculator.setAreoTea(calcAreoTea);
        calculator.setAreoEpoc(calcAreoEpoc);
        calculator.setGymTime(calcGymTime);
        calculator.setGymTea(calcGymTea);
        calculator.setGymEpoc(calcGymEpoc);
        calculator.setSomatotype(calcSomatotype);
        calculator.setBMR(userMale);
//        calculator.setSomatotype(Double.valueOf(userSomatotypeArray.get(userSomatotypeArray.size() - 1)));
        calculator.setSomatotype(userSomatotype);

        /* DON'T REMOVE realising values, otherwise finalResult will be 0  */
        Log.e(TAG, "calculateFinalResukt: getting calc info: "+"userHeight: "+userHeight+" userWeight: "+userWeight+" userAgeint: "+userAgeint+" calcAreoTime: "+calcAreoTime+" calcAreoEpoc: "+calcAreoEpoc+" calcGymTime: "+calcGymTime+" calcGymTea: "+calcGymTea+" calcGymEpoc: "+" calcSomatotype: "+calcSomatotype+" userMale: "+userMale+" somatotype: "+userSomatotype);
        Log.e(TAG, "calculateFinalResukt: tef result: "+calculator.countTEF());
        Log.e(TAG, "calculateFinalResukt: somatotype: "+ userSomatotype);
        Log.e(TAG, "calculateFinalResukt: final result: "+ calculator.finalKcalResult());
        /* DON'T REMOVE realising values, otherwise finalResult will be 0  */

        finalResult = calculator.finalKcalResult();
    }

    // getting results from gym and cardio activities
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

                // calculating final result
                Calculator calculator1 = new Calculator();
                calculator1.setHeight(userHeight);
                calculator1.setWeight(userWeight);
                calculator1.setAge(userAgeint);
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

                Log.d(TAG, "finalResult " + finalResult);
                Log.d(TAG, "calcGymTime " + calcGymTime);
                Log.d(TAG, "calcGymTea " + calcGymTea);
                Log.d(TAG, "calcGymEpoc " + calcGymEpoc);

                Log.d(TAG, "calcAreoTime " + calcAreoTime);
                Log.d(TAG, "calcAreoTea " + calcAreoTea);
                Log.d(TAG, "calcAreoEpoc " + calcAreoEpoc);
            }
        }
    }

    // puhsing info to db
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
                calculator.setAge(userAgeint);
                calculator.setAreoTime(calcAreoTime);
                calculator.setAreoTea(calcAreoTea);
                calculator.setAreoEpoc(calcAreoEpoc);
                calculator.setGymTime(calcGymTime);
                calculator.setGymTea(calcGymTea);
                calculator.setGymEpoc(calcGymEpoc);
                calculator.setSomatotype(calcSomatotype);
                calculator.setBMR(userMale);
                calculator.setSomatotype(userSomatotype);

                Log.d(TAG, "onClick: tefresult: "+calculator.countTEF());
                Log.d(TAG, "onClick: somatotype: "+userSomatotype);
                Log.e(TAG, "onClick: final result: "+calculator.finalKcalResult());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: response "+response);
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
                MetacalcUserUpdateKcalresRequest metacalcUserUpdateKcalresRequest = new MetacalcUserUpdateKcalresRequest(userIDint,userName,String.format("%.0f",calculator.finalKcalResult()),dateToday,responseListener);
                RequestQueue queue = Volley.newRequestQueue(MetacalcActivity3.this);
                queue.add(metacalcUserUpdateKcalresRequest);

                break;
        }
    }
}
