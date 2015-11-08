package com.system.mrlukashem.Interfaces;

import android.graphics.Bitmap;

/**
 * Created by mrlukashem on 08.11.15.
 */
public interface BitmapLoader {

    Bitmap getBitmapFromURL(String url);

    Bitmap getBitmapFromPath(String path);
}
