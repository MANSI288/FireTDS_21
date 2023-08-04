package com.example.firetds;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TreatmentOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_options);

        Toolbar toolbar_back = findViewById(R.id.header);
        setSupportActionBar(toolbar_back);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.header_title));

        // Get the ppm value from intent extras
        int ppm = getIntent().getIntExtra("ppm", 0);

        // Initialize the TextView to display treatment options
        TextView treatmentOptionsTextView = findViewById(R.id.treatmentOptionsTextView);

        // Adjust the treatment options based on the ppm value
        String treatmentOptions;
        if (ppm >= 1000 && ppm < 1500) {
            treatmentOptions = getString(R.string.treatment_options_medium_tds);
        } else {
            treatmentOptions = getString(R.string.treatment_options_low_tds);
        }

        // Display the treatment options in the TextView
        treatmentOptionsTextView.setText(Html.fromHtml(treatmentOptions, Html.FROM_HTML_MODE_COMPACT));
        treatmentOptionsTextView.setTextColor(getResources().getColor(android.R.color.white));
    }
}
