package com.example.loginsignup;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BranchCourseList extends AppCompatActivity {

    private DBHelper dbHelper;
    private Spinner courseSpinner, branchSpinner;
    private Button searchButton;
    private RecyclerView recyclerView;
    private CourseListAdapter adapter;
    private List<Course> courseList;

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_course_list);

        dbHelper = new DBHelper(this);
        courseSpinner = findViewById(R.id.spinnercourse);
        branchSpinner = findViewById(R.id.spinnerbranch);
        searchButton = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recyclerView);
        courseList = new ArrayList<>();
        adapter = new CourseListAdapter(this, courseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchCourseList.this, RegisterCourseWithPay.class);
                startActivity(intent);
            }
        });

        // Populate the course spinner
        populateSpinnerFromDB(dbHelper.getCourseNames(), courseSpinner);

        // Populate the branch spinner
        populateSpinnerFromDB(dbHelper.getBranchNames(), branchSpinner);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCourse = courseSpinner.getSelectedItem().toString();
                String selectedBranch = branchSpinner.getSelectedItem().toString();

                Cursor cursor = dbHelper.getCourseDetails(selectedCourse, selectedBranch);
                if (cursor != null && cursor.moveToFirst()) {
                    courseList.clear();
                    do {
                        Course course = new Course();
                        course.setCourseName(selectedCourse);
                        course.setCourseFee(cursor.getString(cursor.getColumnIndex(DBHelper.COL_COURSE_FEE)));
                        course.setStartingDate(cursor.getString(cursor.getColumnIndex(DBHelper.COL_START_DATE)));
                        course.setRegistrationCloseDate(cursor.getString(cursor.getColumnIndex(DBHelper.COL_REGISTRATION_CLOSE_DATE)));
                        course.setSelectedBranch(selectedBranch);
                        course.setSelectedDuration(cursor.getString(cursor.getColumnIndex(DBHelper.COL_SELECTED_DURATION)));
                        courseList.add(course);
                    } while (cursor.moveToNext());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(BranchCourseList.this, "No courses found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        // Method to populate a spinner with data retrieved from the database
    private void populateSpinnerFromDB(List<String> dataList, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the DBHelper instance when the activity is destroyed
        dbHelper.close();
    }
}
