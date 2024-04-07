package com.example.loginsignup;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperBranch extends SQLiteOpenHelper {
    private static final String DB_NAME = "Branches";  // Database name
    private static final int DB_VERSION = 1;           // Database version

    // Table name and column names for BRANCH table
    private static final String TABLE_BRANCH = "BRANCH";
    private static final String COL_BRANCH_ID = "_Id";
    public static final String COL_BRANCH_NAME = "branchName";
    public static final String COL_BRANCH_CODE = "branchCode";

    // Create table query for BRANCH table
    private static final String CREATE_TABLE_BRANCH = "CREATE TABLE " + TABLE_BRANCH + "("
            + COL_BRANCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_BRANCH_CODE + " TEXT,"
            + COL_BRANCH_NAME + " TEXT);";

    public DBHelperBranch(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_BRANCH);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRANCH);
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert branch details into the database
    public void addBranch(String branchCode, String branchName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BRANCH_CODE, branchCode);
        values.put(COL_BRANCH_NAME, branchName);

        try {
            db.insert(TABLE_BRANCH, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    // Retrieve all branches from the database
    public Cursor getAllBranches() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_BRANCH, null, null, null, null, null, null);
    }
}
