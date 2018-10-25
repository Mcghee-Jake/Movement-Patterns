package com.example.jmcghee.movementpatterns.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MovementDBHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "movementPatterns.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_MOVEMENT = "movement";
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_MOVEMENT_CATEGORIES = "movement_categories";

    // Column Names

        // Common Column Names
        public static final String KEY_ID = "id";

        // Movement Table - Column Names
        public static final String KEY_MOVEMENT_NAME = "name";

        // Category Table - Column Names
        public static final String KEY_CATEGORY_NAME ="category";

        // Movement_Category Table - Column Names
        public static final String KEY_MOVEMENT_ID = "movement_id";
        public static final String KEY_CATEGORY_ID = "category_id";

    // Create Table Statements

        // Movement Table Create Statement
        final String CREATE_MOVEMENT_TABLE = "CREATE TABLE " + TABLE_MOVEMENT + " (" +
                KEY_ID + "INTEGER PRIMARY KEY, " +
                KEY_MOVEMENT_NAME + " TEXT" + ")";

        // Category Table Create Statement
        final String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + " (" +
                KEY_ID + "INTEGER PRIMARY KEY, " +
                KEY_CATEGORY_NAME + " TEXT" + ")";

        // Movement_Category Create Statement
        final String CREATE_MOVEMENT_CATEGORY_TABLE = "CREATE TABLE " + TABLE_MOVEMENT_CATEGORIES + " (" +
                KEY_ID + "INTEGER PRIMARY KEY, " +
                KEY_MOVEMENT_ID + " INTEGER, " +
                KEY_CATEGORY_ID + " INTEGER" + ")";
                

    public MovementDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVEMENT_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_MOVEMENT_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    }

    public static List<Movement> makeMovementList(Cursor cursor) {

        List<Movement> movementList = new ArrayList<>();
        Movement movement;

        if (cursor.moveToFirst()) {
            do {
                movement = getMovement(cursor);
                movementList.add(movement);
            } while (cursor.moveToNext());
        }

        return movementList;
    }


    public static Movement getMovement(Cursor cursor) {
        String movementName = cursor.getString(cursor.getColumnIndex(KEY_MOVEMENT_NAME));

        return new Movement(movementName);
    }

    public static List<String> makeCategoryList(Cursor cursor) {

        List<String> categoriesList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String category = cursor.getString(cursor.getColumnIndex(KEY_CATEGORY_NAME));
                categoriesList.add(category);
            } while (cursor.moveToNext());
        }

        return categoriesList;
    }

}
