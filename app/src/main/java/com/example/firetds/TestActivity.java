package com.example.firetds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TestActivity extends AppCompatActivity {

    //declare buttons
    private TextView tdsTitleText, tdsValueDisplay, InsightTextView;
    private Button treatmentDetailButton, saveButton; // Declare the button
    private FirebaseAnalytics mFirebaseAnalytics;
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;
    // ...

    String tdsStatus;
    int ppm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        treatmentDetailButton = findViewById(R.id.treatmentDetailButton);
        treatmentDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the TreatmentOptionsActivity when the button is clicked
                Intent intent = new Intent(TestActivity.this, TreatmentOptionsActivity.class);
                intent.putExtra("ppm", ppm);

                startActivity(intent);
            }
        });

        // FVBI
        tdsTitleText = findViewById(R.id.tdsTitleText);
        tdsValueDisplay = findViewById(R.id.tdsValueDisplay);
        InsightTextView = findViewById(R.id.InsightTextView);
        saveButton = findViewById(R.id.saveButton);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TDS");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                tdsStatus = dataSnapshot.child("ppm").getValue().toString();

                tdsValueDisplay.setText(tdsStatus);

                ppm = dataSnapshot.child("ppm").getValue(int.class);

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

        // OnClickListener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("TDSList");

                java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy.MM.dd '@' HH:mm");
                String date = df.format(java.util.Calendar.getInstance().getTime());
                int ppm1 = ppm;
                String insight;

                if (ppm < 100) {
                    insight = "Acceptable Water TDS";

                }
                if (ppm < 150) {
                    insight = "Acceptable Water TDS";

                }else{
                    insight = "Unacceptable Water TDS";
                }

                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(new java.util.Date());
                int hour = cal.get(java.util.Calendar.HOUR_OF_DAY);

                TdsData obj = new TdsData(date,insight, ppm1, hour);
                myRef.push().setValue(obj);
            }
        });
    }
}
