package com.coursera.eugenel.modernart.tests;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.test.ApplicationTestCase;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.RelativeLayout;

import com.coursera.eugenel.modernart.ColorRect;
import com.coursera.eugenel.modernart.MainActivity;
import com.coursera.eugenel.modernart.R;
import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * Created by eugenel on 6/14/2015.
 */
public class Test2_SeekBar extends ActivityInstrumentationTestCase2<MainActivity>{

    private Solo solo;

    public Test2_SeekBar() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    /**
     *
     */
    public void testRun() {

        int delay = 2000;

        // ============= Section One ==============
        assertTrue(     "ResetTest failed:" + "Section One:" + "MainActivity did not correctly load.",
                        solo.waitForActivity(com.coursera.eugenel.modernart.MainActivity.class,
                            delay));

        //TODO Take color of the first triangle (hardcoded)
        //We can use the first point color


        ArrayList<ColorRect> views = solo.getCurrentViews(ColorRect.class);
        Bitmap bitmap = loadBitmapFromView(views.get(0));
        int pixel = bitmap.getPixel(0, 0);
        int r = Color.red(pixel);
        int b = Color.blue(pixel);
        int g = Color.green(pixel);
        System.out.println("RGB:"+r+b+g);


        solo.setProgressBar((android.widget.SeekBar) solo.getCurrentActivity().findViewById(R.id.seekBar), 50);

        //TODO Get from seek bar the position

        //TODO set Saturation seek bar to end - everything should be white

        //TODO set Value seek bar to end - everything should be black


        solo.sleep(delay);
    }

    /**
     *
     * @param v
     * @return
     */
    private static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

}
