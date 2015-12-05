package com.system.mrlukashem.datebase;

import android.provider.BaseColumns;

/**
 * Created by mrlukashem on 14.11.15.
 */
public final class ReaderContract {

    private ReaderContract() {}

    public static abstract class Entry implements BaseColumns {

        public static final String TABLE_NAME = "trackingWayPoints";

        public static final String DESC_TABLE_NAME = "descriptionWayPoints";

        public static final String COLUMN_NAME_ENTRY_ID = "tag";

        public static final String COLUMN_NAME_LAT = "lat";

        public static final String COLUMN_NAME_LNG = "lng";

        public static final String COLUMN_NAME_DESC = "desc";
    }
}
