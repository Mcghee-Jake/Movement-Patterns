package com.example.jmcghee.movementpatterns.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jmcghee.movementpatterns.Movement;
import com.example.jmcghee.movementpatterns.R;

import java.util.List;

public class SequenceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Movement> movementList;

    private final int MOVEMENT = 0, ADD_BTN = 1;

    class SequenceViewHolder extends RecyclerView.ViewHolder {

        TextView tvMovementName;
        ImageView downArrow;

        public SequenceViewHolder(View itemView) {
            super(itemView);
            tvMovementName = itemView.findViewById(R.id.tv_movement_name);
            downArrow = itemView.findViewById(R.id.img_down_arrow);
        }
    }

    class ExtendSequenceViewHolder extends RecyclerView.ViewHolder {

        ImageButton btnExtendSequence;

        public ExtendSequenceViewHolder(View itemView) {
            super(itemView);
            btnExtendSequence = itemView.findViewById(R.id.btn_extend_sequence);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < movementList.size()) {
            return MOVEMENT;
        } else {
            return ADD_BTN;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case MOVEMENT:
                View view1 = inflater.inflate(R.layout.viewholder_sequence, parent, false);
                viewHolder = new SequenceViewHolder(view1);
                break;
            default:
                View view2 = inflater.inflate(R.layout.viewholder_extend_sequence, parent, false);
                viewHolder = new ExtendSequenceViewHolder(view2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case MOVEMENT:
                SequenceViewHolder sequenceViewHolder = (SequenceViewHolder) holder;
                sequenceViewHolder.tvMovementName.setText(movementList.get(position).getName());
                if (position + 1 == movementList.size()) {
                    sequenceViewHolder.downArrow.setVisibility(View.GONE);
                } else {
                    sequenceViewHolder.downArrow.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    @Override
    public int getItemCount() {
        if (movementList == null) {
            return 0;
        } else {
            return movementList.size() + 1;
        }
    }

    public void updateList(List<Movement> movementList) {
        this.movementList = movementList;
        if (movementList != null) {
            this.notifyDataSetChanged();
        }
    }
}
