package com.example.jmcghee.movementpatterns;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MovementIndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement_index);

        // Get the data
        List<Movement> movementList = getDummyData();

        // Set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_movements);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        MovementAdapter adapter = new MovementAdapter(movementList);
        recyclerView.setAdapter(adapter);

    }

    List<Movement> getDummyData(){
        List<Movement> movementList = new ArrayList();

        Movement m1 = new Movement("Negativa");
        movementList.add(m1);

        Movement m2 = new Movement("Role");
        movementList.add(m2);

        return movementList;
    }


}
