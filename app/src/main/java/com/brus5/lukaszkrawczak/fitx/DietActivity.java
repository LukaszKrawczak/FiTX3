package com.brus5.lukaszkrawczak.fitx;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Diet.Diet;
import com.brus5.lukaszkrawczak.fitx.Diet.DietDeleteMeal;
import com.brus5.lukaszkrawczak.fitx.Diet.DietDeleteRequest;
import com.brus5.lukaszkrawczak.fitx.Diet.DietEditWeight;
import com.brus5.lukaszkrawczak.fitx.Diet.DietListAdapter;
import com.brus5.lukaszkrawczak.fitx.Diet.DietShowByUser;
import com.brus5.lukaszkrawczak.fitx.Diet.DietUpdateKcalResult;

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


    String userName, userFirstName, userBirthday, userPassword, userEmail, userMale, userID;

    String name, username, age, password, email, male, somatotypeS;
    String description;
    String weight;
    int id,userIDint,userAgeint;


    double proteins = 0d;
    double  fats = 0d;
    double  carbs = 0d;

    double  pWeight = 0d;

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
    String cDate = simpleDateFormat.format(c.getTime());
    String dateInsde;
    ProgressBar progressBarProteins,progressBarFats,progressBarCarbs,progressBarKcal;
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

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButtonTEST);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent searchForMeal = new Intent(DietActivity.this, DietSearchActivity2.class);
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
        });


        mTaskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                View parent1 = (View) view.getParent();

//                String selectedSweet = mTaskListView.getItemAtPosition(position).toString();

                TextView taskTextView = view.findViewById(R.id.meal_title);
                TextView tvId = view.findViewById(R.id.meal_id);
                TextView tvWeight = view.findViewById(R.id.meal_weight);
                final TextView tvProteins = view.findViewById(R.id.meal_proteins);
                TextView tvFats =   view.findViewById(R.id.meal_fats);
                TextView tvCarbs = view.findViewById(R.id.meal_carbs);
                TextView tvKcal = view.findViewById(R.id.meal_kcal);

               final String description = taskTextView.getText().toString();
               final int idd = Integer.valueOf(tvId.getText().toString());
               final String weight = String.valueOf(tvWeight.getText());
               final String proteins = String.valueOf(tvProteins.getText());
               final String fats = String.valueOf(tvFats.getText());
               final String carbs = String.valueOf(tvCarbs.getText());
               final String kcal = String.valueOf(tvKcal.getText());



               setProteins(Double.valueOf(proteins));
               setFats(Double.valueOf(fats));
               setCarbs(Double.valueOf(carbs));


                Log.d(TAG, "onItemLongClick: desc "+description );
                Log.d(TAG, "onItemLongClick: idd "+idd );
                Log.d(TAG, "onItemLongClick: weight "+weight );


                LayoutInflater inflater = LayoutInflater.from(DietActivity.this);
                View textEntryView = inflater.inflate(R.layout.activity_diet_options,null);

                final TextView mProteins = textEntryView.findViewById(R.id.meal_proteins4);
                final TextView mFats = textEntryView.findViewById(R.id.meal_fats4);
                final TextView mCarbs = textEntryView.findViewById(R.id.meal_carbs4);


                mProteins.setText(proteins);
                mFats.setText(fats);
                mCarbs.setText(carbs);


//                double dProteins = Double.valueOf(proteins);
//                double dFats = Double.valueOf(fats);
//                double dCarbs = Double.valueOf(carbs);

//                setProteins(Double.valueOf(proteins));


                Log.e(TAG, "onItemLongClick: getProteins "+Double.valueOf(proteins)*100/Double.valueOf(weight));

                final EditText editTextUserWeight = textEntryView.findViewById(R.id.editTextChangeWeight);

                tvProteins.setText(proteins);
                tvFats.setText(fats);
                tvCarbs.setText(carbs);

                editTextUserWeight.setText(weight);

                final double dProteins = Double.valueOf(proteins) * 100 / Double.valueOf(weight);
                final double dFats = Double.valueOf(fats) * 100 / Double.valueOf(weight);
                final double dCarbs = Double.valueOf(carbs) * 100 / Double.valueOf(weight);
                Log.e(TAG, "onItemLongClick: proteins100 "+dProteins );

                editTextUserWeight.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        double finalProteins = 0d;
                        double finalFats = 0d;
                        double finalCarbs = 0d;

                        if (!s.toString().isEmpty()) {
                            finalProteins = Double.valueOf(s.toString())/100 * dProteins;
                            finalFats = Double.valueOf(s.toString())/100 * dFats;
                            finalCarbs = Double.valueOf(s.toString())/100 * dCarbs;

                            Log.e(TAG, "beforeTextChanged: proteins final "+finalProteins);
                        }
                        mProteins.setText(String.format("%.1f",finalProteins));
                        mFats.setText(String.format("%.1f",finalFats));
                        mCarbs.setText(String.format("%.1f",finalCarbs));
//                        double value = getPweight()*100*getProteins()/getPweight();
//                        mProteins.setText(String.format("%.1f",value));
                    }
                });

                AlertDialog.Builder alert = new AlertDialog.Builder(DietActivity.this);
                alert.setTitle("Edit meal")
                        .setView(textEntryView)
                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Response.Listener<String> listener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e(TAG,"response"+response);
                                    }
                                };
                                DietEditWeight dietEditWeight = new DietEditWeight(idd,editTextUserWeight.getText().toString(),userName,dateInsde,listener);
                                RequestQueue queue = Volley.newRequestQueue(DietActivity.this);
                                queue.add(dietEditWeight);
                                // Load data after 0,5s
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadData();
                                    }
                                },500);
                            }
                        })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Response.Listener<String> listener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e(TAG,"response"+response);
                            }
                        };
                        DietDeleteRequest dietDeleteRequest = new DietDeleteRequest(Integer.valueOf(idd),weight,userName,dateInsde,listener);
                        RequestQueue queue = Volley.newRequestQueue(DietActivity.this);
                        queue.add(dietDeleteRequest);
                        // Load data after 0,5s
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadData();
                            }
                        },500);
                    }
                })
                        .setIcon(R.drawable.icon_diet1)
                ;

                alert.show().getWindow().setLayout(800,800);





                Log.e(TAG, "onItemLongClick: desc "+description);
                return true;


            }
        });


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

        progressBarProteins = findViewById(R.id.progressBarProteins);
        progressBarFats = findViewById(R.id.progressBarFats);
        progressBarCarbs = findViewById(R.id.progressBarCarbs);
        progressBarKcal = findViewById(R.id.progressBarKcal);

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

                Log.d(TAG, "onDateSelected: date "+date);
                Log.d(TAG, "onDateSelected: dateInside "+dateInsde);
                Log.d(TAG, "onDateSelected: cDate "+cDate);

                loadData();


//                wait2secs();

                Toast.makeText(DietActivity.this, DateFormat.getDateInstance().format(date) + " is selected", Toast.LENGTH_SHORT).show();
            }

            private void wait2secs() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },1000);

            }


        }


        );

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

            super.onPostExecute(aVoid);
        }
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

        productWeight.clear();
        dietArrayList.clear();
        kcalList.clear();
        proteinsList.clear();
        fatsList.clear();
        carbsList.clear();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response "+response);
                double kcalResult = 0d;
                double proteinsResult = 0d;
                double fatsResult = 0d;
                double carbsResult = 0d;

                String proteinsratio = "";
                String fatsratio = "";
                String carbsratio="";
                String result="0";



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
                    String name="";
                    int product_id;
                    double proteins;
                    double fats;
                    double carbs;
                    double kcal;
                    String date="";
                    String username="";
                    if (server_response.length() > 0){
                        for (int i = 0; i < server_response.length(); i++){
                            JSONObject c = server_response.getJSONObject(i);
                            product_id = Integer.valueOf(c.getString("product_id"));
                            name = c.getString("name");
                            proteins = Double.valueOf(c.getString("proteins"));
                            fats = Double.valueOf(c.getString("fats"));
                            carbs = Double.valueOf(c.getString("carbs"));
                            kcal = Double.valueOf(c.getString("kcal"));
                            username = c.getString("username");
                            date = c.getString("date");
                            proteins = proteins*(Double.valueOf(productWeight.get(i))/100);
                            fats = fats*(Double.valueOf(productWeight.get(i))/100);
                            carbs = carbs*(Double.valueOf(productWeight.get(i))/100);

                            double finalkcal = kcal * ((Double.valueOf(productWeight.get(i)))*0.01);
                            Log.e(TAG, "onResponse: date "+date);
                            String upName = name.substring(0,1).toUpperCase() + name.substring(1);

                            kcalList.add(String.format("%.1f",finalkcal));
                            proteinsList.add(String.format("%.1f",proteins));
                            fatsList.add(String.format("%.1f",fats));
                            carbsList.add(String.format("%.1f",carbs));
                            Diet diet = new Diet(String.valueOf(product_id), upName, productWeight.get(i), String.format("%.1f",proteins), String.format("%.1f",fats), String.format("%.1f",carbs),String.format("%.0f",finalkcal));
                            dietArrayList.add(diet);
                        }

                        for (int a = 0; a < server_response.length(); a++){
                            kcalResult +=       Double.valueOf(kcalList.get(a));
                            proteinsResult +=   Double.valueOf(proteinsList.get(a));
                            fatsResult +=       Double.valueOf(fatsList.get(a));
                            carbsResult +=      Double.valueOf(carbsList.get(a));
                        }
                    }


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


                    progressBarProteins.getProgressDrawable().setColorFilter(0xFF3287C3, PorterDuff.Mode.SRC_IN);
                    progressBarProteins.setMax(proteinGoal.intValue());
                    progressBarProteins.setProgress(Integer.valueOf(String.format("%.0f",proteinsResult)));

                    progressBarFats.getProgressDrawable().setColorFilter(0xFFF3AE28, PorterDuff.Mode.SRC_IN);
                    progressBarFats.setMax(fatGoal.intValue());
                    progressBarFats.setProgress(Integer.valueOf(String.format("%.0f",fatsResult)));

                    progressBarCarbs.getProgressDrawable().setColorFilter(0xFFBD2121, PorterDuff.Mode.SRC_IN);
                    progressBarCarbs.setMax(carbsGoal.intValue());
                    progressBarCarbs.setProgress(Integer.valueOf(String.format("%.0f",carbsResult)));

                    progressBarKcal.getProgressDrawable().setColorFilter(0xFF89C611, PorterDuff.Mode.SRC_IN);
                    progressBarKcal.setMax(Integer.valueOf(result));
                    progressBarKcal.setProgress(Integer.valueOf(String.format("%.0f",kcalResult)));


                    if (Integer.valueOf(String.format("%.0f",kcalResult)) > Integer.valueOf(result)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            progressBarKcal.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
                        } else {
                            progressBarKcal.getProgressDrawable().setColorFilter(0xFF89C611, PorterDuff.Mode.SRC_IN);
                        }
                    }

                    if ((int) proteinsResult > proteinGoal){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            progressBarProteins.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
                        }
                        else {
                            progressBarProteins.getProgressDrawable().setColorFilter(0xFF3287C3, PorterDuff.Mode.SRC_IN);
                        }
                    }

                    if ((int) fatsResult > fatGoal) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            progressBarFats.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
                        } else {
                            progressBarFats.getProgressDrawable().setColorFilter(0xFFF3AE28, PorterDuff.Mode.SRC_IN);
                        }
                    }

                    if ((int) carbsResult > carbsGoal) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            progressBarCarbs.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
                        } else {
                            progressBarCarbs.getProgressDrawable().setColorFilter(0xFFBD2121, PorterDuff.Mode.SRC_IN);
                        }
                    }


                    Log.i(TAG, "onResponse: proteinGoal: "+proteinGoal+" fatGoal: "+fatGoal+" carbGoal: "+carbsGoal);

                    if (kcalResult > 0d && cDate.equals(dateInsde)) {

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
                    else if (kcalResult == 0d){
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

                    adapter = new DietListAdapter(DietActivity.this,R.layout.meal_row,dietArrayList);
                    mTaskListView.setAdapter(adapter);
                    mTaskListView.invalidate();

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        };
        DietShowByUser dietShowByUser = new DietShowByUser(userName, dateInsde, responseListener);
        RequestQueue queue = Volley.newRequestQueue(DietActivity.this);
        queue.add(dietShowByUser);
    }



//    public void deleteMealTask(View view) {



    @Override
    protected void onRestart() {

        productWeight.clear();
        dietArrayList.clear();

        new LongRunningTask().execute();
        super.onRestart();
    }



    //    }
//        new LongRunningTask().execute();
//        dietArrayList.clear();
//        productWeight.clear();
//
//        Log.d(TAG, "deleteMealTask: dateInside "+dateInsde);
//        Log.d(TAG, "deleteMealTask: username "+userName);
//        Log.d(TAG, "deleteMealTask: weight "+ weight);
//        Log.d(TAG, "deleteMealTask: id " + id);
//
//        queue.add(dietDeleteRequest);
//        RequestQueue queue = Volley.newRequestQueue(DietActivity.this);
//        DietDeleteRequest dietDeleteRequest = new DietDeleteRequest(Integer.valueOf(id),weight,userName,dateInsde,responseListener);
//        };
//            }
//
//            public void onResponse(String response) {
//            @Override
//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//
//        String weight = String.valueOf(tvWeight.getText());
//        String id = String.valueOf(tvId.getText());
//        String description = String.valueOf(taskTextView.getText());
//
//        TextView tvWeight = parent.findViewById(R.id.meal_weight);
//        TextView tvId = parent.findViewById(R.id.meal_id);
//        TextView taskTextView = parent.findViewById(R.id.meal_title);
//        View parent = (View) view.getParent();



    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    //    public int setCountKcal(int proteins, int fats, int carbs){
//        return proteins*4+fats*9+carbs*4;
//    }
    public double getCountKcal(){
        return proteins*4.0+fats*9.0+carbs*4.0;
    }

}

