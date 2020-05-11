package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.ViewHolder.date_viewholder;
import com.example.myapplication.ViewHolder.time_viewholder;
import com.example.myapplication.models.datecls;
import com.example.myapplication.models.time;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class history_activity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<datecls, date_viewholder> adapter;
    FirebaseRecyclerAdapter<time, time_viewholder> adapter2;
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("history");

        manager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView=findViewById(R.id.recylerview);
        recyclerView.setLayoutManager(manager);

        FirebaseRecyclerOptions<datecls> options = new FirebaseRecyclerOptions.Builder<datecls>().setQuery(reference, datecls.class).build();

        //ADAPTER START
        adapter=new FirebaseRecyclerAdapter<datecls, date_viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull date_viewholder dateViewholder, int position, @NonNull datecls datecls) {
            dateViewholder.date.setText(datecls.getDate());

            FirebaseRecyclerOptions<time> options2 = new FirebaseRecyclerOptions.Builder<time>()
                    .setQuery(reference.child(datecls.getDate()).child("time"),time.class).build();

            adapter2=new FirebaseRecyclerAdapter<time, time_viewholder>(options2) {
                @Override
                protected void onBindViewHolder(@NonNull time_viewholder holder, int position, @NonNull time model) {
                    holder.time_val.setText(model.getTime());
                    holder.action_val.setText(model.getAction());
                    holder.name_val.setText(model.getUsername());
                }
                @NonNull
                @Override
                public time_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View v2=LayoutInflater.from(getBaseContext()).inflate(R.layout.time_view,parent,false);
                    return new time_viewholder(v2);
                }
            };
            adapter2.startListening();
            adapter2.notifyDataSetChanged();
            dateViewholder.recyclerviewdate.setAdapter(adapter2);

            }


            @NonNull
            @Override
            public date_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v1= LayoutInflater.from(getBaseContext()).inflate(R.layout.date_view,parent,false);
                return new date_viewholder(v1);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);





    }
}
