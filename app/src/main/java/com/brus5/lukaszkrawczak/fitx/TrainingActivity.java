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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Training.Training;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingDeleteRequest;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingDoneRequest;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingInsertRequest;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingListAdapter;
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
import java.util.HashMap;
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

//    private ArrayAdapter<String> adapter;
    ListView mTaskListView;
    MixAdapter<RecyclerView.ViewHolder> adapter1 = new MixAdapter<>();
    /* Gettings date */
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
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

        arrayDescription.clear();
        trainingArrayList.clear();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: response "+response);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray server_response = jsonObject.getJSONArray("server_response");

                    String id;
                    String description;
                    String done;
                    if (server_response.length() > 0){
                        for (int i = 0; i < server_response.length(); i++){
                            JSONObject c = server_response.getJSONObject(i);
                            id = c.getString("id");
                            done = c.getString("done");
                            description = c.getString("description");
                            HashMap<String,String> server = new HashMap<>();
                            server.put("id", id);
                            server.put("description", description);
                            server.put("done", done);
                            arrayDescription.add(description);
                            Training training = new Training(id,done,description);
                            trainingArrayList.add(training);
                        }
                    }
                    adapter = new TrainingListAdapter(TrainingActivity.this,R.layout.training_row,trainingArrayList);
                    mTaskListView.setAdapter(adapter);
                    mTaskListView.invalidate();

                    Log.e(TAG,"arrayDescription: "+arrayDescription);
                    Log.d(TAG,"trainingArrayList: "+trainingArrayList);

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
        switch (item.getItemId()) {
            case R.id.action_add_training:
                Log.d(TAG, "Add a new task");
                final EditText taskEditText = new EditText(this);

                final String input = taskEditText.getText().toString();



                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add excercise")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                description = escapeSpecialRegexChars(String.valueOf(taskEditText.getText()));
                                Log.e(TAG, "escapeSpecialRegexChars(input);" + escapeSpecialRegexChars(input));
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e(TAG,"response: "+response);
                                    }
                                };
                                TrainingInsertRequest trainingInsertRequest = new TrainingInsertRequest(userIDint, userName, dateInsde, 0, description, responseListener);
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
        
        
//        adapter = new ArrayAdapter<>(this, R.layout.training_row,R.id.task_title,arrayDescription);
//        adapter = new ArrayAdapter<String>(this, R.layout.training_row,R.id.task_title,arrayDescription);
//        mTaskListView.setAdapter(adapter);
        Log.e(TAG,"adapter: "+adapter);
    }

    public void doneTrainingTask(View view) {
        View parent = (View) view.getParent();
        TextView taskIdTextView = parent.findViewById(R.id.task_id);
        TextView taskTextView = parent.findViewById(R.id.task_title);
        CheckBox taskCheckBox = parent.findViewById(R.id.task_done);
        String id = String.valueOf(taskIdTextView.getText());
        String description = String.valueOf(taskTextView.getText());
        String done = String.valueOf(taskCheckBox.isChecked());
        int doneint = 0;
        if (done.equals("true")){
            doneint = 1;
        }else if (done.equals("false")){
            doneint = 0;
        }
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        };
        TrainingDoneRequest trainingDoneRequest = new TrainingDoneRequest(id,doneint,responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(TrainingActivity.this);
        requestQueue.add(trainingDoneRequest);

        Log.e(TAG, "id: "+id);
        Log.e(TAG, "doneTrainingTask: "+description);
        Log.e(TAG, "done: "+done);
    }



    public void deleteTrainingTask(View view){
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

        TrainingDeleteRequest trainingDeleteRequest = new TrainingDeleteRequest(userName, dateInsde, description, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(TrainingActivity.this);
        requestQueue.add(trainingDeleteRequest);
        Log.e(TAG, "CRASH1");

//        onRestart();
        Log.e(TAG, "CRASH2");
        Log.d(TAG, "task: "+description);

        arrayDescription.clear();
        trainingArrayList.clear();
        new LongRunningTask().execute();

    }

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

//    public class UsersAdapter extends ArrayAdapter<User> {
//        public UsersAdapter(Context context, ArrayList<User> users) {
//            super(context, 0, users);
//        }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        User user = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.training_row, parent, false);
//        }
//        // Lookup view for data population
//        TextView tvName = (TextView) convertView.findViewById(R.id.task_title);
//        TextView tvHome = (TextView) convertView.findViewById(R.id.task_done);
//        // Populate the data into the template view using the data object
//        tvName.setText(user.description);
//        tvHome.setText(user.done);
//        // Return the completed view to render on screen
//        return convertView;
//    }
//}

    String escapeSpecialRegexChars(String str) {

        return SPECIAL_REGEX_CHARS.matcher(str).replaceAll("\\\\$0");
    }

public void deleteMealTask1(android.view.View view) {}
}


