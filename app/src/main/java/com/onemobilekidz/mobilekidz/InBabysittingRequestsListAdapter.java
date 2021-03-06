package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.onemobilekidz.mobilekidz.model.InBabysittingRequestsModel;
import com.onemobilekidz.mobilekidz.model.UserModel;

import java.util.HashMap;
import java.util.Map;

/**
 * /**
 * Created by vfischer on 3/16/15.
 */
public class InBabysittingRequestsListAdapter extends FirebaseListAdapter<InBabysittingRequestsModel> {

    private static final String LOG = "BSRListAdapter";

    public InBabysittingRequestsListAdapter(Context context, Query ref, Activity activity, int layout) {
        super(context, ref, InBabysittingRequestsModel.class, layout, activity);
        Log.v(LOG, "I'm here");
        System.out.println(InBabysittingRequestsModel.class);
    }

    @Override
    protected void populateView(final View view, final InBabysittingRequestsModel babysittingRequestObj, final int i) {
        final String requestor;
        final String requestorName;
        final int duration;
        final String jobStartTime;
        Log.v(LOG, "I'm here still");
        if (babysittingRequestObj != null) {
            requestor = babysittingRequestObj.getRequestor();
            requestorName = babysittingRequestObj.getRequestorName();
            jobStartTime = babysittingRequestObj.getJob_start_time();
            duration = babysittingRequestObj.getDuration();
            final TextView babysitterNameText = (TextView) view.findViewById(R.id.babySitterName);
            babysitterNameText.setText(requestorName);

            final TextView jobStartTimeText = (TextView) view.findViewById(R.id.requestDate);
            jobStartTimeText.setText(jobStartTime);

            final TextView jobEndTimeText = (TextView) view.findViewById(R.id.requestEndTime);
            jobEndTimeText.setText(duration + " hours");

            Log.v(LOG, "requestor: " + requestor);
            Log.v(LOG, " requestorName: " + requestorName + " jobstartTime: " + jobStartTime + " jobEndTime: " + duration);

            Button acceptJobButton = (Button) view.findViewById(R.id.acceptJobButton);
            Button rejectJobButton = (Button) view.findViewById(R.id.rejectJobButton);

            acceptJobButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    // Records the job in the My Schedule page
                    Log.v(LOG, "requestor: " + requestor + " pos: " + i);
                    Firebase outRef = new Firebase(Constants.FIREBASE_URL).child("my_schedule").child(UserModel.getCurrentUser().getUserId()).child(babysittingRequestObj.getId());
                    Map<String, Object> outBabysittingRequests = new HashMap<String, Object>();
                    outBabysittingRequests.put("job_start_time", jobStartTime);
                    outBabysittingRequests.put("duration", duration);
                    outBabysittingRequests.put("requestor", requestor);
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

                    //Remove the requests from incoming so that it won't show up again.
                    Log.v(LOG, "requestor: " + requestor + " pos: " + i);
                    new Firebase(Constants.FIREBASE_URL).child("incoming_babysitting_requests").child(UserModel.getCurrentUser().getUserId()).child(babysittingRequestObj.getId()).removeValue();
                    Toast.makeText(v.getContext(), "Job Accepted", Toast.LENGTH_SHORT).show();

                    // Subtract points from the requester.

                    Log.v(LOG, "Subtracting points from the requestor");
                    Firebase pointRef = new Firebase(Constants.FIREBASE_URL).child("points").child(requestor).child(babysittingRequestObj.getId());
                    Map<String, Object> points = new HashMap<String, Object>();
                    points.put("points", duration * -1);
                    pointRef.setValue(points, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (firebaseError != null) {
                                Log.v(LOG, "Points could not be updated. " + firebaseError.getMessage());
                            } else {
                                Log.v(LOG, "Points " + duration + " was subtracted from " + requestor + ".");
                            }
                        }
                    });

                    // Add points to the requestee.
                    Log.v(LOG, "Adding points to the requestee");
                    Firebase pointRef1 = new Firebase(Constants.FIREBASE_URL).child("points").child(UserModel.getCurrentUser().getUserId()).child(babysittingRequestObj.getId());
                    Map<String, Object> points1 = new HashMap<String, Object>();
                    points1.put("points", duration);
                    pointRef1.setValue(points1, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (firebaseError != null) {
                                Log.v(LOG, "Points could not be updated. " + firebaseError.getMessage());
                            } else {
                                Log.v(LOG, "Points " + duration + " was added to " + UserModel.getCurrentUser().getUserId() + ".");
                            }
                        }
                    });


                    //Send a message to the requestor letting them know that the request has been accepted.
                    Firebase messageRef = new Firebase(Constants.FIREBASE_URL).child("messages").child(requestor);
                    Map<String, String> message = new HashMap<String, String>();
                    message.put("sender", UserModel.getCurrentUser().getUserId());
                    message.put("message", "I just accepted your request for " + jobStartTime + ".");
                    Firebase ref = messageRef.push();
                    ref.setValue(message,
                            new Firebase.CompletionListener() {
                                @Override
                                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                    if (firebaseError != null) {
                                        System.out.println("Message could not be saved. " + firebaseError.getMessage());
                                    } else {
                                        Toast.makeText(view.getContext(), "Message Sent", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

            });

            rejectJobButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    //Remove the requests from incoming so that it won't show up again.
                    Log.v(LOG, "requestor: " + requestor + " pos: " + i);
                    new Firebase(Constants.FIREBASE_URL).child("incoming_babysitting_requests").child(UserModel.getCurrentUser().getUserId()).child(babysittingRequestObj.getId()).removeValue();
                    Toast.makeText(v.getContext(), "Job Rejected", Toast.LENGTH_SHORT).show();

                    //Send a message to the requestor letting them know that the request cannot be committed to.
                    Firebase messageRef = new Firebase(Constants.FIREBASE_URL).child("messages").child(requestor);
                    Map<String, String> message = new HashMap<String, String>();
                    message.put("sender", UserModel.getCurrentUser().getUserId());
                    message.put("message", "Sorry I can't babysit on " + jobStartTime + ".");
                    Firebase ref = messageRef.push();
                    ref.setValue(message,
                            new Firebase.CompletionListener() {
                                @Override
                                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                    if (firebaseError != null) {
                                        System.out.println("Message could not be saved. " + firebaseError.getMessage());
                                    } else {
                                        Toast.makeText(view.getContext(), "Message Sent", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
        }

    }

}