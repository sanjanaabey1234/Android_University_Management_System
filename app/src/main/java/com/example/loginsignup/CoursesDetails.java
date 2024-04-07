package com.example.loginsignup;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class CoursesDetails extends AppCompatActivity {

    private static final int GALLERY_INTENT_CODE = 1023;
    TextView fullName, email, phone, verifyMsg, address, city, dob, nic, gender;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageReference;

    private Spinner spinnerCourse, spinnerBranch;
    private Button search;
    private TextView courseName, fee, branch, startingDate, duration;
    private DBHelper dbHelper;

    private Button courseRegisterButton;
    private DatabaseHelper dbHelper2;

    private EditText cvvEditText, expireDateEditText, cardNoEditText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_details);

        fullName = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        phone = findViewById(R.id.profilePhone);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        dob = findViewById(R.id.dob);
        nic = findViewById(R.id.nic);
        gender = findViewById(R.id.gender);

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


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        if (fAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), RegisterCourseWithPay.class));
            finish();
            return;
        }

        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        StorageReference profileRef = storageReference.child("users/" + userId + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    phone.setText(documentSnapshot.getString("phone"));
                    fullName.setText(documentSnapshot.getString("fName"));
                    email.setText(documentSnapshot.getString("email"));
                    address.setText(documentSnapshot.getString("address"));
                    city.setText(documentSnapshot.getString("city"));
                    dob.setText(documentSnapshot.getString("dob"));
                    nic.setText(documentSnapshot.getString("nic"));
                    gender.setText(documentSnapshot.getString("gender"));
                } else {
                    Log.d("ProfileDashboard", "Document does not exist");
                }
            }
        });


        cvvEditText = findViewById(R.id.cvv);
        expireDateEditText = findViewById(R.id.expiredate);
        cardNoEditText = findViewById(R.id.cardno);


        courseRegisterButton = findViewById(R.id.courseregister);

        dbHelper2 = new DatabaseHelper(this);

        courseRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cvv = cvvEditText.getText().toString().trim();
                String expireDate = expireDateEditText.getText().toString().trim();
                String cardNo = cardNoEditText.getText().toString().trim();

//                // Fetch user profile data
//                String fullName = fullName.getText().toString();
//                String email = email.getText().toString();
//                String phone = phone.getText().toString();
//                String address = address.getText().toString();
//                String city = city.getText().toString();
//                String dob = dob.getText().toString();
//                String nic = nic.getText().toString();
//                String gender = gender.getText().toString();

                // You should also validate the input fields here

                // Add course registration data and user profile data to the database
                long result = dbHelper2.addCourseRegistration(
                        courseName.getText().toString(),
                        fee.getText().toString(),
                        branch.getText().toString(),
                        startingDate.getText().toString(),
                        duration.getText().toString(),
                        cvv,
                        expireDate,
                        cardNo,
                        fullName.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString(),
                        address.getText().toString(),
                        city.getText().toString(),
                        dob.getText().toString(),
                        nic.getText().toString(),
                        gender.getText().toString()
                );

                if (result != -1) {
                    Toast.makeText(CoursesDetails.this, "Course registration successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CoursesDetails.this, "Failed to register for the course", Toast.LENGTH_SHORT).show();
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
