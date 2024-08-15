package com.example.dharmaev.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dharmaev.R;
import com.example.dharmaev.activity_machine;
import com.example.dharmaev.model.station;

import java.util.List;
import java.util.Locale;

public class stationAdapter extends RecyclerView.Adapter<stationAdapter.ViewHolder> {

    private List<station> stations;
    private Context context;
    private CardView cv;

    public stationAdapter(Context context, List<station> stations) {
        this.context = context;
        this.stations = stations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_station, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        station station = stations.get(position);
        holder.stationName.setText("Kode Stasiun: " + station.getStation());
        holder.location.setText("Lokasi: "+station.getLocation());

        double lat = Double.parseDouble(station.getLat());
        double lon = Double.parseDouble(station.getLon());
        holder.openMaps.setOnClickListener(v ->{

            String uri = "http://maps.google.com/maps?daddr=" + lat + "," + lon;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            context.startActivity(intent);
        });

        holder.pilih.setOnClickListener(v -> {
            Intent intent = new Intent(context, activity_machine.class);
            intent.putExtra("stationName", station.getStation());
            intent.putExtra("sellerName", station.getSeller());
            context.startActivity(intent);
        });



    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView stationName, location,  openMaps, pilih;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stationName = itemView.findViewById(R.id.stationName);

            location = itemView.findViewById(R.id.location);

     
            openMaps = itemView.findViewById(R.id.Maps);
            pilih = itemView.findViewById(R.id.pilih);

        }
    }
}
