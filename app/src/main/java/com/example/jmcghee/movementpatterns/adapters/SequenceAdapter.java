package com.example.jmcghee.movementpatterns.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jmcghee.movementpatterns.Movement;
import com.example.jmcghee.movementpatterns.R;

import java.util.List;

public class SequenceAdapter extends RecyclerView.Adapter<SequenceAdapter.SequenceViewHolder> {

    List<Movement> movementList;

    class SequenceViewHolder extends RecyclerView.ViewHolder {

        TextView tvMovementName;
        ImageView downArrow;

        public SequenceViewHolder(View itemView) {
            super(itemView);
            tvMovementName = itemView.findViewById(R.id.tv_movement_name);
            downArrow = itemView.findViewById(R.id.img_down_arrow);
        }
    }

    @NonNull
    @Override
    public SequenceAdapter.SequenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.viewholder_sequence, parent, false);
        SequenceViewHolder viewHolder = new SequenceViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SequenceViewHolder holder, int position) {
        holder.tvMovementName.setText(movementList.get(position).getName());
        if (position + 1 == movementList.size()) {
            holder.downArrow.setVisibility(View.GONE);
        } else {
            holder.downArrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (movementList == null) {
            return 0;
        } else {
            return movementList.size();
        }
    }

    public void updateList(List<Movement> movementList) {
        this.movementList = movementList;
        if (movementList != null) {
            this.notifyDataSetChanged();
        }
    }
}
