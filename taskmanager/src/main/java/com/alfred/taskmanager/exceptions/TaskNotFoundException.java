package com.alfred.taskmanager.exceptions;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String s) {
        super(s);
    }
}
