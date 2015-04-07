package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.onemobilekidz.mobilekidz.model.PointsModel;
import com.onemobilekidz.mobilekidz.model.UserModel;

import java.util.HashMap;
import java.util.Map;

public class Points extends Activity {

    private static final String LOG = "Points";
    private static final String FIREBASE_URL = "https://crackling-heat-9656.firebaseio.com/";
    private Firebase mFirebaseRef;
    int points;

    public int getPoints() {
        return points;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        Firebase.setAndroidContext(this);


        Log.v(LOG, "this is my userid " + UserModel.getCurrentUser().getUserId());
        // Setup our Firebase mFirebaseRef
        try {
            mFirebaseRef = new Firebase(FIREBASE_URL).child("points").child(UserModel.getCurrentUser().getUserId());
        } catch (Exception e) {
            Log.e(LOG, e.toString());
        }

        mFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());
                int sum = 0;
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

    }


}
