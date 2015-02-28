package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


public class Schedule extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        final String[] scheduleArray = getResources().getStringArray(R.array.schedule);
        ListView scheduleList = (ListView) findViewById(R.id.scheduleList);
    }


    public void onHome(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);

    }

    public void onFriends(View view) {
        Intent intent = new Intent(this, Friends.class);
        startActivity(intent);

    }


}
