package com.example.jmcghee.movementpatterns;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

        final EditText etMovementName = view.findViewById(R.id.et_movement_name);
        etMovementName.requestFocus();

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
                String movementName = etMovementName.getText().toString();
                if (movementName.length() == 0) return; // Exit if nothing was entered
                ContentValues movementData = new ContentValues();
                movementData.put(MovementDBHelper.COLUMN_NAME, movementName);
                movementDataPasser.onMovementDataPassed(movementData);
                dismiss();
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        // Set the width of the dialog proportional to 90% of the screen width
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }
}
