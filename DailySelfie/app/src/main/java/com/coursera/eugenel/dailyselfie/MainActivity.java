package com.coursera.eugenel.dailyselfie;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public final static String EXTRA_INFO = "com.coursera.eugenel.dailyselfie.INFO";
    private ArrayList<String> text = new ArrayList<String>();
    private ArrayList<Bitmap> image = new ArrayList<Bitmap>();
    private SelfieList adapter = null;
    private File fileList[] = null;
    // Display dimensions
    public static int mDisplayWidth, mDisplayHeight;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Alarm alarm = new Alarm();
        alarm.SetAlarm(this);

        createImageList();
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.camera){
            //Launch camera app
            dispatchTakePictureIntent();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("onActivityResult:", " requestCode:" + requestCode + " resultcode:" + resultCode);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            createImageList();
        }
    }

    /**
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            RelativeLayout mFrame = (RelativeLayout) findViewById(R.id.frame);
            // Get the size of the display so this View knows where borders are
            mDisplayWidth = mFrame.getWidth();
            mDisplayHeight = mFrame.getHeight();
        }
    }

    /**
     *
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = new File (getExternalFilesDir(Environment.DIRECTORY_PICTURES), getTimeNow());
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(photoFile));
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     *
     */
    private void createImageList(){
        getAllFiles();

        adapter = new
                SelfieList(MainActivity.this, image,text);
        ListView list=(ListView)findViewById(R.id.camera_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Add the new intent and pass the image index according to position.
                Intent intent = new Intent(MainActivity.this, OneImage.class);
                Log.i("onItemClick:", "Position:" + position);
                Log.i("onItemClick", "Position is " + position);
                if(fileList[position]==null){
                    Log.i("onItemClick", "fileList[position]==null");
                    return;
                }
                intent.putExtra(EXTRA_INFO, fileList[position]);
                startActivity(intent);
            }
        });
    }

    /**
     *
     * @return
     */
    private String getTimeNow(){
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    /**
     *
     */
    private void getAllFiles(){
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(storageDir!=null) {
            fileList = new File[storageDir.listFiles().length];
            fileList=storageDir.listFiles();

            Log.i("getAllFiles", "we have "+fileList.length+" files");
            image.removeAll(image);
            text.removeAll(text);

            for( int i=0; i< fileList.length; i++)
            {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(fileList[i].getAbsolutePath(),bmOptions);

                image.add(bitmap);
                text.add(fileList[i].getName());
            }
        }
        else{
            Log.i("getAllFiles", "storageDir == NULL");
        }
    }
}
