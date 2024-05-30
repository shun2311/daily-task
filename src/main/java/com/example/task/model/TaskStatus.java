package com.example.task.model;


public enum TaskStatus {
    IC ("INCOMPLETE status"), //Incomplete
    AA ("COMPLETED status"); // Completed

    private final String name;       

    private TaskStatus(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
