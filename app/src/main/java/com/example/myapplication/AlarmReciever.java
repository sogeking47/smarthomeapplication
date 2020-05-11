package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        FirebaseDatabase mdb= FirebaseDatabase.getInstance();
        DatabaseReference lights=mdb.getReference("lights");
        lights.setValue(1);
    }
}
