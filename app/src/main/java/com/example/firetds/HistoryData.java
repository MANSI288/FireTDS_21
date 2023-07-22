package com.example.firetds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

public class HistoryData extends AppCompatActivity {
    private TextView Data;
    public Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_data);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Water test");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Data=findViewById(R.id.Data);
        Data.setText("Data");

    }
}
