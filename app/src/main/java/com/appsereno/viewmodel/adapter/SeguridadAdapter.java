package com.appsereno.viewmodel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsereno.model.SeguridadModel;
import com.appsereno.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SeguridadAdapter extends RecyclerView.Adapter<SeguridadAdapter.SeguridadAdapterHolder> {

    private SeguridadModel seguridadModel;

    public SeguridadAdapter(SeguridadModel seguridadModel) {
        this.seguridadModel = seguridadModel;
    }

    @NonNull
    @Override
    public SeguridadAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seguridad_response, parent, false);

        return new SeguridadAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SeguridadAdapterHolder holder,int pod) {

        holder.mesage.setText(seguridadModel.getMessage());
        holder.data.setText(seguridadModel.getData());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void setData(SeguridadModel seguridadModel) {
        this.seguridadModel = seguridadModel;
        notifyDataSetChanged();
    }

    public class SeguridadAdapterHolder extends RecyclerView.ViewHolder {
        private TextView mesage;
        private TextView data;

        public SeguridadAdapterHolder(@NonNull View itemView) {
            super(itemView);
            mesage = itemView.findViewById(R.id.messageResp);
            data = itemView.findViewById(R.id.data);
        }
    }
}