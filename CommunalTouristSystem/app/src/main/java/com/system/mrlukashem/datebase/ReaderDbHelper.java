package com.system.mrlukashem.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mrlukashem on 14.11.15.
 */
public class ReaderDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "TrackingWayData.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String NUMBER_TYPE = " REAL";

    private static final String COMMA_SEP = ",";

    private static final String LEFT_BRACKET = "(";

    private static final String RIGHT_BRACKET = ");";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE" + ReaderContract.Entry.TABLE_NAME
            + LEFT_BRACKET + ReaderContract.Entry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE
            + COMMA_SEP
            + ReaderContract.Entry.COLUMN_NAME_LAT + NUMBER_TYPE
            + COMMA_SEP
            + ReaderContract.Entry.COLUMN_NAME_LNG + NUMBER_TYPE
            + RIGHT_BRACKET;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ReaderContract.Entry.TABLE_NAME;


    public ReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
