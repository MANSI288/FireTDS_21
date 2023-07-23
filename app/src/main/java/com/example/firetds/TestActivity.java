package com.example.firetds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TestActivity extends AppCompatActivity {

    //declare buttons
    private TextView tdsTitleText, tdsValueDisplay, InsightTextView;
    private Button treatmentDetailButton; // Declare the button
    private Button SaveDataButton;
    private Button HistoryButton;
    private FirebaseAnalytics mFirebaseAnalytics;
    private DatabaseReference mDatabase;
    // ...

    String tdsStatus;
    double ppm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        treatmentDetailButton = findViewById(R.id.treatmentDetailButton);
        treatmentDetailButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the TreatmentOptionsActivity when the button is clicked
                Intent intent = new Intent(TestActivity.this, TreatmentOptionsActivity.class);
                startActivity(intent);
            }
        });



        // FVBI
        tdsTitleText = findViewById(R.id.tdsTitleText);
        tdsValueDisplay = findViewById(R.id.tdsValueDisplay);
        InsightTextView = findViewById(R.id.InsightTextView);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TDS");

        // myRef.setValue("Hello, World!");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                tdsStatus = dataSnapshot.child("ppm").getValue().toString();

                tdsValueDisplay.setText(tdsStatus);

                ppm = dataSnapshot.child("ppm").getValue(double.class);

                if (ppm < 100) {
                    InsightTextView.setText("Water TDS is acceptable");
                    treatmentDetailButton.setVisibility(View.GONE);
                }
                if(ppm<150){
                    InsightTextView.setText("Water TDS is acceptable");
                    treatmentDetailButton.setVisibility(View.VISIBLE);
                }
                else {
                    InsightTextView.setText("Water TDS is Unacceptable");
                    treatmentDetailButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("ppm", "Failed to read value.", error.toException());
            }
        });

        SaveDataButton = findViewById(R.id.SaveData);
        SaveDataButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the TreatmentOptionsActivity when the button is clicked
                SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd '@' HH:mm");
                String date = df.format(Calendar.getInstance().getTime());
                myRef.child("test").push().setValue(date + "\n " + tdsStatus + " ppm" );

                // Display the Toast message
                Toast.makeText(TestActivity.this, "Data saved in history", Toast.LENGTH_SHORT).show();
            }
        });

        // OnClickListener
    }
}
