package com.example.mynotes;

import android.provider.BaseColumns;

public class NotesContract {
    public static final class NotesEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLOMN_TITLE = "title";
        public static final String COLOMN_DESCRIPTION = "description";
        public static final String COLOMN_DAY_OF_WEEK = "day_of_week";
        public static final String COLOMN_PRIORITY = "priority";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + _ID + " " + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT, " + COLOMN_TITLE +
                " " + TYPE_TEXT + ", " + COLOMN_DESCRIPTION + " " + TYPE_TEXT + ", " + COLOMN_DAY_OF_WEEK +
                " " + TYPE_INTEGER + ", " + COLOMN_PRIORITY + " " + TYPE_INTEGER + ")";

        public static final String DPOP_COMMAND = "DROP TABLE IF EXISTS " + TABLE_NAME;


    }
}
