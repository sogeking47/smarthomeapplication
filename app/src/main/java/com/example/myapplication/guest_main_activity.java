package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.models.timestamp_user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class guest_main_activity extends AppCompatActivity {

    private TextView status;
    private Button logout;
    private String name,time;



    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseDatabase lock_db=FirebaseDatabase.getInstance();
    DatabaseReference status_lock=lock_db.getReference();
    DatabaseReference history=lock_db.getReference("history");
    DatabaseReference user_info=lock_db.getReference("user_info");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_main_activity);


        user_info.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name=dataSnapshot.child(auth.getCurrentUser().getUid()+"/name").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(guest_main_activity.this, "loggedout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(guest_main_activity.this,login_activity.class));
                finish();
            }
        });

    }

    public void lock(View view)
    {

        FirebaseDatabase.getInstance().getReference().child("status").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String value = dataSnapshot.getValue(String.class);
                time = current_time();

                if (value.equals("open"))
                {
                    time=current_time();
                    timestamp_user point_in_time=new timestamp_user(name,"locked",time);
                    status_lock.child("status").setValue("close");
                    history.child(current_date()).push().setValue(point_in_time);
                }
                else
                {
                    time=current_time();
                    timestamp_user point_in_time=new timestamp_user(name,"unlocked",time);
                    status_lock.child("status").setValue("open");
                    history.child(current_date()).push().setValue(point_in_time);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
    }


    public String current_date(){
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
        String current_date=sdf_date.format(calendar.getTime());
        return current_date;
    }

    public String current_time(){
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf_time =new SimpleDateFormat("HH:mm:ss");
        String current_time=sdf_time.format(calendar.getTime());
        return current_time;
    }





}
