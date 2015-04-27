package com.onemobilekidz.mobilekidz;

import android.support.test.uiautomator.Until;
import android.test.InstrumentationTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.By;

public class UIHomeTest extends InstrumentationTestCase {


    private UiDevice mDevice;

    public void setUp() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

    }


}