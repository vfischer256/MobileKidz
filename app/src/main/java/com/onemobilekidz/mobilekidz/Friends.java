package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;


public class Friends extends Activity {

    private ListView friendList;

    private FriendListAdapter friendListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        bindViews();

        try {
            friendListAdapter = new FriendListAdapter(this);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        friendList.setAdapter(friendListAdapter);

        registerForContextMenu(friendList);

    }

    private void bindViews() {
        friendList = (ListView) findViewById(R.id.friendList);
    }


}
