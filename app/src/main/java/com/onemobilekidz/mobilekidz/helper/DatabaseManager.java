package com.onemobilekidz.mobilekidz.helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.onemobilekidz.mobilekidz.Friends;
import com.onemobilekidz.mobilekidz.model.FriendsModel;
import com.onemobilekidz.mobilekidz.model.RequestsModel;

import java.sql.SQLException;


/**
 * Created by vfischer on 3/11/15.
 */
public class DatabaseManager {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    // Database Version
    private static final int DB_VERSION = 11;
    // Database Name
    private static final String DB_NAME = "babysitting";
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
            + TABLE_FRIENDS + "(" + KEY_ID + " integer primary key autoincrement not null,"
            + KEY_BABYSITTER_ID + " integer not null,"
            + KEY_FRIEND_NAME + " text not null,"
            + KEY_CREATED_AT + " text not null" + ")";
    // FRIENDS Table - column names
    private static final String KEY_REQUEST_DATE = "request_date";
    // Table Create Statements
    // REQUESTS table create statement
    private static final String CREATE_TABLE_REQUESTS = "CREATE TABLE "
            + TABLE_REQUESTS + " (" + KEY_ID + " integer primary key autoincrement not null,"
            + KEY_BABYSITTER_ID + " integer not null,"
            + KEY_REQUEST_DATE + " text not null,"
            + KEY_CREATED_AT + " text not null,"
            + KEY_UPDATED_AT + " text not null,"
            + KEY_STATUS + " text not null" + ")";
    // FRIEND_REQUESTS Table - column names
    private static final String KEY_OUT_OF_NETWORK_USERS = "out_of_network_users";
    // FRIEND_REQUESTS table create statement
    private static final String CREATE_TABLE_FRIEND_REQUESTS = "CREATE TABLE "
            + TABLE_FRIEND_REQUESTS + "(" + KEY_ID + " integer primary key autoincrement not null,"
            + KEY_OUT_OF_NETWORK_USERS + " text not null,"
            + KEY_CREATED_AT + " text not null,"
            + KEY_UPDATED_AT + " text not null,"
            + KEY_STATUS + " text not null" + ")";
    // MESSAGES Table - column names
    private static final String KEY_MESSAGE = "message";
    // MESSAGES table create statement
    private static final String CREATE_TABLE_MESSAGES = "CREATE TABLE "
            + TABLE_MESSAGES + "(" + KEY_ID + " integer primary key autoincrement not null,"
            + KEY_BABYSITTER_ID + " integer not null,"
            + KEY_MESSAGE + " text not null,"
            + KEY_CREATED_AT + " text not null,"
            + KEY_UPDATED_AT + " text not null,"
            + KEY_STATUS + " text not null" + ")";
    // POINTS Table - column names
    private static final String KEY_POINTS = "points";
    private static final String KEY_REQUEST_ID = "request_id";
    // POINTS table create statement
    private static final String CREATE_TABLE_POINTS = "CREATE TABLE "
            + TABLE_POINTS + "(" + KEY_ID + " integer primary key autoincrement not null,"
            + KEY_POINTS + " integer not null,"
            + KEY_CREATED_AT + " text not null,"
            + KEY_REQUEST_ID + " integer not null" + ")";
    // Reference to the database manager class
    private SQLiteDatabase db;
    private Context context;

    public DatabaseManager(Context context) {
        this.context = context;

        // create or open the database
        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);

        try {
            Log.v(LOG, "Writing to the database.");
            this.db = helper.getWritableDatabase();
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void addRowRequests(RequestsModel requestsObj) {
        ContentValues values = prepareRequestsData(requestsObj);
        // ask the database object to insert the new data
        try {
            Log.v("DB", "Inserting requests data");
            db.insert(TABLE_REQUESTS, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString()); // prints the error message to
            // the log
            e.printStackTrace(); // prints the stack trace to the log
        }
    }

    private ContentValues prepareRequestsData(RequestsModel requestsObj) {

        ContentValues values = new ContentValues();
        values.put(KEY_BABYSITTER_ID, requestsObj.getBabysitterId());
        values.put(KEY_REQUEST_DATE, requestsObj.getRequestDate());
        values.put(KEY_CREATED_AT, "CreateAt");
        values.put(KEY_UPDATED_AT, "UpdatedAt");
        values.put(KEY_STATUS, requestsObj.getRequestStatus());
        return values;

    }

    public RequestsModel getRowAsObject(int rowID) {

        RequestsModel rowRequestsObj = new RequestsModel();
        Cursor cursor;

        try {
            cursor = db.query(TABLE_REQUESTS, new String[]{KEY_ID,
                            KEY_STATUS}, KEY_ID + "=" + rowID, null, null, null, null
            );
            cursor.moveToFirst();
            prepareSendRequestsObject(rowRequestsObj, cursor);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
            e.printStackTrace();
        }

        return rowRequestsObj;


    }

    private void prepareSendRequestsObject(RequestsModel rowObj, Cursor cursor) {
        rowObj.setRequestStatus(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)));
    }

    public void addRowFriends(FriendsModel friendsObj) {
        ContentValues values = prepareFriendsData(friendsObj);
        // ask the database object to insert the new data
        try {
            Log.v("DB", "Inserting friends data");
            db.insert(TABLE_FRIENDS, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString()); // prints the error message to
            // the log
            e.printStackTrace(); // prints the stack trace to the log
        }
    }

    private ContentValues prepareFriendsData(FriendsModel friendsObj) {

        ContentValues values = new ContentValues();
        values.put(KEY_BABYSITTER_ID, friendsObj.getBabysitterId());
        values.put(KEY_FRIEND_NAME, friendsObj.getFriendName());
        values.put(KEY_CREATED_AT, "CreatedAt");
        return values;

    }

    // the beginnings our SQLiteOpenHelper class
    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {


        public CustomSQLiteOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {


            try {
                Log.v(LOG, "Creating table REQUESTS");
                db.execSQL(CREATE_TABLE_REQUESTS);
                Log.v(LOG, "Creating table friends" + CREATE_TABLE_FRIENDS);
                db.execSQL(CREATE_TABLE_FRIENDS);
                db.execSQL(CREATE_TABLE_FRIEND_REQUESTS);
                db.execSQL(CREATE_TABLE_MESSAGES);
                db.execSQL(CREATE_TABLE_POINTS);
            } catch (Exception e) {
                Log.e("DB ERROR", e.toString()); // prints the error message to
                // the log
                e.printStackTrace(); // prints the stack trace to the log
            }
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

}
