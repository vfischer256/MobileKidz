package com.onemobilekidz.mobilekidz;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.Button;

import junit.framework.TestCase;

public class MakeRequestsTest extends ActivityUnitTestCase<MakeRequests> {

    private Intent mLaunchIntent;

    public MakeRequestsTest() {
        super(MakeRequests.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //Create an intent to launch target Activity
        mLaunchIntent = new Intent(getInstrumentation().getTargetContext(),
                MakeRequests.class);
    }


    /**
     * Tests the preconditions of this test fixture.
     */
    @MediumTest
    public void testViewPoints() {
        startActivity(mLaunchIntent, null, null);
        final Button launchNextButton = (Button) getActivity().findViewById(R.id.goToPoints);
        //Because this is an isolated ActivityUnitTestCase we have to directly click the
        //button from code
        launchNextButton.performClick();

        // Get the intent for the next started activity
        final Intent launchIntent = getStartedActivityIntent();
        //Verify the intent was not null.
        assertNotNull("Intent was null", launchIntent);
    }


}