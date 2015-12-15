package com.system.mrlukashem.datebase;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.system.mrlukashem.communaltouristsystem.TrackingWay;
import com.system.mrlukashem.refbases.TrackingWayRefBase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mrlukashem on 14.11.15.
 */
public class  DatabaseWrapper {

    private static final String ILLEGAL_ARGUMENT_MSG = "Not supported class!";

    private static final String ILLEGAL_ARGUMENTS_LIST_MSG = "Wrong arguments list for given class!";

    private DatabaseWrapper() {}

    private static DatabaseWrapper mInstance;

    private static ReaderDbHelper mDbHelper;

    private static final String EMPTY_STRING = "";

    public static void initDb(@NonNull Context context) throws SQLException {
        mDbHelper = new ReaderDbHelper(context);
    }

    public static DatabaseWrapper getInstance() {
        if(mInstance == null) {
            mInstance = new DatabaseWrapper();
        }

        return mInstance;
    }

    public static DatabaseWrapper getInstanceAndInitDb(@NonNull Context context) throws SQLException {
        if(mInstance == null) {
            mInstance = new DatabaseWrapper();
        }

        mDbHelper = new ReaderDbHelper(context);

        return mInstance;
    }

    public <T> boolean put(@NonNull T value) throws SQLException, IllegalArgumentException {
        if(value instanceof TrackingWayRefBase) {
            TrackingWayRefBase way = (TrackingWayRefBase)value;

            if(way.isNill()) {
                return false;
            }

            if(way.getTag().isEmpty()) {
                return false;
            }

            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            List<LatLng> points = way.getPoints();

            for(LatLng point : points) {
                ContentValues values = new ContentValues();

                values.put(ReaderContract.Entry.COLUMN_NAME_ENTRY_ID, way.getTag());
                values.put(ReaderContract.Entry.COLUMN_NAME_LAT, point.latitude);
                values.put(ReaderContract.Entry.COLUMN_NAME_LNG, point.longitude);

                long rowId = db.insert(
                        ReaderContract.Entry.TABLE_NAME,
                        null,
                        values);

                if(rowId == -1) {
                    db.close();
                    return false;
                }
            }

            ContentValues values = new ContentValues();
            values.put(ReaderContract.Entry.COLUMN_NAME_ENTRY_ID, way.getTag());
            values.put(ReaderContract.Entry.COLUMN_NAME_DESC, way.getDescription());

            long rowId = db.insert(
                    ReaderContract.Entry.DESC_TABLE_NAME,
                    null,
                    values);

            if(rowId == -1) {
                db.close();
                return false;
            }

            db.close();
            return true;
        } else {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MSG);
        }
    }

    public <T> boolean read(@NonNull T classToBeFilled, Object... args) throws SQLException, IllegalArgumentException {
        if(classToBeFilled instanceof TrackingWayRefBase) {
            TrackingWayRefBase way = (TrackingWayRefBase)classToBeFilled;

            if(way.isNill()) {
                return false;
            }

            Object tag_obj = args[0];
            if(tag_obj == null) {
                throw  new IllegalArgumentException(ILLEGAL_ARGUMENTS_LIST_MSG);
            }

            String tag;
            try {
                tag = (String)tag_obj;
            } catch (ClassCastException cce) {
                cce.printStackTrace();
                throw  new IllegalArgumentException(ILLEGAL_ARGUMENTS_LIST_MSG);
            }

            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            String select = "SELECT * FROM " + ReaderContract.Entry.TABLE_NAME
                    + " WHERE tag=" + "'" + tag + "';";

            Cursor cursor = db.rawQuery(select, null);

            if(cursor == null) {
                db.close();
                return false;
            }

            cursor.moveToFirst();
            Double lat;
            Double lng;
            do {
                lat = cursor.getDouble(1);
                lng = cursor.getDouble(2);

                way.pushPoint(new LatLng(lat, lng));
            } while (cursor.moveToNext());

            cursor.close();

            select = "SELECT " + ReaderContract.Entry.COLUMN_NAME_DESC + " FROM "
                    + ReaderContract.Entry.DESC_TABLE_NAME + " WHERE tag=" + "'" + tag + "';";

            cursor = db.rawQuery(select, null);

            if(cursor == null) {
                way.setDescription(EMPTY_STRING);
            } else {
                if(!cursor.moveToFirst()) {
                    db.close();
                    return false;
                }

                way.setDescription(cursor.getString(0));
                way.setTag(tag);
                way.setTitle(tag);
            }

            db.close();

            return true;
        } else {
            throw  new IllegalArgumentException(ILLEGAL_ARGUMENT_MSG);
        }
    }

    public <T> boolean readAll(@NonNull List<T> mClassesToBeFilled) throws IllegalArgumentException, SQLException{
        if(mClassesToBeFilled.get(0) instanceof TrackingWayRefBase) {
            mClassesToBeFilled.clear();
            List<TrackingWayRefBase> list;
            try {
                list = (ArrayList<TrackingWayRefBase>) mClassesToBeFilled;
            } catch (ClassCastException cce) {
                try {
                    list = (LinkedList<TrackingWayRefBase>)mClassesToBeFilled;
                } catch (ClassCastException cce_sec) {
                    throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MSG);
                }
            }

            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            String select = "SELECT * FROM " + ReaderContract.Entry.DESC_TABLE_NAME + ";";

            Cursor cursor = db.rawQuery(select, null);
            if(cursor == null) {
                return false;
            }

            if(!cursor.moveToFirst()) {
                return false;
            }

            do {
                String tag = cursor.getString(0);
                list.add(new TrackingWay());
                read(list.get(list.size() - 1), tag);
            } while(cursor.moveToNext());

            cursor.close();
            db.close();

            return true;
        } else {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MSG);
        }
    }

    public void deleteWays() throws SQLException {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.execSQL(ReaderDbHelper.SQL_DELETE_POINTS_ENTRIES);
        db.execSQL(ReaderDbHelper.SQL_DELETE_DESC_ENTRIES);
    }

    public void delete(String tag) throws SQLException {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String remove = "DELETE FROM " + ReaderContract.Entry.DESC_TABLE_NAME
                + " WHERE tag=" + "'" + tag + "';";
        db.execSQL(remove);

        remove = "DELETE FROM " + ReaderContract.Entry.TABLE_NAME
                + " WHERE tag=" + "'" + tag + "';";
        db.execSQL(remove);

        db.close();
    }
}
