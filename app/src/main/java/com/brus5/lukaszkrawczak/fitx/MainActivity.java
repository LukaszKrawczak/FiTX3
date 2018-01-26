package com.brus5.lukaszkrawczak.fitx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    String UserInfo = "", name, username, age, password, email, male;


    ArrayList<String> graphDATE, graphRESULT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println(v1(3,0));
/*
        Calendar calendar = Calendar.getInstance();
       final Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE,2);
        final Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 2);
        final Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, 3);
        final Date d4 = calendar.getTime();
        calendar.add(Calendar.DATE, 4);
        final Date d5 = calendar.getTime();
*/


        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, -360);
        final Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 720);
        final Date d3 = calendar.getTime();


        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);

        graphDATE = new ArrayList<>();
        graphRESULT = new ArrayList<>();


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
                System.out.println(response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray server_response = jsonObject.getJSONArray("server_response");

                    // looping through All Contacts

                    String date;
                    String RESULT;

                    Date dateX = new Date();

                    System.out.println(response);

                    DateFormat format = new SimpleDateFormat("dd.MM.yyyy");


                    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");


                    for (int i = 0; i < server_response.length(); i++) {
                        JSONObject c = server_response.getJSONObject(i);
                        date = c.getString("date");
                        RESULT = c.getString("RESULT");
                        HashMap<String, String> server = new HashMap<>();
                        server.put("RESULT",RESULT);
                        server.put("date",date);
                        graphDATE.add(date);
                        graphRESULT.add(RESULT);
                        Log.e("date1",""+dateX);
                        Log.e("HashMap_server","141"+server);
//                        for (int a = 0; a < server_response.length(); a++){
//                            ArrayList<Date> daty =  new ArrayList<>();
//                        }
                    }

                String result;
                result = response.replaceAll("[\\p{Punct}&&[^@.]]"," ");

                String[] serverResponse_result;
                serverResponse_result = result.split("\\s+");
                String id = serverResponse_result[4];
                String username = serverResponse_result[6];

                    Log.e("id",""+id);
                    Log.e("username",""+username);


                    List<Date> dates = new ArrayList<>(graphDATE.size());

                    for (String graphDAT : graphDATE) {
//                        dates.add(format.parse(graphDAT));
                        dates.add(simpleDateFormat.parse(graphDAT));
                    }
                    Log.e("dates----checker", "" + dates);


                    System.out.println(graphRESULT);
                    System.out.println(graphDATE);


                    Calendar calendar = Calendar.getInstance();
                    final Date d1 = calendar.getTime();
                    calendar.add(Calendar.DATE,31);
//                    final Date d2 = calendar.getTime();
//                    calendar.add(Calendar.DATE, 30);
//                    final Date d3 = calendar.getTime();
//                    calendar.add(Calendar.DATE, 30);
//                    final Date d4 = calendar.getTime();
//                    calendar.add(Calendar.DATE, 30);
//                    final Date d5 = calendar.getTime();
//                    calendar.add(Calendar.DATE, 180);


                    System.out.println(d1);
//                    System.out.println(d2);
//                    System.out.println(d3);
//                    System.out.println(d4);
//                    System.out.println(d5);

                    System.out.println(graphDATE.size());

                    if (graphDATE.size() == 0){
                        graphDATE.add("10.01.2018");
                    }

                    int checker = graphDATE.size();
                    switch (checker){
                        case 0:
                            break;
                        case 1:
                            LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(new DataPoint[]{
                                    new DataPoint(dates.get(dates.size() - 1), Double.parseDouble(graphRESULT.get(graphRESULT.size() - 1))),
                            });
                            graph.addSeries(series1);
                            break;
                        case 2:
                            LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[]{
//                                    new DataPoint(dates.get(dates.size() - 1), Double.parseDobruuble(graphRESULT.get(graphRESULT.size() - 2))),
//                                    new DataPoint(dates.get(dates.size() - 1), Double.parseDouble(graphRESULT.get(graphRESULT.size() - 1))),
                            });
                            graph.addSeries(series2);
                            break;
                        case 3:
                            System.out.println("Not enough index of array");
                            break;
                        case 4:
                            break;
                    }

                    if (graphDATE.size() > 5){

                    }
                    LineGraphSeries<DataPoint> series5 = new LineGraphSeries<>(new DataPoint[]{
                            new DataPoint(dates.get(dates.size()-6), Double.parseDouble(graphRESULT.get(graphRESULT.size()-6))),
                            new DataPoint(dates.get(dates.size()-5), Double.parseDouble(graphRESULT.get(graphRESULT.size()-5))),
                            new DataPoint(dates.get(dates.size()-4), Double.parseDouble(graphRESULT.get(graphRESULT.size()-4))),
                            new DataPoint(dates.get(dates.size()-3), Double.parseDouble(graphRESULT.get(graphRESULT.size()-3))),
                            new DataPoint(dates.get(dates.size()-2), Double.parseDouble(graphRESULT.get(graphRESULT.size()-2))),
                            new DataPoint(dates.get(dates.size()-1), Double.parseDouble(graphRESULT.get(graphRESULT.size()-1))),
                    });
                    graph.addSeries(series5);


                    series5.setThickness(3);
                    series5.setAnimated(true);
                    series5.setDrawDataPoints(true);
                    series5.setDataPointsRadius(7);
                    graph.getViewport().setMinX(d2.getTime());
                    graph.getViewport().setMaxX(d3.getTime());
                    graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
                    graph.getGridLabelRenderer().setHorizontalLabelsAngle(150);
                    graph.getGridLabelRenderer().setTextSize(25);
//                    graph.getGridLabelRenderer().setLabelsSpace(25);
                    graph.getGridLabelRenderer().setLabelHorizontalHeight(80);
//                    graph.getGridLabelRenderer().setHorizontalLabelsColor(100);

                    StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                    staticLabelsFormatter.setHorizontalLabels(new String[]  {"old", "middle", "new","newewst","1"});
                    graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
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
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }};

        UpdateRequestShowKcal updateRequestShowKcal = new UpdateRequestShowKcal(username, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(updateRequestShowKcal);

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

}