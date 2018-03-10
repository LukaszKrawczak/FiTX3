package com.brus5.lukaszkrawczak.fitx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
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

import junit.framework.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    SeekBar sbGymTEA,sbAreoTEA, sbNEAT, sbGymTime, sbAreoTime;

    RadioButton rbWoman, rbMan;
    EditText etWeight, etHeight, etAge;
    TextView tvResult, tvGymTEA, tvAreoTEA, tvGymTime, tvAreoTime, tvTEAresult, tvAREOresult;

    String UserInfo = "", name, username, birthday, password, email, male, id1;
    int id;
    int age;

    boolean defaultLogin = true;

    ArrayList<String> graphDATE = new ArrayList<>();
    ArrayList<String> graphRESULT = new ArrayList<>();

    final List<Date> dates = new ArrayList<>();

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();

        defaultLogin = intent.getBooleanExtra("defaultLogin",false);

        if (defaultLogin){
            username = intent.getStringExtra("username");
        }
        else {
            username = com.facebook.Profile.getCurrentProfile().getId();
            Bundle b = intent.getExtras();
            String nameTest = b.getString("first_name");
            Log.e(TAG,"nameTest: "+nameTest);
        }

        System.out.println(v1(3,0));
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE,-90);
        final Date d2 = calendar.getTime();

        System.out.println(d1);
        System.out.println(d2);

        Log.e("Dates",""+dates);

        final TextView tvWelcome = findViewById(R.id.tvWelcome);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//                String data = prefs.getString("string_id", "no id"); //no id: default value
////                username = data;
//            }
//        },200);

        String welcome = "Hello, " + username + " " + ". " + "Here is the menu. The choice is yours.";

        tvWelcome.setText(welcome);

        final Button btUserProfile = findViewById(R.id.btUserProfile);
        final Button btMetaCalc = findViewById(R.id.btMetaCalc);
        final Button btTraining = findViewById(R.id.btTraining);
        final Button btDiet =  findViewById(R.id.btDiet);
        final Button btTest =  findViewById(R.id.btTest);

        final GraphView graph = findViewById(R.id.graph);

        Response.Listener<String> responseListner = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                UserInfo = response.replaceAll("[\\p{Punct}&&[^@.]]"," ");

                String[] words;

                words = UserInfo.split("\\s+");
                id1 = words[5];
                id = Integer.parseInt(id1);
                name = words[7];
                username = words[9];
                birthday = words[11];
                password = words[13];
                email = words[15];
                male = words[17];

                Log.e(TAG,   "Arrays.toString(words): " + Arrays.toString(words));
                Log.e(TAG,   "id:                     " + id);
                Log.e(TAG,   "name:                   " + name);
                Log.e(TAG,   "username:               " + username);
                Log.e(TAG,   "birthday:               " + birthday);
                Log.e(TAG,   "password:               " + password);
                Log.e(TAG,   "email:                  " + email);
                Log.e(TAG,   "male:                   " + male);
/*
  // constricting my new data type from 09/04/1989 to converted_birthday: 04.09.1989
                                    String day = birthday.substring(3,5);
                                    String month = birthday.substring(0,2);
                                    String year = birthday.substring(6,10);
                                    */
                int day = Integer.valueOf(birthday.substring(0,2));
                int month = Integer.valueOf(birthday.substring(3,5));
                int year = Integer.valueOf(birthday.substring(6,10));
                Log.i(TAG,"getAge(year,month,day)"+getAge(year,month,day));
                age = Integer.valueOf(getAge(year,month,day));
                System.out.println("HELLO "+UserInfo);

            }
        };

        UserInfoShowRequest userInfoShowRequest = new UserInfoShowRequest(username, responseListner);
        RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this);
        queue1.add(userInfoShowRequest);

        btUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,UserProfileActivity.class);
                intent1.putExtra("username",username);
                intent1.putExtra("defaultLogin",defaultLogin);
                MainActivity.this.startActivity(intent1);
            }
        });

        btMetaCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent metacalcIntent = new Intent(MainActivity.this, MetacalcActivity3.class);
                metacalcIntent.putExtra("id",id);
                metacalcIntent.putExtra("name",name);
                metacalcIntent.putExtra("username",username);
                metacalcIntent.putExtra("age", age);
                metacalcIntent.putExtra("male",male);

                MainActivity.this.startActivity(metacalcIntent);
            }
        });

        btTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent trainingIntent = new Intent(MainActivity.this, TrainingActivity.class);
                trainingIntent.putExtra("id",id);
                trainingIntent.putExtra("name",name);
                trainingIntent.putExtra("username",username);
                trainingIntent.putExtra("birthday", birthday);
                trainingIntent.putExtra("male",male);
                MainActivity.this.startActivity(trainingIntent);
            }
        });

        btDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dietIntent = new Intent(MainActivity.this,DietActivity.class);
                dietIntent.putExtra("id",id);
                dietIntent.putExtra("name",name);
                dietIntent.putExtra("username",username);
                dietIntent.putExtra("birthday", birthday);
                dietIntent.putExtra("male",male);
                MainActivity.this.startActivity(dietIntent);
            }
        });

        btTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dietIntent = new Intent(MainActivity.this,Test.class);
                MainActivity.this.startActivity(dietIntent);
            }
        });

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

                    Date dateX = new Date();

                    System.out.println("response1"+response);



                    if (server_response.length() > 0) {
                        for (int i = 0; i < server_response.length(); i++) {
                            JSONObject c = server_response.getJSONObject(i);
                            date = c.getString("date");
                            RESULT = c.getString("RESULT");
                            HashMap<String, String> server = new HashMap<>();
                            server.put("RESULT", RESULT);
                            server.put("date", date);
                            graphDATE.add(date);
                            graphRESULT.add(RESULT);
                            Log.e("date1", "" + dateX);
                            Log.e("HashMap_server", "141" + server);
                        }
                    }
                    else if (server_response.length() == 0){
                        for (int i = 0; i < 10; i++) {
                            HashMap<String, String> server = new HashMap<>();
                            RESULT = "2000";
                            graphRESULT.add(RESULT);
                            dates.add(new Date(118,1,1));
                            Log.e("HashMap_server", "" + server);
                        }
                        Log.e("server_checker",""+graphRESULT);
                    }

                String result;
                result = response.replaceAll("[\\p{Punct}&&[^@.]]"," ");

                String[] serverResponse_result;
                serverResponse_result = result.split("\\s+");
                String id = serverResponse_result[4];
                String username = serverResponse_result[6];

                    Log.e("id",""+id);
                    Log.e("username",""+username);

                    for (String graphDAT : graphDATE) {
                        dates.add(simpleDateFormat.parse(graphDAT));
                    }
                    Log.e("dates----checker", "" + dates);


                    System.out.println("graphRESULT"+graphRESULT);
                    System.out.println("graphDATE"+graphDATE);

                    Calendar calendar = Calendar.getInstance();
                    final Date d1 = calendar.getTime();
                    calendar.add(Calendar.DATE,-31);

                    Log.e("graphDATE.size()",""+graphDATE.size());
                    Log.e("dates.size()",""+dates.get(dates.size() - 1));
                    Log.e("graphRESULT",""+graphRESULT.get(graphRESULT.size() - 1));

                    if (graphDATE.size() == 0){
                        graphDATE.add("10.01.2018");
                    }



                    LineGraphSeries<DataPoint> series5 = new LineGraphSeries<>(new DataPoint[]{});

                    int count = dates.size();
                    DataPoint[] values = new DataPoint[count];

                    for (int j = count; j>0; j--){

                        Date x = dates.get(dates.size()-j);
                        Double y = Double.parseDouble(graphRESULT.get(graphRESULT.size()-j));
                        DataPoint v = new DataPoint(x,y);
                        series5.appendData(v,true,count,true);

                        Log.e("values",""+v);
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
                    Date d8 = dates.get(dates.size()-1);
                    long l8 = d8.getTime()+2*86400;

                    Log.e("SPRAWdzAM",""+dates.get(0).getTime());
                    Log.e("SPRAWdzAM",""+d2.getTime());
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

        GraphShowKcalRequest graphShowKcalRequest = new GraphShowKcalRequest(username, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(graphShowKcalRequest);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                generateData2();
                Log.e("generateData2()",""+generateData2());
            }
        },100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("ASDASD + "+data.getStringExtra("message"));
        if (requestCode == 999 && resultCode == RESULT_OK){
            System.out.println(data.getStringExtra("message"));
        }
    }

    public int v1 (int var, int var2){


        int result = var+var2;

        return result;

    }

//    private DataPoint[] generateData() {
//        Random rand = new Random();
//        int count = 30;
//        DataPoint[] values = new DataPoint[count];
//        for (int i=0; i<count; i++) {
//            double x = i;
//            double f = rand.nextDouble()*0.15+0.3;
//            double y = Math.sin(i*f+2) + rand.nextDouble()*0.3;
//            DataPoint v = new DataPoint(x, y);
//            values[i] = v;
//        }
//        Log.e("values",""+values);
//        return values;
//    }

    private DataPoint[] generateData2(){
        int count = dates.size();
        DataPoint[] values = new DataPoint[count];
        Date x;
        Double y;
        for (int i = 1; i<count; i++){

            x = dates.get(dates.size()-i);
            y = Double.parseDouble(graphRESULT.get(graphRESULT.size()-i));
            DataPoint v = new DataPoint(x,y);
            Log.e("DataPoint v",""+v);
            values[i] = v;
            Log.e("DataPoint v",""+values);
        }
        for (int j = count; j>=0; j--){
            values = new DataPoint[j];
            Log.e("Od tylu",""+values);
        }
        Log.e("values",""+values);
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

