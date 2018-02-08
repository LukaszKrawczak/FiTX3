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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MenuTraining extends AppCompatActivity {

    private static final String TAG = "MenuTraining";

    String name, username, age, password, email, male, somatotypeS;
    String description;
    int id;

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

        mTaskListView = (ListView) findViewById(R.id.list_training);

        Intent intent1 = getIntent();
        id = intent1.getIntExtra("id",0);
        name = intent1.getStringExtra("name");
        username = intent1.getStringExtra("username");
        age = intent1.getStringExtra("age");
        male = intent1.getStringExtra("male");

        Log.e(TAG, "ID "        +id);
        Log.e(TAG, "name "      +name);
        Log.e(TAG, "username "  +username);
        Log.e(TAG, "age "       +age);
        Log.e(TAG, "male "      +male);

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
        },2000);
    }

    private void loadData() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG,"getTrainingRequest response: "+response);

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
        GetTrainingRequest getTrainingRequest = new GetTrainingRequest(username, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MenuTraining.this);
        queue.add(getTrainingRequest);
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
                                UpdateTraining updateTraining = new UpdateTraining(Integer.valueOf(id), username, date, description, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(MenuTraining.this);
                                queue.add(updateTraining);
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
                    },1000);

            }
        },2000);


        super.onRestart();
    }
}


