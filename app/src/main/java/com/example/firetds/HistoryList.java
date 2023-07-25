package com.example.firetds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class HistoryList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    TdsAdapter tdsAdapter;
    ArrayList<TdsData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historylist);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.historyList);
        databaseReference = FirebaseDatabase.getInstance().getReference("TDSList");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        tdsAdapter = new TdsAdapter(this, list);
        recyclerView.setAdapter(tdsAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the list before adding items
                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TdsData tdsData = dataSnapshot.getValue(TdsData.class);
                    tdsData.setKey(dataSnapshot.getKey());
                    list.add(tdsData);
                }
                tdsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        Button predictionButton = findViewById(R.id.predictionButton);
        predictionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() < 2) {
                    Toast.makeText(HistoryList.this, "Not enough data to calculate trend.", Toast.LENGTH_LONG).show();
                    return;
                }

                Collections.sort(list, (tdsData1, tdsData2) -> tdsData1.getDate().compareTo(tdsData2.getDate()));

                int totalIncrease = list.get(list.size() - 1).getPpm1() - list.get(0).getPpm1();
                float avgIncreasePerDay = (float) totalIncrease / (list.size() - 1);

                int remainingPpm = 1000 - list.get(list.size() - 1).getPpm1();
                float estimatedDays = remainingPpm / avgIncreasePerDay;

                Intent intent = new Intent(HistoryList.this, PredictionActivity.class);
                intent.putExtra("prediction", Math.round(estimatedDays));
                startActivity(intent);
            }
        });
    }
}
