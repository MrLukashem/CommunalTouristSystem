package com.system.mrlukashem.refbases;

import android.graphics.Bitmap;

import com.system.mrlukashem.Interfaces.BitmapCallback;

/**
 * Created by mrlukashem on 10.11.15.
 */
public abstract class BitmapLoader {

    protected BitmapCallback mCallback;

    public abstract void getBitmapFromURL(String url);

    public abstract void getBitmapFromURL(String path, BitmapCallback callback);

    public abstract Bitmap getBitmapFromPath(String path);

    public void registerCallback(BitmapCallback callback) {
        mCallback = callback;
    }
}
