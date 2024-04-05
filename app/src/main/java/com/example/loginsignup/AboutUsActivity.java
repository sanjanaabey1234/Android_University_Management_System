package com.example.loginsignup;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        // Retrieve the TextViews and ImageViews
        TextView aboutUsTitle = findViewById(R.id.aboutUsTitle);
        TextView aboutUsDescription = findViewById(R.id.aboutUsDescription);


        // Set the About Us details
        aboutUsTitle.setText("About Us");
        aboutUsDescription.setText("Colombo City Campus is a leading institution providing higher education in Colombo, Sri Lanka. We offer a wide range of programs in various fields including Business, IT, Engineering, and more. Our mission is to provide quality education and empower students to excel in their chosen careers.");


    }
}
