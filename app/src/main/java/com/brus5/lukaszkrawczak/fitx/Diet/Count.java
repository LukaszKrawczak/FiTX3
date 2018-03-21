package com.brus5.lukaszkrawczak.fitx.Diet;

import java.util.ArrayList;

/**
 * Created by lukaszkrawczak on 21.03.2018.
 */

public class Count extends ArrayList<Integer>{

    private int count;
    private ArrayList arrayList;

    public Count(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;

    }


//    ArrayList<Integer> arrayList = new ArrayList<>();
//    public ArrayList<Integer> getArrayList() {
//        add(getCount());
//        return arrayList;
//    }
}
