package com.example.testlogin;


public class Task {
    private final int id;
    private final String taskName;
    private final String taskDescription;

    public Task(int id, String taskName, String taskDescription) {
        this.id = id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    public int getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }
}
