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

public class Home extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        } else {
            System.out.println("No action bar");
        }
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

    Intent intent;

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


}
