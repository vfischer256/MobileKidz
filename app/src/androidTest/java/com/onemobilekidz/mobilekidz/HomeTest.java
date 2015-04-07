package com.onemobilekidz.mobilekidz;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;

import junit.framework.TestCase;

public class HomeTest extends ActivityUnitTestCase<Home> {

    private Intent mLaunchIntent;

    public HomeTest() {
        super(Home.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //Create an intent to launch target Activity
        mLaunchIntent = new Intent(getInstrumentation().getTargetContext(),
                Home.class);
    }


    /**
     * Tests the preconditions of this test fixture.
     */
    @MediumTest
    public void testPreconditionsGoSchedule() {
        //Start the activity under test in isolation, without values for savedInstanceState and
        //lastNonConfigurationInstance
        startActivity(mLaunchIntent, null, null);
        final View launchNextButton = getActivity().findViewById(R.id.firstRow);

        assertNotNull("mLaunchActivity is null", getActivity());
        assertNotNull("mLaunchNextButton is null", launchNextButton);
    }


    @MediumTest
    public void testGoSchedule() {
        startActivity(mLaunchIntent, null, null);
        final View launchNextButton = getActivity().findViewById(R.id.firstRow);
        //Because this is an isolated ActivityUnitTestCase we have to directly click the
        //button from code
        launchNextButton.performClick();

        // Get the intent for the next started activity
        final Intent launchIntent = getStartedActivityIntent();
        //Verify the intent was not null.
        assertNotNull("Intent was null", launchIntent);

    }

    public void testGoProfile() throws Exception {
        startActivity(mLaunchIntent, null, null);
        final View launchNextButton = getActivity().findViewById(R.id.thirdRow);
        //Because this is an isolated ActivityUnitTestCase we have to directly click the
        //button from code
        launchNextButton.performClick();

        // Get the intent for the next started activity
        final Intent launchIntent = getStartedActivityIntent();
        //Verify the intent was not null.
        assertNotNull("Intent was null", launchIntent);

    }
}