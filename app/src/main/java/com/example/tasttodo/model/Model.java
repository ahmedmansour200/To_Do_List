package com.example.tasttodo.model;

import java.util.Date;

public class Model {
    private int taskId;
    private  String taskTitle , taskBode;
    private boolean isComplete;

    public Model(String taskTitle, String taskBode) {
        this.taskTitle = taskTitle;
        this.taskBode = taskBode;
    }
    public Model(){}


    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskBode() {
        return taskBode;
    }

    public void setTaskBode(String taskBode) {
        this.taskBode = taskBode;
    }


    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}