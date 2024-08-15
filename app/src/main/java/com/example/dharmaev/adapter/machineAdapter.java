package com.example.dharmaev.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dharmaev.R;
import com.example.dharmaev.model.machine;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class machineAdapter extends RecyclerView.Adapter<machineAdapter.ViewHolder> {

    private Context context;
    private List<machine> machineList;

    public machineAdapter(Context context, List<machine> machineList) {
        this.context = context;
        this.machineList = machineList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_machine, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        machine mach = machineList.get(position);
        Log.d("idKuy", mach.getIdMachine());
        if (holder.idMachine != null) {
            holder.idMachine.setText("ID: " + mach.getIdMachine());
        } else {
            Log.e("machineAdapter", "idMachine TextView is null");
        }
        if (holder.identity != null) {
            holder.identity.setText("Identity: " + mach.getIdentity());
        } else {
            Log.e("machineAdapter", "identity TextView is null");
        }
    }

    @Override
    public int getItemCount() {
        return machineList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView idMachine, identity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idMachine = itemView.findViewById(R.id.idMachine);
            identity = itemView.findViewById(R.id.identity);
        }


    }


}
