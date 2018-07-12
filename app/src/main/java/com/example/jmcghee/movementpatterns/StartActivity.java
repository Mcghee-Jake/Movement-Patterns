package com.example.jmcghee.movementpatterns;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btnMovementIndex = findViewById(R.id.btn_movement_index);
        btnMovementIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MovementIndexActivity on click
                Class movementIndexActivity = MovementIndexActivity.class;
                Intent intent = new Intent(StartActivity.this, movementIndexActivity);
                startActivity(intent);
            }
        });
    }
}
