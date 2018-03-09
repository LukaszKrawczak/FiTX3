package com.brus5.lukaszkrawczak.fitx.Metacalc;

import android.util.Log;

/**
 * Created by lukaszkrawczak on 07.03.2018.
 */

public class Person {

    private double height;
    private double weight;

    public void setHeight(double height) {
        if (height > 40 && height < 250) {
            this.height = height;
            Log.e("Person: ","height met criteria "+height);
        }else {
            Log.e("Person: ","height is too small or too big "+height);
        }
    }
    public double getHeight() {
        return height;
    }
    public void setWeight(double weight) {
        if (weight > 30 && weight < 300) {
            this.weight = weight;
            Log.e("Person: ","weight met criteria "+weight);
        }else{
            Log.e("Person: ","weight is too small or too big "+weight);
        }
    }
    public double getWeight() {
        return weight;
    }

}
