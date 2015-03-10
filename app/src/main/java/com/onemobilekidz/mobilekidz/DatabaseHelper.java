package com.onemobilekidz.mobilekidz;

/**
 * Created by vfischer on 3/10/15.
 */

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "babysitting";

    // Table Names
    private static final String TABLE_REQUESTS = "requests";
    private static final String TABLE_FRIENDS = "friends";
    private static final String TABLE_FRIEND_REQUESTS = "friend_requests";
    private static final String TABLE_MESSAGES = "messages";
    private static final String TABLE_POINTS = "points";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_UPDATED_AT = "updated_at";
    private static final String KEY_STATUS = "status";
    private static final String KEY_FRIEND_NAME = "friend_name";

    // REQUESTS Table - column names
    private static final String KEY_BABYSITTER_ID = "babysitter_id";
    // FRIENDS table create statement
    private static final String CREATE_TABLE_FRIENDS = "CREATE TABLE "
            + TABLE_FRIENDS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BABYSITTER_ID + " INTEGER,"
            + KEY_FRIEND_NAME + " STRING,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    // FRIENDS Table - column names
    private static final String KEY_REQUEST_DATE = "request_date";
    // Table Create Statements
    // REQUESTS table create statement
    private static final String CREATE_TABLE_REQUESTS = "CREATE TABLE "
            + TABLE_REQUESTS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BABYSITTER_ID + " INTEGER,"
            + KEY_REQUEST_DATE + "DATETIME,"
            + KEY_CREATED_AT + " DATETIME,"
            + KEY_UPDATED_AT + " DATETIME,"
            + KEY_STATUS + "STRING" + ")";
    // FRIEND_REQUESTS Table - column names
    private static final String KEY_OUTOFNETWORKUSERS = "out_of_network_users";
    // FRIEND_REQUESTS table create statement
    private static final String CREATE_TABLE_FRIEND_REQUESTS = "CREATE TABLE "
            + TABLE_FRIEND_REQUESTS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_OUTOFNETWORKUSERS + " STRING,"
            + KEY_CREATED_AT + " DATETIME,"
            + KEY_UPDATED_AT + " DATETIME,"
            + KEY_STATUS + "STRING" + ")";
    // MESSAGES Table - column names
    private static final String KEY_MESSAGE = "message";
    // MESSAGES table create statement
    private static final String CREATE_TABLE_MESSAGES = "CREATE TABLE "
            + TABLE_MESSAGES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BABYSITTER_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME,"
            + KEY_UPDATED_AT + " DATETIME,"
            + KEY_MESSAGE + " STRING,"
            + KEY_STATUS + "STRING" + ")";
    // POINTS Table - column names
    private static final String KEY_POINTS = "points";
    private static final String KEY_REQUEST_ID = "request_id";
    // POINTS table create statement
    private static final String CREATE_TABLE_POINTS = "CREATE TABLE "
            + TABLE_POINTS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_CREATED_AT + " DATETIME,"
            + KEY_REQUEST_ID + "INTEGER" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_REQUESTS);
        db.execSQL(CREATE_TABLE_FRIEND_REQUESTS);
        db.execSQL(CREATE_TABLE_MESSAGES);
        db.execSQL(CREATE_TABLE_POINTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUESTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND_REQUESTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POINTS);

        // create new tables
        onCreate(db);
    }


}
