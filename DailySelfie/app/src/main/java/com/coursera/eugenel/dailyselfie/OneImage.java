package com.coursera.eugenel.dailyselfie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;

/**
 * Created by eugenel on 7/14/2015.
 */
public class OneImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File fileBmp = (File)getIntent().getExtras().get(MainActivity.EXTRA_INFO);

        // Get the dimensions of the View
        int targetW =  MainActivity.mDisplayWidth;
        int targetH = MainActivity.mDisplayHeight;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileBmp.getAbsolutePath(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bmp = BitmapFactory.decodeFile(fileBmp.getAbsolutePath(), bmOptions);

//        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(fileBmp.getAbsolutePath(), bmOptions),
//                    MainActivity.mDisplayWidth, MainActivity.mDisplayHeight, false);


        setContentView(R.layout.one_image);

        ImageView imageView = (ImageView)findViewById(R.id.one_img);

        imageView.setImageBitmap(bmp);
    }

}
