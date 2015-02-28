package com.onemobilekidz.mobilekidz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class OutOfNetWorkFriends extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_of_net_work_friends);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_out_of_net_work_friends, menu);
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
