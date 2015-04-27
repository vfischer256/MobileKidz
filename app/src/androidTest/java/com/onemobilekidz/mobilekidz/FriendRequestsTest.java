package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.Button;

import junit.framework.TestCase;

public class FriendRequestsTest extends ActivityUnitTestCase<FriendRequests> {

    private Intent mLaunchIntent;


    public FriendRequestsTest() {
        super(FriendRequests.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //Create an intent to launch target Activity
        mLaunchIntent = new Intent(getInstrumentation().getTargetContext(),
                FriendsTest.class);
    }


    public void testSendFriendRequest() throws Exception {

    }
}