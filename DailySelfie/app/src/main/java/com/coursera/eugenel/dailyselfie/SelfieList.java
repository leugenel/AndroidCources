package com.coursera.eugenel.dailyselfie;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by eugenel on 7/12/2015.
 */
public class SelfieList extends ArrayAdapter<Bitmap> {

    private final Activity context;
    private final ArrayList<Bitmap> image;
    private final ArrayList<String> text;

    public SelfieList(Activity context, ArrayList<Bitmap> image, ArrayList<String> text) {
        super(context, R.layout.one_selfie, image);
        this.context = context;
        this.text=text;
        this.image=image;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View rowView = view;

        if(rowView==null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.one_selfie, null, true);
        }

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        txtTitle.setText(text.get(position));
        imageView.setImageBitmap(image.get(position));

        return rowView;
    }

}
