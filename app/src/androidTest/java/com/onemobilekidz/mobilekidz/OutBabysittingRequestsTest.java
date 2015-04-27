package com.onemobilekidz.mobilekidz;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;

import junit.framework.TestCase;

public class OutBabysittingRequestsTest extends ActivityUnitTestCase<OutBabysittingRequests> {

    private Intent mLaunchIntent;

    public OutBabysittingRequestsTest() {
        super(OutBabysittingRequests.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //Create an intent to launch target Activity
        mLaunchIntent = new Intent(getInstrumentation().getTargetContext(),
                OutBabysittingRequests.class);
    }


}