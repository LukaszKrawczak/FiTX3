package com.brus5.lukaszkrawczak.fitx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity{

    String UserInfo = "", name, username, age, password, email, male;


    ArrayList<String> graphDATE = new ArrayList<>();
    ArrayList<String> graphRESULT = new ArrayList<>();

    final List<Date> dates = new ArrayList<>();

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println(v1(3,0));
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE,-90);
        final Date d2 = calendar.getTime();

        System.out.println(d1);
        System.out.println(d2);

        Log.e("Dates",""+dates);

        final TextView tvWelcome = findViewById(R.id.tvWelcome);

        final Intent intent = getIntent();
        username = intent.getStringExtra("username");
        String welcome = "Hello, " + username + " " + ". " + "Here is the menu. The choice is yours.";

        tvWelcome.setText(welcome);

        final Button btUserProfile = findViewById(R.id.btUserProfile);
        final Button btMetaCalc = findViewById(R.id.btMetaCalc);
        final Button btTraining = findViewById(R.id.btTraining);
        final Button btDiet =  findViewById(R.id.btDiet);

        final GraphView graph = findViewById(R.id.graph);

        Response.Listener<String> responseListner = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                UserInfo = response.replaceAll("[\\p{Punct}&&[^@.]]"," ");

                String[] words;

                words = UserInfo.split("\\s+");

                name = words[4];
                username = words[6];
                age = words[8];
                password = words[10];
                email = words[12];
                male = words[14];

                Log.e("MenuUserProfile",   "Arrays.toString(words) "    + Arrays.toString(words));
                Log.e("MenuUserProfile",   "name "                      + name);
                Log.e("MenuUserProfile",   "username "                  + username);
                Log.e("MenuUserProfile",   "age "                       + age);
                Log.e("MenuUserProfile",   "password "                  + password);
                Log.e("MenuUserProfile",   "email "                     + email);
                Log.e("MenuUserProfile",   "male "                      + male);

                System.out.println("HELLO "+UserInfo);

            }
        };

        GetInfoRequest getInfoRequest = new GetInfoRequest(username, responseListner);
        RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this);
        queue1.add(getInfoRequest);

        btUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,MenuUserProfile.class);
                intent1.putExtra("username",username);
                MainActivity.this.startActivity(intent1);
            }
        });

        btMetaCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent metacalcIntent = new Intent(MainActivity.this, MenuMetaCalc.class);
                metacalcIntent.putExtra("name",name);
                metacalcIntent.putExtra("username",username);
                metacalcIntent.putExtra("age",age);
                metacalcIntent.putExtra("male",male);


                MainActivity.this.startActivity(metacalcIntent);
            }
        });

        btTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent trainingIntent = new Intent(MainActivity.this, MenuTraining.class);
                MainActivity.this.startActivity(trainingIntent);
            }
        });

        btDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dietIntent = new Intent(MainActivity.this,MenuDiet.class);
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
//                    Log.e("dates.get",""+dates.get(dates.size() - 2));

//                    for (int i = 1; i<count; i++){
//
//                        Date x = dates.get(dates.size()-i);
//                        Double y = Double.parseDouble(graphRESULT.get(graphRESULT.size()-i));
//                        DataPoint v = new DataPoint(x,y);
//                        series5.appendData(v,true,count,false);
//                        values[i] = v;
//                    }

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
                    series5.setDataPointsRadius(5);

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


//                    graph.getViewport().setMinX(d2.getTime());
//                    graph.getGridLabelRenderer().setNumHorizontalLabels(6);
//                    graph.getViewport().setMaxX(l8);
//                    graph.getViewport().setMinY(1000);
//                    graph.getViewport().setMaxY(5000);
//                    graph.getViewport().setMaxXAxisSize(l8);
//                    graph.getGridLabelRenderer().setVerticalLabelsVisible(true);
                    graph.getGridLabelRenderer().setHorizontalLabelsAngle(150);
                    graph.getGridLabelRenderer().setTextSize(25);
                    graph.getGridLabelRenderer().setLabelsSpace(20);
                    graph.getGridLabelRenderer().setLabelHorizontalHeight(80);
//                    graph.getViewport().setMinX(l7);
//                    graph.getViewport().isScrollable();
                    graph.getViewport().isScalable();
//                    graph.onDataChanged(true,false);
//                    graph.computeScroll();
//                    graph.getGridLabelRenderer().setHorizontalLabelsColor(100);

                    Viewport viewport = graph.getViewport();
//                    viewport.setYAxisBoundsManual(true);
//                    viewport.setXAxisBoundsManual(true);
//                    viewport.setMinX(l7);
//                    viewport.setMaxX(l9);
//                    viewport.isScrollable();
//                    viewport.setScrollable(true);

                    viewport.setScalable(true);
                    viewport.setScalableY(true);

//                    viewport.computeScroll();
//                    viewport.getXAxisBoundsStatus();
//                    graph.getGridLabelRenderer().setHumanRounding(true);
//                    viewport.scrollToEnd();
//                    viewport.setScalableY(true);
//                    viewport.setScrollableY(true);

//                    viewport.setMinY(1000);
//                    viewport.setMaxX(5000);
//                    viewport.computeScroll();
//                    viewport.setMaxX(l8);
//                    graph.getViewport().setScrollable(true);
//                    StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//                    staticLabelsFormatter.setVerticalLabels(new String[]{"old", "middle", "new"});
//                    StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//                    staticLabelsFormatter.setHorizontalLabels(new String[]  {"old", "middle", "new"});

//                    graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
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


/*
                    series5.setThickness(3);
                    series5.setAnimated(true);
                    series5.setDrawDataPoints(true);
                    series5.setDataPointsRadius(7);
                    graph.addSeries(series5);
                    System.out.println(dates);

//                    graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext()));
                    graph.getGridLabelRenderer().setLabelsSpace(75);
                    graph.getGridLabelRenderer().setNumHorizontalLabels(10);
                    graph.getGridLabelRenderer().setHorizontalLabelsAngle(125);
                    graph.getGridLabelRenderer().setPadding(75); //75
                    graph.getGridLabelRenderer().setTextSize(25);

//                    graph.getViewport().setMinX(dates.get(dates.size()-5).getTime());
//                    graph.getViewport().setMaxX(d5.getTime());
                    graph.getViewport().setXAxisBoundsManual(true);
//                    graph.getViewport().setScrollable(true);
//                    graph.getGridLabelRenderer().setHumanRounding(true);

                    StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                    staticLabelsFormatter.setHorizontalLabels(new String[]  {"old", "middle", "new"});
                    staticLabelsFormatter.setDynamicLabelFormatter(new LabelFormatter() {
                        @Override
                        public String formatLabel(double value, boolean isValueX) {
                            return null;
                        }

                        @Override
                        public void setViewport(Viewport viewport) {

                        }
                    });
                    graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                    graph.getGridLabelRenderer().reloadStyles();
                    graph.getGridLabelRenderer().setHumanRounding(true);
//
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
                    */

            }
            catch (JSONException e) {
                    e.printStackTrace();
                }

                catch (ParseException e) {
                    e.printStackTrace();
                }
            }};

        UpdateRequestShowKcal updateRequestShowKcal = new UpdateRequestShowKcal(username, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(updateRequestShowKcal);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                generateData();
                Log.e("generateData()",""+generateData());
                generateData2();
                Log.e("generateData2()",""+generateData2());
            }
        },2000);

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

    private DataPoint[] generateData() {
        Random rand = new Random();
        int count = 30;
        DataPoint[] values = new DataPoint[count];
        for (int i=0; i<count; i++) {
            double x = i;
            double f = rand.nextDouble()*0.15+0.3;
            double y = Math.sin(i*f+2) + rand.nextDouble()*0.3;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        Log.e("values",""+values);
        return values;

    }

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
/*
        Log.e("dates.size()",""+dates.size());
        Log.e("graphDATE.size()",""+graphDATE.size());
        Log.e("graphDATE.size()",""+graphDATE.get(7));
        Log.e("graphDATE.size()",""+dates.get(dates.size() - 1));
        Date date1 = dates.get(dates.size() - 1);
        Date date2 = dates.get(dates.size() - 2);
        long i2 = date2.getTime();
        long i1 = (date1.getTime()-i2)/3600000;
        int i3 = (int) date2.getTime()-3*3600*24;
        Log.e("graphDATE.size()",""+i1);
        Log.e("graphDATE.size()",""+i2);
        Log.e("graphDATE.size()",""+i3);
*/


//            DataPoint v = new DataPoint(date,value);
        Log.e("values",""+values);
        return values;
    }



    }

//    public int v2 (int var, int var2){
//
//
//
//
//
//
//        return result;
//
//    }

