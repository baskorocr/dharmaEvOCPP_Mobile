package com.example.dharmaev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "MyAppPreferences";
    private static final String CACHE_KEY = "myCache";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Check if cache exists and equals 1
        int cacheValue = sharedPreferences.getInt(CACHE_KEY, 0);
        if (cacheValue == 1) {
            // Open MainActivity2 if cache is 1
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
            finish();
        } else {
            // Stay on MainActivity if cache is not 1
            setContentView(R.layout.activity_main);
        }
    }


    public void toHome(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CACHE_KEY, 1);
        editor.apply();

        Intent intent = new Intent(this, MainActivity2.class );
        startActivity(intent);
        finish();

    }
}