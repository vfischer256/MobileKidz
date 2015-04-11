package com.onemobilekidz.mobilekidz;

import android.app.ActionBar;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.model.people.Person;
import com.onemobilekidz.mobilekidz.model.ScheduleModel;
import com.onemobilekidz.mobilekidz.model.UserModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import android.util.Log;
import android.widget.Toast;


public class Home extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {

    private static final String LOG = "Home";
    public String key;
    Intent intent;
    private GoogleApiClient mGoogleApiClient;
    private boolean emailExists;
    private String email;
    private String displayName;
    private String newDisplayName;
    private double mLatitude;
    private double mLongitude;
    private static final String FIREBASE_URL = "https://crackling-heat-9656.firebaseio.com/";
    Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addApi(LocationServices.API)

                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        mGoogleApiClient.connect();


        //  initializeUser("katie@gmail.com", "Katie Fischer");
        //    initializeUser("jessica@gmail.com", "Jessica Fischer");
        //  initializeUser("timothy@gmail.com", "Timothy Fischer");
        // initializeUser("corinaa@gmail.com", "Corina Alvarez");
        //     initializeUser("allanon256@gmail.com", "Ethan Fischer");
        //     initializeUser("gabbya@gmail.com", "Gabby Alvarez");

        //   initializeUser("lisapoobear@gmail.com", "Lisa Po");
        //   initializeUser("apple1980@hotmail.com", "Apple Hsu");
        //   initializeUser("eli_24343@gmail.com", "Elizabeth Carter");
        //  initializeUser("buckwildman@yahoo.com", "William Chu");

        setContentView(R.layout.activity_home);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        } else {
            System.out.println("No action bar");
        }
    }

    private void initializeUser(final String email, final String displayName) {
        Query query = new Firebase("https://crackling-heat-9656.firebaseio.com/users").equalTo(email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    UserModel.getCurrentUser().setEmail(email);
                    if (snapshot.getValue() == null) {
                        UserModel.getCurrentUser().setUserId(createUser(email, displayName).getKey());
                        updateLocation(UserModel.getCurrentUser().getUserId(), mLatitude, mLongitude);
                        initializePoints(UserModel.getCurrentUser().getUserId());

                    } else {
                        for (String id : ((Map<String, Object>) snapshot.getValue()).keySet()) {
                            UserModel.getCurrentUser().setUserId(id);
                            newDisplayName = ((Map<String, Map<String, String>>) snapshot.getValue()).get(id).get("displayName");
                            if (newDisplayName.equals("Unknown User")) {
                                updateDisplayName(displayName);
                                initializePoints(UserModel.getCurrentUser().getUserId());
                            }
                            UserModel.getCurrentUser().setDisplayName(displayName);
                            updateLocation(UserModel.getCurrentUser().getUserId(), mLatitude, mLongitude);

                            //   updateLocation(UserModel.getCurrentUser().getUserId(), 34.198198, -118.399390);

                        }
                    }
                    System.out.println(UserModel.getCurrentUser().getUserId());
                } catch (Exception e) {
                    Log.e(LOG, e.toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    private Firebase createUser(String email, String displayName) {
        Firebase postRef = new Firebase("https://crackling-heat-9656.firebaseio.com/users");
        Map<String, String> user = new HashMap<String, String>();
        user.put("email", email);
        user.put("displayName", displayName);
        // Set the priority so we can easily search for users by email.
        user.put(".priority", email);
        Firebase ref = postRef.push();
        ref.setValue(user);
        return ref;
    }

    private Firebase updateDisplayName(String displayName) {
        Firebase postRef = new Firebase("https://crackling-heat-9656.firebaseio.com").child("users").child(UserModel.getCurrentUser().getUserId());
        Map<String, Object> user = new HashMap<String, Object>();
        user.put("displayName", displayName);
        Log.v(LOG, "my new name " + displayName);
        postRef.updateChildren(user);
        return postRef;

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


    public static void initializePoints(String userId) {
        Firebase pointRef = new Firebase(FIREBASE_URL).child("points").child(userId).child("initial");
        Map<String, Object> points = new HashMap<String, Object>();
        points.put("points", 5);
        pointRef.setValue(points, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Log.v(LOG, "Points could not be updated. " + firebaseError.getMessage());
                } else {
                    Log.v(LOG, "Points was initialized to 5.");
                }
            }
        });

    }

    public void goSchedule(View view) {
        intent = new Intent(this, Schedule.class);
        startActivity(intent);
    }

    public void goMessages(View view) {
        intent = new Intent(this, Messages.class);
        startActivity(intent);
    }

    public void goProfile(View view) {
        intent = new Intent(this, Profile.class);
        startActivity(intent);
    }


    @Override
    public void onConnected(Bundle bundle) {
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {

            email = Plus.AccountApi.getAccountName(mGoogleApiClient);
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            displayName = currentPerson.getDisplayName();
            initializeUser(email, displayName);
            Log.v(LOG, "user Id + " + UserModel.getCurrentUser().getUserId());
            if (UserModel.getCurrentUser().getUserId() == null) {
                Toast.makeText(Home.this, "You're Offline. Click on Home to Reconnect", Toast.LENGTH_SHORT).show();
            }

        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitude = mLastLocation.getLatitude();
            mLongitude = mLastLocation.getLongitude();
            Log.v(LOG, "Latitude: " + String.valueOf(mLastLocation.getLatitude()));
            Log.v(LOG, "Longitude: " + String.valueOf(mLastLocation.getLongitude()));

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

    private void updateLocation(String userId, double latitude, double longitude) {
        GeoFire geoFire = new GeoFire(new Firebase("https://crackling-heat-9656.firebaseio.com").child("user_location"));
        geoFire.setLocation(userId, new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, FirebaseError error) {
                if (error != null) {
                    System.err.println("There was an error saving the location to GeoFire: " + error);
                } else {
                    System.out.println("Location saved on server successfully!");
                }
            }
        });
    }
}
