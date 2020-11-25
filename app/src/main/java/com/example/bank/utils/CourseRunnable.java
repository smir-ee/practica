package com.example.bank.utils;

import com.example.bank.CourseParams;

import java.util.ArrayList;

public abstract class CourseRunnable implements Runnable {
    @Override
    public void run() { }
    public abstract void run(ArrayList<CourseParams> courses);
}
