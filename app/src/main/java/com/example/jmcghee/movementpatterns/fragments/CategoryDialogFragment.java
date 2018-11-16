package com.example.jmcghee.movementpatterns.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.example.jmcghee.movementpatterns.R;
import com.example.jmcghee.movementpatterns.data.MovementDBHelper;

public class CategoryDialogFragment extends DialogFragment {

    public interface CategoryDataPasser {
        void onCategoryDataPassed(ContentValues cv);
    }

    private CategoryDataPasser categoryDataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        categoryDataPasser = (CategoryDataPasser) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialogfragment_category, container, false);

        final EditText etCategoryName = view.findViewById(R.id.et_category_name);

        // Dismiss dialog
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // Pass category data to parent activity
        Button btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get name
                String categoryName = etCategoryName.getText().toString();
                if (categoryName.isEmpty()) return; // Exit if nothing was entered

                ContentValues categoryData = new ContentValues();
                categoryData.put(MovementDBHelper.KEY_CATEGORY_NAME, categoryName);
                categoryDataPasser.onCategoryDataPassed(categoryData);

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
