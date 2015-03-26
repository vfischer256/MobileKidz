package com.onemobilekidz.mobilekidz;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.MenuItem;
import android.view.Menu;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.model.people.Person;
import com.onemobilekidz.mobilekidz.model.UserModel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import android.util.Log;


public class Home extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {

    private static final String LOG = "Home";
    Intent intent;
    private GoogleApiClient mGoogleApiClient;
    private boolean emailExists;

    private String email;
    private String displayName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        mGoogleApiClient.connect();


        setContentView(R.layout.activity_home);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        } else {
            System.out.println("No action bar");
        }

    }

    private void initializeUser(final String email) {
        Firebase myFirebaseRef = new Firebase("https://crackling-heat-9656.firebaseio.com/users");

        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot snapshot) {

                try {
                    Map<String, Object> value = (Map<String, Object>) snapshot.getValue();
                    for (Object o : value.values()) {
                        // Got a user. Cast it.
                        Map<String, Object> user = (Map<String, Object>) o;
                        System.out.println(user.get("email"));
                        if (email.equals(user.get("email"))) {
                            Log.v(LOG, "email  exists");
                            emailExists = true;
                        }
                    }
                } catch (Exception e) {
                    Log.e(LOG, e.toString());
                    e.printStackTrace();
                }

                if (!emailExists) {
                    createUser(email, displayName);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


    }

    private void createUser(String email, String displayName) {
        Firebase myFirebaseRef = new Firebase("https://crackling-heat-9656.firebaseio.com");

        Firebase postRef = myFirebaseRef.child("users");
        Map<String, String> user = new HashMap<String, String>();
        user.put("email", email);
        user.put("displayName", displayName);
        postRef.push().setValue(user);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case (android.R.id.home):
                Intent intent = new Intent(this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }

    public void goSchedule(View view) {
        intent = new Intent(this, Schedule.class);
        startActivity(intent);
    }

    public void goRequests(View view) {
        intent = new Intent(this, Requests.class);
        startActivity(intent);
    }

    public void goMakeRequests(View view) {
        intent = new Intent(this, MakeRequests.class);
        startActivity(intent);
    }


    @Override
    public void onConnected(Bundle bundle) {
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {

            email = Plus.AccountApi.getAccountName(mGoogleApiClient);
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            displayName = currentPerson.getDisplayName();
            initializeUser(email);

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
