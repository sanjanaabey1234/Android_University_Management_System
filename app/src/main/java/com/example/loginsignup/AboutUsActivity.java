package com.example.loginsignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutUsActivity extends AppCompatActivity {
    private Button buttonNewBranches;
    private ImageView buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //Button
        buttonBack=findViewById(R.id.buttonBack);
        buttonNewBranches=findViewById(R.id.buttonNewBranches);

        // Get the unique index number from the intent(user)
        int indexNumber = getIntent().getIntExtra("INDEX_user", 4);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(indexNumber==2) {
                    Intent intent = new Intent(AboutUsActivity.this, UserDashboard.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(AboutUsActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        buttonNewBranches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AboutUsActivity.this,DisplayBranchDetails.class);
                startActivity(intent);
            }
        });

        // Retrieve the TextViews and ImageViews
        TextView aboutUsTitle = findViewById(R.id.aboutUsTitle);
        TextView aboutUsDescription = findViewById(R.id.aboutUsDescription);


        // Set the About Us details
        aboutUsTitle.setText("About Us");
        aboutUsDescription.setText("Colombo City Campus is a leading institution providing higher education in Colombo, Sri Lanka. We offer a wide range of programs in various fields including Business, IT, Engineering, and more. Our mission is to provide quality education and empower students to excel in their chosen careers.");


    }
}