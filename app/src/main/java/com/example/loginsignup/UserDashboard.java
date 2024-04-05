package com.example.loginsignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class UserDashboard extends AppCompatActivity {

    Button EditButton;
    CardView courseCard, locationMap, studentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        // Finding views
        EditButton = findViewById(R.id.editprofilebtn);
        courseCard = findViewById(R.id.courseCard);
        locationMap = findViewById(R.id.locationMap);
        studentView = findViewById(R.id.studentView);

        // Setting onClickListener for EditButton
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, ProfileDashboard.class);
                startActivity(intent);
            }
        });

        // Setting onClickListener for courseCard
        courseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCourse();
            }
        });

        // Setting onClickListener for locationMap
        locationMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for locationMap
            }
        });

        // Setting onClickListener for studentView
        studentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBranchCourseList();
            }
        });
    }

    // Method to display courses
    public void displayCourse() {
        Intent intent = new Intent(UserDashboard.this, DisplayCourses.class);
        startActivity(intent);
    }

    // Method to open BranchCourseList activity
    public void openBranchCourseList() {
        Intent intent = new Intent(UserDashboard.this, BranchCourseList.class);
        startActivity(intent);
    }
}
