package com.onemobilekidz.mobilekidz;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.widget.*;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.onemobilekidz.mobilekidz.model.FriendsModel;
import com.onemobilekidz.mobilekidz.model.PointsModel;
import com.onemobilekidz.mobilekidz.model.UserModel;

import org.w3c.dom.Text;

import java.util.*;


/* Part of this code is copied from Sheusi, J. C. (2012). Android™ Application Development
for Java® Programmers. Course Technology PTR. */

public class MakeRequests extends Activity {

    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
    static final int BABYSITTER_DIALOG_ID = 2;
    static final int END_TIME_DIALOG_ID = 3;
    private static final String LOG = "MakeRequests";
    private static final String FIREBASE_URL = "https://crackling-heat-9656.firebaseio.com/";

    int points;

    public int getPoints() {
        return points;
    }

    String mChosenDateTime;
    int mDuration = 1;

    private static TextView tv;
    static Dialog d;

    private Firebase mFirebaseRef;
    private FriendListAdapter friendListAdapter;
    private TextView mTimeDisplay;
    private TextView pointsText;

    private Button mPickTime;
    private Button mPickDate;
    private Button duration;


    private Button mChooseBabysitter;

    private int sum;

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


    private String babysitterId;

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


        Firebase.setAndroidContext(this);
        try {
            mFirebaseRef = new Firebase(FIREBASE_URL).child("friends").child(UserModel.getCurrentUser().getUserId());
        } catch (Exception e) {
            Log.e(LOG, e.toString());
        }

        pointsText = (TextView) findViewById(R.id.points);
        displayPoints();

        // capture our View elements
        mTimeDisplay = (TextView) findViewById(R.id.myChosenDate);
        mPickTime = (Button) findViewById(R.id.pickTime);
        mPickDate = (Button) findViewById(R.id.pickDate);
        duration = (Button) findViewById(R.id.duration);
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
        duration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(END_TIME_DIALOG_ID);
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
                try {
                    friendListAdapter = new FriendListAdapter(getApplicationContext(), mFirebaseRef, this, R.layout.babysitter_list_row);
                } catch (Exception e) {
                    Log.e(LOG, e.toString());
                }
                builder.setTitle("Pick a Babysitter");

                try {
                    if (friendListAdapter != null) {
                        builder.setAdapter(friendListAdapter, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.v("LOG", "Position: " + String.valueOf(which) + "Count: " + friendListAdapter.getCount());
                                FriendsModel model = friendListAdapter.getItem(which);
                                Log.v("LOG", "MODEL " + model.getFriend().getDisplayName() + model.getId());
                                babysitterId = model.getId();

                            }
                        });

                    }
                } catch (Exception e) {
                    Log.e(LOG, e.toString());
                }

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.v("LOG", "I'm being cancelled.");
                    }
                });


                return builder.create();
            case END_TIME_DIALOG_ID:
                final CharSequence[] items = {"1", "2", "3", "4", "5", "6"};

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setTitle("Choose number of hours.");
                dialogBuilder.setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        mDuration = which + 1;
                        Log.v(LOG, String.valueOf(mDuration));
                        updateDisplay();
                    }

                });
                dialogBuilder.create().show();
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

        String hour = "hour";
        if (mDuration > 1) {
            hour = "hours";
        }
        mTimeDisplay.setText("On: " + mChosenDateTime + " for: " + mDuration + " " + hour);

    }

    public void submitBabysittingRequest(final View view) {
        try {
            Firebase postRef = new Firebase(FIREBASE_URL).child("incoming_babysitting_requests").child(babysitterId);
            Map<String, Object> inBabysitingRequests = new HashMap<String, Object>();
            inBabysitingRequests.put("job_start_time", mChosenDateTime);
            inBabysitingRequests.put("duration", mDuration);
            inBabysitingRequests.put("requestor", UserModel.getCurrentUser().getUserId());
            Firebase ref = postRef.push();
            ref.setValue(inBabysitingRequests,
                    new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (firebaseError != null) {
                                Toast.makeText(view.getContext(), "Job could not be sent. " + firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(view.getContext(), "Job requested", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            String postId = ref.getKey();
            //outgoing babysitting requests

            Firebase outRef = new Firebase(FIREBASE_URL).child("outgoing_babysitting_requests").child(UserModel.getCurrentUser().getUserId()).child(postId);
            Map<String, Object> outBabysittingRequests = new HashMap<String, Object>();
            outBabysittingRequests.put("job_start_time", mChosenDateTime);
            outBabysittingRequests.put("duration", mDuration);
            outBabysittingRequests.put("requestee", babysitterId);
            outRef.setValue(outBabysittingRequests,
                    new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (firebaseError != null) {
                                Toast.makeText(view.getContext(), "Job could not be sent. " + firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(view.getContext(), "Job requested", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


            Intent intent = new Intent(this, OutBabysittingRequests.class);
            startActivity(intent);
        } catch (Exception e) {
            Log.e(LOG, e.toString());
        }
    }

    private void displayPoints() {

        try {
            mFirebaseRef = new Firebase(FIREBASE_URL).child("points").child(UserModel.getCurrentUser().getUserId());


            mFirebaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    System.out.println(snapshot.getValue());

                    for (DataSnapshot o : snapshot.getChildren()) {
                        System.out.println("child " + o);
                        sum += ((PointsModel) o.getValue(PointsModel.class)).getPoints();
                    }
                    System.out.println(sum);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });


            pointsText.setText("You have " + sum + " points.");


        } catch (Exception e) {
            pointsText.setText("Points cannot be displayed. Not connected.");
            Log.e(LOG, e.toString());
        }


    }


}
