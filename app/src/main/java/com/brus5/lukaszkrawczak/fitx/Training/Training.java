package com.brus5.lukaszkrawczak.fitx.Training;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class Training {
    private String task_id;
    private String task_done;
    private String task_name;

    public Training(String task_id, String task_done, String task_name) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_done = task_done;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_done() {
        return task_done;
    }

    public void setTask_done(String task_done) {
        this.task_done = task_done;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }
}
