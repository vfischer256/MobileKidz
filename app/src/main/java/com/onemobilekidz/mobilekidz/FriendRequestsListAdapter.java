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
import com.onemobilekidz.mobilekidz.model.FriendRequestsModel;
import com.onemobilekidz.mobilekidz.model.UserModel;

public class FriendRequestsListAdapter extends FirebaseListAdapter<FriendRequestsModel> {

    private static final String LOG = "FRListAdapter";

    public FriendRequestsListAdapter(Context context, Query ref, Activity activity, int layout) {
        super(context, ref, FriendRequestsModel.class, layout, activity);
    }

    @Override
    protected void populateView(View view, final FriendRequestsModel friendRequestObj, final int i) {
        final String recipientId = friendRequestObj.getId();
        final String recipientEmail;
        if (friendRequestObj.getUser() == null) {
            recipientEmail = friendRequestObj.getId();
        } else {
            recipientEmail = friendRequestObj.getUser().getEmail();
        }

        Log.v(LOG, "1This is my recipientId: " + recipientId + " recipientEmail: " + recipientEmail + " pos: " + i);
        final TextView recipientIdText = (TextView) view.findViewById(R.id.friendRequestName);
        recipientIdText.setText(recipientEmail);

        Button acceptFriendButton = (Button) view.findViewById(R.id.acceptFriendButton);
        Button rejectFriendButton = (Button) view.findViewById(R.id.rejectFriendButton);

        acceptFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                // Add friend to the requestor
                Log.v(LOG, "2This is my recipientId: " + recipientId + " recipientEmail: " + recipientEmail + " pos: " + i);
                new Firebase(Constants.FIREBASE_URL).child("friends").child(UserModel.getCurrentUser().getUserId()).child(recipientId).child("friend").setValue(true,
                        new Firebase.CompletionListener() {
                            @Override
                            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                if (firebaseError != null) {
                                    System.out.println("Friend could not be saved. " + firebaseError.getMessage());
                                } else {
                                    new Firebase(Constants.FIREBASE_URL).child("friend_requests").child(UserModel.getCurrentUser().getUserId()).child(recipientId).removeValue();

                                    Toast.makeText(v.getContext(), "Friend Added", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                // Add friend to the requestee
                new Firebase(Constants.FIREBASE_URL).child("friends").child(recipientId).child(UserModel.getCurrentUser().getUserId()).child("friend").setValue(true,
                        new Firebase.CompletionListener() {
                            @Override
                            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                if (firebaseError != null) {
                                    System.out.println("Friend could not be saved. " + firebaseError.getMessage());
                                }
                            }
                        });


            }

        });

        rejectFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.v(LOG, "this is my friend " + i);
                Log.v(LOG, "this is my friend " + recipientId);
                new Firebase(Constants.FIREBASE_URL).child("friend_requests").child(UserModel.getCurrentUser().getUserId()).child(recipientId).removeValue();
                Toast.makeText(v.getContext(), "Friend Rejected", Toast.LENGTH_SHORT).show();
            }
        });

    }


}