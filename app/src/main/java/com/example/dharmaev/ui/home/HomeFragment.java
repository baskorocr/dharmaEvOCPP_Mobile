package com.example.dharmaev.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dharmaev.R;

import com.example.dharmaev.databinding.FragmentHomeBinding;
import com.example.dharmaev.model.station;

import com.example.dharmaev.adapter.stationAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private stationAdapter adapter;
    private List<station> stationList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.stationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stationList = new ArrayList<>();

        fetchData();

        return view;
    }

    private void fetchData() {
        String url = "https://cgs.dharmap.com/laraApi/api/station";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray stationsArray = response.getJSONArray("station");

                            for (int i = 0; i < stationsArray.length(); i++) {
                                JSONObject stationObject = stationsArray.getJSONObject(i);

                                station stations = new station();
                                stations.setStation(stationObject.getString("station"));
                                stations.setLocation(stationObject.getString("location"));
                                stations.setLat(stationObject.getString("Lat"));
                                stations.setLon(stationObject.getString("Long"));
                                stations.setSeller(stationObject.getString("seller"));



                                stationList.add(stations);
                            }

                            adapter = new stationAdapter(getContext(), stationList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API Error", error.getMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}