package com.example.jmcghee.movementpatterns.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.jmcghee.movementpatterns.Movement;
import com.example.jmcghee.movementpatterns.R;
import com.example.jmcghee.movementpatterns.adapters.SequenceAdapter;
import com.example.jmcghee.movementpatterns.data.MovementDBHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SequenceGeneratorActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private SequenceAdapter adapter;
    private List<Movement> movementList;
    private SeekBar seekBar;
    private String focus_movement_entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence_generator);

        // Get the database
        MovementDBHelper dbHelper = new MovementDBHelper(this);
        mDb = dbHelper.getReadableDatabase();

        // Get the data
        movementList = MovementDBHelper.makeMovementList(getAllMovements());

        // Set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_sequence);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new SequenceAdapter();
        recyclerView.setAdapter(adapter);
        generateSequence();

        // Set up the fab
        FloatingActionButton fab = findViewById(R.id.fab_new_sequence);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateSequence();
            }
        });

        // Set up the bottom sheet header
        LinearLayout bottomSheetLayout = findViewById(R.id.bottom_sheet_layout);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        View bottomSheatHeader = findViewById(R.id.bottom_sheet_header);
        bottomSheatHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

            }
        });

        // Set up the seekbar
        final TextView sequenceSizeTextView = findViewById(R.id.tv_sequence_size);
        seekBar = findViewById(R.id.sb_sequence_size);
        int sequenceMax = getResources().getInteger(R.integer.max_sequence_num);
        if (getAllMovements().getCount() <= sequenceMax) {
            seekBar.setMax(getAllMovements().getCount());
        } else {
            seekBar.setMax(sequenceMax);
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sequenceSizeTextView.setText("Sequence Size: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Set up the autocomplete text view
        /* This is set up so that it forces the user to select one of the
           suggested options.
         */
        final AutoCompleteTextView actv = findViewById(R.id.actv_focus_movement);
        final ArrayList<String> movementNames = new ArrayList<>();
        for (Movement movement : movementList) {
            movementNames.add(movement.getName());
        }
        final ArrayAdapter<String> actv_adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, movementNames);
        actv.setAdapter(actv_adapter);
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                focus_movement_entry = actv_adapter.getItem(position);
            }
        });
        actv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String entered_text = actv.getText().toString();
                boolean nameFound = false;
                for (String movementName : movementNames) {
                    if (movementName.equals(entered_text)) nameFound = true;
                }
                focus_movement_entry = nameFound ? null : entered_text;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sequence_generator_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.btn_index:
                // Start MovementIndexActivity on click
                Class movementIndexActivity = MovementIndexActivity.class;
                Intent intent = new Intent(SequenceGeneratorActivity.this, movementIndexActivity);
                startActivity(intent);
            default:
                return false;
        }
    }

    private void generateSequence() {
        movementList = MovementDBHelper.makeMovementList(getAllMovements());
        Collections.shuffle(movementList);
        if (focus_movement_entry == null) {
            movementList = movementList.subList(0, seekBar.getProgress());
        } else {
            Movement focusMovement = null;
            // Find the movement in the list
            for (Movement movement : movementList) {
                if (movement.getName().equals(focus_movement_entry)) {
                    focusMovement = movement;
                }
            }
            // Separate the movement temporarily
            movementList.remove(movementList.indexOf(focusMovement));
            // Get a list of movements that is one smaller than than the sequence length
            movementList = movementList.subList(0, seekBar.getProgress() - 1);
            // Add the focusMovement to the sublist
            movementList.add(focusMovement);
            // Shuffle the list again
            Collections.shuffle(movementList);
        }
        adapter.updateList(movementList);
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
