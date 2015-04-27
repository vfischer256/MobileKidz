package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.TextView;

import junit.framework.TestCase;

import org.w3c.dom.Text;


public class HomeTest extends ActivityInstrumentationTestCase2<Home> {

    private Home home;
    private TextView homeText;
    private TextView messagesText;
    private TextView profileText;


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





}