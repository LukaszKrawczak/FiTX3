package com.brus5.lukaszkrawczak.fitx;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingDeleteRequest;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingInsertRequest;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingShowRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class TrainingActivity extends AppCompatActivity {

    private static final String TAG = "TrainingActivity";
    String userName, userFirstName, userBirthday, userPassword, userEmail, userMale, userID;
    int id,userIDint,userAgeint;
//    String name, username, age, password, email, male, somatotypeS;
    String description;




    private ArrayList<String> arrayDescription = new ArrayList<>();

    private ArrayAdapter<String> adapter;
    private ListView mTaskListView;

    /* Gettings date */
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String date = simpleDateFormat.format(c.getTime());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_training);

        mTaskListView = findViewById(R.id.list_training);
//
//        Intent intent1 = getIntent();
//        id = intent1.getIntExtra("id",0);
//        name = intent1.getStringExtra("name");
//        username = intent1.getStringExtra("username");
//        age = intent1.getStringExtra("birthday");
//        male = intent1.getStringExtra("male");

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


        loadData();

        wait2secs();


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
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG,"trainingShowRequest response: "+response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray server_response = jsonObject.getJSONArray("server_response");

                    String description;

                    if (server_response.length() > 0){
                        for (int i = 0; i < server_response.length(); i++){
                            JSONObject c = server_response.getJSONObject(i);
                            description = c.getString("description");
                            HashMap<String,String> server = new HashMap<>();
                            server.put("description", description);
                            arrayDescription.add(description);
                        }
                    }

                    Log.e(TAG,"arrayDescription: "+arrayDescription);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        TrainingShowRequest trainingShowRequest = new TrainingShowRequest(userName, responseListener);
        RequestQueue queue = Volley.newRequestQueue(TrainingActivity.this);
        queue.add(trainingShowRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Log.d(TAG, "Add a new task");
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add excercise")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                description = String.valueOf(taskEditText.getText());

                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e(TAG,"response: "+response);
                                    }
                                };
                                com.brus5.lukaszkrawczak.fitx.Training.TrainingInsertRequest trainingInsertRequest = new TrainingInsertRequest(userIDint, userName, date, description, responseListener);
                                RequestQueue requestQueue = Volley.newRequestQueue(TrainingActivity.this);
                                requestQueue.add(trainingInsertRequest);
                                onRestart();
                                Log.d(TAG,"Task to add: "+ description);
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void updateUI() {
        adapter = new ArrayAdapter<String>(this, R.layout.training_row,R.id.task_title,arrayDescription);

        mTaskListView.setAdapter(adapter);

        Log.e(TAG,"adapter: "+adapter);
    }

    public void deleteTask(View view){
        View parent = (View) view.getParent();
        TextView taskTextView = parent.findViewById(R.id.task_title);
        String description = String.valueOf(taskTextView.getText());

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success =  jsonObject.getBoolean("success");
                    if (success) Log.e(TAG,"success"+response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        TrainingDeleteRequest trainingDeleteRequest = new TrainingDeleteRequest(userName, description, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(TrainingActivity.this);
        requestQueue.add(trainingDeleteRequest);
        onRestart();
        Log.e(TAG, "task: "+description);
    }

    @Override
    protected void onRestart() {
        adapter.clear();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateUI();
                        }
                    },500);

            }
        },500);

        super.onRestart();
    }
}


