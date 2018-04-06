package com.brus5.lukaszkrawczak.fitx.Training;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class TrainingListAdapter extends ArrayAdapter<Training> {

    final private static String TAG = "TrainingListAdapter";



    private Context mContext;
    int mResource;

    public TrainingListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Training> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        String task_id = getItem(position).getTask_id();
        String task_name = getItem(position).getTask_name();
        String task_rest = getItem(position).getTask_rest();
        String task_weight = getItem(position).getTask_weight();
        String task_reps = getItem(position).getTask_reps();
//        String task_done = getItem(position).getTask_done();

        Training training = new Training(task_id,task_name,task_rest,task_weight,task_reps);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvId = convertView.findViewById(R.id.task_id);
        TextView tvName = convertView.findViewById(R.id.task_name);
        TextView tvRest = convertView.findViewById(R.id.task_restTime);
        TextView tvSet1 = convertView.findViewById(R.id.task_set1);
        TextView tvSet2 = convertView.findViewById(R.id.task_set2);
        TextView tvSet3 = convertView.findViewById(R.id.task_set3);
        TextView tvSet4 = convertView.findViewById(R.id.task_set4);
        TextView tvSet5 = convertView.findViewById(R.id.task_set5);
        TextView tvSet6 = convertView.findViewById(R.id.task_set6);
        TextView tvSet7 = convertView.findViewById(R.id.task_set7);
        TextView tvSet8 = convertView.findViewById(R.id.task_set8);
        TextView tvSet9 = convertView.findViewById(R.id.task_set9);
        TextView tvSet10 = convertView.findViewById(R.id.task_set10);
//        TextView tvReps1 = convertView.findViewById(R.id.task_reps1);
//        TextView tvReps2 = convertView.findViewById(R.id.task_reps2);
//        TextView tvReps3 = convertView.findViewById(R.id.task_reps3);
//        TextView tvReps4 = convertView.findViewById(R.id.task_reps4);
//        TextView tvReps5 = convertView.findViewById(R.id.task_reps5);
//        TextView tvReps6 = convertView.findViewById(R.id.task_reps6);
//        TextView tvReps7 = convertView.findViewById(R.id.task_reps7);
//        TextView tvReps8 = convertView.findViewById(R.id.task_reps8);
//        TextView tvReps9 = convertView.findViewById(R.id.task_reps9);
//        TextView tvReps10 = convertView.findViewById(R.id.task_reps10);
//
//        ImageView ivCol1 = convertView.findViewById(R.id.imageViewCol1);
//        ImageView ivCol2 = convertView.findViewById(R.id.imageViewCol2);
//        ImageView ivCol3 = convertView.findViewById(R.id.imageViewCol3);
//        ImageView ivCol4 = convertView.findViewById(R.id.imageViewCol4);
//        ImageView ivCol5 = convertView.findViewById(R.id.imageViewCol5);
//        ImageView ivCol6 = convertView.findViewById(R.id.imageViewCol6);
//        ImageView ivCol7 = convertView.findViewById(R.id.imageViewCol7);
//        ImageView ivCol8 = convertView.findViewById(R.id.imageViewCol8);
//        ImageView ivCol9 = convertView.findViewById(R.id.imageViewCol9);

        tvId.setText(task_id);
        tvName.setText(task_name);
        tvRest.setText(task_rest+"s");

        String mTask_weight = task_weight.replaceAll("\\p{Punct}"," ");
        String[] mWeight_table = mTask_weight.split("\\s+");

        String mTask_reps = task_reps.replaceAll("\\p{Punct}"," ");
        String[] mReps_table = mTask_reps.split("\\s+");

        Log.e(TAG, "getView: weight_table"+ Arrays.toString(mWeight_table));
        Log.e(TAG, "getView: mReps_table"+ Arrays.toString(mReps_table));
        Log.e(TAG, "getView: lenght "+mWeight_table.length);



if (mWeight_table.length >= 1){
    tvSet1.setVisibility(View.VISIBLE);
    tvSet1.setText(mReps_table[0]+"x"+mWeight_table[0]+"kg");
}

if (mWeight_table.length >= 2){
    tvSet2.setVisibility(View.VISIBLE);
    tvSet2.setText(mReps_table[1]+"x"+mWeight_table[1]+"kg");
}

if (mWeight_table.length >= 3){
    tvSet3.setVisibility(View.VISIBLE);
    tvSet3.setText(mReps_table[2]+"x"+mWeight_table[2]+"kg");
}

        if (mWeight_table.length >= 4){
            tvSet4.setVisibility(View.VISIBLE);
            tvSet4.setText(mReps_table[3]+"x"+mWeight_table[3]+"kg");
        }

        if (mWeight_table.length >= 5){
            tvSet5.setVisibility(View.VISIBLE);
            tvSet5.setText(mReps_table[4]+"x"+mWeight_table[4]+"kg");
        }

        if (mWeight_table.length >= 6){
            tvSet6.setVisibility(View.VISIBLE);
            tvSet6.setText(mReps_table[5]+"x"+mWeight_table[5]+"kg");
        }
        if (mWeight_table.length >= 7){
            tvSet7.setVisibility(View.VISIBLE);
            tvSet7.setText(mReps_table[6]+"x"+mWeight_table[6]+"kg");
        }

        if (mWeight_table.length >= 8){
            tvSet8.setVisibility(View.VISIBLE);
            tvSet8.setText(mReps_table[7]+"x"+mWeight_table[7]+"kg");
        }

        if (mWeight_table.length >= 9){
            tvSet9.setVisibility(View.VISIBLE);
            tvSet9.setText(mReps_table[8]+"x"+mWeight_table[8]+"kg");
        }

        if (mWeight_table.length >= 10){
            tvSet10.setVisibility(View.VISIBLE);
            tvSet10.setText(mReps_table[9]+"x"+mWeight_table[9]+"kg");
        }


//        Log.e(TAG, "getView: weight1"+weight_table[weight_table.length-weight_table.length+1]);

//        if (task_done.equals("1")){
//            cbDone.setChecked(true);
//        } else  if (task_done.equals("0")){
//            cbDone.setChecked(false);
//        }

        return convertView;

    }
}
