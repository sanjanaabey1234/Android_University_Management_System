package com.example.loginsignup;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DisplayCourses extends AppCompatActivity {

    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private ArrayList<String> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_courses);

        dbHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true); // Ensure RecyclerView has fixed size
        courseList = new ArrayList<>();

        // Fetch all courses from the database
        Cursor cursor = dbHelper.getAllCourses();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No courses found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                // Append course details to the list
                String courseDetails = "Course Name: " + cursor.getString(1) + "\n" +
                        "Fee: " + cursor.getString(2) + "\n" +
                        "Start Date: " + cursor.getString(3) + "\n" +
                        "Registration Close Date: " + cursor.getString(4) + "\n" +
                        "Max Participants: " + cursor.getString(5) + "\n" +
                        "Branch: " + cursor.getString(6) + "\n" +
                        "Duration: " + cursor.getString(7);

                courseList.add(courseDetails);
            }

            // Create an adapter to populate the RecyclerView
            adapter = new CourseAdapter(this, courseList);
            recyclerView.setAdapter(adapter);
        }
    }
}
