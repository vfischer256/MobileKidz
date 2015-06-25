package com.onemobilekidz.mobilekidz;

import android.content.Context;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class FirebaseConnectionStatus {
    public static Context context;

    public static ValueEventListener checkConnectionStatus(Firebase mFirebaseRef, Context context) {
        FirebaseConnectionStatus.context = context;
        // Finally, a little indication of connection status
        ValueEventListener mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(FirebaseConnectionStatus.context, "Connected to Mobile Kidz", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FirebaseConnectionStatus.context, "Working Offline", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
// No-op
            }
        });
        return mConnectedListener;
    }
}