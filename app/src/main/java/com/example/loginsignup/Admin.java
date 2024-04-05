package com.example.loginsignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Admin extends AppCompatActivity {
    CardView clothingCard,studentCard,branchCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        clothingCard = findViewById(R.id.clothingCard);
        studentCard =findViewById(R.id.studentView);
        branchCard=findViewById(R.id.branchCard);

        clothingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, AddCourseActivity.class);
                startActivity(intent);
            }
        });


        studentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, DisplayCourses.class);
                startActivity(intent);
            }
        });


        branchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, AddBranchActivity.class);
                startActivity(intent);
            }
        });

    }
}