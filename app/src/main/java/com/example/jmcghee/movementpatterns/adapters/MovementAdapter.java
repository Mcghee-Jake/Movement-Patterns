package com.example.jmcghee.movementpatterns.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.jmcghee.movementpatterns.R;
import com.example.jmcghee.movementpatterns.data.MovementDBHelper;

public class MovementAdapter extends RecyclerView.Adapter<MovementAdapter.MovementViewHolder>{

    private Context context;
    private Cursor movementList;
    private MovementOptionsClickListener movementOptionsClickListener;

    public interface MovementOptionsClickListener {
        void deleteMovement(String movementName);
    }

    public MovementAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.movementList = cursor;
        movementOptionsClickListener = (MovementOptionsClickListener) context;
    }

    class MovementViewHolder extends RecyclerView.ViewHolder {

        final TextView tvMovementName;
        ImageButton btnMovementOptions;

        public MovementViewHolder(View itemView) {
            super(itemView);
            tvMovementName = itemView.findViewById(R.id.tv_movement_name);
            btnMovementOptions = itemView.findViewById(R.id.btn_movement_options);
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
    public void onBindViewHolder(@NonNull final MovementAdapter.MovementViewHolder holder, int position) {
        if (!movementList.moveToPosition(position)) return;
        final String movementName = movementList.getString(movementList.getColumnIndex(MovementDBHelper.COLUMN_NAME));
        holder.tvMovementName.setText(movementName);
        holder.btnMovementOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.btnMovementOptions);
                popup.inflate(R.menu.movement_options);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete_movement:
                                movementOptionsClickListener.deleteMovement(movementName);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });
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
