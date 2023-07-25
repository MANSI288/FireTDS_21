package com.example.firetds;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PredictionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        int prediction = getIntent().getIntExtra("prediction", 0);

        TextView predictionTextView = findViewById(R.id.prediction_text);
        predictionTextView.setText("Estimated days until pool cleaning: " + prediction + " days");
    }
}
