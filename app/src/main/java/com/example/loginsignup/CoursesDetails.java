package com.example.loginsignup;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class CoursesDetails extends AppCompatActivity {

    private Spinner spinnerCourse, spinnerBranch;
    private Button search;
    private TextView courseName, fee, branch, startingDate, duration;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_details);

        spinnerCourse = findViewById(R.id.spinnercourse);
        spinnerBranch = findViewById(R.id.spinnerbranch);
        search = findViewById(R.id.search);
        courseName = findViewById(R.id.coursename);
        fee = findViewById(R.id.fee);
        branch = findViewById(R.id.branch);
        startingDate = findViewById(R.id.startingdate);
        duration = findViewById(R.id.duration);

        dbHelper = new DBHelper(this);

        // Populate spinners with course and branch data
        populateSpinners();

        // Set listener for the "Search" button
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve selected course and branch
                String selectedCourse = spinnerCourse.getSelectedItem().toString();
                String selectedBranch = spinnerBranch.getSelectedItem().toString();

                // Get course details from the database
                Course course = dbHelper.getCourse(selectedCourse, selectedBranch);
                if (course != null) {
                    // Update TextViews with course details
                    courseName.setText(course.getCourseName());
                    fee.setText(course.getCourseFee());
                    branch.setText(course.getSelectedBranch());
                    startingDate.setText(course.getStartingDate());
                    duration.setText(course.getSelectedDuration());
                }
            }
        });
    }

    private void populateSpinners() {
        // Populate spinner with course names
        List<String> courseNames = dbHelper.getCourseNames();
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseNames);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourse.setAdapter(courseAdapter);

        // Populate spinner with branch names
        List<String> branchNames = dbHelper.getBranchNames();
        ArrayAdapter<String> branchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branchNames);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBranch.setAdapter(branchAdapter);
    }
}
