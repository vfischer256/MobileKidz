package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityUnitTestCase;

import junit.framework.TestCase;

public class FriendsTest extends ActivityUnitTestCase<Friends> {

    private Intent mLaunchIntent;


    public FriendsTest() {
        super(Friends.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //Create an intent to launch target Activity
        mLaunchIntent = new Intent(getInstrumentation().getTargetContext(),
                FriendsTest.class);
    }

}