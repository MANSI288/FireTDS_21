package com.example.firetds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
    private Button treatmentDetailButton, saveButton, treatmentIdealButton; // Declare the button
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

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        treatmentDetailButton = findViewById(R.id.treatmentDetailButton);
        treatmentIdealButton = findViewById(R.id.treatmentIdealButton);
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

        ImageView gaugeMeter = findViewById(R.id.ppmImageView250);


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

                //Gauge Meter
                if (ppm <= 250){
                    gaugeMeter.setImageResource(R.drawable.ppm250);
                } else if(ppm >250 && ppm <= 500){
                    gaugeMeter.setImageResource(R.drawable.ppm500);
                }else if(ppm >500 && ppm <= 750){
                    gaugeMeter.setImageResource(R.drawable.ppm750);
                }else if(ppm >750 && ppm <= 1000){
                    gaugeMeter.setImageResource(R.drawable.ppm1000);
                }else if(ppm >1000 && ppm <= 1250){
                    gaugeMeter.setImageResource(R.drawable.ppm1250);
                }else if(ppm >1250 && ppm <= 1500){
                    gaugeMeter.setImageResource(R.drawable.ppm1500);
                }else if(ppm >1500 && ppm <= 1750){
                    gaugeMeter.setImageResource(R.drawable.ppm1750);
                }else if(ppm >1750 && ppm <= 2000){
                    gaugeMeter.setImageResource(R.drawable.ppm2000);
                }else {
                    gaugeMeter.setImageResource(R.drawable.ppm2000);
                }


                if (ppm < 1000) {
                    InsightTextView.setText(getString(R.string.text_ideal));
                    treatmentDetailButton.setVisibility(View.GONE);
                    treatmentIdealButton.setVisibility(View.VISIBLE);

                } else if (ppm >1000 && ppm < 1500) {
                    InsightTextView.setText(getString(R.string.text_accept));
                    treatmentDetailButton.setVisibility(View.VISIBLE);
                    treatmentIdealButton.setVisibility(View.GONE);
                } else {
                    InsightTextView.setText(getString(R.string.text_unaccept));
                    treatmentDetailButton.setVisibility(View.VISIBLE);
                    treatmentIdealButton.setVisibility(View.GONE);
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

                if (ppm < 1000) {
                    insight = getString(R.string.text_ideal);
                } else if (ppm >1000 && ppm < 1500) {
                    insight = getString(R.string.text_accept);
                } else {
                    insight = getString(R.string.text_unaccept);
                }

                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(new java.util.Date());
                int hour = cal.get(java.util.Calendar.HOUR_OF_DAY);

                TdsData obj = new TdsData(date, insight, ppm1, hour);
                myRef.push().setValue(obj);

                // Show a toast message to indicate data is saved
                Toast.makeText(TestActivity.this, getString(R.string.text_saveprompt), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDropdownMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.dropdown_menu, popupMenu.getMenu());

        // Handle menu item click events
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_predictions) {
                navigateToPredictionsActivity();
                return true;
            } else if (item.getItemId() == R.id.menu_log_out) {
                Intent intent = new Intent(TestActivity.this, Login.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.contact_support) {
                openSupportEmailClient();

                return true;
            }
            return false;
        });

        popupMenu.show();
    }

    private void openSupportEmailClient() {
        String emailAddress = "support@example.com";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + emailAddress));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Support Request");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }




    private void navigateToPredictionsActivity() {
        Intent intent = new Intent(TestActivity.this, PredictionActivity.class);
        startActivity(intent);
    }




}
