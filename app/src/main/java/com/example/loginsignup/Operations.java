package com.example.loginsignup;

import android.content.Context;
import android.database.Cursor;

public class Operations {

    private DBHelper dbHelper;

    public Operations(Context context) {

        dbHelper = new DBHelper(context);
    }

    public void addCourse(String courseName,String courseFee,String startingDate,String registrationCloseDate,String maxParticipants,String selectedBranch,String selectedDuration) {
        dbHelper.insertDetails(courseName,courseFee,startingDate,registrationCloseDate,maxParticipants,selectedBranch,selectedDuration);
    }
    public Cursor getAllCourse() {
        return dbHelper.getAllCourses();
    }
}
