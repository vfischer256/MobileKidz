package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.TouchUtils;
import android.widget.Button;

import junit.framework.TestCase;

public class MakeRequestsTest extends ActivityInstrumentationTestCase2<MakeRequests> {

    private MakeRequests makeRequests;
    private Button homeButton;
    private Button friendsButton;
    private Button babysittingButton;

    private static final int TIMEOUT_IN_MS = 5000;


    public MakeRequestsTest() {
        super(MakeRequests.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        makeRequests = getActivity();

        homeButton = (Button) makeRequests.findViewById(R.id.homeButton);

        friendsButton = (Button) makeRequests.findViewById(R.id.friendsButton);

        babysittingButton = (Button) makeRequests.findViewById(R.id.babysittingRequestButton);

    }

    public void testHomeButton() {
        Instrumentation.ActivityMonitor homeActivityMonitor = getInstrumentation()
                .addMonitor(Home.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, homeButton);

        Home home = (Home) homeActivityMonitor
                .waitForActivityWithTimeout(TIMEOUT_IN_MS);

        assertNotNull("Home is null", home);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, homeActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                Home.class, home.getClass());

        getInstrumentation().removeMonitor(homeActivityMonitor);

    }

    public void testFriendsButton() {
        Instrumentation.ActivityMonitor friendsActivityMonitor = getInstrumentation()
                .addMonitor(Friends.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, friendsButton);

        Friends friends = (Friends) friendsActivityMonitor
                .waitForActivityWithTimeout(TIMEOUT_IN_MS);

        assertNotNull("Friends is null", friends);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, friendsActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                Friends.class, friends.getClass());

        getInstrumentation().removeMonitor(friendsActivityMonitor);

    }

    public void testBabysittingRequestButton() {
        Instrumentation.ActivityMonitor bRActivityMonitor = getInstrumentation()
                .addMonitor(BabysittingRequests.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, babysittingButton);

        BabysittingRequests babysittingRequests = (BabysittingRequests) bRActivityMonitor
                .waitForActivityWithTimeout(TIMEOUT_IN_MS);

        assertNotNull("Babysitting Request is null", babysittingRequests);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, bRActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                BabysittingRequests.class, babysittingRequests.getClass());

        getInstrumentation().removeMonitor(bRActivityMonitor);

    }
}