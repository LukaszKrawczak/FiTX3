package com.brus5.lukaszkrawczak.fitx.Training;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.Training.DTO.TrainingInsertDTO;

import java.util.HashMap;
import java.util.Map;

public class TrainingService extends StringRequest{

    private static final String TRAINING = "/Training/";
    private static final String INSERT_TRAINING_URL = Configuration.BASE_URL + TRAINING + "InsertTraining.php";
    private Map<String,String> params = new HashMap<>();
    private Context context;
    private static final String TAG = "TrainingService";

    public TrainingService(Response.Listener<String> listener) {
        super(Method.POST, INSERT_TRAINING_URL, listener, null);
    }

    public void InsertTraining(TrainingInsertDTO trainingInsertDTO){
        params.put("id", String.valueOf(trainingInsertDTO.id));
        params.put("done", trainingInsertDTO.date);
        params.put("rest", trainingInsertDTO.rest);
        params.put("reps", trainingInsertDTO.reps);
        params.put("weight", trainingInsertDTO.weight);
        params.put("username", trainingInsertDTO.userName);
        params.put("date", trainingInsertDTO.date);
        params.put("notepad", trainingInsertDTO.notepad);
    }

    @SuppressLint("LongLogTag")
    @Override
    public Map<String, String> getParams() {
        Log.e("UserProfileUpdateRequest","ParamsChecker"+params);
        return params;
    }

}
