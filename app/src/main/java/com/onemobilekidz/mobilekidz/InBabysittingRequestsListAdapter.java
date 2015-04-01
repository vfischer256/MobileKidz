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
 * <p/>
 * <p/>
 * This class is an example of how to use FirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class InBabysittingRequestsListAdapter extends FirebaseListAdapter<InBabysittingRequestsModel> {

    private static final String LOG = "BSRListAdapter";
    private static final String FIREBASE_URL = "https://crackling-heat-9656.firebaseio.com/";

    public InBabysittingRequestsListAdapter(Context context, Query ref, Activity activity, int layout) {
        super(context, ref, InBabysittingRequestsModel.class, layout, activity);
        Log.v(LOG, "I'm here");
        System.out.println(InBabysittingRequestsModel.class);
    }

    /**
     * Bind an instance of the <code>FriendRequestsModel</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>FriendRequestsModel</code> instance that represents the current data to bind.
     *
     * @param view                  A view instance corresponding to the layout we passed to the constructor.
     * @param babysittingRequestObj An instance representing the current state of a chat message
     */


    @Override
    protected void populateView(final View view, final InBabysittingRequestsModel babysittingRequestObj, final int i) {
        final String requestor;
        final String requestorName;
        final String jobStartTime;
        final String jobEndTime;
        Log.v(LOG, "I'm here still");
        if (babysittingRequestObj != null) {
            requestor = babysittingRequestObj.getRequestor();
            requestorName = babysittingRequestObj.getRequestorName();
            jobStartTime = babysittingRequestObj.getJob_start_time();
            jobEndTime = babysittingRequestObj.getJob_end_time();
            final TextView babysitterNameText = (TextView) view.findViewById(R.id.babySitterName);
            babysitterNameText.setText(requestorName);

            final TextView jobStartTimeText = (TextView) view.findViewById(R.id.requestDate);
            jobStartTimeText.setText(jobStartTime);

            final TextView jobEndTimeText = (TextView) view.findViewById(R.id.requestEndTime);
            jobEndTimeText.setText(jobEndTime);

            Log.v(LOG, "requestor: " + requestor);
            Log.v(LOG, " requestorName: " + requestorName + " jobstartTime: " + jobStartTime + " jobEndTime: " + jobEndTime);

            Button acceptJobButton = (Button) view.findViewById(R.id.acceptJobButton);
            Button rejectJobButton = (Button) view.findViewById(R.id.rejectJobButton);

            acceptJobButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Log.v(LOG, "requestor: " + requestor + " pos: " + i);
                    Firebase outRef = new Firebase(FIREBASE_URL).child("my_schedule").child(UserModel.getCurrentUser().getUserId()).child(babysittingRequestObj.getId());
                    Map<String, String> outBabysittingRequests = new HashMap<String, String>();
                    outBabysittingRequests.put("job_start_time", jobStartTime);
                    outBabysittingRequests.put("job_end_time", jobEndTime);
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
                    new Firebase(FIREBASE_URL).child("incoming_babysitting_requests").child(UserModel.getCurrentUser().getUserId()).child(babysittingRequestObj.getId()).removeValue();
                    Toast.makeText(v.getContext(), "Job Accepted", Toast.LENGTH_SHORT).show();

                    //Send a message to the requestor letting them know that the request cannot be committed to.
                    Firebase messageRef = new Firebase(FIREBASE_URL).child("messages").child(requestor);
                    Map<String, String> message = new HashMap<String, String>();
                    message.put("sender", requestor);
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
                    new Firebase(FIREBASE_URL).child("incoming_babysitting_requests").child(UserModel.getCurrentUser().getUserId()).child(babysittingRequestObj.getId()).removeValue();
                    Toast.makeText(v.getContext(), "Job Rejected", Toast.LENGTH_SHORT).show();

                    //Send a message to the requestor letting them know that the request cannot be committed to.
                    Firebase messageRef = new Firebase(FIREBASE_URL).child("messages").child(requestor);
                    Map<String, String> message = new HashMap<String, String>();
                    message.put("sender", requestor);
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