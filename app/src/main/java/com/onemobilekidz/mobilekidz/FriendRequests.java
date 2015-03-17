package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.onemobilekidz.mobilekidz.helper.DatabaseManager;
import com.onemobilekidz.mobilekidz.model.FriendRequestsModel;
import com.onemobilekidz.mobilekidz.model.FriendsModel;


public class FriendRequests extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests);
    }

    public void acceptFriendRequest(View view) {
        DatabaseManager dbManager = new DatabaseManager(this);

        FriendsModel friendsObj = new FriendsModel("Maria");
        dbManager.addRowFriends(friendsObj);

        FriendRequestsModel friendRequestsObj = new FriendRequestsModel("user1", "not added");
        dbManager.addRowFriendRequests(friendRequestsObj);
    }


}
