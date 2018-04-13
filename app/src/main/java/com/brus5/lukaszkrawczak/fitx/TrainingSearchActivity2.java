package com.brus5.lukaszkrawczak.fitx;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.brus5.lukaszkrawczak.fitx.Training.TrainingInsertTraining;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingListSearchAdapter;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingSearchByName;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TrainingSearchActivity2 extends AppCompatActivity {

    private static final String TAG = "TrainingSearchActivity2";

    String userFirstName, userName, userUserName, userBirthday, userPassword, userEmail, userMale, dateInsde;
    int userIDint,userAgeint;
    ListView mTaskListView;

    TrainingListSearchAdapter adapter;
    EditText inputSearch;
    ArrayList<Training> trainingArrayList = new ArrayList<>();

    // Getting date
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String date = simpleDateFormat.format(c.getTime());

    private int setNumber = 0;

    String reps = "";
    String weight = "";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_search2);

        Toolbar toolbar3 = findViewById(R.id.toolbarDietSearch);
        setSupportActionBar(toolbar3);
        getWindow().setStatusBarColor(ContextCompat.getColor(TrainingSearchActivity2.this,R.color.color_main_activity_statusbar));
        mTaskListView = findViewById(R.id.viewProducts1);

        Intent intent1 = getIntent();
        userIDint = intent1.getIntExtra("userIDint",0);
        userFirstName = intent1.getStringExtra("userFirstName");
        userName = intent1.getStringExtra("userName");
        userBirthday = intent1.getStringExtra("userBirthday");
        userAgeint = intent1.getIntExtra("userAgeint",0);
        userPassword = intent1.getStringExtra("userPassword");
        userEmail = intent1.getStringExtra("userEmail");
        userMale = intent1.getStringExtra("userMale");
        dateInsde = intent1.getStringExtra("dateInsde");
        Log.e(TAG,"informacje"+" "+userIDint+" "+userFirstName+" "+userName+" "+userBirthday+" "+userAgeint+" "+userPassword+" "+userEmail+" "+userMale+" "+dateInsde);

        inputSearch = findViewById(R.id.inputSearch1);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                trainingArrayList.clear();
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: response "+response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray server_response = jsonObject.getJSONArray("server_response");

                            String name;
                            int id;
                            String username;
                            String date;
                            String done;
                            String description;


                            if (server_response.length() > 0){
                                for (int i = 0; i < server_response.length(); i++){
                                    JSONObject c = server_response.getJSONObject(i);
                                    id = Integer.valueOf(c.getString("id"));
                                    username = c.getString("username");
                                    date = c.getString("date");
                                    description = c.getString("description");


                                    Log.e(TAG, "onResponse: description "+description);

                                    String upName = description.substring(0,1).toUpperCase() + description.substring(1);

                                    Training training = new Training(String.valueOf(id),description,null,null,null);

                                    trainingArrayList.add(training);

                                    adapter = new TrainingListSearchAdapter(TrainingSearchActivity2.this,R.layout.training_search_row,trainingArrayList);
                                    mTaskListView.setAdapter(adapter);
                                    mTaskListView.invalidate();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                TrainingSearchByName trainingSearchByName = new TrainingSearchByName(s.toString(),responseListener);
                RequestQueue queue = Volley.newRequestQueue(TrainingSearchActivity2.this);
                queue.add(trainingSearchByName);
            }
        });





mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick: "+position+" "+id);

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
        TextView task_id = view.findViewById(R.id.task_id);



        final String description = task_name.getText().toString();
        final int idd = Integer.valueOf(task_id.getText().toString());

        Log.e(TAG, "onItemClick: desc"+description );
        Log.e(TAG, "onItemClick: idd"+idd );



        LayoutInflater inflater = LayoutInflater.from(TrainingSearchActivity2.this);
        View textEntryView = inflater.inflate(R.layout.activity_training_options,null);

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

        final TextView mTask_name = textEntryView.findViewById(R.id.task_name4);
        final TextView mRest_time = textEntryView.findViewById(R.id.task_restTime4);


        final Button buttonAddSet = textEntryView.findViewById(R.id.buttonAddSet);
        final Button buttonCheck = textEntryView.findViewById(R.id.buttonCheck);


        final ArrayList<String> reps_list = new ArrayList<>();
        final ArrayList<String> weight_list = new ArrayList<>();

        mTask_name.setText(task_name.getText().toString());

        final ArrayList<String> arrayReps = new ArrayList<>();

        final ArrayList<String> arrayWeight = new ArrayList<>();



//                String reps = trainingSet.getReps1()+"."+trainingSet.getReps2()+"."+trainingSet.getReps3()+"."+trainingSet.getReps4()+"."+trainingSet.getReps5()+"."+trainingSet.getReps6()+"."+trainingSet.getReps7()+"."+trainingSet.getReps8()+"."+trainingSet.getReps9()+"."+trainingSet.getReps10()+".";
        Log.e(TAG, "onClick: reps "+reps);

        buttonAddSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setNumber++;

                Log.e(TAG, "onClick: reps_list "+reps_list.size());
                Log.e(TAG, "onClick: reps_list "+reps_list);
                Log.e(TAG, "onClick: weight_list "+weight_list);

                Log.e(TAG, "onItemClick: editTextReps1 "+ editTextReps1.getText().toString());

                Log.e(TAG, "onClick: click: "+setNumber);



// FIXME: 05.04.2018 working right here... add each element to arraylist and then convert to 10.10.10 format...

                if (setNumber == 1){
                    editTextReps1.setVisibility(View.VISIBLE);
                    editTextWeight1.setVisibility(View.VISIBLE);
                    textViewNumber1.setVisibility(View.VISIBLE);
                }
                if (setNumber == 2){
                    editTextReps2.setVisibility(View.VISIBLE);
                    editTextWeight2.setVisibility(View.VISIBLE);
                    textViewNumber2.setVisibility(View.VISIBLE);

                    arrayReps.add(editTextReps2.getText().toString());
                    arrayWeight.add(editTextReps2.getText().toString());
                }
                if (setNumber == 3){
                    arrayReps.add(editTextReps3.getText().toString());
                    arrayWeight.add(editTextReps3.getText().toString());

                    editTextReps3.setVisibility(View.VISIBLE);
                    editTextWeight3.setVisibility(View.VISIBLE);
                    textViewNumber3.setVisibility(View.VISIBLE);
                }
                if (setNumber == 4){

                    arrayReps.add(editTextReps4.getText().toString());
                    arrayWeight.add(editTextReps4.getText().toString());

                    editTextReps4.setVisibility(View.VISIBLE);
                    editTextWeight4.setVisibility(View.VISIBLE);
                    textViewNumber4.setVisibility(View.VISIBLE);
                }
                if (setNumber == 5){

                    arrayReps.add(editTextReps5.getText().toString());
                    arrayWeight.add(editTextReps5.getText().toString());

                    editTextReps5.setVisibility(View.VISIBLE);
                    editTextWeight5.setVisibility(View.VISIBLE);
                    textViewNumber5.setVisibility(View.VISIBLE);
                }
                if (setNumber == 6){

                    arrayReps.add(editTextReps6.getText().toString());
                    arrayWeight.add(editTextReps6.getText().toString());

                    editTextReps6.setVisibility(View.VISIBLE);
                    editTextWeight6.setVisibility(View.VISIBLE);
                    textViewNumber6.setVisibility(View.VISIBLE);

                    textViewReps2.setVisibility(View.VISIBLE);
                    textViewWeight2.setVisibility(View.VISIBLE);
                }
                if (setNumber == 7){

                    arrayReps.add(editTextReps7.getText().toString());
                    arrayWeight.add(editTextReps7.getText().toString());

                    editTextReps7.setVisibility(View.VISIBLE);
                    editTextWeight7.setVisibility(View.VISIBLE);
                    textViewNumber7.setVisibility(View.VISIBLE);
                }
                if (setNumber == 8){

                    arrayReps.add(editTextReps8.getText().toString());
                    arrayWeight.add(editTextReps8.getText().toString());

                    editTextReps8.setVisibility(View.VISIBLE);
                    editTextWeight8.setVisibility(View.VISIBLE);
                    textViewNumber8.setVisibility(View.VISIBLE);
                }
                if (setNumber == 9){

                    arrayReps.add(editTextReps9.getText().toString());
                    arrayWeight.add(editTextReps9.getText().toString());

                    editTextReps9.setVisibility(View.VISIBLE);
                    editTextWeight9.setVisibility(View.VISIBLE);
                    textViewNumber9.setVisibility(View.VISIBLE);
                }
                if (setNumber == 10){

                    arrayReps.add(editTextReps10.getText().toString());
                    arrayWeight.add(editTextReps10.getText().toString());

                    editTextReps10.setVisibility(View.VISIBLE);
                    editTextWeight10.setVisibility(View.VISIBLE);
                    textViewNumber10.setVisibility(View.VISIBLE);
                }

            }
        });



        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: reps "+reps );

//                arrayWeight.add(editTextWeight1.getText().toString());
//                arrayWeight.add(editTextWeight2.getText().toString());
//                arrayWeight.add(editTextWeight3.getText().toString());
//                arrayWeight.add(editTextWeight4.getText().toString());
//                arrayWeight.add(editTextWeight5.getText().toString());
//                arrayWeight.add(editTextWeight6.getText().toString());
//                arrayWeight.add(editTextWeight7.getText().toString());
//                arrayWeight.add(editTextWeight8.getText().toString());
//                arrayWeight.add(editTextWeight9.getText().toString());
//                arrayWeight.add(editTextWeight10.getText().toString());
//                arrayReps.add(editTextReps1.getText().toString());
//                arrayReps.add(editTextReps2.getText().toString());
//                arrayReps.add(editTextReps3.getText().toString());
//                arrayReps.add(editTextReps4.getText().toString());
//                arrayReps.add(editTextReps5.getText().toString());
//                arrayReps.add(editTextReps6.getText().toString());
//                arrayReps.add(editTextReps7.getText().toString());
//                arrayReps.add(editTextReps8.getText().toString());
//                arrayReps.add(editTextReps9.getText().toString());
//                arrayReps.add(editTextReps10.getText().toString());


            }
        });










        AlertDialog.Builder alert = new AlertDialog.Builder(TrainingSearchActivity2.this);
        alert.setTitle("Add training")
                .setView(textEntryView)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        TrainingSet trainingSet = new TrainingSet(editTextWeight1.getText().toString(), editTextWeight2.getText().toString(), editTextWeight3.getText().toString(), editTextWeight4.getText().toString(), editTextWeight5.getText().toString(), editTextWeight6.getText().toString(), editTextWeight7.getText().toString(), editTextWeight8.getText().toString(), editTextWeight9.getText().toString(), editTextWeight10.getText().toString(), editTextReps1.getText().toString(), editTextReps2.getText().toString(), editTextReps3.getText().toString(), editTextReps4.getText().toString(), editTextReps5.getText().toString(), editTextReps6.getText().toString(), editTextReps7.getText().toString(), editTextReps8.getText().toString(), editTextReps9.getText().toString(), editTextReps10.getText().toString());

                        if (setNumber == 1) {
                            reps = trainingSet.getReps1();
                            weight = trainingSet.getWeight1();
                        }
                        if (setNumber == 2) {
                            reps = trainingSet.getReps1() + "." + trainingSet.getReps2();
                            weight = trainingSet.getWeight1() + "." + trainingSet.getWeight2();
                        }
                        if (setNumber == 3) {
                            reps = trainingSet.getReps1() + "." + trainingSet.getReps2() + "." + trainingSet.getReps3();
                            weight = trainingSet.getWeight1() + "." + trainingSet.getWeight2() + "." + trainingSet.getWeight3();
                        }
                        if (setNumber == 4) {
                            reps = trainingSet.getReps1() + "." + trainingSet.getReps2() + "." + trainingSet.getReps3() + "." + trainingSet.getReps4();
                            weight = trainingSet.getWeight1() + "." + trainingSet.getWeight2() + "." + trainingSet.getWeight3() + "." + trainingSet.getWeight4();
                        }
                        if (setNumber == 5) {
                            reps = trainingSet.getReps1() + "." + trainingSet.getReps2() + "." + trainingSet.getReps3() + "." + trainingSet.getReps4() + "." + trainingSet.getReps5();
                            weight = trainingSet.getWeight1() + "." + trainingSet.getWeight2() + "." + trainingSet.getWeight3() + "." + trainingSet.getWeight4() + "." + trainingSet.getWeight5();
                        }
                        if (setNumber == 6) {
                            reps = trainingSet.getReps1() + "." + trainingSet.getReps2() + "." + trainingSet.getReps3() + "." + trainingSet.getReps4() + "." + trainingSet.getReps5() + "." + trainingSet.getReps6();
                            weight = trainingSet.getWeight1() + "." + trainingSet.getWeight2() + "." + trainingSet.getWeight3() + "." + trainingSet.getWeight4() + "." + trainingSet.getWeight5() + "." + trainingSet.getWeight6();
                        }
                        if (setNumber == 7) {
                            reps = trainingSet.getReps1() + "." + trainingSet.getReps2() + "." + trainingSet.getReps3() + "." + trainingSet.getReps4() + "." + trainingSet.getReps5() + "." + trainingSet.getReps6() + "." + trainingSet.getReps7();
                            weight = trainingSet.getWeight1() + "." + trainingSet.getWeight2() + "." + trainingSet.getWeight3() + "." + trainingSet.getWeight4() + "." + trainingSet.getWeight5() + "." + trainingSet.getWeight6() + "." + trainingSet.getWeight7();
                        }
                        if (setNumber == 8) {
                            reps = trainingSet.getReps1() + "." + trainingSet.getReps2() + "." + trainingSet.getReps3() + "." + trainingSet.getReps4() + "." + trainingSet.getReps5() + "." + trainingSet.getReps6() + "." + trainingSet.getReps7() + "." + trainingSet.getReps8();
                            weight = trainingSet.getWeight1() + "." + trainingSet.getWeight2() + "." + trainingSet.getWeight3() + "." + trainingSet.getWeight4() + "." + trainingSet.getWeight5() + "." + trainingSet.getWeight6() + "." + trainingSet.getWeight7() + "." + trainingSet.getWeight8();
                        }
                        if (setNumber == 9) {
                            reps = trainingSet.getReps1() + "." + trainingSet.getReps2() + "." + trainingSet.getReps3() + "." + trainingSet.getReps4() + "." + trainingSet.getReps5() + "." + trainingSet.getReps6() + "." + trainingSet.getReps7() + "." + trainingSet.getReps8() + "." + trainingSet.getReps9();
                            weight = trainingSet.getWeight1() + "." + trainingSet.getWeight2() + "." + trainingSet.getWeight3() + "." + trainingSet.getWeight4() + "." + trainingSet.getWeight5() + "." + trainingSet.getWeight6() + "." + trainingSet.getWeight7() + "." + trainingSet.getWeight8() + "." + trainingSet.getWeight9();
                        }
                        if (setNumber == 10) {
                            reps = trainingSet.getReps1() + "." + trainingSet.getReps2() + "." + trainingSet.getReps3() + "." + trainingSet.getReps4() + "." + trainingSet.getReps5() + "." + trainingSet.getReps6() + "." + trainingSet.getReps7() + "." + trainingSet.getReps8() + "." + trainingSet.getReps9() + "." + trainingSet.getReps10();
                            weight = trainingSet.getWeight1() + "." + trainingSet.getWeight2() + "." + trainingSet.getWeight3() + "." + trainingSet.getWeight4() + "." + trainingSet.getWeight5() + "." + trainingSet.getWeight6() + "." + trainingSet.getWeight7() + "." + trainingSet.getWeight8() + "." + trainingSet.getWeight9() + "." + trainingSet.getWeight10();
                        }

                        if (reps.isEmpty() || weight.isEmpty()) {
                            Toast.makeText(TrainingSearchActivity2.this, "Add training failed. You need to add atleast one serie.", Toast.LENGTH_LONG).show();
                        } else {

                        Response.Listener<String> listener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e(TAG, "response" + response);
                            }
                        };
                        TrainingInsertTraining trainingInsertTraining = new TrainingInsertTraining(idd, 0, editTextRestTime4.getText().toString(), reps, weight, userName, dateInsde, "blabla", listener);
                        RequestQueue requestQueue = Volley.newRequestQueue(TrainingSearchActivity2.this);
                        requestQueue.add(trainingInsertTraining);
                    }
        inputSearch.setText("");

                    }
                })
                .setIcon(R.drawable.icon_plan)
        ;

        alert.show();

        setNumber = 0;

        Log.e(TAG, "onItemLongClick: desc "+description);

    }
});



    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Instantiate menu XML files into Menu object
//        getMenuInflater().inflate(R.menu.add_meal,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_add_meal:
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
