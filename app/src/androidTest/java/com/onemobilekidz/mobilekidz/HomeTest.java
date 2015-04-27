package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import junit.framework.TestCase;

import org.w3c.dom.Text;


public class HomeTest extends ActivityInstrumentationTestCase2<Home> {

    private Home home;
    private TextView homeText;
    private TextView messagesText;
    private TextView profileText;
    private View myScheduleView;
    private View messagesView;
    private View profileView;
    private Button homeButton;
    private Button friendsButton;
    private Button babysittingButton;

    private static final int TIMEOUT_IN_MS = 5000;

    public HomeTest() {
        super(Home.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        home = getActivity();
        homeText =
                (TextView) home
                        .findViewById(R.id.textView);
        messagesText = (TextView) home.findViewById(R.id.messageTextView);

        profileText = (TextView) home.findViewById(R.id.textView9);

        myScheduleView = home.findViewById(R.id.firstRow);

        messagesView = home.findViewById(R.id.secondRow);

        profileView = home.findViewById(R.id.thirdRow);

        homeButton = (Button) home.findViewById(R.id.homeButton);

        friendsButton = (Button) home.findViewById(R.id.friendsButton);

        babysittingButton = (Button) home.findViewById(R.id.babysittingRequestButton);


    }

    public void testMySchedule_labelText() {
        final String expected =
                "My Schedule";
        final String actual = homeText.getText().toString();
        assertEquals(expected, actual);
    }

    public void testMessages_labelText() {
        final String expected =
                "Messages";
        final String actual = messagesText.getText().toString();
        assertEquals(expected, actual);
    }

    public void testProfile_labelText() {
        final String expected =
                "Profile";
        final String actual = profileText.getText().toString();
        assertEquals(expected, actual);
    }

    public void testMyScheduleLink() {
        final View myScheduleButton = getActivity().findViewById(R.id.firstRow);
        Instrumentation.ActivityMonitor myScheduleActivityMonitor = getInstrumentation()
                .addMonitor(Schedule.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, myScheduleButton);


        Schedule schedule = (Schedule) myScheduleActivityMonitor
                .waitForActivityWithTimeout(TIMEOUT_IN_MS);

        assertNotNull("Schedule is null", schedule);

        getInstrumentation().removeMonitor(myScheduleActivityMonitor);

    }

    public void testMessagesLink() {
        final View messagesButton = getActivity().findViewById(R.id.secondRow);
        Instrumentation.ActivityMonitor messagesActivityMonitor = getInstrumentation()
                .addMonitor(Messages.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, messagesButton);


        Messages messages = (Messages) messagesActivityMonitor
                .waitForActivityWithTimeout(TIMEOUT_IN_MS);

        assertNotNull("Messages is null", messages);

        getInstrumentation().removeMonitor(messagesActivityMonitor);

    }

    public void testProfileLink() {
        final View profileButton = getActivity().findViewById(R.id.thirdRow);
        Instrumentation.ActivityMonitor profileActivityMonitor = getInstrumentation()
                .addMonitor(Profile.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, profileButton);


        Profile profile = (Profile) profileActivityMonitor
                .waitForActivityWithTimeout(TIMEOUT_IN_MS);

        assertNotNull("Profile is null", profile);

        getInstrumentation().removeMonitor(profileActivityMonitor);

    }

    public void testHomeButton() {
        Instrumentation.ActivityMonitor homeActivityMonitor = getInstrumentation()
                .addMonitor(Home.class.getName(), null, false);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, homeButton);

        Home home = (Home) homeActivityMonitor
                .waitForActivityWithTimeout(TIMEOUT_IN_MS);

        assertNotNull("Home is null", home);

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

        getInstrumentation().removeMonitor(bRActivityMonitor);

    }


}