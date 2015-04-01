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


public class BabysittingRequests extends Activity {

    private static final String LOG = "BSRequests";
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitting_requests);

    }

    public void goIncomingBabysittingRequests(View view) {
        intent = new Intent(this, InBabysittingRequests.class);
        startActivity(intent);
    }

    public void goOutgoingBabysittingRequests(View view) {
        intent = new Intent(this, OutBabysittingRequests.class);
        startActivity(intent);
    }

    public void goMakeRequests(View view) {
        intent = new Intent(this, MakeRequests.class);
        startActivity(intent);
    }
}
