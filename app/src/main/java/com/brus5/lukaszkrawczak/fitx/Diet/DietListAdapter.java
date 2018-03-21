package com.brus5.lukaszkrawczak.fitx.Diet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.R;

import java.util.ArrayList;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class DietListAdapter extends ArrayAdapter<Diet>{

    private static final String TAG = "DietListAdapter";

    private Context mContext;
    int mResource;

    public DietListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Diet> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        RecyclerView.ViewHolder holder;

        final View result;

        String id = getItem(position).getId();
        String name = getItem(position).getName();
        String weight = getItem(position).getWeight();
        String proteins = getItem(position).getProteins();
        String fats = getItem(position).getFats();
        String carbs = getItem(position).getCarbs();
        String kcal = getItem(position).getKcal();

        Diet diet = new Diet(id, name, weight, proteins, fats, carbs, kcal);



//        if (convertView == null){
//        }
//        else {
//            holder = (RecyclerView.ViewHolder) convertView.getTag();
//            result = convertView;
//        }

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);

        TextView meal_title = convertView.findViewById(R.id.meal_title);
        TextView meal_weight = convertView.findViewById(R.id.meal_weight);
        TextView meal_proteins = convertView.findViewById(R.id.meal_proteins);
        TextView meal_fats = convertView.findViewById(R.id.meal_fats);
        TextView meal_carbs = convertView.findViewById(R.id.meal_carbs);
        TextView meal_id = convertView.findViewById(R.id.meal_id);
        TextView meal_kcal = convertView.findViewById(R.id.meal_kcal);

        meal_title.setText(name);
        meal_weight.setText(weight);
        meal_proteins.setText(proteins);
        meal_fats.setText(fats);
        meal_carbs.setText(carbs);
        meal_id.setText(id);
        meal_kcal.setText(kcal);

        Count count = new Count(Integer.valueOf(kcal));
        Log.d(TAG, "getView: getCount "+count.getCount());



        ArrayList arrayList = new ArrayList();
        arrayList.add(kcal);

        Log.e(TAG, "getView: ArrayList "+arrayList);

        return convertView;
    }
}





















