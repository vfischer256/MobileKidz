package com.onemobilekidz.mobilekidz.helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.onemobilekidz.mobilekidz.FriendRequests;
import com.onemobilekidz.mobilekidz.Friends;
import com.onemobilekidz.mobilekidz.Messages;
import com.onemobilekidz.mobilekidz.model.FriendRequestsModel;
import com.onemobilekidz.mobilekidz.model.FriendsModel;
import com.onemobilekidz.mobilekidz.model.MessagesModel;
import com.onemobilekidz.mobilekidz.model.PointsModel;
import com.onemobilekidz.mobilekidz.model.RequestsModel;

import java.util.ArrayList;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * Created by vfischer on 3/11/15.
 */
public class DatabaseManager {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    // Database Version
    private static final int DB_VERSION = 15;
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
    // FRIENDS table create statement
    private static final String CREATE_TABLE_FRIENDS = "CREATE TABLE "
            + TABLE_FRIENDS + "(" + KEY_ID + " integer primary key autoincrement not null,"
            + KEY_FRIEND_NAME + " text not null,"
            + KEY_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";
    // REQUESTS Table - column names
    private static final String KEY_BABYSITTER_ID = "babysitter_id";
    // FRIENDS Table - column names
    private static final String KEY_REQUEST_DATE = "request_date";
    // Table Create Statements
    // REQUESTS table create statement
    private static final String CREATE_TABLE_REQUESTS = "CREATE TABLE "
            + TABLE_REQUESTS + " (" + KEY_ID + " integer primary key autoincrement not null,"
            + KEY_BABYSITTER_ID + " integer not null,"
            + KEY_REQUEST_DATE + " text not null,"
            + KEY_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + KEY_UPDATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
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
        values.put(KEY_CREATED_AT, getDateTime());
        values.put(KEY_UPDATED_AT, getDateTime());
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
        values.put(KEY_FRIEND_NAME, friendsObj.getFriendName());
        values.put(KEY_CREATED_AT, getDateTime());
        return values;

    }

    public FriendsModel getFriendsRowAsObject(int rowID) {

        FriendsModel rowFriendsObj = new FriendsModel();
        Cursor cursor;

        try {
            cursor = db.query(TABLE_FRIENDS, new String[]{KEY_ID,
                            KEY_FRIEND_NAME}, KEY_ID + "=" + rowID, null, null, null, null
            );
            cursor.moveToFirst();
            prepareSendFriendsObject(rowFriendsObj, cursor);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
            e.printStackTrace();
        }

        return rowFriendsObj;

    }

    // Returns all the rows data in form of ContactModel object list

    public ArrayList<FriendsModel> getAllData() {

        ArrayList<FriendsModel> allRowsObj = new ArrayList<FriendsModel>();
        Cursor cursor;
        FriendsModel rowFriendsObj;

        String[] columns = new String[]{KEY_ID, KEY_FRIEND_NAME};

        try {

            cursor = db
                    .query(TABLE_FRIENDS, columns, null, null, null, null, null);
            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    rowFriendsObj = new FriendsModel();
                    rowFriendsObj.setId(cursor.getInt(0));
                    prepareSendFriendsObject(rowFriendsObj, cursor);
                    allRowsObj.add(rowFriendsObj);

                } while (cursor.moveToNext()); // try to move the cursor's
                // pointer forward one position.
            }
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return allRowsObj;

    }

    private void prepareSendFriendsObject(FriendsModel rowObj, Cursor cursor) {
        rowObj.setFriendName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_FRIEND_NAME)));
    }

    public void addRowFriendRequests(FriendRequestsModel friendRequestsObj) {
        ContentValues values = prepareFriendRequestsData(friendRequestsObj);
        // ask the database object to insert the new data
        try {
            Log.v("DB", "Inserting friend requests data");
            db.insert(TABLE_FRIEND_REQUESTS, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString()); // prints the error message to
            // the log
            e.printStackTrace(); // prints the stack trace to the log
        }

    }

    private ContentValues prepareFriendRequestsData(FriendRequestsModel friendRequestsObj) {

        ContentValues values = new ContentValues();
        values.put(KEY_OUT_OF_NETWORK_USERS, friendRequestsObj.getOutOfNetworkUsers());
        values.put(KEY_CREATED_AT, "CreateAt");
        values.put(KEY_UPDATED_AT, "UpdatedAt");
        values.put(KEY_STATUS, friendRequestsObj.getStatus());
        return values;

    }

    public void addRowMessages(MessagesModel messagesObj) {
        ContentValues values = prepareMessagesData(messagesObj);
        // ask the database object to insert the new data
        try {
            Log.v("DB", "Inserting messages data");
            db.insert(TABLE_MESSAGES, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString()); // prints the error message to
            // the log
            e.printStackTrace(); // prints the stack trace to the log
        }

    }

    private ContentValues prepareMessagesData(MessagesModel messagesObj) {

        ContentValues values = new ContentValues();
        values.put(KEY_BABYSITTER_ID, messagesObj.getBabysitterId());
        values.put(KEY_MESSAGE, messagesObj.getMessage());
        values.put(KEY_CREATED_AT, "CreateAt");
        values.put(KEY_UPDATED_AT, "UpdatedAt");
        values.put(KEY_STATUS, messagesObj.getStatus());
        return values;

    }

    public void addRowPoints(PointsModel pointsObj) {
        ContentValues values = preparePointsData(pointsObj);
        // ask the database object to insert the new data
        try {
            Log.v("DB", "Inserting points data");
            db.insert(TABLE_POINTS, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString()); // prints the error message to
            // the log
            e.printStackTrace(); // prints the stack trace to the log
        }

    }

    private ContentValues preparePointsData(PointsModel pointsObj) {

        ContentValues values = new ContentValues();
        values.put(KEY_POINTS, pointsObj.getPoints());
        values.put(KEY_CREATED_AT, "CreateAt");
        values.put(KEY_REQUEST_ID, pointsObj.getRequestId());
        return values;

    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // the beginnings our SQLiteOpenHelper class
    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {


        public CustomSQLiteOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {


            try {
                Log.v(LOG, "Creating tables");
                db.execSQL(CREATE_TABLE_REQUESTS);
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
