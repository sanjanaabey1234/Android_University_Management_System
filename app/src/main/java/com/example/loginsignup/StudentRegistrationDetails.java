package com.example.loginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

public class StudentRegistrationDetails extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    TextView fullName;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration_details);

        fullName = findViewById(R.id.profileName);
        dbHelper = new DatabaseHelper(this);

        // Retrieve data from the database
        List<String> registrationDetails = getRegistrationDetails();

        // Populate ListView with retrieved data
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, registrationDetails);
        listView.setAdapter(adapter);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        if (fAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), Login.class));
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
                    fullName.setText(documentSnapshot.getString("fName"));

                } else {
                    Log.d("ProfileDashboard", "Document does not exist");
                }
            }
        });
    }

    private List<String> getRegistrationDetails() {
        List<String> registrationDetails = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                // Append each column value to the list
                String courseName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_COURSE_NAME));
                String fee = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FEE));
                String branch = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_BRANCH));
                String startingDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_STARTING_DATE));
                String duration = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DURATION));
                String cvv = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_CVV));
                String expireDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_EXPIRE_DATE));
                String cardNo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_CARD_NO));
                String fullName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FULL_NAME));
                String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_EMAIL));
                String phone = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PHONE));
                String address = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ADDRESS));
                String city = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_CITY));
                String dob = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DOB));
                String nic = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NIC));
                String gender = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_GENDER));

                // Append all details to the list
                registrationDetails.add("Course Name: " + courseName +
                        "\nFee: " + fee +
                        "\nBranch: " + branch +
                        "\nStarting Date: " + startingDate +
                        "\nDuration: " + duration +
                        "\nCVV: " + cvv +
                        "\nExpire Date: " + expireDate +
                        "\nCard Number: " + cardNo +
                        "\nFull Name: " + fullName +
                        "\nEmail: " + email +
                        "\nPhone: " + phone +
                        "\nAddress: " + address +
                        "\nCity: " + city +
                        "\nDOB: " + dob +
                        "\nNIC: " + nic +
                        "\nGender: " + gender);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return registrationDetails;
    }
}
