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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Metacalc.Calculator;
import com.brus5.lukaszkrawczak.fitx.Metacalc.MetacalcGymActivity;
import com.brus5.lukaszkrawczak.fitx.Metacalc.MetacalcUserInfoShowRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MetacalcActivity3 extends AppCompatActivity implements View.OnClickListener{

    /*OLD VARIABLES*/
    SeekBar sbGymTEA,sbAreoTEA, sbNEAT, sbGymTime, sbAreoTime;
    RadioButton rbWoman, rbMan;
    EditText etWeight, etHeight, etAge;

    TextView tvResult, tvGymTEA, tvAreoTEA, tvGymTime, tvAreoTime, tvTEAresult, tvAREOresult;
    Switch switchGym, switchIsAreo;
    ImageView ivNEAT;
    /*OLD VARIABLES*/


    /*NEW VARIABLES*/
    TextView textViewWeight,textViewHeight,textViewAge;

    FloatingActionButton buttonGym,buttonAreo;

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

    ArrayList<String> userWeightArray       = new ArrayList<>();
    ArrayList<String> userHeightArray       = new ArrayList<>();
    ArrayList<String> userSomatotypeArray   = new ArrayList<>();
    ArrayList<String> userDateArray         = new ArrayList<>();

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


        getWindow().setStatusBarColor(ContextCompat.getColor(MetacalcActivity3.this,R.color.color_meta_calc_statusbar));





        init();
        getUserPersonalData(); // getting userInfo by Intent method
        batchPersonalInformation(); // loading information about user from database


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initialize();        // initialize method affter look below
                setTextViews(); //
            }
        },2000);
    }

    private void init() {
        buttonGym = findViewById(R.id.buttonGym);
        buttonGym.setOnClickListener(this);
        buttonAreo = findViewById(R.id.buttonAreo);
        buttonAreo.setOnClickListener(this);
        textViewWeight = findViewById(R.id.textViewWeight);
        textViewHeight = findViewById(R.id.textViewHeight);
        textViewAge = findViewById(R.id.textViewAge);
    }
    private void initialize() {

    }

    private void getUserPersonalData() {
        Intent intent = getIntent();
        userID              = intent.getIntExtra                    ("id",0);
        userFirstName       = intent.getStringExtra                 ("name");
        userUserName        = intent.getStringExtra                 ("username");
        userAge             = intent.getIntExtra                    ("age",0);
        userMale            = intent.getStringExtra                 ("male");
    }

    private void batchPersonalInformation() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray server_response = jsonObject.getJSONArray("server_response");

                    String weight, height, somatotype, date;

                    if (server_response.length() > 0){
                        for (int i = 0; i < server_response.length(); i++) {
                            JSONObject c = server_response.getJSONObject(i);

                            weight = c.getString("weight");
                            height = c.getString("height");
                            somatotype = c.getString("somatotype");
                            date = c.getString("date");

                            userWeightArray     .add(weight);
                            userHeightArray     .add(height);
                            userSomatotypeArray .add(somatotype);
                            userDateArray       .add(date);
                        }
                    }

                } catch (JSONException e) {e.printStackTrace();}
            }
        };
        MetacalcUserInfoShowRequest metacalcUserInfoShow = new MetacalcUserInfoShowRequest(userUserName, responseListener);
        RequestQueue queue2 = Volley.newRequestQueue(MetacalcActivity3.this);
        queue2.add(metacalcUserInfoShow);
    }

    private void calculateFinalResukt() {
        Calculator calculator = new Calculator();
        calculator.setHeight        (userHeight);
        calculator.setWeight        (userWeight);
        calculator.setAge           (userAge);
        calculator.setAreoTime      (calcAreoTime);
        calculator.setAreoTea       (calcAreoTea);
        calculator.setAreoEpoc      (calcAreoEpoc);
        calculator.setGymTime       (calcGymTime);
        calculator.setGymTea        (calcGymTea);
        calculator.setGymEpoc       (calcGymEpoc);
        calculator.setSomatotype    (calcSomatotype);
        calculator.setBMR           (userMale);
    }

    private void setTextViews() {
        userHeight = Double.valueOf(userHeightArray.get(userHeightArray.size() - 1));
        userWeight = Double.valueOf(userWeightArray.get(userWeightArray.size() - 1));
        textViewHeight.setText(String.valueOf(userHeight));
        textViewWeight.setText(String.valueOf(userWeight));
        textViewAge.setText(String.valueOf(userAge));
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.buttonGym:
                Log.e(TAG,"buttonGym Clicked");

                Intent intent = new Intent(MetacalcActivity3.this, MetacalcGymActivity.class);
                startActivity(intent);

                break;
            case R.id.buttonAreo:
                Log.e(TAG,"buttonAreo Clicked");
                break;
        }

        if (userMale.equals("w")){


        }else if (userMale.equals("m")){

        }
    }


}