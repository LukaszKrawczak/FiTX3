package com.brus5.lukaszkrawczak.fitx;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Graph.GraphShowKcalRequest;
import com.brus5.lukaszkrawczak.fitx.Graph.GraphShowKcalResultRequest;
import com.brus5.lukaszkrawczak.fitx.User.UserInfoShowRequest;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    final private static String TAG = "MainActivity";

    String userFirstName, userName, userBirthday, userPassword, userEmail, userMale, userID;

    int userIDint, userAgeint;

    boolean defaultLogin = false;
    Button btn;
    ArrayList<String> resultArray = new ArrayList<>();
    ArrayList<String> dateArray = new ArrayList<>();

    ArrayList<String> weightArray = new ArrayList<>();
    ArrayList<String> wDateArray = new ArrayList<>();



    final List<Date> dateList = new ArrayList<>();
    final List<Date> dateList1 = new ArrayList<>();

    /* Gettings date */
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String date = simpleDateFormat.format(c.getTime());

    GraphView graph;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(TAG,"onCreate();");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setElevation(0);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.color_main_activity_statusbar));
        graph = findViewById(R.id.graph123);


        final Intent intent = getIntent();

        defaultLogin = SaveSharedPreference.getDefLogin(MainActivity.this);

        Log.e(TAG, "onCreate: SaveShared "+SaveSharedPreference.getDefLogin(MainActivity.this));

        if (defaultLogin){
//            userName = intent.getStringExtra("userName");
            userName = SaveSharedPreference.getUserName(MainActivity.this);
        }
        else {
            userName = com.facebook.Profile.getCurrentProfile().getId();
            Bundle b = intent.getExtras();
            String nameTest = b.getString("first_name");
            Log.e(TAG,"nameTest: "+nameTest);
            Log.e(TAG, "onCreate: SaveShared fb "+SaveSharedPreference.getDefLogin(MainActivity.this));
        }

//        Calendar calendar = Calendar.getInstance();
//        Date d1 = calendar.getTime();
//        calendar.add(Calendar.DATE,-90);
//        final Date d2 = calendar.getTime();
//

//        new LongOperation().execute("");


        Log.e(TAG,"userName "+userName);


        Button buttonProfile = findViewById(R.id.buttonProfile);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UserProfileActivity.class);
                intent.putExtra("id",userID);
                intent.putExtra("name",userFirstName);
                intent.putExtra("userName",userName);
                intent.putExtra("age", userAgeint);
                intent.putExtra("male",userMale);
                intent.putExtra("defaultLogin",defaultLogin);
                startActivity(intent);
            }
        });


        Button buttonDiet = findViewById(R.id.buttonDiet);
        buttonDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DietActivity.class);
                intent.putExtra("userIDint",userIDint);
                intent.putExtra("userFirstName",userFirstName);
                intent.putExtra("userName",userName);
                intent.putExtra("userBirthday",userBirthday);
                intent.putExtra("userAgeint",userAgeint);
                intent.putExtra("userPassword",userPassword);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userMale",userMale);
                startActivity(intent);
            }
        });


        Button buttonMetacalc = findViewById(R.id.buttonMetacalc);
        buttonMetacalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MetacalcActivity3.class);
                intent.putExtra("userIDint",userIDint);
                intent.putExtra("userFirstName",userFirstName);
                intent.putExtra("userName",userName);
                intent.putExtra("userBirthday",userBirthday);
                intent.putExtra("userAgeint",userAgeint);
                intent.putExtra("userPassword",userPassword);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userMale",userMale);
                startActivity(intent);
            }
        });


        Button buttonTraining = findViewById(R.id.buttonTraining);
        buttonTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TrainingActivity.class);
                intent.putExtra("userIDint",userIDint);
                intent.putExtra("userFirstName",userFirstName);
                intent.putExtra("userName",userName);
                intent.putExtra("userBirthday",userBirthday);
                intent.putExtra("userAgeint",userAgeint);
                intent.putExtra("userPassword",userPassword);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userMale",userMale);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        dateList.clear();
        dateList1.clear();
        resultArray.clear();
        dateArray.clear();
        wDateArray.clear();
        weightArray.clear();
        graph.removeAllSeries();
        new LongOperation().execute("");
        Log.e(TAG,"onStart()");
    }

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            for (int i = 0; i < 5; i++) {
                try {


                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            Log.e(TAG,"doInBackground");
            return "userID "+userID+"\nuserFirstName "+userFirstName+"\nuserName "+userName+"\nuserAge "+String.valueOf(userAgeint)+"\nuserPassword "+userPassword+"\nuserEmail "+userEmail+"\nuserMale "+userMale;
        }
        @Override
        protected void onPostExecute(String result) {
            TextView txt = (TextView) findViewById(R.id.output);
            txt.setText(result); // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
            Log.e(TAG,"onPostExecute");
        }

        @Override
        protected void onPreExecute() {
            TextView txt = (TextView) findViewById(R.id.output);
            txt.setText("Loading...");
            Log.e(TAG,"onPreExecute");
            batchPersonalInformation();
            batchGraphResult();
            batchWeightResult();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            Log.e(TAG,"onProgressUpdate");
        }
    }
    private void batchPersonalInformation() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG,"response"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray server_response = jsonObject.getJSONArray("server_response");

                    if (server_response.length() > 0) {
                        for (int i = 0; i < server_response.length(); i++) {
                            JSONObject c = server_response.getJSONObject(i);

                            userID = c.getString("user_id");
                            userFirstName = c.getString("name");
                            userName=  c.getString("username");
                            userBirthday = c.getString("birthday");
                            userPassword = c.getString("password");
                            userEmail = c.getString("email");
                            userMale = c.getString("male");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int day = Integer.valueOf(userBirthday.substring(0,2));
                int month = Integer.valueOf(userBirthday.substring(3,5));
                int year = Integer.valueOf(userBirthday.substring(6,10));
                Log.i(TAG,"getAge(year,month,day)"+getAge(year,month,day));
                userAgeint = Integer.valueOf(getAge(year,month,day));
                userIDint = Integer.valueOf(userID);
            }

        };
        UserInfoShowRequest userInfoShowRequest = new UserInfoShowRequest(userName,responseListener);
        RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this);
        queue1.add(userInfoShowRequest);
    }



    private void batchGraphResult() {
        final Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("response"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean noresult = jsonObject.getBoolean("noresult");
                    if (noresult){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Data will appear when you will give us some data from Metabolic Calculator")
                                .setNegativeButton("OK, I see", null)
                                .create()
                                .show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray server_response = jsonObject.getJSONArray("server_response");

                    // looping through All Contacts

                    String date;
                    String RESULT;

                    System.out.println("response1"+response);

                    if (server_response.length() > 0) {
                        for (int i = 0; i < server_response.length(); i++) {
                            JSONObject c = server_response.getJSONObject(i);
                            date = c.getString("date");
                            RESULT = c.getString("RESULT");
                            HashMap<String, String> server = new HashMap<>();
                            server.put("RESULT", RESULT);
                            server.put("date", date);
                            dateArray.add(date);
                            resultArray.add(RESULT);
                        }
                    }
                    else if (server_response.length() == 0){
                        for (int i = 0; i < 10; i++) {
                            HashMap<String, String> server = new HashMap<>();
                            RESULT = "2000";
                            resultArray.add(RESULT);
                            dateList.add(new Date(118,1,1));
                        }
                    }

                    for (String graphDAT : dateArray) {
                        dateList.add(simpleDateFormat.parse(graphDAT));
                    }
                    Calendar calendar = Calendar.getInstance();
                    final Date d1 = calendar.getTime();
                    calendar.add(Calendar.DATE,-31);

                    if (dateArray.size() == 0){
                        dateArray.add("10.01.2018");
                    }

                    LineGraphSeries<DataPoint> series5 = new LineGraphSeries<>(new DataPoint[]{});

                    int count = dateList.size();
                    for (int j = count; j>0; j--){
                        Date x = dateList.get(dateList.size()-j);
                        Double y = Double.parseDouble(resultArray.get(resultArray.size()-j));
                        DataPoint v = new DataPoint(x,y);
                        series5.appendData(v,true,count,true);
                    }
                    graph.addSeries(series5);
                    series5.setThickness(3);
                    series5.setAnimated(true);
                    series5.setDrawDataPoints(false);
                    series5.setDataPointsRadius(3);
                    series5.setColor(Color.argb(255,82,153,173));
                    series5.setOnDataPointTapListener(new OnDataPointTapListener() {
                        @Override
                        public void onTap(Series series, DataPointInterface dataPoint) {
                            Log.d(TAG, "onTap: dataPoint.getY() "+dataPoint.getY());
                            Log.d(TAG, "onTap: dataPoint.getX() "+dataPoint.getX());
                            Toast.makeText(MainActivity.this, dataPoint.getY()+ " kCal", Toast.LENGTH_SHORT).show();
                        }
                    });

//                    calendar.add(Calendar.DATE,-120);
//                    final Date d3 = calendar.getTime();
//                    long l7 = d3.getTime();
//                    calendar.add(Calendar.DATE,260);
//                    final Date d4 = calendar.getTime();
//                    long l9 = d4.getTime();
//                    Date d8 = dateList.get(dateList.size()-1);
//                    long l8 = d8.getTime()+2*86400;

//                    Log.e("SPRAWdzAM",""+dateList.get(0).getTime());
//                    Log.e("SPRAWdzAM",""+l8);

                    graph.getGridLabelRenderer().setHorizontalLabelsAngle(150);
                    graph.getGridLabelRenderer().setTextSize(25);
                    graph.getGridLabelRenderer().setLabelsSpace(20);
                    graph.getGridLabelRenderer().setLabelHorizontalHeight(80);
                    graph.getGridLabelRenderer().isHumanRounding();
                    graph.getGridLabelRenderer().setGridColor(Color.argb(255,204,204,204));


                    Viewport viewport = graph.getViewport();

                    viewport.setScalable(true);
                    viewport.setScalableY(false);

                    viewport.setXAxisBoundsManual(true);
                    viewport.setMinX(1.520114E12); //1.5185628E12
                    viewport.setMaxX(1.521714E12); // 1.5186492E12
                    viewport.setScrollable(true);
                    viewport.scrollToEnd();


                    graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext()){
                        @Override
                        public String formatLabel(double value, boolean isValueX) {
                            if(isValueX){
                                return simpleDateFormat.format(new Date((long) value));
                            }
                            else {
                                return super.formatLabel(value, false);
                            }

                        }
                    });
                }
                catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Problem with connection", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                catch (ParseException e) {
                    Toast.makeText(MainActivity.this, "Problem with connection", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }};

        GraphShowKcalRequest graphShowKcalRequest = new GraphShowKcalRequest(userName, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(graphShowKcalRequest);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                generateData2();
            }
        },100);

    }

    private void batchWeightResult() {
        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response "+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean noresult = jsonObject.getBoolean("noresult");
                    if (noresult){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Data will appear when you will give us some data from Metabolic Calculator")
                                .setNegativeButton("OK, I see", null)
                                .create()
                                .show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray server_response = jsonObject.getJSONArray("server_response");

                    // looping through All Contacts

                    String RESULT;
                    String date;



                    if (server_response.length() > 0) {
                        for (int i = 0; i < server_response.length(); i++) {
                            JSONObject c = server_response.getJSONObject(i);
                            date = c.getString("date");
                            RESULT = c.getString("RESULT");
                            HashMap<String, String> server = new HashMap<>();
                            server.put("weight", RESULT);
                            server.put("date", date);
                            wDateArray.add(date);
                            weightArray.add(RESULT);
                        }
                    }
                    else if (server_response.length() == 0){
                        for (int i = 0; i < 10; i++) {
                            HashMap<String, String> server = new HashMap<>();
                            RESULT = "70";
                            weightArray.add(RESULT);
                            dateList1.add(new Date(118,1,1));
                        }
                    }

                    for (String graphDAT : wDateArray) {
                        dateList1.add(simpleDateFormat.parse(graphDAT));
                    }
                    Calendar calendar = Calendar.getInstance();
                    final Date d1 = calendar.getTime();
                    calendar.add(Calendar.DATE,-31);

                    if (wDateArray.size() == 0){
                        wDateArray.add("10.01.2018");
                    }

                    LineGraphSeries<DataPoint> series5 = new LineGraphSeries<>(new DataPoint[]{});

                    int count = dateList1.size();
                    for (int j = count; j>0; j--){
                        Date x = dateList1.get(dateList1.size()-j);
                        Double y = Double.parseDouble(weightArray.get(weightArray.size()-j));
                        DataPoint v = new DataPoint(x,y);
                        series5.appendData(v,true,count,true);
                    }
                    graph.addSeries(series5);
                    series5.setThickness(3);
                    series5.setAnimated(true);
                    series5.setDrawDataPoints(false);
                    series5.setDataPointsRadius(3);
                    series5.setColor(Color.argb(255,255,0,0));
                    series5.setOnDataPointTapListener(new OnDataPointTapListener() {
                        @Override
                        public void onTap(Series series, DataPointInterface dataPoint) {
                            Log.d(TAG, "onTap: dataPoint.getY() "+dataPoint.getY());
                            Log.d(TAG, "onTap: dataPoint.getX() "+dataPoint.getX());
                            Toast.makeText(MainActivity.this, dataPoint.getY()+ " eated kcal", Toast.LENGTH_SHORT).show();
                        }
                    });

//                    calendar.add(Calendar.DATE,-120);
//                    final Date d3 = calendar.getTime();
//                    long l7 = d3.getTime();
//                    calendar.add(Calendar.DATE,260);
//                    final Date d4 = calendar.getTime();
//                    long l9 = d4.getTime();
//                    Date d8 = dateList.get(dateList.size()-1);
//                    long l8 = d8.getTime()+2*86400;

//                    Log.e("SPRAWdzAM",""+dateList.get(0).getTime());
//                    Log.e("SPRAWdzAM",""+l8);

                    graph.getGridLabelRenderer().setHorizontalLabelsAngle(150);
                    graph.getGridLabelRenderer().setTextSize(25);
                    graph.getGridLabelRenderer().setLabelsSpace(20);
                    graph.getGridLabelRenderer().setLabelHorizontalHeight(80);
                    graph.getGridLabelRenderer().isHumanRounding();
                    graph.getGridLabelRenderer().setGridColor(Color.argb(255,152,150,150));

                    Viewport viewport = graph.getViewport();

                    viewport.setScalable(true);
                    viewport.setScalableY(false);

                    viewport.setXAxisBoundsManual(true);
                    viewport.setMinX(1.520114E12); //1.5185628E12
                    viewport.setMaxX(1.521714E12); //1.5186492E12
                    viewport.setScrollable(true);
                    viewport.scrollToEnd();


                    graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext()){
                        @Override
                        public String formatLabel(double value, boolean isValueX) {
                            if(isValueX){
                                return simpleDateFormat.format(new Date((long) value));
                            }
                            else {
                                return super.formatLabel(value, false);
                            }

                        }
                    });
                }
                catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        };
        Log.e(TAG, "batchGraphResult: userIDint "+userName);
        GraphShowKcalResultRequest graphShowKcalResultRequest = new GraphShowKcalResultRequest(userName,responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this);
        queue1.add(graphShowKcalResultRequest);
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                Collections.sort(dateList);
                generateData3();
            }
        },100);
    }


    private DataPoint[] generateData2(){

        int count = dateList.size();
        DataPoint[] values = new DataPoint[count];
        Date x;
        Double y;
        for (int i = 1; i<count; i++){
            x = dateList.get(dateList.size()-i);
            y = Double.parseDouble(resultArray.get(resultArray.size()-i));
            DataPoint v = new DataPoint(x,y);
            values[i] = v;
        }
        for (int j = count; j>=0; j--){
            values = new DataPoint[j];
        }
        return values;
    }

    private DataPoint[] generateData3(){

        int count = dateList1.size();
        DataPoint[] values = new DataPoint[count];
        Date x;
        Double y;
        for (int i = 1; i<count; i++){
            x = dateList1.get(dateList1.size()-i);
            y = Double.parseDouble(weightArray.get(weightArray.size()-i));
            DataPoint v = new DataPoint(x,y);
            values[i] = v;
        }
        for (int j = count; j>=0; j--){
            values = new DataPoint[j];
        }
        return values;
    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }








}

