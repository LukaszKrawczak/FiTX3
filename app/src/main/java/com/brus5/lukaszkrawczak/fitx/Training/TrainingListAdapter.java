package com.brus5.lukaszkrawczak.fitx.Training;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.R;

import java.util.ArrayList;

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
        String task_done = getItem(position).getTask_done();

        Training training = new Training(task_id,task_name,task_done);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvId = convertView.findViewById(R.id.task_id);
        TextView tvName = convertView.findViewById(R.id.task_title);
        CheckBox cbDone = convertView.findViewById(R.id.task_done);

        tvName.setText(task_name);
        tvId.setText(task_id);

//        if (task_done.equals("1")){
//            cbDone.setChecked(true);
//        } else  if (task_done.equals("0")){
//            cbDone.setChecked(false);
//        }

        return convertView;

    }
}
