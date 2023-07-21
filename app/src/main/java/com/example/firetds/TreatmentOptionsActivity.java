package com.example.firetds;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

public class TreatmentOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_options);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Initialize the TextView to display treatment options
        TextView treatmentOptionsTextView = findViewById(R.id.treatmentOptionsTextView);

        // Example treatment options, you can customize this as needed
        String treatmentOptions = "1. Add chlorine to the pool.\n" +
                "2. Use an algaecide to control algae growth.\n" +
                "3. Balance pH levels with appropriate chemicals.\n" +
                "4. Use a pool clarifier to improve water clarity.\n" +
                "5. Regularly clean the pool filters.";

        // Display the treatment options in the TextView
        treatmentOptionsTextView.setText(treatmentOptions);
    }
}
