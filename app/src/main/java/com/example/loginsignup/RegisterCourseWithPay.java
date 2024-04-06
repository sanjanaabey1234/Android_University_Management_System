package com.example.loginsignup;

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

public class RegisterCourseWithPay extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_course_with_pay);


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

    }



}

