package com.example.jmcghee.movementpatterns;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jmcghee.movementpatterns.data.MovementDBHelper;

import java.util.List;

public class MovementAdapter extends RecyclerView.Adapter<MovementAdapter.MovementViewHolder>{

    private Cursor movementList;

    public MovementAdapter(Cursor cursor) {
        this.movementList = movementList;
    }

    class MovementViewHolder extends RecyclerView.ViewHolder {

        final TextView tvMovementName;

        public MovementViewHolder(View itemView) {
            super(itemView);
            tvMovementName = itemView.findViewById(R.id.tv_movement_name);
        }
    }

    @NonNull
    @Override
    public MovementAdapter.MovementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.viewholder_movement, parent, false);
        MovementViewHolder viewHolder = new MovementViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovementAdapter.MovementViewHolder holder, int position) {
        if (!movementList.moveToPosition(position)) return;
        String movementName = movementList.getString(movementList.getColumnIndex(MovementDBHelper.COLUMN_NAME));
        holder.tvMovementName.setText(movementName);
    }

    @Override
    public int getItemCount() {
        return movementList.getCount();
    }

    public void updateCursor(Cursor newCursor) {
        if (movementList != null) movementList.close();
        movementList = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

}
