package com.onemobilekidz.mobilekidz;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
