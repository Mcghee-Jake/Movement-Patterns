package com.example.jmcghee.movementpatterns.activities;

import com.example.jmcghee.movementpatterns.R;
import com.example.jmcghee.movementpatterns.data.MovementDBHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SequenceGeneratorActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence_generator);

        // Get the data
        MovementDBHelper dbHelper = new MovementDBHelper(this);
        mDb = dbHelper.getReadableDatabase();
        Cursor movementList = getAllMovements();
    }

    private Cursor getAllMovements() {
        return mDb.query(
                MovementDBHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MovementDBHelper.COLUMN_NAME
        );
    }
}
