package com.example.loginsignup;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBranchActivity extends AppCompatActivity {

    Button addBranch;
    EditText textBranchCode, textBranchName;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);

        addBranch = findViewById(R.id.buttonAdd);
        textBranchCode = findViewById(R.id.textBranchCode);
        textBranchName = findViewById(R.id.textBranchName);

        // Initialize the DBHelper object
        dbHelper = new DBHelper(this);

        addBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBranch();
            }
        });
    }

    private void addBranch() {
        if (isValidInput()) {
            // Get user input
            String branchCode = textBranchCode.getText().toString().trim();
            String branchName = textBranchName.getText().toString().trim();

            // Insert branch details into the database
            dbHelper.insertBranchDetails(branchCode, branchName);

            // Display success message
            Toast.makeText(getApplicationContext(), "Branch Added Successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Display error message if input is not valid
            Toast.makeText(getApplicationContext(), "Please fill out all fields correctly", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidInput() {
        // Get user input
        String branchCode = textBranchCode.getText().toString().trim();
        String branchName = textBranchName.getText().toString().trim();

        // Check if branch code and branch name are not empty
        return !TextUtils.isEmpty(branchCode) && !TextUtils.isEmpty(branchName);
    }

}