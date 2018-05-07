package com.brus5.lukaszkrawczak.fitx.Metacalc;

import android.util.Log;

/**
 * Created by lukaszkrawczak on 08.03.2018.
 */

public class Calculator {
    final private static String TAG = "Calculator";

    private double height;
    private double weight;
    private double age;

    private double gymtime;
    private double gymtea;
    private double areotime;
    private double areotea;
    private double gymepoc;
    private double areoepoc;
    private double bmr;
    private double somatotype;
    private double TEF;
    private double kcalResult;


    public void setHeight(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public double getAge() {
        return age;
    }

    public void setBMR(String male){
        if (male.equals("m")){
            bmr = weight*9.99+height*6.25-age*4.92+5d;
        }else if (male.equals("w")){
            bmr = weight*9.99+height*6.25-age*4.92-161d;
        }
        Log.i(TAG,"BMR: "+bmr);
        this.bmr = bmr;
    }

    public double getBmr() {
        return bmr;
    }

    public void setGymTime(double gymtime) {
        Log.i(TAG,"gymTime "+gymtime);
        this.gymtime = gymtime;
    }

    public double getGymTime() {
        return gymtime;
    }

    public void setGymTea(double gymtea) {
        Log.i(TAG,"gymTea "+gymtea);
        this.gymtea = gymtea;
    }

    public double getGymTea() {
        return gymtea;
    }

    public void setAreoTime(double areotime) {
        Log.i(TAG,"areotime "+areotime);
        this.areotime = areotime;
    }

    public double getAreoTime() {
        return areotime;
    }

    public void setAreoTea(double areotea) {
        Log.i(TAG,"areotea "+areotea);
        this.areotea = areotea;
    }

    public double getAreoTea() {
        return areotea;
    }

    public void setGymEpoc(double gymepoc) {
        Log.i(TAG,"gymEpoc "+gymepoc);
        this.gymepoc = gymepoc;
    }

    public double getGymEpoc() {
        return gymepoc;
    }

    public void setAreoEpoc(double areoepoc) {
        Log.i(TAG,"areoEpoc "+areoepoc);
        this.areoepoc = areoepoc;
    }

    public double getAreoEpoc() {
        return areoepoc;
    }

    public void setSomatotype(double somatotype) {
        Log.i(TAG,"somatotype "+somatotype);
        this.somatotype = somatotype;
    }

    public double getSomatotype() {
        return somatotype;
    }

    public void setTEF(double TEF) {

        this.TEF = bmr + (gymtime * gymtea) + (areotime * areotea) /*+ (gymepoc * bmr)*/ + areoepoc + somatotype;
    }

    public double countTEF() {
        Log.i(TAG,"TEF "+TEF);
        Log.i(TAG,"TEF "+TEF);
        Log.i(TAG, "countTEF: "+"bmr: "+bmr+" + (gymtime: "+gymtime+" * gymtea: "+gymtea+")"+" (areotime: "+areotime+" * areotea: "+areotea+")"+" + areoepoc: "+areoepoc+" + somatotype: "+somatotype);
        return TEF = bmr + (gymtime * gymtea) + (areotime * areotea) /*+ (gymepoc * bmr)*/ + areoepoc + somatotype;
    }

    public double finalKcalResult() {
        return kcalResult = TEF + (0.1d * TEF);
    }

}
