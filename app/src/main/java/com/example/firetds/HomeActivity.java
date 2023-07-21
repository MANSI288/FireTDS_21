package com.example.firetds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    public Button testButton;
    public Toolbar toolbar; //declare toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the testButton view by ID
        testButton = findViewById(R.id.testButton);
        
        //find toolbar by id
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Check if the testButton is not null before setting the click listener
        if (testButton != null) {
            testButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToTestActivity();
                }
            });
        }
    }




    public void goToTestActivity() {
        Intent intent = new Intent(HomeActivity.this, TestActivity.class);
        startActivity(intent);
    }
}
