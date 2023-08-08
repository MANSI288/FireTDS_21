package com.example.firetds;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PredictionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        Toolbar toolbar_back = findViewById(R.id.header);
        setSupportActionBar(toolbar_back);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        int prediction = getIntent().getIntExtra(getString(R.string.text_prediction), 0);

        TextView predictionTextView = findViewById(R.id.prediction_text);
        if (prediction <= 0) {
            predictionTextView.setText(getString(R.string.clean_now));
        } else {//new change

            predictionTextView.setText(getString(R.string.text_predict) + prediction + getString(R.string.text_days));

        }
    }
}
