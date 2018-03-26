package com.dyhdyh.gpuimage.support.example.model;

import android.graphics.Bitmap;

/**
 * @author dengyuhan
 *         created 2018/3/26 11:20
 */
public class CoverBitmap {
    private int index;
    private Bitmap bitmap;

    public CoverBitmap(int index, Bitmap bitmap) {
        this.index = index;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
