package com.brus5.lukaszkrawczak.fitx;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Diet.DietShowByUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class DietActivity extends AppCompatActivity {

    private static final String TAG = "DietActivity";

    String name, username, age, password, email, male, somatotypeS;
    String description;
    String weight;
    int id;

    private ArrayList<String> productWeight = new ArrayList<>();
    private ArrayList<Spanned> productNameList = new ArrayList<Spanned>();

    private ArrayAdapter<String> adapterWeight;
    private ArrayAdapter<Spanned> adapterName;
    private ListView mTaskListView;

    /* Gettings date */
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String date = simpleDateFormat.format(c.getTime());

    String dateInsde;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_diet);

        mTaskListView = findViewById(R.id.list_diet);


        Intent intent1 = getIntent();
        id = intent1.getIntExtra("id",0);
        name = intent1.getStringExtra("name");
        username = intent1.getStringExtra("username");
        age = intent1.getStringExtra("birthday");
        male = intent1.getStringExtra("male");

        HorizontalCalendar horizontalCalendar;

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        horizontalCalendar = new HorizontalCalendar.Builder(DietActivity.this, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .textSize(14f, 24f, 14f)
                .showDayName(true)
                .showMonthName(true)

                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                dateInsde = simpleDateFormat.format(date.getTime());
                Log.e(TAG,"date: "+date);
                Log.e(TAG,"date: "+dateInsde);
                productNameList.clear();
                productWeight.clear();
                loadData();
                wait2secs();
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                };



                Toast.makeText(DietActivity.this, DateFormat.getDateInstance().format(date) + " is selected!", Toast.LENGTH_SHORT).show();
            }

        });

/*        Button search_meal = (Button) findViewById(R.id.action_search_meal);
        search_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchForMeal = new Intent(DietActivity.this, SearchForMeal.class);
                DietActivity.this.startActivity(searchForMeal);
            }
        });*/
/*mTaskListView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        Log.e(TAG, "View v "+v);
        Log.e(TAG, "scrollX "+ scrollX);
        Log.e(TAG, "scrollY "+ scrollY);
        Log.e(TAG, "oldScrollX "+oldScrollX);
        Log.e(TAG, "oldScrollY "+oldScrollY);
    }
});*/



    }



    private void wait2secs() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateUI();
                updateWeight();
            }
        },1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_meal,menu);
        return super.onCreateOptionsMenu(menu);
    }

/*

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_meal,menu);
        return super.onCreateOptionsMenu(menu);
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search_meal:
                Intent searchForMeal = new Intent(DietActivity.this, DietSearchActivity.class);
                searchForMeal.putExtra("id",id);
                searchForMeal.putExtra("name",name);
                searchForMeal.putExtra("username",username);
                searchForMeal.putExtra("birthday",age);
                searchForMeal.putExtra("male",male);
                DietActivity.this.startActivity(searchForMeal);
        }
        return super.onOptionsItemSelected(item);
    }



    private void loadData() {
/*

        Response.Listener<String> responseListenerWeight = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG,"getWeight response: "+response);

                try {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DietShowByUser dietShowByUser1 = new DietShowByUser(username, dateInsde, responseListenerWeight);
        RequestQueue queue1 = Volley.newRequestQueue(DietActivity.this);
        queue1.add(dietShowByUser1);
*/


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG,"getTrainingRequest response: "+response);




                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray product_weight_response = jsonObject.getJSONArray("product_weight_response");
                    if (product_weight_response.length() > 0) {
                        for (int i = 0; i < product_weight_response.length(); i++) {
                            JSONObject d = product_weight_response.getJSONObject(i);
                            weight = d.getString("weight");
                            productWeight.add(weight);
                        }
                    }
                    Log.e(TAG,"productWeight: "+productWeight);

                    JSONArray server_response = jsonObject.getJSONArray("server_response");

                    String name;

                    float proteins;
                    float fats;
                    float carbs;
                    String date;
                    float kcal;
                    if (server_response.length() > 0){
                        for (int i = 0; i < server_response.length(); i++){
                            JSONObject c = server_response.getJSONObject(i);
                            name = c.getString("name");
                            proteins = Float.valueOf(c.getString("proteins"));
                            fats = Float.valueOf(c.getString("fats"));
                            carbs = Float.valueOf(c.getString("carbs"));
                            date = c.getString("date");

                            Log.e(TAG,"proteins: "+proteins);

                            proteins = proteins*(Float.valueOf(productWeight.get(i))/100);
                            fats = fats*(Float.valueOf(productWeight.get(i))/100);
                            carbs = carbs*(Float.valueOf(productWeight.get(i))/100);

                            kcal = (proteins*4)+(fats*9)+(carbs*4);

//                            String.format("%.0f",RESULT);

                            productNameList.add(Html.fromHtml("<big>"+name+"</big> "+"<b>"+productWeight.get(i)+"g</b> "+"<br><br>"+"<small>"+"Proteins: "+"<font color=#00cc44><b>"+String.format("%.1f",proteins)+"</b></font>"+"<font color=#c1c3bb>"+" / </font>"+"Fats: "+"<font color=#ff4d4d><b>"+String.format("%.1f",fats)+"</b></font>"+"<font color=#c1c3bb>"+" / </font>"+"Carbohydrates: "+"<font color=#3385ff><b>"+String.format("%.1f",carbs)+"</b></font>"+" "+"</small>"+"<br><b><medium>"+String.format("%.0f",kcal)+" kCal</medium></b>"));


                        }
                    }

                    Log.e(TAG,"productNameList: "+productNameList);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        DietShowByUser dietShowByUser = new DietShowByUser(username, dateInsde, responseListener);
        RequestQueue queue = Volley.newRequestQueue(DietActivity.this);
        queue.add(dietShowByUser);
    }

    private void updateUI(){
        adapterName = new ArrayAdapter<Spanned>(this, R.layout.meal_row,R.id.meal_title,productNameList);
        adapterWeight = new ArrayAdapter<String>(this,R.layout.meal_row,R.id.meal_weight,productWeight);
        mTaskListView.setAdapter(adapterName);

        mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("HelloListView", "You clicked Item: " + id + " at position:" + position);
            }
        });

        Log.e(TAG,"adapter: "+adapterName);


    }

    private void updateWeight(){


        Log.e(TAG,"adapter: "+adapterWeight);
    }


}

