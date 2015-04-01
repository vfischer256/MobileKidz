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

/**
 * Created by vfischer on 3/31/15.
 */
public class InBabysittingRequests extends ListActivity {


    private static final String LOG = "InBabysittingReq";

    private static final String FIREBASE_URL = "https://crackling-heat-9656.firebaseio.com/";
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private InBabysittingRequestsListAdapter inBabysittingRequestsListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_babysitting_requests);
        Firebase.setAndroidContext(this);

        Log.v(LOG, "this is my userid " + UserModel.getCurrentUser().getUserId());
        // Setup our Firebase mFirebaseRef
        try {
            mFirebaseRef = new Firebase(FIREBASE_URL).child("incoming_babysitting_requests").child(UserModel.getCurrentUser().getUserId());
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
            inBabysittingRequestsListAdapter = new InBabysittingRequestsListAdapter(getApplicationContext(), mFirebaseRef, this, R.layout.babysitting_request_list_row);
            Log.v(LOG, "getCount: " + inBabysittingRequestsListAdapter.getCount());

            listView.setAdapter(inBabysittingRequestsListAdapter);
            inBabysittingRequestsListAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    listView.setSelection(inBabysittingRequestsListAdapter.getCount() - 1);
                }
            });

// Finally, a little indication of connection status
            mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean connected = (Boolean) dataSnapshot.getValue();
                    if (connected) {
                        Toast.makeText(InBabysittingRequests.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(InBabysittingRequests.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
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
            inBabysittingRequestsListAdapter.cleanup();
        }
    }

}
