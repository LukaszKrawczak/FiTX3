package com.brus5.lukaszkrawczak.fitx.Training;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.Training.DTO.TrainingDeleteDTO;
import com.brus5.lukaszkrawczak.fitx.Training.DTO.TrainingEditDTO;
import com.brus5.lukaszkrawczak.fitx.Training.DTO.TrainingInsertDTO;
import com.brus5.lukaszkrawczak.fitx.Training.DTO.TrainingSearchDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TrainingService {

    private static final String TRAINING = "Training/";
    private static final String INSERT_TRAINING_URL = Configuration.BASE_URL + TRAINING + "InsertTraining.php";
    private static final String EDIT_TRAINING_URL = Configuration.BASE_URL + TRAINING + "TrainingEditTraining.php";
    private static final String SEARCH_TRAINING_URL = Configuration.BASE_URL + TRAINING + "TrainingSearchByName.php";
    private static final String DELETE_TRAINING_URL = Configuration.BASE_URL + TRAINING + "TrainingDeleteRequest.php";
    private Map<String,String> params;
    private static final String TAG = "TrainingService";

    public static final String CONNECTION_INTERNET_FAILED = "Connection with failed";

    public JSONObject showTrainingByName;

    public void InsertTraining(final TrainingInsertDTO trainingInsertDTO, Context ctx){

        StringRequest strRequest = new StringRequest(Request.Method.POST, INSERT_TRAINING_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.i(TAG, "onResponse: "+response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                params = new HashMap<>();
                params.put("id", String.valueOf(trainingInsertDTO.id));
                params.put("done", trainingInsertDTO.date);
                params.put("rest", trainingInsertDTO.rest);
                params.put("reps", trainingInsertDTO.reps);
                params.put("weight", trainingInsertDTO.weight);
                params.put("username", trainingInsertDTO.userName);
                params.put("date", trainingInsertDTO.date);
                params.put("notepad", trainingInsertDTO.notepad);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);


    }

    public void SearchTraining(final TrainingSearchDTO trainingSearchDTO, final Context ctx){

        StringRequest strRequest = new StringRequest(Request.Method.POST, SEARCH_TRAINING_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            setShowTrainingByName(jsonObject);
                            Log.e(TAG, "onResponse: "+getShowTrainingByName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "onResponse: "+response);
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
                params.put("description", String.valueOf(trainingSearchDTO.description));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    public void EditTraining(final TrainingEditDTO trainingEditDTO, final Context ctx){

        StringRequest strRequest = new StringRequest(Request.Method.POST, EDIT_TRAINING_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                params = new HashMap<>();
                params.put("id", String.valueOf(trainingEditDTO.id));
                params.put("rest", trainingEditDTO.rest);
                params.put("reps", trainingEditDTO.reps);
                params.put("weight", trainingEditDTO.weight);
                params.put("username", trainingEditDTO.username);
                params.put("date", trainingEditDTO.date);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    public void DeleteTraining(final TrainingDeleteDTO trainingDeleteDTO, final Context ctx){

        StringRequest strRequest = new StringRequest(Request.Method.POST, DELETE_TRAINING_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e(TAG, "onResponse: "+response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                params = new HashMap<>();
                params.put("id", String.valueOf(trainingDeleteDTO.id));
                params.put("username", trainingDeleteDTO.username);
                params.put("date", trainingDeleteDTO.date);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    public JSONObject getShowTrainingByName() {
        return showTrainingByName;
    }

    public void setShowTrainingByName(JSONObject showTrainingByName) {
        this.showTrainingByName = showTrainingByName;
    }

}
