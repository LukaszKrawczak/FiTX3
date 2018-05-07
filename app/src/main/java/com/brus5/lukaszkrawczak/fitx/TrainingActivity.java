package com.brus5.lukaszkrawczak.fitx;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Training.Training;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingDeleteRequest;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingEditTraining;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingListAdapter;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingSet;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingShowByUser;
import com.jintin.mixadapter.MixAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class TrainingActivity extends AppCompatActivity {

    private static final String TAG = "TrainingActivity";
    String userName, userFirstName, userBirthday, userPassword, userEmail, userMale, userID;
    int id,userIDint,userAgeint;
//    String name, username, age, password, email, male, somatotypeS;
    String description;

    ArrayList<Training> trainingArrayList = new ArrayList<>();

    private ArrayList<String> arrayDescription = new ArrayList<>();
    private ArrayList<String> arrayDone = new ArrayList<>();
    private ArrayList<String> arrayRest = new ArrayList<>();
    private ArrayList<String> arrayReps = new ArrayList<>();
    private ArrayList<String> arrayWeight = new ArrayList<>();
    int setNumber = 0;
    String reps = "";
    String weight = "";
//    private ArrayAdapter<String> adapter;
    ListView mTaskListView;
    MixAdapter<RecyclerView.ViewHolder> adapter1 = new MixAdapter<>();
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

        mTaskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
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

                        if (setNumber == 1){
                            editTextReps1.setVisibility(View.VISIBLE);
                            editTextWeight1.setVisibility(View.VISIBLE);
                            textViewNumber1.setVisibility(View.VISIBLE);
                        }
                        if (setNumber == 2){
                            editTextReps2.setVisibility(View.VISIBLE);
                            editTextWeight2.setVisibility(View.VISIBLE);
                            textViewNumber2.setVisibility(View.VISIBLE);
                        }
                        if (setNumber == 3){
                            editTextReps3.setVisibility(View.VISIBLE);
                            editTextWeight3.setVisibility(View.VISIBLE);
                            textViewNumber3.setVisibility(View.VISIBLE);
                        }
                        if (setNumber == 4){
                            editTextReps4.setVisibility(View.VISIBLE);
                            editTextWeight4.setVisibility(View.VISIBLE);
                            textViewNumber4.setVisibility(View.VISIBLE);
                        }
                        if (setNumber == 5){
                            editTextReps5.setVisibility(View.VISIBLE);
                            editTextWeight5.setVisibility(View.VISIBLE);
                            textViewNumber5.setVisibility(View.VISIBLE);
                        }
                        if (setNumber == 6){
                            editTextReps6.setVisibility(View.VISIBLE);
                            editTextWeight6.setVisibility(View.VISIBLE);
                            textViewNumber6.setVisibility(View.VISIBLE);

                            textViewReps2.setVisibility(View.VISIBLE);
                            textViewWeight2.setVisibility(View.VISIBLE);
                        }
                        if (setNumber == 7){
                            editTextReps7.setVisibility(View.VISIBLE);
                            editTextWeight7.setVisibility(View.VISIBLE);
                            textViewNumber7.setVisibility(View.VISIBLE);
                        }
                        if (setNumber == 8){
                            editTextReps8.setVisibility(View.VISIBLE);
                            editTextWeight8.setVisibility(View.VISIBLE);
                            textViewNumber8.setVisibility(View.VISIBLE);
                        }
                        if (setNumber == 9){
                            editTextReps9.setVisibility(View.VISIBLE);
                            editTextWeight9.setVisibility(View.VISIBLE);
                            textViewNumber9.setVisibility(View.VISIBLE);
                        }
                        if (setNumber == 10){
                            editTextReps10.setVisibility(View.VISIBLE);
                            editTextWeight10.setVisibility(View.VISIBLE);
                            textViewNumber10.setVisibility(View.VISIBLE);
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

                                if (setNumber == 1){
                                    reps = trainingSet.getReps1();
                                    weight = trainingSet.getWeight1();
                                }
                                if (setNumber == 2){
                                    reps = trainingSet.getReps1()+"."+trainingSet.getReps2();
                                    weight = trainingSet.getWeight1()+"."+trainingSet.getWeight2();
                                }
                                if (setNumber == 3){
                                    reps = trainingSet.getReps1()+"."+trainingSet.getReps2()+"."+trainingSet.getReps3();
                                    weight = trainingSet.getWeight1()+"."+trainingSet.getWeight2()+"."+trainingSet.getWeight3();
                                }
                                if (setNumber == 4){
                                    reps = trainingSet.getReps1()+"."+trainingSet.getReps2()+"."+trainingSet.getReps3()+"."+trainingSet.getReps4();
                                    weight = trainingSet.getWeight1()+"."+trainingSet.getWeight2()+"."+trainingSet.getWeight3()+"."+trainingSet.getWeight4();
                                }
                                if (setNumber == 5){
                                    reps = trainingSet.getReps1()+"."+trainingSet.getReps2()+"."+trainingSet.getReps3()+"."+trainingSet.getReps4()+"."+trainingSet.getReps5();
                                    weight = trainingSet.getWeight1()+"."+trainingSet.getWeight2()+"."+trainingSet.getWeight3()+"."+trainingSet.getWeight4()+"."+trainingSet.getWeight5();
                                }
                                if (setNumber == 6){
                                    reps = trainingSet.getReps1()+"."+trainingSet.getReps2()+"."+trainingSet.getReps3()+"."+trainingSet.getReps4()+"."+trainingSet.getReps5()+"."+trainingSet.getReps6();
                                    weight = trainingSet.getWeight1()+"."+trainingSet.getWeight2()+"."+trainingSet.getWeight3()+"."+trainingSet.getWeight4()+"."+trainingSet.getWeight5()+"."+trainingSet.getWeight6();
                                }
                                if (setNumber == 7){
                                    reps = trainingSet.getReps1()+"."+trainingSet.getReps2()+"."+trainingSet.getReps3()+"."+trainingSet.getReps4()+"."+trainingSet.getReps5()+"."+trainingSet.getReps6()+"."+trainingSet.getReps7();
                                    weight = trainingSet.getWeight1()+"."+trainingSet.getWeight2()+"."+trainingSet.getWeight3()+"."+trainingSet.getWeight4()+"."+trainingSet.getWeight5()+"."+trainingSet.getWeight6()+"."+trainingSet.getWeight7();
                                }
                                if (setNumber == 8){
                                    reps = trainingSet.getReps1()+"."+trainingSet.getReps2()+"."+trainingSet.getReps3()+"."+trainingSet.getReps4()+"."+trainingSet.getReps5()+"."+trainingSet.getReps6()+"."+trainingSet.getReps7()+"."+trainingSet.getReps8();
                                    weight = trainingSet.getWeight1()+"."+trainingSet.getWeight2()+"."+trainingSet.getWeight3()+"."+trainingSet.getWeight4()+"."+trainingSet.getWeight5()+"."+trainingSet.getWeight6()+"."+trainingSet.getWeight7()+"."+trainingSet.getWeight8();
                                }
                                if (setNumber == 9){
                                    reps = trainingSet.getReps1()+"."+trainingSet.getReps2()+"."+trainingSet.getReps3()+"."+trainingSet.getReps4()+"."+trainingSet.getReps5()+"."+trainingSet.getReps6()+"."+trainingSet.getReps7()+"."+trainingSet.getReps8()+"."+trainingSet.getReps9();
                                    weight = trainingSet.getWeight1()+"."+trainingSet.getWeight2()+"."+trainingSet.getWeight3()+"."+trainingSet.getWeight4()+"."+trainingSet.getWeight5()+"."+trainingSet.getWeight6()+"."+trainingSet.getWeight7()+"."+trainingSet.getWeight8()+"."+trainingSet.getWeight9();
                                }
                                if (setNumber == 10){
                                    reps = trainingSet.getReps1()+"."+trainingSet.getReps2()+"."+trainingSet.getReps3()+"."+trainingSet.getReps4()+"."+trainingSet.getReps5()+"."+trainingSet.getReps6()+"."+trainingSet.getReps7()+"."+trainingSet.getReps8()+"."+trainingSet.getReps9()+"."+trainingSet.getReps10();
                                    weight = trainingSet.getWeight1()+"."+trainingSet.getWeight2()+"."+trainingSet.getWeight3()+"."+trainingSet.getWeight4()+"."+trainingSet.getWeight5()+"."+trainingSet.getWeight6()+"."+trainingSet.getWeight7()+"."+trainingSet.getWeight8()+"."+trainingSet.getWeight9()+"."+trainingSet.getWeight10();
                                }

                                Response.Listener<String> listener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e(TAG,"response"+response);
                                    }
                                };


                                String v = String.valueOf(reps_list);

                                Log.e(TAG, "onClick: reps_list "+v );
                                Log.e(TAG, "onClick: lista " +Integer.valueOf(task_id.getText().toString())+ "   "+editTextRestTime4.getText().toString()+ "   "+reps+ "   "+weight+ "   "+userName+ "   "+dateInsde);
                                TrainingEditTraining trainingEditTraining = new TrainingEditTraining(task_id.getText().toString(),editTextRestTime4.getText().toString(),reps,weight,userName,dateInsde,listener);
                                RequestQueue queue = Volley.newRequestQueue(TrainingActivity.this);
                                queue.add(trainingEditTraining);
                                // Load data after 0,5s
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        onRestart();
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
                                TrainingDeleteRequest trainingDeleteRequest = new TrainingDeleteRequest(Integer.valueOf(task_id.getText().toString()),userName, dateInsde, listener);
                                RequestQueue requestQueue = Volley.newRequestQueue(TrainingActivity.this);
                                requestQueue.add(trainingDeleteRequest);
                                // Load data after 0,5s
                                onRestart();
                            }
                        })
                        .setIcon(R.drawable.icon_training)
                ;

                alert.show();





                Log.e(TAG, "onItemLongClick: desc "+description);
                return true;


            }
        });

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

                Log.d(TAG, "onDateSelected: date "+date);
                Log.d(TAG, "onDateSelected: dateInside "+dateInsde);

                loadData();

                new LongRunningTask().execute();
//                loadData();
//                wait2secs();

                Toast.makeText(TrainingActivity.this, DateFormat.getDateInstance().format(date) + " is selected",Toast.LENGTH_SHORT).show();
            }

        });
        loadData();
        wait2secs();
    }

    private class LongRunningTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: ");
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "onPostExecute: ");

            super.onPostExecute(aVoid);
        }
    }
    

    private void wait2secs() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateUI();
            }
        },500);
    }

    private void loadData() {
        arrayDone.clear();
        arrayRest.clear();
        arrayReps.clear();
        arrayWeight.clear();
        arrayDescription.clear();
        trainingArrayList.clear();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response "+response);
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
        };
        TrainingShowByUser trainingShowByUser = new TrainingShowByUser(userName,dateInsde,responseListener);
        RequestQueue queue = Volley.newRequestQueue(TrainingActivity.this);
        queue.add(trainingShowByUser);
    }

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



//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_add_training:
//                Log.d(TAG, "Add a new task");
//                final EditText taskEditText = new EditText(this);
//
//                final String input = taskEditText.getText().toString();
//
//
//
//                AlertDialog dialog = new AlertDialog.Builder(this)
//                        .setTitle("Add excercise")
//                        .setMessage("What do you want to do next?")
//                        .setView(taskEditText)
//                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                description = escapeSpecialRegexChars(String.valueOf(taskEditText.getText()));
//                                Log.e(TAG, "escapeSpecialRegexChars(input);" + escapeSpecialRegexChars(input));
//                                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//                                        Log.e(TAG,"response: "+response);
//                                    }
//                                };
//                                TrainingInsertRequest trainingInsertRequest = new TrainingInsertRequest(userIDint, userName, dateInsde, 0, description, responseListener);
//                                RequestQueue requestQueue = Volley.newRequestQueue(TrainingActivity.this);
//                                requestQueue.add(trainingInsertRequest);
//                                onRestart();
//                                Log.d(TAG,"Task to add: "+ description);
//                            }
//                        })
//                        .setNegativeButton("Cancel",null)
//                        .create();
//                dialog.show();
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }



    private void updateUI() {
        
        
//        adapter = new ArrayAdapter<>(this, R.layout.training_row,R.id.task_title,arrayDescription);
//        adapter = new ArrayAdapter<String>(this, R.layout.training_row,R.id.task_title,arrayDescription);
//        mTaskListView.setAdapter(adapter);
        Log.e(TAG,"adapter: "+adapter);
    }

//    public void doneTrainingTask(View view) {
//        View parent = (View) view.getParent();
//        TextView taskIdTextView = parent.findViewById(R.id.task_id);
//        TextView taskTextView = parent.findViewById(R.id.task_title);
//        CheckBox taskCheckBox = parent.findViewById(R.id.task_done);
//        String id = String.valueOf(taskIdTextView.getText());
//        String description = String.valueOf(taskTextView.getText());
//        String done = String.valueOf(taskCheckBox.isChecked());
//        int doneint = 0;
//        if (done.equals("true")){
//            doneint = 1;
//        }else if (done.equals("false")){
//            doneint = 0;
//        }
//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//            }
//        };
//        TrainingDoneRequest trainingDoneRequest = new TrainingDoneRequest(id,doneint,responseListener);
//        RequestQueue requestQueue = Volley.newRequestQueue(TrainingActivity.this);
//        requestQueue.add(trainingDoneRequest);
//
//        Log.e(TAG, "id: "+id);
//        Log.e(TAG, "doneTrainingTask: "+description);
//        Log.e(TAG, "done: "+done);
//    }



//    public void deleteTrainingTask(View view){
//        View parent = (View) view.getParent();
//        TextView taskTextView = parent.findViewById(R.id.task_name);
//        String description = String.valueOf(taskTextView.getText());
//
//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    boolean success =  jsonObject.getBoolean("success");
//                    if (success) Log.e(TAG,"success"+response);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
////        TrainingDeleteRequest trainingDeleteRequest = new TrainingDeleteRequest(userName, dateInsde, description, responseListener);
////        RequestQueue requestQueue = Volley.newRequestQueue(TrainingActivity.this);
////        requestQueue.add(trainingDeleteRequest);
//        Log.e(TAG, "CRASH1");
//
////        onRestart();
//        Log.e(TAG, "CRASH2");
//        Log.d(TAG, "task: "+description);
//
//        arrayDescription.clear();
//        trainingArrayList.clear();
//        new LongRunningTask().execute();
//
//    }

    @Override
    protected void onRestart() {
//        adapter.clear();
        trainingArrayList.clear();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        },500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateUI();
            }
        },1000);
        super.onRestart();
    }

    //    @Override
//    protected void onRestart() {
//        Log.d(TAG,"trainingArrayList1 "+trainingArrayList);
//        adapter.clear();
//        trainingArrayList.clear();
//        Log.d(TAG,"trainingArrayList2 "+trainingArrayList);
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadData();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            updateUI();
//                        }
//                    },500);
//
//            }
//        },500);
//
//        super.onRestart();
//    }

    public class User {
        public String description;
        public String done;

        public User(String description, String done){
            this.description = description;
            this.done = done;
        }
    }

    String escapeSpecialRegexChars(String str) {

        return SPECIAL_REGEX_CHARS.matcher(str).replaceAll("\\\\$0");
    }

}


