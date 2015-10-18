package com.coursera.eugenel.modernart.tests;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.ActivityInstrumentationTestCase2;

import com.coursera.eugenel.modernart.R;
import com.robotium.solo.Solo;
import com.coursera.eugenel.modernart.MainActivity;

/**
 * Created by eugenel on 6/4/2015.
 */
public class Test1_Menu extends ActivityInstrumentationTestCase2<MainActivity>{

    private Solo solo;

    //Default constructor
    public Test1_Menu() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    // Executes the ResetTest
    public void testRun() {

        int delay = 2000;

        // ============= Section One ==============
        assertTrue("ResetTest failed:" + "Section One:"
                        + "MainActivity did not correctly load.",
                solo.waitForActivity(
                        com.coursera.eugenel.modernart.MainActivity.class,
                        delay));

        solo.clickOnMenuItem(getActivity().getResources().getString(R.string.action_settings));

        // Wait for dialog
        if(solo.waitForDialogToOpen(delay)){
            solo.clickOnButton(getActivity().getResources().getString(R.string.cancel));
        }

        assertTrue("MainActivity did not correctly back from the dialog.",
                solo.waitForActivity(
                        com.coursera.eugenel.modernart.MainActivity.class,
                        delay));

        solo.sleep(delay);

    }

}
