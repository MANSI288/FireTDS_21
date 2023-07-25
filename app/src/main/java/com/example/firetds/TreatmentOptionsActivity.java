package com.example.firetds;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.text.Html;
import android.os.Bundle;
import android.widget.TextView;

public class TreatmentOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_options);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Current TDS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the ppm value from intent extras
        int ppm = getIntent().getIntExtra("ppm", 0);

        // Initialize the TextView to display treatment options
        TextView treatmentOptionsTextView = findViewById(R.id.treatmentOptionsTextView);

        // Adjust the treatment options based on the ppm value
        String treatmentOptions;
        if (ppm >= 150 && ppm < 300) {
            treatmentOptions = "<ol>" +
                    "<li>Change 1/3 of the pool water.</li>" +
                    "</ol>";
        } else if (ppm >= 300 && ppm < 1000) {
            treatmentOptions = "<ol>" +
                    "<li>Use disinfection water and other cleaning supplies.</li>" +
                    "</ol>";
        } else if (ppm >= 1000) {
            treatmentOptions = "<ol>" +
                    "<li>All the water needs to be drained, then the swimming pool needs to be cleaned before adding new water.</li>" +
                    "</ol>";
        } else {
            treatmentOptions = "<ol>" +
                    "<li>Add chlorine to the pool.</li>" +
                    "<li>Use an algaecide to control algae growth.</li>" +
                    "<li>Balance pH levels with appropriate chemicals.</li>" +
                    "<li>Use a pool clarifier to improve water clarity.</li>" +
                    "<li>Regularly clean the pool filters.</li>" +
                    "</ol>";
        }

        // Display the treatment options in the TextView
        treatmentOptionsTextView.setText(Html.fromHtml(treatmentOptions, Html.FROM_HTML_MODE_COMPACT));
        treatmentOptionsTextView.setTextColor(getResources().getColor(android.R.color.white));
    }
}
