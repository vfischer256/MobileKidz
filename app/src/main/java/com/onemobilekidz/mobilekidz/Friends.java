package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.onemobilekidz.mobilekidz.model.UserModel;


public class Friends extends ListActivity {

    private static final String LOG = "Friends";

    private static final String FIREBASE_URL = "https://crackling-heat-9656.firebaseio.com/";
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private FriendListAdapter friendListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Firebase.setAndroidContext(this);

        Log.v(LOG, "this is my userid " + UserModel.getCurrentUser().getUserId());
        // Setup our Firebase mFirebaseRef
        try {
            mFirebaseRef = new Firebase(FIREBASE_URL).child("friends").child(UserModel.getCurrentUser().getUserId());
        } catch (Exception e) {
            Log.e(LOG, e.toString());
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = getListView();

        if (mFirebaseRef != null) {
            friendListAdapter = new FriendListAdapter(getApplicationContext(), mFirebaseRef, this, R.layout.friend_list_row);

            listView.setAdapter(friendListAdapter);
            friendListAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    listView.setSelection(friendListAdapter.getCount() - 1);
                }
            });

// Finally, a little indication of connection status
            mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean connected = (Boolean) dataSnapshot.getValue();
                    if (connected) {
                        Toast.makeText(Friends.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Friends.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
// No-op
                }
            });
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mFirebaseRef != null) {
            mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
            friendListAdapter.cleanup();
        }
    }


}
