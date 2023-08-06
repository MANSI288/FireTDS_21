package com.example.firetds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Find the "Get Started" button and set its click listener
        Button getStartedButton = findViewById(R.id.getStartedButton);

        Button createAccountButton = findViewById(R.id.CreateAccountButton);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the TestActivity when the button is clicked
                Intent intent = new Intent(WelcomeActivity.this, Login.class);
                startActivity(intent);

            }
        });
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

    }
}
