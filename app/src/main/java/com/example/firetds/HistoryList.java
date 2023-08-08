package com.example.firetds;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HistoryList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    TdsAdapter tdsAdapter;
    ArrayList<TdsData> list;
    Map<String, TdsData> maxTdsPerHourMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historylist);

        Toolbar toolbar_back = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_back);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        recyclerView = findViewById(R.id.historyList);
        databaseReference = FirebaseDatabase.getInstance().getReference("TDSList");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        maxTdsPerHourMap = new HashMap<>();
        tdsAdapter = new TdsAdapter(this, list);
        recyclerView.setAdapter(tdsAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                maxTdsPerHourMap.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TdsData tdsData = dataSnapshot.getValue(TdsData.class);
                    tdsData.setKey(dataSnapshot.getKey());

                    list.add(tdsData);

                    String hour = tdsData.getDate().split(":")[0];

                    if (!maxTdsPerHourMap.containsKey(hour) || maxTdsPerHourMap.get(hour).getPpm1() < tdsData.getPpm1()) {
                        maxTdsPerHourMap.put(hour, tdsData);
                    }
                }
                tdsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Button predictionButton = findViewById(R.id.predictionButton);
        predictionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maxTdsPerHourMap.size() < 2) {
                    Toast.makeText(HistoryList.this, "Not enough data to calculate trend.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Create a sorted list of TDS data from the map
                ArrayList<TdsData> maxTdsList = new ArrayList<>(maxTdsPerHourMap.values());
                Collections.sort(maxTdsList, (tdsData1, tdsData2) -> tdsData1.getDate().compareTo(tdsData2.getDate()));
                // Calculate total and average of TDS measurements
                int total = 0;
                for (TdsData tdsData : maxTdsList) {
                    total += tdsData.getPpm1();
                }
                float averageTds = (float) total / maxTdsList.size();
                // Calculate remaining PPM and estimated time
                int remainingPpm = 1500 - maxTdsList.get(maxTdsList.size() - 1).getPpm1();
                float estimatedHours = remainingPpm / averageTds;

                Intent intent = new Intent(HistoryList.this, PredictionActivity.class);
                intent.putExtra("prediction", Math.round(estimatedHours));
                startActivity(intent);
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
                Intent intent = new Intent(HistoryList.this, Login.class);
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
        Intent intent = new Intent(HistoryList.this, PredictionActivity.class);
        startActivity(intent);
    }


}
