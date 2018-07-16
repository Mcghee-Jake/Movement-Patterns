package com.example.jmcghee.movementpatterns.activities;

import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jmcghee.movementpatterns.MovementDialogFragment;
import com.example.jmcghee.movementpatterns.R;
import com.example.jmcghee.movementpatterns.adapters.MovementAdapter;
import com.example.jmcghee.movementpatterns.data.MovementDBHelper;


public class MovementIndexActivity extends AppCompatActivity implements
        MovementDialogFragment.MovementDataPasser,
        MovementAdapter.MovementOptionsClickListener {

    private SQLiteDatabase mDb;
    private MovementAdapter adapter;

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
        adapter = new MovementAdapter(this, movementList);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab_new_movement);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                DialogFragment dialogFragment = new MovementDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), getString(R.string.movement_dialog_tag));
            }
        });
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

    @Override
    public void onMovementDataPassed(ContentValues cv) {
        mDb.insert(MovementDBHelper.TABLE_NAME, null, cv);
        adapter.updateCursor(getAllMovements());
    }

    @Override
    public void deleteMovement(String movementName) {
        mDb.delete(MovementDBHelper.TABLE_NAME, MovementDBHelper.COLUMN_NAME + "='" + movementName + "'", null);
        adapter.updateCursor(getAllMovements());
    }
}
