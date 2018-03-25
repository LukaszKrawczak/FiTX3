package com.brus5.lukaszkrawczak.fitx;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Diet.Diet;
import com.brus5.lukaszkrawczak.fitx.Diet.DietDeleteMeal;
import com.brus5.lukaszkrawczak.fitx.Diet.DietDeleteRequest;
import com.brus5.lukaszkrawczak.fitx.Diet.DietListAdapter;
import com.brus5.lukaszkrawczak.fitx.Diet.DietShowByUser;
import com.brus5.lukaszkrawczak.fitx.Diet.DietUpdateKcalResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class DietActivity extends AppCompatActivity {


    private static final String TAG = "DietActivity";


    String userName, userFirstName, userBirthday, userPassword, userEmail, userMale, userID;

    String name, username, age, password, email, male, somatotypeS;
    String description;
    String weight;
    int id,userIDint,userAgeint;

    ArrayList<String> productWeight = new ArrayList<>();
//    ArrayList<Spanned> productNameList = new ArrayList<Spanned>();
    ArrayList<Diet> dietArrayList = new ArrayList<>();

    ArrayList<String> kcalList = new ArrayList<>();
    ArrayList<String> proteinsList = new ArrayList<>();
    ArrayList<String> fatsList = new ArrayList<>();
    ArrayList<String> carbsList = new ArrayList<>();


//    ArrayAdapter<String> adapterWeight;
//    ArrayAdapter<Spanned> adapterName;
    ListView mTaskListView;
    DietListAdapter adapter;
    /* Gettings date */
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String date = simpleDateFormat.format(c.getTime());
    String dateInsde;
    ProgressBar pbProteins,pbFats,pbCarbs,pbKcal;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_diet);
        Log.d(TAG,"onCreate: Started.");
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        mTaskListView = findViewById(R.id.list_diet);
        getWindow().setStatusBarColor(ContextCompat.getColor(DietActivity.this,R.color.color_main_activity_statusbar));


        Intent intent1 = getIntent();
        userIDint = intent1.getIntExtra("userIDint",0);
        userFirstName = intent1.getStringExtra("userFirstName");
        userName = intent1.getStringExtra("userName");
        userBirthday = intent1.getStringExtra("userBirthday");
        userAgeint = intent1.getIntExtra("userAgeint",0);
        userPassword = intent1.getStringExtra("userPassword");
        userEmail = intent1.getStringExtra("userEmail");
        userMale = intent1.getStringExtra("userMale");

        Log.e(TAG,"informacje"+" "+userIDint+" "+userFirstName+" "+userName+" "+userBirthday+" "+userAgeint+" "+userPassword+" "+userEmail+" "+userMale);
        intentPersonInfo();

        pbProteins = findViewById(R.id.pbProteins);
        pbFats = findViewById(R.id.pbFats);
        pbCarbs = findViewById(R.id.pbCarbs);
        pbKcal = findViewById(R.id.pbKcal);


//        Button search_meal = (Button) findViewById(R.id.action_search_meal);
//        search_meal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent searchForMeal = new Intent(DietActivity.this, SearchForMeal.class);
//                DietActivity.this.startActivity(searchForMeal);
//            }
//        });
//mTaskListView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//    @Override
//    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//        Log.e(TAG, "View v "+v);
//        Log.e(TAG, "scrollX "+ scrollX);
//        Log.e(TAG, "scrollY "+ scrollY);
//        Log.e(TAG, "oldScrollX "+oldScrollX);
//        Log.e(TAG, "oldScrollY "+oldScrollY);
//    }
//});



        HorizontalCalendar horizontalCalendar;

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        horizontalCalendar = new HorizontalCalendar.Builder(DietActivity.this, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(7)
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
                Log.d(TAG, "onDateSelected: date "+date);
                Log.d(TAG, "onDateSelected: dateInside "+dateInsde);
                productWeight.clear();
                dietArrayList.clear();
                kcalList.clear();
                proteinsList.clear();
                fatsList.clear();
                carbsList.clear();
                new LongRunningTask().execute();

//                wait2secs();

//                Toast.makeText(DietActivity.this, DateFormat.getDateInstance().format(date) + " is selected!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class LongRunningTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: ");
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");

            loadData();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "onPostExecute: updateUI");
            adapter = new DietListAdapter(DietActivity.this,R.layout.meal_row,dietArrayList);
            super.onPostExecute(aVoid);
        }
    }

    private void intentPersonInfo() {

    }


    private void wait2secs() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.meal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search_meal_1:
                Intent searchForMeal = new Intent(DietActivity.this, DietSearchActivity.class);
                searchForMeal.putExtra("userIDint",userIDint);
                searchForMeal.putExtra("userFirstName",userFirstName);
                searchForMeal.putExtra("userName",userName);
                searchForMeal.putExtra("userBirthday",userBirthday);
                searchForMeal.putExtra("userAgeint",userAgeint);
                searchForMeal.putExtra("userPassword",userPassword);
                searchForMeal.putExtra("userEmail", userEmail);
                searchForMeal.putExtra("userMale", userMale);
                searchForMeal.putExtra("dateInsde", dateInsde);
                DietActivity.this.startActivity(searchForMeal);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response "+response);
                double kcalResult = 0;
                double proteinsResult = 0;
                double fatsResult = 0;
                double carbsResult = 0;

                String proteinsratio = "";
                String fatsratio = "";
                String carbsratio="";
                String result="";



                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray dietratio = jsonObject.getJSONArray("dietratio");
                    if (dietratio.length() > 0) {
                        for (int i = 0; i < dietratio.length(); i++) {
                            JSONObject d = dietratio.getJSONObject(i);
                            proteinsratio = d.getString("proteinsratio");
                            fatsratio = d.getString("fatsratio");
                            carbsratio = d.getString("carbsratio");
//                            productWeight.add(weight);
                        }
                    }

                    Log.i(TAG, "onResponse: ratio " + proteinsratio+":"+ fatsratio+":"+carbsratio);

                    JSONArray RESULT = jsonObject.getJSONArray("RESULT");
                    if (dietratio.length() > 0) {
                        for (int i = 0; i < RESULT.length(); i++) {
                            JSONObject d = RESULT.getJSONObject(i);
                            result = d.getString("RESULT");
                        }
                    }

                    Log.i(TAG, "onResponse: result "+result);

                    JSONArray product_weight_response = jsonObject.getJSONArray("product_weight_response");
                    if (product_weight_response.length() > 0) {
                        for (int i = 0; i < product_weight_response.length(); i++) {
                            JSONObject d = product_weight_response.getJSONObject(i);
                            weight = d.getString("weight");
                            productWeight.add(weight);
                        }
                    }
                    Log.d(TAG, "onResponse: productWeight "+productWeight);
                    JSONArray server_response = jsonObject.getJSONArray("server_response");
                    String name;
                    int product_id;
                    double proteins;
                    double fats;
                    double carbs;
                    String date;
                    String username;
                    double kcal;
                    if (server_response.length() > 0){
                        for (int i = 0; i < server_response.length(); i++){
                            JSONObject c = server_response.getJSONObject(i);
                            product_id = Integer.valueOf(c.getString("product_id"));
                            name = c.getString("name");
                            proteins = Double.valueOf(c.getString("proteins"));
                            fats = Double.valueOf(c.getString("fats"));
                            carbs = Double.valueOf(c.getString("carbs"));
                            username = c.getString("username");
                            date = c.getString("date");

                            proteins = proteins*(Double.valueOf(productWeight.get(i))/100);
                            fats = fats*(Double.valueOf(productWeight.get(i))/100);
                            carbs = carbs*(Double.valueOf(productWeight.get(i))/100);

                            kcal = (proteins*4)+(fats*9)+(carbs*4);

                            String Name = name.substring(0,1).toUpperCase() + name.substring(1);

                            kcalList.add(String.format("%.1f",kcal));
                            proteinsList.add(String.format("%.1f",proteins));
                            fatsList.add(String.format("%.1f",fats));
                            carbsList.add(String.format("%.1f",carbs));

                            Diet diet = new Diet(String.valueOf(product_id), Name, productWeight.get(i), String.format("%.1f",proteins), String.format("%.1f",fats), String.format("%.1f",carbs),String.format("%.0f",kcal));
                            dietArrayList.add(diet);
//                            Diet diet = new Diet(String.valueOf(product_id),Name,String.valueOf(proteins),String.valueOf(fats),String.valueOf(carbs),String.valueOf(weight),username,date);
//                            dietArrayList.add(diet);
//                            productNameList.add(Html.fromHtml("<medium>"+Name+" <b>"+productWeight.get(i)+" g</b></medium>"+"<br><small>"+product_id+"</small><br>"+"<small>"+"Proteins: "+"<font color=#99ccff><b>"+String.format("%.1f",proteins)+"</b></font>"+"<font color=#c1c3bb>"+" / </font>"+"Fats: "+"<font color=#d9b526><b>"+String.format("%.1f",fats)+"</b></font>"+"<font color=#c1c3bb>"+" / </font>"+"Carbohydrates: "+"<font color=#ff9980><b>"+String.format("%.1f",carbs)+"</b></font>"+" "+"</small>"+"<br><b><small>"+String.format("%.0f",kcal)+" kCal</small></b>"));

                            // TODO: 20.03.2018 need to make the calorie counter and then parsing and sending to mysql
                        }

                        for (int a = 0; a < server_response.length(); a++){
                            kcalResult +=       Double.valueOf(kcalList.get(a));
                            proteinsResult +=   Double.valueOf(proteinsList.get(a));
                            fatsResult +=       Double.valueOf(fatsList.get(a));
                            carbsResult +=      Double.valueOf(carbsList.get(a));
                        }
                    }

                    mTaskListView.setAdapter(adapter);
                    mTaskListView.invalidate();

                    TextView tvProteins = findViewById(R.id.tvProteins);
                    tvProteins.setText(String.format("%.1f",proteinsResult));

                    TextView tvFats = findViewById(R.id.tvFats);
                    tvFats.setText(String.format("%.1f",fatsResult));

                    TextView tvCarbs = findViewById(R.id.tvCarbs);
                    tvCarbs.setText(String.format("%.1f",carbsResult));

                    TextView tvKcal = findViewById(R.id.tvKcal);
                    tvKcal.setText(String.format("%.0f",kcalResult));

                    Double proteinGoal = 0d;
                    Double fatGoal = 0d;
                    Double carbsGoal = 0d;

                    proteinGoal = (Double.valueOf(result)*(Double.valueOf(proteinsratio)*0.01))/4;
                    fatGoal = Double.valueOf(result)*(Double.valueOf(fatsratio)*0.01)/9;
                    carbsGoal = Double.valueOf(result)*(Double.valueOf(carbsratio)*0.01)/4;

                    TextView tvTotalProteins = findViewById(R.id.tvTotalProteins);
                    tvTotalProteins.setText(String.format("%.0f",proteinGoal));

                    TextView tvTotalFats = findViewById(R.id.tvTotalFats);
                    tvTotalFats.setText(String.format("%.0f",fatGoal));

                    TextView tvTotalCarbs = findViewById(R.id.tvTotalCarbs);
                    tvTotalCarbs.setText(String.format("%.0f",carbsGoal));

                    TextView tvTotalKcal = findViewById(R.id.tvTotalKcal);
                    tvTotalKcal.setText(result.toString());


                    pbProteins.getProgressDrawable().setColorFilter(0xFF3287C3, PorterDuff.Mode.SRC_IN);
                    pbProteins.setMax(proteinGoal.intValue());
                    pbProteins.setProgress(Integer.valueOf(String.format("%.0f",proteinsResult)));

                    pbFats.getProgressDrawable().setColorFilter(0xFFF3AE28, PorterDuff.Mode.SRC_IN);
                    pbFats.setMax(fatGoal.intValue());
                    pbFats.setProgress(Integer.valueOf(String.format("%.0f",fatsResult)));

                    pbCarbs.getProgressDrawable().setColorFilter(0xFFBD2121, PorterDuff.Mode.SRC_IN);
                    pbCarbs.setMax(carbsGoal.intValue());
                    pbCarbs.setProgress(Integer.valueOf(String.format("%.0f",carbsResult)));

                    pbKcal.getProgressDrawable().setColorFilter(0xFF89C611, PorterDuff.Mode.SRC_IN);
                    pbKcal.setMax(Integer.valueOf(result));
                    pbKcal.setProgress(Integer.valueOf(String.format("%.0f",kcalResult)));


                    if (Integer.valueOf(String.format("%.0f",kcalResult)) > Integer.valueOf(result)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            pbKcal.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
                        } else {
                            pbKcal.getProgressDrawable().setColorFilter(0xFF89C611, PorterDuff.Mode.SRC_IN);
                        }
                    }

                    if ((int) proteinsResult > proteinGoal){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            pbProteins.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
                        }
                        else {
                            pbProteins.getProgressDrawable().setColorFilter(0xFF3287C3, PorterDuff.Mode.SRC_IN);
                        }
                    }

                    if ((int) fatsResult > fatGoal) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            pbFats.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
                        } else {
                            pbFats.getProgressDrawable().setColorFilter(0xFFF3AE28, PorterDuff.Mode.SRC_IN);
                        }
                    }

                    if ((int) carbsResult > carbsGoal) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            pbCarbs.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
                        } else {
                            pbCarbs.getProgressDrawable().setColorFilter(0xFFBD2121, PorterDuff.Mode.SRC_IN);
                        }
                    }


                    Log.i(TAG, "onResponse: proteinGoal: "+proteinGoal+" fatGoal: "+fatGoal+" carbGoal: "+carbsGoal);

                    if (kcalResult > 0) {

                        Response.Listener<String> listener1 = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(TAG, "onResponse: DietUpdateKcalResult " + response);
                            }
                        };
                        DietUpdateKcalResult dietUpdateKcalResult = new DietUpdateKcalResult(userIDint, String.format("%.1f", kcalResult), userName, dateInsde, listener1);
                        RequestQueue queue = Volley.newRequestQueue(DietActivity.this);
                        queue.add(dietUpdateKcalResult);
                    }
                    else if (kcalResult == 0){
                        Response.Listener<String> listener2 = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(TAG, "onResponse: DietDeleteMeal "+response);
                            }
                        };
                        DietDeleteMeal dietDeleteMeal = new DietDeleteMeal(userName,dateInsde,listener2);
                        RequestQueue queue = Volley.newRequestQueue(DietActivity.this);
                        queue.add(dietDeleteMeal);
                    }
                    Log.i(TAG, "onResponse: kcalList: "+kcalResult+" P: "+proteinsResult+" F: "+fatsResult+" C: "+carbsResult);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        DietShowByUser dietShowByUser = new DietShowByUser(userName, dateInsde, responseListener);
        RequestQueue queue = Volley.newRequestQueue(DietActivity.this);
        queue.add(dietShowByUser);
    }

    public void deleteMealTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = parent.findViewById(R.id.meal_title);
        TextView tvId = parent.findViewById(R.id.meal_id);
        TextView tvWeight = parent.findViewById(R.id.meal_weight);

        String description = String.valueOf(taskTextView.getText());
        String id = String.valueOf(tvId.getText());
        String weight = String.valueOf(tvWeight.getText());

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        };
        DietDeleteRequest dietDeleteRequest = new DietDeleteRequest(Integer.valueOf(id),weight,userName,dateInsde,responseListener);
        RequestQueue queue = Volley.newRequestQueue(DietActivity.this);
        queue.add(dietDeleteRequest);

        Log.d(TAG, "deleteMealTask: id " + id);
        Log.d(TAG, "deleteMealTask: weight "+ weight);
        Log.d(TAG, "deleteMealTask: username "+userName);
        Log.d(TAG, "deleteMealTask: dateInside "+dateInsde);

        productWeight.clear();
        dietArrayList.clear();
        new LongRunningTask().execute();
    }

    @Override
    protected void onRestart() {

        productWeight.clear();
        dietArrayList.clear();
        new LongRunningTask().execute();
        super.onRestart();
    }
}

