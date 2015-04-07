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

    public void testLifeCycleCreation() {
        Activity activity = startActivity(mLaunchIntent, null, null);

        // At this point, onCreate() has been called, but nothing else
        // so we complete the startup of the activity
        getInstrumentation().callActivityOnStart(activity);
        getInstrumentation().callActivityOnResume(activity);

        // At this point you could test for various configuration aspects
        // or you could use a Mock Context
        // to confirm that your activity has made
        // certain calls to the system and set itself up properly.

        getInstrumentation().callActivityOnPause(activity);

        // At this point you could confirm that
        // the activity has paused properly,
        // as if it is no longer the topmost activity on screen.

        getInstrumentation().callActivityOnStop(activity);

        // At this point, you could confirm that
        // the activity has shut itself down appropriately,
        // or you could use a Mock Context to confirm that
        // your activity has released any
        // system resources it should no longer be holding.

        // ActivityUnitTestCase.tearDown() is always automatically called
        // and will take care of calling onDestroy().
    }

}