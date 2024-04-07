package com.example.loginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayBranchDetails extends AppCompatActivity {
    TextView textViewBranchDetails;
    ImageView buttonBack;

    DBHelperBranch branchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_branch_details);

        textViewBranchDetails = findViewById(R.id.textViewBranchDetails);
        branchHelper = new DBHelperBranch(this);
        buttonBack=findViewById(R.id.buttonBack);

        displayBranchDetails();



        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DisplayBranchDetails.this,AboutUsActivity.class);
                startActivity(intent);
            }
        });



    }

    private void displayBranchDetails() {
        // Retrieve all branch details from the database
        Cursor cursor = branchHelper.getAllBranches();

        if (cursor != null && cursor.moveToFirst()) {
            StringBuilder branchDetails = new StringBuilder();

            do {
                String branchCode = cursor.getString(cursor.getColumnIndex(branchHelper.COL_BRANCH_CODE));
                String branchName = cursor.getString(cursor.getColumnIndex(branchHelper.COL_BRANCH_NAME));

                branchDetails.append("Branch Code: ").append(branchCode).append("\n");
                branchDetails.append("Branch Name: ").append(branchName).append("\n\n");
            } while (cursor.moveToNext());

            // Display all branch details
            textViewBranchDetails.setText(branchDetails.toString());
        } else {
            // No branches found
            Toast.makeText(this, "No branch details found.", Toast.LENGTH_SHORT).show();
        }

        // Close the cursor
        if (cursor != null)
            cursor.close();
    }
}