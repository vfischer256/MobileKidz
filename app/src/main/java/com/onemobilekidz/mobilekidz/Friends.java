package com.onemobilekidz.mobilekidz;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


public class Friends extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        final String[] scheduleArray = getResources().getStringArray(R.array.friends);
        ListView scheduleList = (ListView) findViewById(R.id.friendList);
    }





}
