package com.onemobilekidz.mobilekidz;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.Activity;
import android.widget.*;

import com.onemobilekidz.mobilekidz.helper.DatabaseManager;
import com.onemobilekidz.mobilekidz.model.FriendRequestsModel;
import com.onemobilekidz.mobilekidz.model.FriendsModel;
import com.onemobilekidz.mobilekidz.model.MessagesModel;
import com.onemobilekidz.mobilekidz.model.PointsModel;
import com.onemobilekidz.mobilekidz.model.RequestsModel;

import java.util.*;


/* Part of this code is copied from Sheusi, J. C. (2012). Android™ Application Development
for Java® Programmers. Course Technology PTR. */

public class MakeRequests extends Activity {

    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
    static final int BABYSITTER_DIALOG_ID = 2;

    String mChosenDateTime;
    private TextView mTimeDisplay;
    private Button mPickTime;
    private Button mPickDate;
    private Button mChooseBabysitter;

    private int mHour;
    private int mMinute;
    // the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    updateDisplay();
                }
            };
    private int mYear;
    private int mMonthOfYear;
    private int mDayOfMonth;
    // The overridden method shown below gets invoked when
    //'showDialog()' is called inside the 'onClick()' method defined
    // for handling the click event of the button 'change the time'
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonthOfYear = monthOfYear + 1;
                    mDayOfMonth = dayOfMonth;
                    updateDisplay();

                }
            };
    private int babysitterId = 1;

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_requests);

        // capture our View elements
        mTimeDisplay = (TextView) findViewById(R.id.myChosenDate);
        mPickTime = (Button) findViewById(R.id.pickTime);
        mPickDate = (Button) findViewById(R.id.pickDate);
        mChooseBabysitter = (Button) findViewById(R.id.chooseBabysitter);

        // add a click listener to the button
        mPickTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        mChooseBabysitter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(BABYSITTER_DIALOG_ID);
            }
        });

        // get the current time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // get the current date
        mYear = c.get(Calendar.YEAR);
        mMonthOfYear = c.get(Calendar.MONTH) + 1;
        mDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        // display the current date
        updateDisplay();


    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, mHour, mMinute, false);
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener, mYear, mMonthOfYear, mDayOfMonth);
            case BABYSITTER_DIALOG_ID:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Pick a Babysitter")
                        .setAdapter(new FriendListAdapter(this), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                babysitterId = which + babysitterId;
                                Log.v("LOG", String.valueOf(babysitterId));
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.v("LOG", "I'm being cancelled.");
                            }
                        });


                return builder.create();
        }
        return null;
    }

    // updates the time we display in the TextView
    private void updateDisplay() {
        mChosenDateTime = new StringBuilder()
                .append(pad(mYear)).append("-")
                .append(pad(mMonthOfYear)).append("-")
                .append(pad(mDayOfMonth)).append(" ")
                .append(pad(mHour)).append(":")
                .append(pad(mMinute)).toString();
        mTimeDisplay.setText(mChosenDateTime
        );
    }

    public void submitBabysittingRequest(View view) {
        DatabaseManager dbManager = new DatabaseManager(this);
        RequestsModel requestsModel = new RequestsModel(babysitterId, mChosenDateTime, "pending", "sent");

        // babysitterId, String requestDate, String requestStatus
        dbManager.addRowRequests(requestsModel);

        Intent intent = new Intent(this, Requests.class);
        startActivity(intent);
    }

    public void viewPoints(View view) {
        Intent intent = new Intent(this, Points.class);
        startActivity(intent);
    }




}
