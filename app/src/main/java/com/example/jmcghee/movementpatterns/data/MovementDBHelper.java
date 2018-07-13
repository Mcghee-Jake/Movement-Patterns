package com.example.jmcghee.movementpatterns.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovementDBHelper extends SQLiteOpenHelper {


    // Movement Contract
    public static final String TABLE_NAME = "movements";
    public static final String COLUMN_NAME = "name";

    private static final String DATABASE_NAME = "movementPatterns.db";
    private static final int DATABASE_VERSION = 1;

    public MovementDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVEMENT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT PRIMARY KEY)";

        db.execSQL(SQL_CREATE_MOVEMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
