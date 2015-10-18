package com.coursera.eugenel.modernart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eugenel on 5/27/2015.
 */
public class ColorRect extends View {

    private final int LINE_WIDTH = 3;
    private final float MAX_SATURATION = 1f;
    private final float MAX_VALUE = 1f;

    Paint paint = new Paint();

    private Point startP = new Point();
    private Point endP = new Point();

    private float currentSaturation = MAX_SATURATION;
    private float currentValue = MAX_VALUE;

    private List <OneRect> rects = new ArrayList<OneRect>();

    public ColorRect(Context context) {
        super(context);
    }

    /***
     * Adding one rectangle
     * @param startP
     * @param endP
     */
    public void addRect(Point startP, Point endP){
       OneRect oner = new OneRect(startP, endP);
       rects.add(oner);
    }

    /***
     * Adding all rectangles that we previously built
     */
    public void addRandomColor2All(){
        int i=0;
        for(OneRect orect : rects){
            float delta = (1f/rects.size())*i*360f;
            orect.setHsvInitColor(new float[]{0f + delta, MAX_SATURATION, MAX_VALUE});
            orect.setHsvColor(new float[]{0f + delta, MAX_SATURATION, MAX_VALUE});
            i++;
        }
    }

    /***
     * Change the rectangles when hue is changed
     * @param change
     */
    public void changeRandomColor2All(int change){
        float hue = (360f * change) / (100*rects.size()) ;
        float [] hsvColor = new float[3];
        for(OneRect orect : rects){
            hsvColor = orect.getHsvInitColor();
            orect.setHsvColor(new float[]{hue + hsvColor[0], currentSaturation, currentValue});
        }
    }

    /***
     * Change the rectangles when saturation or value are changed
     * @param satChange
     * @param valueChange
     */
    public void updateSorV2All(int satChange, int valueChange){
        if(satChange!=-1){
            currentSaturation=MAX_SATURATION-(float)satChange/100f;
        }
        if(valueChange!=-1){
            currentValue=MAX_VALUE-(float)valueChange/100f;
        }
        float [] hsvColor = new float[3];
        for(OneRect orect : rects){
            hsvColor = orect.getHsvColor();
            orect.setHsvColor(new float[]{hsvColor[0], currentSaturation, currentValue});
        }
    }

    /***
     * Remove rectangle from the list
     * @param startP
     * @param endP
     */
    public  void removeRect(Point startP, Point endP){
        for(OneRect orect : rects){
            if(orect.getStartP().x==startP.x &&
               orect.getStartP().y==startP.y &&
               orect.getEndP().x==endP.x &&
               orect.getEndP().y==endP.y) {

               rects.remove(orect);
               break;
            }
        }
    }

    /***
     * Drawing - called on start ant when invalidate is called
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas) {
        for(OneRect orect : rects){
            //Draw the lines first
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(LINE_WIDTH);
            canvas.drawRect(orect.getStartP().x, orect.getStartP().y,
                    orect.getEndP().x, orect.getEndP().y, paint);
            //Now fill the color
            paint.setStrokeWidth(0);
            paint.setColor(Color.HSVToColor(orect.getHsvColor()));
            canvas.drawRect(orect.getStartP().x+LINE_WIDTH, orect.getStartP().y+LINE_WIDTH,
                    orect.getEndP().x-LINE_WIDTH, orect.getEndP().y-LINE_WIDTH, paint);
        }
    }


}
