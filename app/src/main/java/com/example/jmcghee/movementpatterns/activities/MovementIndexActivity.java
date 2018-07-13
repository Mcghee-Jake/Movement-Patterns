package com.example.jmcghee.movementpatterns.activities;

import android.app.DialogFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jmcghee.movementpatterns.MovementAdapter;
import com.example.jmcghee.movementpatterns.R;
import com.example.jmcghee.movementpatterns.data.MovementDBHelper;


public class MovementIndexActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement_index);


        // Get the data
        MovementDBHelper dbHelper = new MovementDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
        Cursor movementList = getAllMovements();


        // Set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_movements);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        MovementAdapter adapter = new MovementAdapter(movementList);
        recyclerView.setAdapter(adapter);
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
