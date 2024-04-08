package com.example.loginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddBranchActivity extends AppCompatActivity {

    private EditText editTextBranchCode;
    private EditText editTextBranchName;
    private Button buttonAddBranch;
    private ImageView buttonBack;
    private DBHelperBranch dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);

        // Initialize UI elements
        editTextBranchCode = findViewById(R.id.editTextBranchCode);
        editTextBranchName = findViewById(R.id.editTextBranchName);
        buttonAddBranch = findViewById(R.id.buttonAddBranch);
        buttonBack=findViewById(R.id.buttonBack);

        // Initialize DBHelper
        dbHelper = new DBHelperBranch(this);

        // Set click listener for the button
        buttonAddBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBranch();

            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddBranchActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to add branch
    private void addBranch() {
        // Retrieve branch details from EditText fields
        String branchCode = editTextBranchCode.getText().toString().trim();
        String branchName = editTextBranchName.getText().toString().trim();

        // Check if branch details are empty
        if (branchCode.isEmpty() || branchName.isEmpty()) {
            // Show toast message if any field is empty
            showToast("Please enter branch details");
        } else {
            // Call DBHelper to add branch
            dbHelper.addBranch(branchCode, branchName);

            showToast("Branch added successfully");
            // Clear EditText fields after successful addition
            clearFields();
        }

    }

    // Method to display toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Method to clear EditText fields
    private void clearFields() {
        editTextBranchCode.setText("");
        editTextBranchName.setText("");
    }
}