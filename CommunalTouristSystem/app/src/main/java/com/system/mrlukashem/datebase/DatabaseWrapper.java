package com.system.mrlukashem.datebase;

import android.content.ContentValues;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.system.mrlukashem.refbases.TrackingWayRefBase;

import java.util.List;

/**
 * Created by mrlukashem on 14.11.15.
 */
public class DatabaseWrapper {

    private static final String ILLEGAL_ARGUMENT_MSG = "Not supported class!";

    private DatabaseWrapper() {}

    private static DatabaseWrapper mInstance;

    private static ReaderDbHelper mDbHelper;

    public static void initDb(@NonNull Context context) {
        mDbHelper = new ReaderDbHelper(context);
    }

    public static DatabaseWrapper getInstance() {
        if(mInstance == null) {
            mInstance = new DatabaseWrapper();
        }

        return mInstance;
    }

    public static DatabaseWrapper getInstanceAndInitDb(@NonNull Context context) {
        if(mInstance == null) {
            mInstance = new DatabaseWrapper();
        }

        mDbHelper = new ReaderDbHelper(context);

        return mInstance;
    }

    public <T> boolean put(@NonNull T value) throws IllegalArgumentException {
        if(value instanceof TrackingWayRefBase) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            TrackingWayRefBase way = (TrackingWayRefBase)value;

            if(way.isNill()) {
                return false;
            }

            List<LatLng> points = way.getPoints();

            for(LatLng point : points) {
                ContentValues values = new ContentValues();

                values.put(ReaderContract.Entry.COLUMN_NAME_ENTRY_ID, way.getTag());
                values.put(ReaderContract.Entry.COLUMN_NAME_LAT, point.latitude);
                values.put(ReaderContract.Entry.COLUMN_NAME_LNG, point.longitude);

                long rowId = db.insert(
                        ReaderDbHelper.DATABASE_NAME,
                        null,
                        values);

                if(rowId == -1) {
                    return false;
                }
            }

            return true;
        } else {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MSG);
        }
    }
}
