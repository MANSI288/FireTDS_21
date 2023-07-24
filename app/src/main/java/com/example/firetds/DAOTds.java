package com.example.firetds;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOTds {
    private DatabaseReference databaseReference;
    public DAOTds(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(TdsData.class.getSimpleName());

    }

    public Task<Void> remove(String key)
    {
        FirebaseDatabase.getInstance().getReference().child("TDSList").child(key).removeValue();
        return databaseReference.child(key).removeValue();

    }



}
