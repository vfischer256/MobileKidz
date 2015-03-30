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

/**
 * @author greg
 * @since 6/21/13
 * <p/>
 * This class is an example of how to use FirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class FriendRequestsListAdapter extends FirebaseListAdapter<FriendRequestsModel> {

    private static final String LOG = "FRListAdapter";
    private static final String FIREBASE_URL = "https://crackling-heat-9656.firebaseio.com/";
    private String recipientId;
    private String recipientEmail;

    public FriendRequestsListAdapter(Context context, Query ref, Activity activity, int layout) {
        super(context, ref, FriendRequestsModel.class, layout, activity);
    }

    /**
     * Bind an instance of the <code>FriendRequestsModel</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>FriendRequestsModel</code> instance that represents the current data to bind.
     *
     * @param view             A view instance corresponding to the layout we passed to the constructor.
     * @param friendRequestObj An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, final FriendRequestsModel friendRequestObj, final int i) {
        recipientId = friendRequestObj.getId();
        recipientEmail = friendRequestObj.getId();
        if (friendRequestObj.getUser() != null) {
            recipientEmail = friendRequestObj.getUser().getEmail();
        }
        final TextView recipientIdText = (TextView) view.findViewById(R.id.friendRequestName);
        recipientIdText.setText(recipientEmail);

        Button acceptFriendButton = (Button) view.findViewById(R.id.acceptFriendButton);
        Button rejectFriendButton = (Button) view.findViewById(R.id.rejectFriendButton);

        acceptFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.v(LOG, "this is my friend " + i);
                Log.v(LOG, "this is my friend " + recipientId);
                new Firebase(FIREBASE_URL).child("friends").child(UserModel.getCurrentUser().getUserId()).child(recipientId).child("friend").setValue(true,
                        new Firebase.CompletionListener() {
                            @Override
                            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                if (firebaseError != null) {
                                    System.out.println("Friend could not be saved. " + firebaseError.getMessage());
                                } else {
                                    new Firebase(FIREBASE_URL).child("friend_requests").child(UserModel.getCurrentUser().getUserId()).child(recipientId).removeValue();

                                    Toast.makeText(v.getContext(), "Friend Added", Toast.LENGTH_SHORT).show();
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
                new Firebase(FIREBASE_URL).child("friend_requests").child(UserModel.getCurrentUser().getUserId()).child(recipientId).removeValue();
                Toast.makeText(v.getContext(), "Friend Rejected", Toast.LENGTH_SHORT).show();
            }
        });

    }
}