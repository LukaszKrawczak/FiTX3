package com.brus5.lukaszkrawczak.fitx.Diet;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class Diet {
    private String id;
    private String name;
    private String weight;
    private String proteins;
    private String fats;
    private String carbs;
    private String kcal;

    public Diet(String id, String name, String weight, String proteins, String fats, String carbs, String kcal) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.proteins = proteins;
        this.fats = fats;
        this.carbs = carbs;
        this.kcal = kcal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getProteins() {
        return proteins;
    }

    public void setProteins(String proteins) {
        this.proteins = proteins;
    }

    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }
}
