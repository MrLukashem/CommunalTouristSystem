package com.system.mrlukashem.communaltouristsystem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import android.support.annotation.NonNull;
import android.util.Log;

import com.system.mrlukashem.Interfaces.BitmapCallback;
import com.system.mrlukashem.refbases.BitmapLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by mrlukashem on 10.11.15.
 */
public class CustomBitmapLoader extends BitmapLoader {

    private AsyncTask<String, Void, Bitmap> mTask;

    @Override
    public void getBitmapFromURL(@NonNull String url) {
        mTask.execute(url);
    }

    @Override
    public void getBitmapFromURL(@NonNull String url, @NonNull BitmapCallback callback) {
        mCallback = callback;
        mTask = new AsyncTask<String, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... paths) {
                String path = paths[0];
                Bitmap bitmap;
                InputStream in = null;
                try {
                    in = new URL(path).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("CustomBitmapLoader:", e.getMessage());
                }

                bitmap = BitmapFactory.decodeStream(in);

                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                if(result != null) {
                    mCallback.bmpCallback(result);
                }
            }
        };

        mTask.execute(url);
    }

    @Override
    public Bitmap getBitmapFromPath(@NonNull String path) {
        return BitmapFactory.decodeFile(path);
    }
}
