package com.averagecoders.bunkmeter;

import android.provider.BaseColumns;

public class SqlContract {

private SqlContract(){};

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "BunkMeter";
        public static final String COLUMN_ONE = "SName";
        public static final String COLUMN_TWO = "Bunked";
        public static final String COLUMN_THREE = "TotalClass";
    }

}
