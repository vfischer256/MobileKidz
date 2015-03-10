package com.onemobilekidz.mobilekidz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.Activity;
import android.widget.*;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.*;


import static java.util.Calendar.YEAR;


/* Part of this code is copied from Sheusi, J. C. (2012). Android™ Application Development
for Java® Programmers. Course Technology PTR. */

public class MakeRequests extends Activity implements DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener{

    /** Called when the activity is first created. */
    DatePicker dp=null;
    Calendar cal=null;
    TextView mcdate=null;
    TimePicker timePicker=null;
    TextView mctime=null;

    DateFormat fmtDateAndTime=DateFormat.getDateTimeInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_requests);

        dp=(DatePicker)findViewById(R.id.datePicker);
        mcdate=(TextView)findViewById(R.id.myChosenDate);
        cal=Calendar.getInstance();
        dp.init(dp.getYear(),dp.getMonth(),dp.getDayOfMonth(),this);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(this);

     }

    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth){
        cal.set(year,monthOfYear, dayOfMonth);
        updateDateTime();

    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        updateDateTime();
    }

    private void updateDateTime (){
        mcdate.setText(fmtDateAndTime
                .format(cal.getTime()));
    }


    public void submitBabysittingRequest(View view){
        Intent intent = new Intent(this, Requests.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_make_requests, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
