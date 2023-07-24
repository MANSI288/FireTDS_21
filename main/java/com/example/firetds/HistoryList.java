package com.example.firetds;

import android.os.Bundle;

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

public class HistoryList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    TdsAdapter tdsAdapter;
    ArrayList<TdsData> list;

    String key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historylist);

        //find toolbar by id
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.historyList);
        databaseReference = FirebaseDatabase.getInstance().getReference("TDSList");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        //tdsAdapter = new TdsAdapter(HistoryList.this, list);
        tdsAdapter = new TdsAdapter(this, list);
        recyclerView.setAdapter(tdsAdapter);




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){


                    TdsData tdsData = dataSnapshot.getValue(TdsData.class);

                    tdsData.setKey(dataSnapshot.getKey());
                    list.add(tdsData);
                    key = dataSnapshot.getKey();


                }

                tdsAdapter.notifyDataSetChanged();





            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

    }

}
