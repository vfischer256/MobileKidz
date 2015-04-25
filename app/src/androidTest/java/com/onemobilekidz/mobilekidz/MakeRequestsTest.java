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


}