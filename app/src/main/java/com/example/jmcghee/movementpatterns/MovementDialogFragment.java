package com.example.jmcghee.movementpatterns;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.jmcghee.movementpatterns.data.MovementDBHelper;

public class MovementDialogFragment extends DialogFragment {

    public interface MovementDataPasser {
        void onMovementDataPassed(ContentValues cv);
    }

    MovementDataPasser movementDataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        movementDataPasser = (MovementDataPasser) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialogfragment_movement, container, false);

        // Dismiss dialog
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // Pass movement data to parent activity
        Button btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etMovementName = view.findViewById(R.id.et_movement_name);
                String movementName = etMovementName.getText().toString();
                if (movementName.length() == 0) return; // Exit if nothing was entered
                ContentValues movementData = new ContentValues();
                movementData.put(MovementDBHelper.COLUMN_NAME, movementName);
                movementDataPasser.onMovementDataPassed(movementData);
            }
        });

        return view;
    }
}
