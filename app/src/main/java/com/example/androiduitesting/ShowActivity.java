package com.example.androiduitesting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_activity);

        String cityName = getIntent().getStringExtra("city_name");

        TextView tv = findViewById(R.id.city_display_text);
        tv.setText(cityName);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
    }
}
