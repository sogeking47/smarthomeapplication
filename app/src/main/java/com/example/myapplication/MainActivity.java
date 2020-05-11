package com.example.myapplication;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.models.timestamp_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    private TextView status;
    private Button logout;
    private String name,action,notify_msg;
    private boolean Reminderexist = false;



    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseDatabase lock_db=FirebaseDatabase.getInstance();
    DatabaseReference status_lock=lock_db.getReference();
    DatabaseReference history=lock_db.getReference("history");
    DatabaseReference user_info=lock_db.getReference("user_info");
    DatabaseReference reminder=lock_db.getReference("reminder");



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();


        status_lock.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("reminder")){
                    notify_msg=dataSnapshot.child("reminder").getValue().toString();
                    Reminderexist=true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        final NotificationCompat.Builder builder;

            builder = new NotificationCompat.Builder(this,"myChannel")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Reminder:")
                    .setContentText("water the plants")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);







        FirebaseMessaging.getInstance().subscribeToTopic("alert").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this,"SUBSCRIBED TO ALERT",Toast.LENGTH_SHORT).show();
            }
        });

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
                Toast.makeText(MainActivity.this, "loggedout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,login_activity.class));
                finish();
            }
        });


        Button lock_unlock = findViewById(R.id.servo_control);
        lock_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Reminderexist)
                {
                    notificationManagerCompat.notify(1,builder.build() );
                }

                FirebaseDatabase.getInstance().getReference().child("status").addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        String value = dataSnapshot.getValue(String.class);
                        String time = current_time();
                        String date = current_date();
                        if (value.equals("open"))
                        {
                            timestamp_user point_in_time=new timestamp_user(name,"locked",time);
                            status_lock.child("status").setValue("close");
                            history.child(date).child("date").setValue(date);
                            history.child(date+"/time").push().setValue(point_in_time);
                        }
                        else
                        {
                            timestamp_user point_in_time=new timestamp_user(name,"unlocked",time);
                            status_lock.child("status").setValue("open");
                            history.child(date).child("date").setValue(date);
                            history.child(date+"/time").push().setValue(point_in_time);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                    }
                });
            }
        });

    }

    public void lock(View view)
    {


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


    public void adduser(View view) {
     startActivity(new Intent(this, add_user_activity.class));
    }

    public void history(View view){
      startActivity(new Intent(MainActivity.this, history_activity.class));
    }



    public void streamlive(View view){
        startActivity(new Intent(this, stream_live_activity.class));
    }

    public void setReminder(View view){
        startActivity(new Intent(this, reminder_activity.class));
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channeltouse";
            String description = "channel for reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("myChannel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
