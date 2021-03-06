package com.onemobilekidz.mobilekidz;

import android.app.ListActivity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.onemobilekidz.mobilekidz.model.UserModel;


public class Messages extends ListActivity {


    private static final String LOG = "Messages";
    private Firebase mFirebaseRef;
    private MessageListAdapter messageListAdapter;
    private ValueEventListener mConnectedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Firebase.setAndroidContext(this);

        try {
            mFirebaseRef = new Firebase(Constants.FIREBASE_URL).child("messages").child(UserModel.getCurrentUser().getUserId());
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
            messageListAdapter = new MessageListAdapter(getApplicationContext(), mFirebaseRef, this, R.layout.message_list_row);

            listView.setAdapter(messageListAdapter);
            messageListAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    listView.setSelection(messageListAdapter.getCount() - 1);
                }
            });

// Finally, a little indication of connection status
            mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean connected = (Boolean) dataSnapshot.getValue();
                    if (connected) {
                        Toast.makeText(Messages.this, "Connected to Mobile Kidz", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Messages.this, "Working Offline", Toast.LENGTH_SHORT).show();
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
            messageListAdapter.cleanup();
        }
    }


}
