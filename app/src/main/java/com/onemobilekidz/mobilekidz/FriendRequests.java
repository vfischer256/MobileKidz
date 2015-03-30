package com.onemobilekidz.mobilekidz;

import android.app.ListActivity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.onemobilekidz.mobilekidz.model.UserModel;

import java.util.Map;


public class FriendRequests extends ListActivity {


    private static final String LOG = "FriendRequests";
    private static final String FIREBASE_URL = "https://crackling-heat-9656.firebaseio.com/";
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private FriendRequestsListAdapter friendRequestsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests);
        Firebase.setAndroidContext(this);

        // Setup our Firebase mFirebaseRef
        try {
            mFirebaseRef = new Firebase(FIREBASE_URL).child("friend_requests").child(UserModel.getCurrentUser().getUserId());
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
            friendRequestsListAdapter = new FriendRequestsListAdapter(mFirebaseRef, this, R.layout.friend_request_list_row);
            listView.setAdapter(friendRequestsListAdapter);
            friendRequestsListAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    listView.setSelection(friendRequestsListAdapter.getCount() - 1);
                }
            });
// Finally, a little indication of connection status
            mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean connected = (Boolean) dataSnapshot.getValue();
                    if (connected) {
                        Toast.makeText(FriendRequests.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FriendRequests.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
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
            friendRequestsListAdapter.cleanup();
        }
    }

    // TODO: When we send a friend request to a user that doesn't exist, it makes a bogus user.
    // TODO: Delete the bogus user after 60 days if the user does not log in at least once.
    public void sendFriendRequest(View view) {
        EditText editText = (EditText) findViewById(R.id.editEmailAddress);
        final String email = editText.getText().toString();
        System.out.println("Sending friend request from " + UserModel.getCurrentUser().getEmail() + " to " + email);
        UserModel.getOrCreateUserByEmail(email, new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                String friendId = null;
                for (String i : ((Map<String, Object>) snapshot.getValue()).keySet()) {
                    friendId = i;
                }
                System.out.println("Sending friend request from " + UserModel.getCurrentUser().getUserId() + " to " + email + " (" + friendId + ")");
                new Firebase(FIREBASE_URL).child("friend_requests").
                        child(UserModel.getCurrentUser().getUserId()).child(friendId).child("recipient").setValue(true);
                new Firebase(FIREBASE_URL).child("friend_requests").
                        child(friendId).child(UserModel.getCurrentUser().getUserId()).child("sender").setValue(true);
            }

            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }


}
