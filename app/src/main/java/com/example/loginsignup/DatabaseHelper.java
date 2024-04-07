package com.example.loginsignup;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "courseRegistrationDATABASE";
    private static final String TABLE_NAME = "courseRegistration";
    private static final String COL_ID = "ID";
    private static final String COL_COURSE_NAME = "courseName";
    private static final String COL_FEE = "fee";
    private static final String COL_BRANCH = "branch";
    private static final String COL_STARTING_DATE = "startingDate";
    private static final String COL_DURATION = "duration";
    private static final String COL_CVV = "cvv";
    private static final String COL_EXPIRE_DATE = "expireDate";
    private static final String COL_CARD_NO = "cardNo";

    private static final String COL_FULL_NAME = "fullName";
    private static final String COL_EMAIL = "email";
    private static final String COL_PHONE = "phone";
    private static final String COL_ADDRESS = "address";
    private static final String COL_CITY = "city";
    private static final String COL_DOB = "dob";
    private static final String COL_NIC = "nic";
    private static final String COL_GENDER = "gender";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_COURSE_NAME + " TEXT, " +
                COL_FEE + " TEXT, " +
                COL_BRANCH + " TEXT, " +
                COL_STARTING_DATE + " TEXT, " +
                COL_DURATION + " TEXT, " +
                COL_CVV + " TEXT, " +
                COL_EXPIRE_DATE + " TEXT, " +
                COL_FULL_NAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PHONE + " TEXT, " +
                COL_ADDRESS + " TEXT, " +
                COL_CITY + " TEXT, " +
                COL_DOB + " TEXT, " +
                COL_NIC + " TEXT, " +
                COL_GENDER + " TEXT, " +
                COL_CARD_NO + " TEXT)";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addCourseRegistration(String courseName, String fee, String branch, String startingDate, String duration,
                                      String cvv, String expireDate, String cardNo, String fullName, String email, String phone, String address, String city, String dob, String nic, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_COURSE_NAME, courseName);
        values.put(COL_FEE, fee);
        values.put(COL_BRANCH, branch);
        values.put(COL_STARTING_DATE, startingDate);
        values.put(COL_DURATION, duration);
        values.put(COL_CVV, cvv);
        values.put(COL_EXPIRE_DATE, expireDate);
        values.put(COL_CARD_NO, cardNo);
        values.put(COL_FULL_NAME, fullName);
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        values.put(COL_ADDRESS, address);
        values.put(COL_CITY, city);
        values.put(COL_DOB, dob);
        values.put(COL_NIC, nic);
        values.put(COL_GENDER, gender);




        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }
}
