package com.example.jmcghee.movementpatterns.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jmcghee.movementpatterns.R;
import com.example.jmcghee.movementpatterns.activities.MovementIndexActivity;
import com.example.jmcghee.movementpatterns.adapters.MovementAdapter;
import com.example.jmcghee.movementpatterns.data.Movement;

import java.io.Serializable;
import java.util.List;

public class MovementIndexFragment extends Fragment implements MovementIndexActivity.DataUpdateListener {
    private static final String MOVEMENT_KEY = "movement_key";
    private List<Movement> movementsList;
    private MovementAdapter adapter;

    public static MovementIndexFragment init(List<Movement> movementsList) {
        MovementIndexFragment movementIndexFragment = new MovementIndexFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MOVEMENT_KEY, (Serializable) movementsList);
        movementIndexFragment.setArguments(bundle);

        return movementIndexFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_movement_index, container, false);

        Context context = getActivity();
        movementsList = (List<Movement>) getArguments().get(MOVEMENT_KEY);

        // Set up the RecyclerView
        RecyclerView recyclerView = viewGroup.findViewById(R.id.rv_movements);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new MovementAdapter(context, movementsList);
        recyclerView.setAdapter(adapter);

        return viewGroup;
    }

    @Override
    public void onDataUpdated(List<Movement> movementList) {
        adapter.updateMovementsList(movementList);
    }
}
