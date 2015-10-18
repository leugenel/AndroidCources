package com.coursera.eugenel.modernart;

import android.graphics.Point;

/**
 * Created by eugenel on 5/30/2015.
 */
public class OneRect {

    private Point startP = new Point();
    public Point getStartP() {
        return startP;
    }

    private Point endP = new Point();
    public Point getEndP() {
        return endP;
    }

    private float [] hsvColor = new float[]{0f,1f,1f};
    public float[] getHsvColor() {
        return hsvColor;
    }
    public void setHsvColor(float[] hsvColor) {
        System.arraycopy(hsvColor,0,this.hsvColor,0,3);
    }

    private float [] hsvInitColor = new float[]{0f,1f,1f};
    public float[] getHsvInitColor() {
        return hsvInitColor;
    }
    public void setHsvInitColor(float[] hsvColor) {
        System.arraycopy(hsvColor,0,hsvInitColor,0,3);
    }

    public OneRect(Point startP, Point endP) {
        this.startP.x = startP.x;
        this.startP.y = startP.y;
        this.endP.x = endP.x;
        this.endP.y = endP.y;
    }

}
