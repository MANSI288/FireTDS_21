package com.example.firetds;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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


        // Initialize the TextView to display treatment options
        TextView treatmentOptionsTextView = findViewById(R.id.treatmentOptionsTextView);

        // Example treatment options, you can customize this as needed
        String treatmentOptions = "<ol>" +
                "<li>Add chlorine to the pool.</li>" +
                "<li>Use an algaecide to control algae growth.</li>" +
                "<li>Balance pH levels with appropriate chemicals.</li>" +
                "<li>Use a pool clarifier to improve water clarity.</li>" +
                "<li>Regularly clean the pool filters.</li>" +
                "</ol>";

        // Display the treatment options in the TextView
        treatmentOptionsTextView.setText(Html.fromHtml(treatmentOptions, Html.FROM_HTML_MODE_COMPACT));
        treatmentOptionsTextView.setTextColor(getResources().getColor(android.R.color.white));
    }
    }

