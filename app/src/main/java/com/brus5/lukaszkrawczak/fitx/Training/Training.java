package com.brus5.lukaszkrawczak.fitx.Training;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class Training {
    private String task_id;
//    private String task_done;
    private String task_name;
    private String task_rest;
    private String task_weight;
    private String task_reps;

    public Training(String task_id, String task_name, String task_rest, String task_weight, String task_reps) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_rest = task_rest;
        this.task_weight = task_weight;
        this.task_reps = task_reps;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_rest() {
        return task_rest;
    }

    public void setTask_rest(String task_rest) {
        this.task_rest = task_rest;
    }

    public String getTask_weight() {
        return task_weight;
    }

    public void setTask_weight(String task_weight) {
        this.task_weight = task_weight;
    }

    public String getTask_reps() {
        return task_reps;
    }

    public void setTask_reps(String task_reps) {
        this.task_reps = task_reps;
    }
}
