package com.example.firetds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    public Button testButton, HistoryData;
    public Toolbar toolbar; //declare toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the testButton view by ID
        testButton = findViewById(R.id.testButton);
        HistoryData = findViewById(R.id.HistoryData);

        //find toolbar by id
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Check if the testButton is not null before setting the click listener
        if (testButton != null) {
            testButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToTestActivity();
                }
            });
        }

        // Set click listener for the HistoryData button
        if (HistoryData != null) {
            HistoryData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to the TreatmentOptionsActivity when the button is clicked
                    Intent intent = new Intent(HomeActivity.this, HistoryData.class);
                    startActivity(intent);
                }
            });
        }
    }

    public void goToTestActivity() {
        Intent intent = new Intent(HomeActivity.this, TestActivity.class);
        startActivity(intent);
    }
}
