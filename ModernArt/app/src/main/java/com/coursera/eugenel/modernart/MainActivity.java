package com.coursera.eugenel.modernart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;



public class MainActivity extends AppCompatActivity {

    static private final int PADDING = 30;
    static private final String URL = "http://www.moma.org";
    static private final String URL_HSV ="http://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html";
    private SeekBar seekBar = null;
    private SeekBar seekBarS = null;
    private SeekBar seekBarV = null;
    private int toplHeight = 0;
    private int toplWidth = 0;
    private ColorRect rect = null;
    RelativeLayout rLayout =null;
    static private final int RECT_NUMBER = 6;

    /***
     * Main entry
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hue seek bar
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println("seek bar progress:" + progressChanged);
                if(rect !=null ) {
                    rect.changeRandomColor2All(progressChanged);
                    rect.invalidate();
                }

            }
        });

        //Saturation seek bar
        seekBarS = (SeekBar) findViewById(R.id.seekBarS);
        seekBarS.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                if(rect !=null ) {
                    rect.updateSorV2All(progressChanged,-1);
                    rect.invalidate();
                }

            }
        });

        //Value seek bar
        seekBarV = (SeekBar) findViewById(R.id.seekBarV);
        seekBarV.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                if(rect !=null ) {
                    rect.updateSorV2All(-1, progressChanged);
                    rect.invalidate();
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Action bar with two menu items
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        int id = item.getItemId();

        //Prepare the dialog before going to MOMA site
        if (id == R.id.action_settings) {
            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(R.string.dialog_message)
                    .setTitle(R.string.dialog_title);
            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    System.out.println("User clicked OK button");
                    // Create a base intent for viewing a URL
                    Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                    // Start the chooser Activity, using the chooser intent
                    startActivity(baseIntent);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    System.out.println("User cancelled the dialog");
                }
            });
            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();

            dialog.show();

            return true;
        }

        //Open site with HSV (hue/saturation/view) explanation
        if (id == R.id.aboutHSV) {
            // Create a base intent for viewing a URL
            Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_HSV));

            // Start the chooser Activity, using the chooser intent
            startActivity(baseIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /***
     * This event for receive the top layout size
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(toplHeight==0 || toplWidth==0) {
            build();
        }
    }


    /***
     * Here we already know the top layout size and we can build our rects
     */
    private void build() {
        rLayout = (RelativeLayout) findViewById(R.id.topLayout);
        toplHeight = rLayout.getHeight();
        toplWidth = rLayout.getWidth();
        Point startP = new Point(PADDING, PADDING);
        Point endP = new Point(toplWidth - PADDING, toplHeight - PADDING);
        rect = new ColorRect(this);
        buildRect(startP, endP, RECT_NUMBER, true);
        rect.addRandomColor2All();
        rLayout.addView(rect);
    }

    /***
     * Build rectangles
     * @param startP
     * @param endP
     * @param maxRect
     * @param isHorizontal
     */
    private void buildRect(Point startP, Point endP, int maxRect, boolean isHorizontal){

        Point startP1 = null;
        Point endP1 = null;
        Point startP2 = null;
        Point endP2 = null;

        int tmpCalcPoint=0;

        if(isHorizontal) {
            startP1 = new Point(startP.x, startP.y);
            tmpCalcPoint=(endP.y)/2;
            endP1 = new Point(endP.x, tmpCalcPoint);

            tmpCalcPoint=(endP.y)/2;
            startP2 = new Point(startP.x, tmpCalcPoint);
            endP2 = new Point(endP.x, endP.y);
        }
        else {
            startP1 = new Point(startP.x, startP.y);
            tmpCalcPoint=(endP.x)/2;
            endP1 = new Point(tmpCalcPoint, endP.y);

            tmpCalcPoint = (endP.x)/2;
            startP2 = new Point(tmpCalcPoint, startP.y);
            endP2 = new Point(endP.x, endP.y);
        }


        rect.removeRect(startP, endP);
        rect.addRect(startP1, endP1);
        rect.addRect(startP2, endP2);

        if(maxRect-2>0){
            buildRect(startP1, endP1, maxRect-2, isHorizontal);
            if(maxRect-4>=0){
                buildRect(startP2, endP2, maxRect-4, !isHorizontal);
            }
        }
    }
}
