package com.example.jmcghee.movementpatterns.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jmcghee.movementpatterns.Movement;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Movement> makeMovementList(Cursor cursor) {

        List<Movement> movementList = new ArrayList<>();
        Movement movement;

        if (cursor.moveToFirst()) {
            do {
                String movementName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                movement = new Movement(movementName);
                movementList.add(movement);
            } while (cursor.moveToNext());
        }

        return movementList;
    }
}
