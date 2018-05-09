package com.brus5.lukaszkrawczak.fitx.Training;

/**
 * Created by lukaszkrawczak on 06.04.2018.
 */

public class TrainingSet {

    private String weight1;
    private String weight2;
    private String weight3;
    private String weight4;
    private String weight5;
    private String weight6;
    private String weight7;
    private String weight8;
    private String weight9;
    private String weight10;

    private String reps1;
    private String reps2;
    private String reps3;
    private String reps4;
    private String reps5;
    private String reps6;
    private String reps7;
    private String reps8;
    private String reps9;
    private String reps10;

    private String repsAll;
    private String weightsAll;

    private int setNumber;

    public TrainingSet(String weight1, String weight2, String weight3, String weight4, String weight5, String weight6, String weight7, String weight8, String weight9, String weight10, String reps1, String reps2, String reps3, String reps4, String reps5, String reps6, String reps7, String reps8, String reps9, String reps10) {
        this.weight1 = weight1;
        this.weight2 = weight2;
        this.weight3 = weight3;
        this.weight4 = weight4;
        this.weight5 = weight5;
        this.weight6 = weight6;
        this.weight7 = weight7;
        this.weight8 = weight8;
        this.weight9 = weight9;
        this.weight10 = weight10;
        this.reps1 = reps1;
        this.reps2 = reps2;
        this.reps3 = reps3;
        this.reps4 = reps4;
        this.reps5 = reps5;
        this.reps6 = reps6;
        this.reps7 = reps7;
        this.reps8 = reps8;
        this.reps9 = reps9;
        this.reps10 = reps10;
    }



    public String getWeight1() {
        return weight1;
    }

    public String getWeight2() {
        return weight2;
    }

    public String getWeight3() {
        return weight3;
    }

    public String getWeight4() {
        return weight4;
    }

    public String getWeight5() {
        return weight5;
    }

    public String getWeight6() {
        return weight6;
    }

    public String getWeight7() {
        return weight7;
    }

    public String getWeight8() {
        return weight8;
    }

    public String getWeight9() {
        return weight9;
    }

    public String getWeight10() {
        return weight10;
    }

    public String getReps1() {
        return reps1;
    }

    public String getReps2() {
        return reps2;
    }

    public String getReps3() {
        return reps3;
    }

    public String getReps4() {
        return reps4;
    }

    public String getReps5() {
        return reps5;
    }

    public String getReps6() {
        return reps6;
    }

    public String getReps7() {
        return reps7;
    }

    public String getReps8() {
        return reps8;
    }

    public String getReps9() {
        return reps9;
    }

    public String getReps10() {
        return reps10;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public String getRepsAll() {
        switch (setNumber){
            case 1:
                repsAll = reps1;
                break;
            case 2:
                repsAll = reps1 + "." + reps2;
                break;
            case 3:
                repsAll = reps1 + "." + reps2 + "." + reps3;
                break;
            case 4:
                repsAll = reps1 + "." + reps2 + "." + reps3 + "." + reps4;
                break;
            case 5:
                repsAll = reps1 + "." + reps2 + "." + reps3 + "." + reps4 + "." + reps5;
                break;
            case 6:
                repsAll = reps1 + "." + reps2 + "." + reps3 + "." + reps4 + "." + reps5 + "." + reps6;
            case 7:
                repsAll = reps1 + "." + reps2 + "." + reps3 + "." + reps4 + "." + reps5 + "." + reps6 + "." + reps7;
                break;
            case 8:
                repsAll = reps1 + "." + reps2 + "." + reps3 + "." + reps4 + "." + reps5 + "." + reps6 + "." + reps7 + "." + reps8;
                break;
            case 9:
                repsAll = reps1 + "." + reps2 + "." + reps3 + "." + reps4 + "." + reps5 + "." + reps6 + "." + reps7 + "." + reps8 + "." + reps9;
                break;
            case 10:
                repsAll = reps1 + "." + reps2 + "." + reps3 + "." + reps4 + "." + reps5 + "." + reps6 + "." + reps7 + "." + reps8 + "." + reps9 + "." + reps10;
                break;

        }
        return repsAll;
    }

    public String getWeightsAll() {
        switch (setNumber){
            case 1:
                weightsAll = weight1;
                break;
            case 2:
                weightsAll = weight1 + "." + weight2;
                break;
            case 3:
                weightsAll = weight1 + "." + weight2 + "." + weight3;
                break;
            case 4:
                weightsAll = weight1 + "." + weight2 + "." + weight3 + "." + weight4;
                break;
            case 5:
                weightsAll = weight1 + "." + weight2 + "." + weight3 + "." + weight4 + "." + weight5;
                break;
            case 6:
                weightsAll = weight1 + "." + weight2 + "." + weight3 + "." + weight4 + "." + weight5 + "." + weight6;
                break;
            case 7:
                weightsAll = weight1 + "." + weight2 + "." + weight3 + "." + weight4 + "." + weight5 + "." + weight6 + "." + weight7;
                break;
            case 8:
                weightsAll = weight1 + "." + weight2 + "." + weight3 + "." + weight4 + "." + weight5 + "." + weight6 + "." + weight7 + "." + weight8;
                break;
            case 9:
                weightsAll = weight1 + "." + weight2 + "." + weight3 + "." + weight4 + "." + weight5 + "." + weight6 + "." + weight7 + "." + weight8 + "." + weight9;
                break;
            case 10:
                weightsAll = weight1 + "." + weight2 + "." + weight3 + "." + weight4 + "." + weight5 + "." + weight6 + "." + weight7 + "." + weight8 + "." + weight9 + "." + weight10;
                break;

        }
        return weightsAll;
    }

    public boolean isEnteredValue() {

        switch (setNumber){
            case 1:
                if (reps1.isEmpty() || weight1.isEmpty() ) return true;
                break;
            case 2:
                if (reps1.isEmpty() || weight1.isEmpty() || reps2.isEmpty() || weight2.isEmpty()) return true;
                break;
            case 3:
                if (reps1.isEmpty() || weight1.isEmpty() || reps2.isEmpty() || weight2.isEmpty() || reps3.isEmpty() || weight3.isEmpty()) return true;
                break;
            case 4:
                if (reps1.isEmpty() || weight1.isEmpty() || reps2.isEmpty() || weight2.isEmpty() || reps3.isEmpty() || weight3.isEmpty() || reps4.isEmpty() || weight4.isEmpty()) return true;
                break;
            case 5:
                if (reps1.isEmpty() || weight1.isEmpty() || reps2.isEmpty() || weight2.isEmpty() || reps3.isEmpty() || weight3.isEmpty() || reps4.isEmpty() || weight4.isEmpty() || reps5.isEmpty() || weight5.isEmpty()) return true;
                break;
            case 6:
                if (reps1.isEmpty() || weight1.isEmpty() || reps2.isEmpty() || weight2.isEmpty() || reps3.isEmpty() || weight3.isEmpty() || reps4.isEmpty() || weight4.isEmpty() || reps5.isEmpty() || weight5.isEmpty() || reps6.isEmpty() || weight6.isEmpty()) return true;
                break;
            case 7:
                if (reps1.isEmpty() || weight1.isEmpty() || reps2.isEmpty() || weight2.isEmpty() || reps3.isEmpty() || weight3.isEmpty() || reps4.isEmpty() || weight4.isEmpty() || reps5.isEmpty() || weight5.isEmpty() || reps6.isEmpty() || weight6.isEmpty() || reps7.isEmpty() || weight7.isEmpty()) return true;
                break;
            case 8:
                if (reps1.isEmpty() || weight1.isEmpty() || reps2.isEmpty() || weight2.isEmpty() || reps3.isEmpty() || weight3.isEmpty() || reps4.isEmpty() || weight4.isEmpty() || reps5.isEmpty() || weight5.isEmpty() || reps6.isEmpty() || weight6.isEmpty() || reps7.isEmpty() || weight7.isEmpty() || reps8.isEmpty() || weight8.isEmpty()) return true;
                break;
            case 9:
                if (reps1.isEmpty() || weight1.isEmpty() || reps2.isEmpty() || weight2.isEmpty() || reps3.isEmpty() || weight3.isEmpty() || reps4.isEmpty() || weight4.isEmpty() || reps5.isEmpty() || weight5.isEmpty() || reps6.isEmpty() || weight6.isEmpty() || reps7.isEmpty() || weight7.isEmpty() || reps8.isEmpty() || weight8.isEmpty() || reps9.isEmpty() || weight9.isEmpty()) return true;
                break;
            case 10:
                if (reps1.isEmpty() || weight1.isEmpty() || reps2.isEmpty() || weight2.isEmpty() || reps3.isEmpty() || weight3.isEmpty() || reps4.isEmpty() || weight4.isEmpty() || reps5.isEmpty() || weight5.isEmpty() || reps6.isEmpty() || weight6.isEmpty() || reps7.isEmpty() || weight7.isEmpty() || reps8.isEmpty() || weight8.isEmpty() || reps9.isEmpty() || weight9.isEmpty() || reps10.isEmpty() || weight10.isEmpty()) return true;
                break;
            }
     return false;
    }
}
