package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class FriendRequests extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_requests, menu);
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

    public void onHome(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);

    }

    public void onFriends(View view) {
        Intent intent = new Intent(this, Friends.class);
        startActivity(intent);

    }

    public void onMessages(View view) {
        Intent intent = new Intent(this, Messages.class);
        startActivity(intent);

    }

    public void onFriendRequests(View view) {
        Intent intent = new Intent(this, FriendRequests.class);
        startActivity(intent);

    }

    public void onOutOfNetworkFriends(View view) {
        Intent intent = new Intent(this, OutOfNetWorkFriends.class);
        startActivity(intent);

    }
}
