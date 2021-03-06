package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.firebase.geofire.LocationCallback;
import com.onemobilekidz.mobilekidz.model.UserModel;

import java.util.ArrayList;


public class OutOfNetWorkFriends extends Activity {

    private static final String LOG = "OONFriends";
    private String userId = UserModel.getCurrentUser().getUserId();
    public GeoFire geoFireOtherUser;
    public GeoQuery geoQuery;

    private String otherUserId;

    private ListView userList;

    ArrayList<String> outOfNetworkList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_of_net_work_friends);

        userList = (ListView) findViewById(R.id.outOfNetworkFriends);
        Firebase.setAndroidContext(this);
        getOutOfNetworkUser();





/*
        // Create The Adapter with passing ArrayList as 3rd parameter
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, outOfNetworkList);
        // Set The Adapter
        userList.setAdapter(arrayAdapter);
        */


    }

    private void getOutOfNetworkUser() {
        try {
            GeoFire geoFireUser = new GeoFire(new Firebase(Constants.FIREBASE_URL).child("user_location"));

            geoFireUser.getLocation(userId, new LocationCallback() {


                @Override
                public void onLocationResult(String key, GeoLocation location) {
                    if (location != null) {
                        System.out.println(String.format("The location for key %s is [%f,%f]", key, location.latitude, location.longitude));


                        geoFireOtherUser = new GeoFire(new Firebase(Constants.FIREBASE_URL).child("user_location"));


                        geoQuery = geoFireOtherUser.queryAtLocation(new GeoLocation(location.latitude, location.longitude), 3);


                        System.out.println("My center: " + geoQuery.getCenter());

                        geoQuery.toString();

                        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                            @Override
                            public void onKeyEntered(String key, GeoLocation location) {
                                System.out.println(String.format("Key %s entered the search area at [%f,%f]", key, location.latitude, location.longitude));
                                //         outOfNetworkList.add(key);
                                otherUserId = key;
                                Firebase ref = new Firebase(Constants.FIREBASE_URL).child("users").child(otherUserId).child("email");
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        System.out.println(dataSnapshot.getValue());
                                        outOfNetworkList.add(dataSnapshot.getValue().toString());

                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });

                            }

                            @Override
                            public void onKeyExited(String key) {
                                System.out.println(String.format("Key %s is no longer in the search area", key));
                            }

                            @Override
                            public void onKeyMoved(String key, GeoLocation location) {
                                System.out.println(String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude));
                            }

                            @Override
                            public void onGeoQueryReady() {


                                displayUser();
                                System.out.println("All initial data has been loaded and events have been fired!");
                            }

                            @Override
                            public void onGeoQueryError(FirebaseError error) {
                                System.err.println("There was an error with this query: " + error);
                            }
                        });

                    } else {
                        System.out.println(String.format("There is no location for key %s in GeoFire", key));
                    }

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.err.println("There was an error getting the GeoFire location: " + firebaseError);
                }
            });
        } catch (Exception e) {
            Log.e(LOG, e.toString());
        }
    }

    private void displayUser() {


        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        // Create The Adapter with passing ArrayList as 3rd parameter
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, outOfNetworkList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView text = (TextView) view.findViewById(android.R.id.text1);
                        text.setTextColor(Color.BLACK);
                        return view;
                    }
                };        // Set The Adapter
        userList.setAdapter(arrayAdapter);

    }
}




