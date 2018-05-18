package com.brus5.lukaszkrawczak.fitx;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Training.DTO.TrainingDeleteDTO;
import com.brus5.lukaszkrawczak.fitx.Training.DTO.TrainingEditDTO;
import com.brus5.lukaszkrawczak.fitx.Training.DTO.TrainingShowByUserDTO;
import com.brus5.lukaszkrawczak.fitx.Training.Training;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingListAdapter;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingService;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

import static com.brus5.lukaszkrawczak.fitx.Training.TrainingService.CONNECTION_INTERNET_FAILED;

public class TrainingActivity extends AppCompatActivity {

    private static final String TAG = "TrainingActivity";
    private static final String TRAINING = "Training/";
    private static final String SHOW_TRAINING_URL = Configuration.BASE_URL + TRAINING + "ShowByUser.php";

    String userName, userFirstName, userBirthday, userPassword, userEmail, userMale, userID;
    int id,userIDint,userAgeint;
    private ArrayList<String> arrayDescription = new ArrayList<>();
    private ArrayList<String> arrayDone = new ArrayList<>();
    private ArrayList<String> arrayRest = new ArrayList<>();
    private ArrayList<String> arrayReps = new ArrayList<>();
    private ArrayList<String> arrayWeight = new ArrayList<>();
    ArrayList<Training> trainingArrayList = new ArrayList<>();
    Training training;
    int setNumber = 0;
    String reps = "";
    String weight = "";

    ListView mTaskListView;

    TrainingDetails trainingDetails = new TrainingDetails();

    private Map<String,String> params;

    /* Gettings date */
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String date = simpleDateFormat.format(c.getTime());
    TrainingListAdapter adapter;
    String dateInsde;
    Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_training);
        Log.d(TAG,"onCreate: Started.");
        getWindow().setStatusBarColor(ContextCompat.getColor(TrainingActivity.this,R.color.color_main_activity_statusbar));
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        mTaskListView = findViewById(R.id.list_training);

        Log.e(TAG, "SaveSharedPreference: "+SaveSharedPreference.getUserFirstName(TrainingActivity.this));
        Log.e(TAG, "SaveSharedPreference: "+SaveSharedPreference.getUserBirthday(TrainingActivity.this));
        Log.e(TAG, "SaveSharedPreference: "+SaveSharedPreference.getUserEmail(TrainingActivity.this));
        Log.e(TAG, "SaveSharedPreference: "+SaveSharedPreference.getUserGender(TrainingActivity.this));

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

        loadCalendar();

        mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTaskListViewOnItemClick(view);
            }
        });
    }

    private void loadCalendar() {
        HorizontalCalendar horizontalCalendar;
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        horizontalCalendar = new HorizontalCalendar.Builder(TrainingActivity.this, R.id.calendarView1)
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
                prepareLoadingData();
            }

        });
        prepareLoadingData();
    }



//    public void loadData() {
//        arrayReps.clear();
//        arrayWeight.clear();
//        arrayDescription.clear();
//        trainingArrayList.clear();
//
//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, "onResponse: response "+response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray trainings_info = jsonObject.getJSONArray("trainings_info");
//                    String done;
//                    String rest;
//                    String reps;
//                    String weight;
//                    if (trainings_info.length() > 0) {
//                        for (int i = 0; i < trainings_info.length(); i++) {
//                            JSONObject d = trainings_info.getJSONObject(i);
//                            done = d.getString("done");
//                            rest = d.getString("rest");
//                            reps = d.getString("reps");
//                            weight = d.getString("weight");
//                            arrayDone.add(done);
//                            arrayRest.add(rest);
//                            arrayReps.add(reps);
//                            arrayWeight.add(weight);
//                        }
//                    }
//                    JSONArray server_response = jsonObject.getJSONArray("server_response");
//                    String id;
//                    String description;
//                    if (server_response.length() > 0){
//                        for (int i = 0; i < server_response.length(); i++){
//                            JSONObject c = server_response.getJSONObject(i);
//                            id = c.getString("id");
//                            description = c.getString("description");
//                            arrayDescription.add(description);
//                            Training training = new Training(id,description,arrayRest.get(i),arrayWeight.get(i),arrayReps.get(i));
//                            trainingArrayList.add(training);
//                        }
//                    }
//                    adapter = new TrainingListAdapter(TrainingActivity.this,R.layout.training_row,trainingArrayList);
//                    mTaskListView.setAdapter(adapter);
//                    mTaskListView.invalidate();
//                    Log.e(TAG,"arrayDescription: "+arrayDescription);
//                    Log.d(TAG,"trainingArrayList: "+trainingArrayList);
//                    Log.e(TAG, "onResponse: arrayDone "+arrayDone);
//                    Log.e(TAG, "onResponse: arrayDone "+arrayDone);
//                    Log.e(TAG, "onResponse: arrayRest "+arrayRest);
//                    Log.e(TAG, "onResponse: arrayReps "+arrayReps);
//                    Log.e(TAG, "onResponse: arrayWeight "+arrayWeight);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        TrainingShowByUser trainingShowByUser = new TrainingShowByUser(userName,dateInsde,responseListener);
//        RequestQueue queue = Volley.newRequestQueue(TrainingActivity.this);
//        queue.add(trainingShowByUser);
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.training, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_training:
                Intent searchForTraining = new Intent(TrainingActivity.this, TrainingSearchActivity.class);
                searchForTraining.putExtra("userIDint",userIDint);
                searchForTraining.putExtra("userFirstName",userFirstName);
                searchForTraining.putExtra("userName",userName);
                searchForTraining.putExtra("userBirthday",userBirthday);
                searchForTraining.putExtra("userAgeint",userAgeint);
                searchForTraining.putExtra("userPassword",userPassword);
                searchForTraining.putExtra("userEmail", userEmail);
                searchForTraining.putExtra("userMale", userMale);
                searchForTraining.putExtra("dateInsde", dateInsde);
                TrainingActivity.this.startActivity(searchForTraining);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareLoadingData();
            }
        },500);
    }

    //    public void loadUserDailyTraining(final TrainingShowByUserDTO trainingShowByUserDTO, final Context ctx){
//        StringRequest strRequest = new StringRequest(Request.Method.POST, SHOW_TRAINING_URL,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response)
//                    {
//                        Log.i(TAG, "onResponse: "+response);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONArray trainings_info = jsonObject.getJSONArray("trainings_info");
//                            String done;
//                            String rest;
//                            String reps;
//                            String weight;
//                            if (trainings_info.length() > 0) {
//                                for (int i = 0; i < trainings_info.length(); i++) {
//                                    JSONObject d = trainings_info.getJSONObject(i);
//                                    done = d.getString("done");
//                                    rest = d.getString("rest");
//                                    reps = d.getString("reps");
//                                    weight = d.getString("weight");
//
//                                    arrayDone.add(done);
//                                    arrayRest.add(rest);
//                                    arrayReps.add(reps);
//                                    arrayWeight.add(weight);
//
//
//                                    trainingsInfo.setDone(done);
//                                    trainingsInfo.setRest(rest);
//                                    trainingsInfo.setReps(reps);
//                                    trainingsInfo.setWeight(weight);
//                                    Log.e(TAG, "onResponse trainingsInfo: "+trainingsInfo.getReps());
//                                }
//                            }
//                            JSONArray server_response = jsonObject.getJSONArray("server_response");
//                            String id;
//                            String description;
//                            if (server_response.length() > 0){
//                                for (int i = 0; i < server_response.length(); i++){
//                                    JSONObject c = server_response.getJSONObject(i);
//                                    id = c.getString("id");
//                                    description = c.getString("description");
//                                    arrayDescription.add(description);
////                                    Training training = new Training(id,description,arrayRest.get(i),arrayWeight.get(i),arrayReps.get(i));
//
//                                    Training training = new Training(id,description,trainingsInfo.getRest(),trainingsInfo.getWeight(),trainingsInfo.getReps());
//                                    trainingArrayList.add(training);
//                                }
//                            }
//                            adapter = new TrainingListAdapter(TrainingActivity.this,R.layout.training_row,trainingArrayList);
//                            mTaskListView.setAdapter(adapter);
//                            mTaskListView.invalidate();
//                            Log.e(TAG,"arrayDescription: "+arrayDescription);
//                            Log.d(TAG,"trainingArrayList: "+trainingArrayList);
//                            Log.e(TAG, "onResponse: arrayDone "+arrayDone);
//                            Log.e(TAG, "onResponse: arrayDone "+arrayDone);
//                            Log.e(TAG, "onResponse: arrayRest "+arrayRest);
//                            Log.e(TAG, "onResponse: arrayReps "+arrayReps);
//                            Log.e(TAG, "onResponse: arrayWeight "+arrayWeight);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error)
//                    {
//                        Toast.makeText(ctx, CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();
//                        Log.e(TAG, "onErrorResponse: Error"+error);
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams()
//            {
//                params = new HashMap<>();
//                params.put("username", trainingShowByUserDTO.username);
//                params.put("date", trainingShowByUserDTO.date);
//                return params;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(ctx);
//        queue.add(strRequest);
//    }

    public void prepareLoadingData(){
        trainingArrayList.clear();
        TrainingShowByUserDTO trainingShowByUserDTO = new TrainingShowByUserDTO();
        trainingShowByUserDTO.username = userName;
        trainingShowByUserDTO.date = dateInsde;
        loadUserDailyTraining(trainingShowByUserDTO,TrainingActivity.this);
    }

    public void loadUserDailyTraining(final TrainingShowByUserDTO trainingShowByUserDTO, final Context ctx){
        StringRequest strRequest = new StringRequest(Request.Method.POST, SHOW_TRAINING_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray trainings_info = jsonObject.getJSONArray("trainings_info");

                            String done;
                            String rest;
                            String reps;
                            String weight;
                            if (trainings_info.length() > 0) {
                                for (int i = 0; i < trainings_info.length(); i++) {
                                    JSONObject d = trainings_info.getJSONObject(i);
                                    done = d.getString("done");
                                    rest = d.getString("rest");
                                    reps = d.getString("reps");
                                    weight = d.getString("weight");
                                    arrayDone.add(done);
                                    arrayRest.add(rest);
                                    arrayReps.add(reps);
                                    arrayWeight.add(weight);
                                }
                            }

                            JSONArray server_response = jsonObject.getJSONArray("server_response");
                            String id;
                            String description;
                            if (server_response.length() > 0){
                                for (int i = 0; i < server_response.length(); i++){
                                    JSONObject c = server_response.getJSONObject(i);
                                    id = c.getString("id");
                                    description = c.getString("description");

                                    arrayDescription.add(description);
                                    Training training = new Training(id,description,arrayRest.get(i),arrayWeight.get(i),arrayReps.get(i));
                                    trainingArrayList.add(training);
                                }
                            }

                            adapter = new TrainingListAdapter(TrainingActivity.this,R.layout.training_row,trainingArrayList);
                            mTaskListView.setAdapter(adapter);
                            mTaskListView.invalidate();

                            Log.e(TAG,"arrayDescription: "+arrayDescription);
                            Log.d(TAG,"trainingArrayList: "+trainingArrayList);
                            Log.e(TAG, "onResponse: arrayDone "+arrayDone);
                            Log.e(TAG, "onResponse: arrayDone "+arrayDone);
                            Log.e(TAG, "onResponse: arrayRest "+arrayRest);
                            Log.e(TAG, "onResponse: arrayReps "+arrayReps);
                            Log.e(TAG, "onResponse: arrayWeight "+arrayWeight);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(ctx, CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onErrorResponse: Error"+error);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                params = new HashMap<>();
                params.put("username", trainingShowByUserDTO.username);
                params.put("date", trainingShowByUserDTO.date);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    public View mTaskListViewOnItemClick(View view){

        View parent1 = (View) view.getParent();
        TextView task_name = view.findViewById(R.id.task_name);
        TextView task_rest = view.findViewById(R.id.task_restTime);
        TextView task_set1 = view.findViewById(R.id.task_set1);
        TextView task_set2 = view.findViewById(R.id.task_set2);
        TextView task_set3 = view.findViewById(R.id.task_set3);
        TextView task_set4 = view.findViewById(R.id.task_set4);
        TextView task_set5 = view.findViewById(R.id.task_set5);
        TextView task_set6 = view.findViewById(R.id.task_set6);
        TextView task_set7 = view.findViewById(R.id.task_set7);
        TextView task_set8 = view.findViewById(R.id.task_set8);
        TextView task_set9 = view.findViewById(R.id.task_set9);
        TextView task_set10 = view.findViewById(R.id.task_set10);
        final TextView task_id = view.findViewById(R.id.task_id);
        Log.e(TAG, "onItemLongClick: task_id "+task_id.getText().toString());
        final String description = task_name.getText().toString();

        LayoutInflater inflater = LayoutInflater.from(TrainingActivity.this);
        View textEntryView = inflater.inflate(R.layout.activity_training_options,null);

        final TextView mTask_name = textEntryView.findViewById(R.id.task_name4);
        final TextView mRest_time = textEntryView.findViewById(R.id.task_restTime4);

        mTask_name.setText(task_name.getText().toString());

        final EditText editTextRestTime4 = textEntryView.findViewById(R.id.editTextRestTime4);
        final EditText editTextReps1 = textEntryView.findViewById(R.id.editTextReps1);
        final EditText editTextReps2 = textEntryView.findViewById(R.id.editTextReps2);
        final EditText editTextReps3 = textEntryView.findViewById(R.id.editTextReps3);
        final EditText editTextReps4 = textEntryView.findViewById(R.id.editTextReps4);
        final EditText editTextReps5 = textEntryView.findViewById(R.id.editTextReps5);
        final EditText editTextReps6 = textEntryView.findViewById(R.id.editTextReps6);
        final EditText editTextReps7 = textEntryView.findViewById(R.id.editTextReps7);
        final EditText editTextReps8 = textEntryView.findViewById(R.id.editTextReps8);
        final EditText editTextReps9 = textEntryView.findViewById(R.id.editTextReps9);
        final EditText editTextReps10 = textEntryView.findViewById(R.id.editTextReps10);
        final EditText editTextWeight1 = textEntryView.findViewById(R.id.editTextWeight1);
        final EditText editTextWeight2 = textEntryView.findViewById(R.id.editTextWeight2);
        final EditText editTextWeight3 = textEntryView.findViewById(R.id.editTextWeight3);
        final EditText editTextWeight4 = textEntryView.findViewById(R.id.editTextWeight4);
        final EditText editTextWeight5 = textEntryView.findViewById(R.id.editTextWeight5);
        final EditText editTextWeight6 = textEntryView.findViewById(R.id.editTextWeight6);
        final EditText editTextWeight7 = textEntryView.findViewById(R.id.editTextWeight7);
        final EditText editTextWeight8 = textEntryView.findViewById(R.id.editTextWeight8);
        final EditText editTextWeight9 = textEntryView.findViewById(R.id.editTextWeight9);
        final EditText editTextWeight10 = textEntryView.findViewById(R.id.editTextWeight10);
        final TextView textViewNumber1 = textEntryView.findViewById(R.id.textViewNumber1);
        final TextView textViewNumber2 = textEntryView.findViewById(R.id.textViewNumber2);
        final TextView textViewNumber3 = textEntryView.findViewById(R.id.textViewNumber3);
        final TextView textViewNumber4 = textEntryView.findViewById(R.id.textViewNumber4);
        final TextView textViewNumber5 = textEntryView.findViewById(R.id.textViewNumber5);
        final TextView textViewNumber6 = textEntryView.findViewById(R.id.textViewNumber6);
        final TextView textViewNumber7 = textEntryView.findViewById(R.id.textViewNumber7);
        final TextView textViewNumber8 = textEntryView.findViewById(R.id.textViewNumber8);
        final TextView textViewNumber9 = textEntryView.findViewById(R.id.textViewNumber9);
        final TextView textViewNumber10 = textEntryView.findViewById(R.id.textViewNumber10);

        final TextView textViewReps1 = textEntryView.findViewById(R.id.textViewReps1);
        final TextView textViewWeight1 = textEntryView.findViewById(R.id.textViewWeight1);
        final TextView textViewReps2 = textEntryView.findViewById(R.id.textViewReps2);
        final TextView textViewWeight2 = textEntryView.findViewById(R.id.textViewWeight2);

        String mTask_rest = task_rest.getText().toString().replaceAll("\\p{Lower}", "");
        editTextRestTime4.setText(mTask_rest);

        editTextReps1.setText(task_set1.getText().toString());

        final Button buttonAddSet = textEntryView.findViewById(R.id.buttonAddSet);

        final ArrayList<String> reps_list = new ArrayList<>();
        final ArrayList<String> weight_list = new ArrayList<>();

        if (task_set1.getText() != null  && !task_set1.getText().equals("")){
            String mTask_reps = task_set1.getText().toString().replaceAll("\\p{Lower}", " ");
            Log.e(TAG, "onItemLongClick: mtask_reps "+mTask_reps);
            String[] mReps_table = mTask_reps.split("\\s+");
            reps_list.add(mReps_table[0]);
            weight_list.add(mReps_table[1]);
            editTextReps1.setText(reps_list.get(0));
            editTextWeight1.setText(weight_list.get(0));

            editTextReps1.setVisibility(View.VISIBLE);
            editTextWeight1.setVisibility(View.VISIBLE);
            editTextWeight1.setVisibility(View.VISIBLE);

            textViewNumber1.setVisibility(View.VISIBLE);

            setNumber = 1;

        }
        if (task_set2.getText() != null  && !task_set2.getText().equals("")){
            String mTask_reps = task_set2.getText().toString().replaceAll("\\p{Lower}"," ");
            String[] mReps_table = mTask_reps.split("\\s+");
            reps_list.add(mReps_table[0]);
            weight_list.add(mReps_table[1]);
            editTextReps2.setText(reps_list.get(1));
            editTextWeight2.setText(weight_list.get(1));

            editTextReps2.setVisibility(View.VISIBLE);
            editTextWeight2.setVisibility(View.VISIBLE);
            editTextWeight2.setVisibility(View.VISIBLE);

            textViewNumber2.setVisibility(View.VISIBLE);

            setNumber = 2;
        }
        if (task_set3.getText() != null  && !task_set3.getText().equals("")){
            String mTask_reps = task_set3.getText().toString().replaceAll("\\p{Lower}"," ");
            String[] mReps_table = mTask_reps.split("\\s+");
            reps_list.add(mReps_table[0]);
            weight_list.add(mReps_table[1]);
            editTextReps3.setText(reps_list.get(2));
            editTextWeight3.setText(weight_list.get(2));

            editTextReps3.setVisibility(View.VISIBLE);
            editTextWeight3.setVisibility(View.VISIBLE);
            editTextWeight3.setVisibility(View.VISIBLE);

            textViewNumber3.setVisibility(View.VISIBLE);

            setNumber = 3;
        }

        if (task_set4.getText() != null  && !task_set4.getText().equals("")){
            String mTask_reps = task_set4.getText().toString().replaceAll("\\p{Lower}"," ");
            String[] mReps_table = mTask_reps.split("\\s+");
            reps_list.add(mReps_table[0]);
            weight_list.add(mReps_table[1]);
            editTextReps4.setText(reps_list.get(3));
            editTextWeight4.setText(weight_list.get(3));

            editTextReps4.setVisibility(View.VISIBLE);
            editTextWeight4.setVisibility(View.VISIBLE);
            editTextWeight4.setVisibility(View.VISIBLE);

            textViewNumber4.setVisibility(View.VISIBLE);

            setNumber = 4;
        }

        if (task_set5.getText() != null  && !task_set5.getText().equals("")){
            String mTask_reps = task_set5.getText().toString().replaceAll("\\p{Lower}"," ");
            String[] mReps_table = mTask_reps.split("\\s+");
            reps_list.add(mReps_table[0]);
            weight_list.add(mReps_table[1]);
            editTextReps5.setText(reps_list.get(4));
            editTextWeight5.setText(weight_list.get(4));

            editTextReps5.setVisibility(View.VISIBLE);
            editTextWeight5.setVisibility(View.VISIBLE);
            editTextWeight5.setVisibility(View.VISIBLE);

            textViewNumber5.setVisibility(View.VISIBLE);

            setNumber = 5;
        }

        if (task_set6.getText() != null  && !task_set6.getText().equals("")){
            String mTask_reps = task_set6.getText().toString().replaceAll("\\p{Lower}"," ");
            String[] mReps_table = mTask_reps.split("\\s+");
            reps_list.add(mReps_table[0]);
            weight_list.add(mReps_table[1]);
            editTextReps6.setText(reps_list.get(5));
            editTextWeight6.setText(weight_list.get(5));

            editTextReps6.setVisibility(View.VISIBLE);
            editTextWeight6.setVisibility(View.VISIBLE);
            editTextWeight6.setVisibility(View.VISIBLE);

            textViewReps2.setVisibility(View.VISIBLE);
            textViewWeight2.setVisibility(View.VISIBLE);

            textViewNumber6.setVisibility(View.VISIBLE);

            setNumber = 6;
        }

        if (task_set7.getText() != null  && !task_set7.getText().equals("")){
            String mTask_reps = task_set7.getText().toString().replaceAll("\\p{Lower}"," ");
            String[] mReps_table = mTask_reps.split("\\s+");
            reps_list.add(mReps_table[0]);
            weight_list.add(mReps_table[1]);
            editTextReps7.setText(reps_list.get(6));
            editTextWeight7.setText(weight_list.get(6));

            editTextReps7.setVisibility(View.VISIBLE);
            editTextWeight7.setVisibility(View.VISIBLE);
            editTextWeight7.setVisibility(View.VISIBLE);

            textViewNumber7.setVisibility(View.VISIBLE);

            setNumber = 7;
        }

        if (task_set8.getText() != null  && !task_set8.getText().equals("")){
            String mTask_reps = task_set8.getText().toString().replaceAll("\\p{Lower}"," ");
            String[] mReps_table = mTask_reps.split("\\s+");
            reps_list.add(mReps_table[0]);
            weight_list.add(mReps_table[1]);
            editTextReps8.setText(reps_list.get(7));
            editTextWeight8.setText(weight_list.get(7));

            editTextReps8.setVisibility(View.VISIBLE);
            editTextWeight8.setVisibility(View.VISIBLE);
            editTextWeight8.setVisibility(View.VISIBLE);

            textViewNumber8.setVisibility(View.VISIBLE);

            setNumber = 8;
        }

        if (task_set9.getText() != null  && !task_set9.getText().equals("")){
            String mTask_reps = task_set9.getText().toString().replaceAll("\\p{Lower}"," ");
            String[] mReps_table = mTask_reps.split("\\s+");
            reps_list.add(mReps_table[0]);
            weight_list.add(mReps_table[1]);
            editTextReps9.setText(reps_list.get(8));
            editTextWeight9.setText(weight_list.get(8));

            editTextReps9.setVisibility(View.VISIBLE);
            editTextWeight9.setVisibility(View.VISIBLE);
            editTextWeight9.setVisibility(View.VISIBLE);

            textViewNumber9.setVisibility(View.VISIBLE);

            setNumber = 9;
        }

        if (task_set10.getText() != null  && !task_set10.getText().equals("")){
            String mTask_reps = task_set10.getText().toString().replaceAll("\\p{Lower}"," ");
            String[] mReps_table = mTask_reps.split("\\s+");
            reps_list.add(mReps_table[0]);
            weight_list.add(mReps_table[1]);
            editTextReps10.setText(reps_list.get(9));
            editTextWeight10.setText(weight_list.get(9));

            editTextReps10.setVisibility(View.VISIBLE);
            editTextWeight10.setVisibility(View.VISIBLE);
            editTextWeight10.setVisibility(View.VISIBLE);

            textViewNumber10.setVisibility(View.VISIBLE);

            setNumber = 10;
        }


        buttonAddSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setNumber++;

                Log.e(TAG, "onClick: reps_list "+reps_list.size());
                Log.e(TAG, "onClick: reps_list "+reps_list);
                Log.e(TAG, "onClick: weight_list "+weight_list);


                Log.e(TAG, "onClick: click: "+setNumber);

                if (editTextReps1.getVisibility() == View.VISIBLE){

                }

                switch (setNumber){
                    case 1:
                        editTextReps1.setVisibility(View.VISIBLE);
                        editTextWeight1.setVisibility(View.VISIBLE);
                        textViewNumber1.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        editTextReps2.setVisibility(View.VISIBLE);
                        editTextWeight2.setVisibility(View.VISIBLE);
                        textViewNumber2.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        editTextReps3.setVisibility(View.VISIBLE);
                        editTextWeight3.setVisibility(View.VISIBLE);
                        textViewNumber3.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        editTextReps4.setVisibility(View.VISIBLE);
                        editTextWeight4.setVisibility(View.VISIBLE);
                        textViewNumber4.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        editTextReps5.setVisibility(View.VISIBLE);
                        editTextWeight5.setVisibility(View.VISIBLE);
                        textViewNumber5.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        editTextReps6.setVisibility(View.VISIBLE);
                        editTextWeight6.setVisibility(View.VISIBLE);
                        textViewNumber6.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        editTextReps7.setVisibility(View.VISIBLE);
                        editTextWeight7.setVisibility(View.VISIBLE);
                        textViewNumber7.setVisibility(View.VISIBLE);
                        break;
                    case 8:
                        editTextReps8.setVisibility(View.VISIBLE);
                        editTextWeight8.setVisibility(View.VISIBLE);
                        textViewNumber8.setVisibility(View.VISIBLE);
                        break;
                    case 9:
                        editTextReps9.setVisibility(View.VISIBLE);
                        editTextWeight9.setVisibility(View.VISIBLE);
                        textViewNumber9.setVisibility(View.VISIBLE);
                        break;
                    case 10:
                        editTextReps10.setVisibility(View.VISIBLE);
                        editTextWeight10.setVisibility(View.VISIBLE);
                        textViewNumber10.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(TrainingActivity.this);
        alert.setTitle("Edit exercise")
                .setView(textEntryView)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TrainingSet trainingSet = new TrainingSet(editTextWeight1.getText().toString(),editTextWeight2.getText().toString(),editTextWeight3.getText().toString(),editTextWeight4.getText().toString(),editTextWeight5.getText().toString(),editTextWeight6.getText().toString(),editTextWeight7.getText().toString(),editTextWeight8.getText().toString(),editTextWeight9.getText().toString(),editTextWeight10.getText().toString(),editTextReps1.getText().toString(),editTextReps2.getText().toString(),editTextReps3.getText().toString(),editTextReps4.getText().toString(),editTextReps5.getText().toString(),editTextReps6.getText().toString(),editTextReps7.getText().toString(),editTextReps8.getText().toString(),editTextReps9.getText().toString(),editTextReps10.getText().toString());

                        trainingSet.setSetNumber(setNumber);
                        reps = trainingSet.getRepsAll();
                        weight = trainingSet.getWeightsAll();

                        if (reps.isEmpty() || weight.isEmpty() || trainingSet.isEnteredValue()) {
                            Toast.makeText(TrainingActivity.this, "Add training failed. You need to add atleast one serie. Don't leave empty fields.", Toast.LENGTH_LONG).show();
                        } else {

                            TrainingEditDTO trainingEditDTO = new TrainingEditDTO();
                            trainingEditDTO.id = task_id.getText().toString();
                            trainingEditDTO.rest = editTextRestTime4.getText().toString();
                            trainingEditDTO.reps = reps;
                            trainingEditDTO.weight = weight;
                            trainingEditDTO.username = userName;
                            trainingEditDTO.date = dateInsde;

                            TrainingService trainingService = new TrainingService();
                            trainingService.EditTraining(trainingEditDTO, TrainingActivity.this);
                        }

                        String v = String.valueOf(reps_list);

                        // Load data after 0,5s
                        onRestart();

                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TrainingDeleteDTO trainingDeleteDTO = new TrainingDeleteDTO();
                        trainingDeleteDTO.id = Integer.valueOf(task_id.getText().toString());
                        trainingDeleteDTO.username = userName;
                        trainingDeleteDTO.date = dateInsde;

                        TrainingService trainingService = new TrainingService();
                        trainingService.DeleteTraining(trainingDeleteDTO,TrainingActivity.this);
//                                // Load data after 0,5s
                        onRestart();
                    }
                })
                .setIcon(R.drawable.icon_training);
        alert.show();

        return view;
    }

    private class TrainingDetails {
        String id;
        String done;
        String rest;
        String reps;
        String weight;
        String description;

        public String getDone() {
            return done;
        }

        public void setDone(String done) {
            this.done = done;
        }

        public String getRest() {
            return rest;
        }

        public void setRest(String rest) {
            this.rest = rest;
        }

        public String getReps() {
            return reps;
        }

        public void setReps(String reps) {
            this.reps = reps;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}

