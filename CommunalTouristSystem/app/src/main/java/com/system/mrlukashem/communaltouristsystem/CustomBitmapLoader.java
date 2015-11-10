package com.system.mrlukashem.communaltouristsystem;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import android.support.annotation.NonNull;

import com.system.mrlukashem.Interfaces.BitmapCallback;
import com.system.mrlukashem.refbases.BitmapLoader;

/**
 * Created by mrlukashem on 10.11.15.
 */
public class CustomBitmapLoader extends BitmapLoader {

    private AsyncTask<String, Void, Bitmap> mTask
            = new AsyncTask<String, Void, Bitmap>() {

        @Override
        protected Bitmap doInBackground(String... paths) {
            String path = paths[0];

            //TODO: downloading bitmap implementation.
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if(result != null) {
                mCallback.bmpCallback(result);
            }
        }
    };


    @Override
    public void getBitmapFromURL(@NonNull String url) {
        mTask.execute(url);
    }

    @Override
    public void getBitmapFromURL(@NonNull String url, @NonNull BitmapCallback callback) {
        mCallback = callback;
        mTask.execute(url);
    }

    @Override
    public Bitmap getBitmapFromPath(@NonNull String path) {
        //TODO: Loading bitmap from sdcard.
        return null;
    }
}
