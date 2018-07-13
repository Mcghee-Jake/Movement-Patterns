package com.example.jmcghee.movementpatterns;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MovementDialogFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_movement, container, false);

        EditText etMovementName = view.findViewById(R.id.et_movement_name);

        String movementName = etMovementName.getText().toString();

        return view;
    }
}
