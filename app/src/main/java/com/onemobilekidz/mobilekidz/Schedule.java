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


    public void onMySchedule(View view) {
        Intent intent = new Intent(this, Schedule.class);
        startActivity(intent);

    }


}
