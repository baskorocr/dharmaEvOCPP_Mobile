package com.example.dharmaev;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dharmaev.adapter.machineAdapter;
import com.example.dharmaev.model.machine;
import androidx.appcompat.widget.Toolbar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class activity_machine extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<machine> machines = new ArrayList<>();
    private machineAdapter adapter;



    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.machineRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String stationName = intent.getStringExtra("stationName");

        Log.d("info", "StationName: " + stationName);

        getData(stationName);

        // Handle the back button press
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Your custom back button logic here
                // For example, finish the activity
                finish();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Closes the activity and navigates back
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getData(String stationName) {
        String url = "https://cgs.dharmap.com/laraApi/api/machine/" + stationName;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                List<machine> machines = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    machine mach = new machine();
                                    mach.setIdMachine(jsonObject.getString("idMachine"));
                                    mach.setIdentity(jsonObject.getString("identity"));
                                    machines.add(mach);
                                    Log.d("Machine Data", "ID: " + mach.getIdMachine() + ", Identity: " + mach.getIdentity());
                                }

                                adapter = new machineAdapter(activity_machine.this, machines); // Use 'this' here
                                recyclerView.setAdapter(adapter);
                            } else {
                                Log.e("API Error", "Success flag is false or data not available.");
                            }
                        } catch (JSONException e) {
                            Log.e("JSON Error", "Failed to parse JSON response", e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API Error", "Failed to fetch data", error);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


}
