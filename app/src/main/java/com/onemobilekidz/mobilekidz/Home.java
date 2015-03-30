package com.onemobilekidz.mobilekidz;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.MenuItem;

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
import java.util.Map;

import android.util.Log;


public class Home extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {

    private static final String LOG = "Home";
    Intent intent;
    private GoogleApiClient mGoogleApiClient;
    private boolean emailExists;

    private String email;
    private String displayName;

    public String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

/*        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        mGoogleApiClient.connect();*/
        //  initializeUser("vfischer@fischerfamily.us", "Vivienne Fischer");
        //  initializeUser("vfischer1@gmail.com", "vfischer1");
        initializeUser("jessica@gmail.com", "Jessica Fischer");

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
                    } else {
                        for (String id : ((Map<String, Object>) snapshot.getValue()).keySet()) {
                            UserModel.getCurrentUser().setUserId(id);
                            String displayName = ((Map<String, Map<String, String>>) snapshot.getValue()).get(id).get("displayName");
                            UserModel.getCurrentUser().setDisplayName(displayName);
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
            initializeUser(email, displayName);
            Log.v(LOG, "user Id + " + UserModel.getCurrentUser().getUserId());
//            Log.v(LOG, "i am a key + " + key);

//            bundle.putString("userId", key);

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
