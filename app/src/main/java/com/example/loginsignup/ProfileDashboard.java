package com.example.loginsignup;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class ProfileDashboard extends AppCompatActivity {

    private static final int GALLERY_INTENT_CODE = 1023;
    TextView fullName, email, phone, verifyMsg, address, city, dob, nic, gender;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button resendCode;
    Button resetPassLocal, changeProfileImage;
    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_dashboard);

        fullName = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        phone = findViewById(R.id.profilePhone);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        dob = findViewById(R.id.dob);
        nic = findViewById(R.id.nic);
        gender = findViewById(R.id.gender);
        resetPassLocal = findViewById(R.id.resetPasswordLocal);
        profileImage = findViewById(R.id.profileImage);
        changeProfileImage = findViewById(R.id.changeProfile);

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

        resetPassLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Are you sure you want to reset your password?");
                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fAuth.sendPasswordResetEmail(user.getEmail()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ProfileDashboard.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileDashboard.this, "Failed to send reset email: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", null);
                passwordResetDialog.create().show();
            }
        });

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageData = data.getData();
            uploadImage(imageData);
        }
    }

    private void uploadImage(Uri imageData) {
        final StorageReference imageReference = storageReference.child("users/" + userId + "/profile.jpg");
        imageReference.putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });
                Toast.makeText(ProfileDashboard.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileDashboard.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}

