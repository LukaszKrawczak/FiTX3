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

//    public double setBMR (double weight, double height, double age, String male){
//        if (male.equals("m")){
//            BMR = weight*9.99+height*6.25+age*4.92+5d;
//        }else if (male.equals("w")){
//            BMR = weight*9.99+height*6.25+age*4.92-161d;
//        }
//        Log.e(TAG,"BMR: " + BMR);
//        return BMR;



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
            bmr = weight*9.99+height*6.25+age*4.92+5d;
        }else if (male.equals("w")){
            bmr = weight*9.99+height*6.25+age*4.92-161d;
        }
        this.bmr = bmr;
        Log.i(TAG,"BMR: "+bmr);
    }

    public double getBmr() {
        return bmr;
    }

    public void setGymTime(double gymtime) {
        this.gymtime = gymtime;
        Log.i(TAG,"gymTime "+gymtime);
    }

    public double getGymTime() {
        return gymtime;
    }

    public void setGymTea(double gymtea) {
        this.gymtea = gymtea;
        Log.i(TAG,"gymTea "+gymtea);
    }

    public double getGymTea() {
        return gymtea;
    }

    public void setAreoTime(double areotime) {
        this.areotime = areotime;
        Log.i(TAG,"areotime "+areotime);
    }

    public double getAreoTime() {
        return areotime;
    }

    public void setAreoTea(double areotea) {
        this.areotea = areotea;
        Log.i(TAG,"areotea "+areotea);
    }

    public double getAreoTea() {
        return areotea;
    }

    public void setGymEpoc(double gymepoc) {
        this.gymepoc = gymepoc;
        Log.i(TAG,"gymEpoc "+gymepoc);
    }

    public double getGymEpoc() {
        return gymepoc;
    }

    public void setAreoEpoc(double areoepoc) {
        this.areoepoc = areoepoc;
        Log.i(TAG,"areoEpoc "+areoepoc);
    }

    public double getAreoEpoc() {
        return areoepoc;
    }

    public void setSomatotype(double somatotype) {
        this.somatotype = somatotype;
        Log.i(TAG,"somatotype "+somatotype);
    }

    public double getSomatotype() {
        return somatotype;
    }

    public void setTEF(double TEF) {
        this.TEF = bmr + (gymtime * gymtea) + (areotime * areotea) + (gymepoc * bmr) + areoepoc + somatotype;
        Log.i(TAG,"TEF "+TEF);
    }

    public double countTEF() {
        return TEF = bmr + (gymtime * gymtea) + (areotime * areotea) + (gymepoc * bmr) + areoepoc + somatotype;
    }

    public double finalKcalResult() {
        return kcalResult = TEF + (0.1d * TEF);
    }

    //    TEF = BMR + (GymTime * GymTea) + (AreoTime * AreoTEA) + (GymEpoc * BMR) + AreoEPOC + somatotype;
//
//    RESULT = TEF + (0.1d * TEF);

//    TEF = BMR + (GymTime * GymTea) + (AreoTime * AreoTEA) + (GymEpoc * BMR) + AreoEPOC + somatotype;
//
//    RESULT = TEF + (0.1d * TEF);
}
