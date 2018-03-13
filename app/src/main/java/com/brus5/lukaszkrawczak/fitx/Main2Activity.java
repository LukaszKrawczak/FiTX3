package com.brus5.lukaszkrawczak.fitx;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Graph.GraphShowKcalRequest;
import com.brus5.lukaszkrawczak.fitx.User.UserInfoShowRequest;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final private static String TAG = "MainActivity";

    String userName, userUserName, userBirthday, userPassword, userEmail, userMale, userID;



    int userIDint, userAgeint;

    boolean defaultLogin = true;
    Button btn;
    ArrayList<String> userGraphDateArray = new ArrayList<>();
    ArrayList<String> userGraphResultArray = new ArrayList<>();



    final List<Date> dateList = new ArrayList<>();

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setStatusBarColor(ContextCompat.getColor(Main2Activity.this,R.color.color_main_activity));


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final Intent intent = getIntent();

        defaultLogin = intent.getBooleanExtra("defaultLogin",false);

        if (defaultLogin){
            userUserName = intent.getStringExtra("username");
        }
        else {
            userUserName = com.facebook.Profile.getCurrentProfile().getId();
            Bundle b = intent.getExtras();
            String nameTest = b.getString("first_name");
            Log.e(TAG,"nameTest: "+nameTest);
        }

//        Calendar calendar = Calendar.getInstance();
//        Date d1 = calendar.getTime();
//        calendar.add(Calendar.DATE,-90);
//        final Date d2 = calendar.getTime();
//

        new LongOperation().execute("");


        Log.e(TAG,"userUserName1 "+userUserName);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main2, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
        // Handle the camera action
            Intent intent = new Intent(Main2Activity.this,UserProfileActivity.class);
            intent.putExtra("id",userID);
            intent.putExtra("name",userName);
            intent.putExtra("username",userUserName);
            intent.putExtra("age", userAgeint);
            intent.putExtra("male",userMale);
            intent.putExtra("defaultLogin",defaultLogin);
            startActivity(intent);
        } else if (id == R.id.nav_meta_calc) {
            Intent intent = new Intent(Main2Activity.this,MetacalcActivity3.class);
            intent.putExtra("id",userID);
            intent.putExtra("name",userName);
            intent.putExtra("username",userUserName);
            intent.putExtra("age", userAgeint);
            intent.putExtra("male",userMale);
            startActivity(intent);
        } else if (id == R.id.nav_diet) {
            Intent intent = new Intent(Main2Activity.this,DietActivity.class);
            intent.putExtra("id",userID);
            intent.putExtra("name",userName);
            intent.putExtra("username",userUserName);
            intent.putExtra("age", userAgeint);
            intent.putExtra("male",userMale);
            startActivity(intent);
        } else if (id == R.id.nav_training) {
            Intent intent = new Intent(Main2Activity.this,TrainingActivity.class);
            intent.putExtra("id",userID);
            intent.putExtra("name",userName);
            intent.putExtra("username",userUserName);
            intent.putExtra("age", userAgeint);
            intent.putExtra("male",userMale);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                            userName = c.getString("name");
                            userUserName=  c.getString("username");
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

            }

        };
        UserInfoShowRequest userInfoShowRequest = new UserInfoShowRequest("brus5",responseListener);
        RequestQueue queue1 = Volley.newRequestQueue(Main2Activity.this);
        queue1.add(userInfoShowRequest);
    }

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            for (int i = 0; i < 5; i++) {
                try {
                    batchPersonalInformation();

                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            Log.e(TAG,"doInBackground");
            return "userID "+userID+"\nuserName "+userName+"\nuserUserName "+userUserName+"\nuserAge "+String.valueOf(userAgeint)+"\nuserPassword "+userPassword+"\nuserEmail "+userEmail+"\nuserMale "+userMale;
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
            batchGraphResult();
            Log.e(TAG,"onPreExecute");
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            Log.e(TAG,"onProgressUpdate");
        }
    }

    private void batchGraphResult() {
        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            GraphView graph = findViewById(R.id.graph123);
            @Override
            public void onResponse(String response) {
                System.out.println("response"+response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean noresult = jsonObject.getBoolean("noresult");
                    if (noresult){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
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
                            userGraphDateArray.add(date);
                            userGraphResultArray.add(RESULT);
                        }
                    }
                    else if (server_response.length() == 0){
                        for (int i = 0; i < 10; i++) {
                            HashMap<String, String> server = new HashMap<>();
                            RESULT = "2000";
                            userGraphResultArray.add(RESULT);
                            dateList.add(new Date(118,1,1));
                        }
                    }




                    for (String graphDAT : userGraphDateArray) {
                        dateList.add(simpleDateFormat.parse(graphDAT));
                    }
                    Calendar calendar = Calendar.getInstance();
                    final Date d1 = calendar.getTime();
                    calendar.add(Calendar.DATE,-31);

                    if (userGraphDateArray.size() == 0){
                        userGraphDateArray.add("10.01.2018");
                    }

                    LineGraphSeries<DataPoint> series5 = new LineGraphSeries<>(new DataPoint[]{});

                    int count = dateList.size();
                    for (int j = count; j>0; j--){
                        Date x = dateList.get(dateList.size()-j);
                        Double y = Double.parseDouble(userGraphResultArray.get(userGraphResultArray.size()-j));
                        DataPoint v = new DataPoint(x,y);
                        series5.appendData(v,true,count,true);
                    }
                    graph.addSeries(series5);
                    series5.setThickness(2);
                    series5.setAnimated(true);
                    series5.setDrawDataPoints(true);
                    series5.setDataPointsRadius(4);

                    calendar.add(Calendar.DATE,-120);
                    final Date d3 = calendar.getTime();
                    long l7 = d3.getTime();
                    calendar.add(Calendar.DATE,260);
                    final Date d4 = calendar.getTime();
                    long l9 = d4.getTime();
                    Date d8 = dateList.get(dateList.size()-1);
                    long l8 = d8.getTime()+2*86400;

                    Log.e("SPRAWdzAM",""+dateList.get(0).getTime());
                    Log.e("SPRAWdzAM",""+l8);


                    graph.getGridLabelRenderer().setHorizontalLabelsAngle(150);
                    graph.getGridLabelRenderer().setTextSize(25);
                    graph.getGridLabelRenderer().setLabelsSpace(20);
                    graph.getGridLabelRenderer().setLabelHorizontalHeight(80);
                    graph.getGridLabelRenderer().isHumanRounding();

                    Viewport viewport = graph.getViewport();

                    viewport.setScalable(true);
                    viewport.setScalableY(true);
                    viewport.setYAxisBoundsManual(true);
                    viewport.setXAxisBoundsManual(true);

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
                }

                catch (ParseException e) {
                    e.printStackTrace();
                }
            }};

        GraphShowKcalRequest graphShowKcalRequest = new GraphShowKcalRequest("brus5", responseListener);
        RequestQueue queue = Volley.newRequestQueue(Main2Activity.this);
        queue.add(graphShowKcalRequest);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                generateData2();
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
            y = Double.parseDouble(userGraphResultArray.get(userGraphResultArray.size()-i));
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
