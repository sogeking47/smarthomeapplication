package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class reminder_activity extends AppCompatActivity {

    FirebaseDatabase lock_db= FirebaseDatabase.getInstance();
    DatabaseReference remind=lock_db.getReference("reminder");
    private String reminder_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_activity);

        final EditText remind_val = findViewById(R.id.editReminder);
        Button add_remind_btn=findViewById(R.id.reminder_add_btn);

        add_remind_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminder_value=remind_val.getText().toString();
                remind.child("reminder").setValue(reminder_value);
            }
        });

    }
}
