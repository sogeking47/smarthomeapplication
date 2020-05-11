package com.example.myapplication;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login_activity extends AppCompatActivity {

    //APP VARIABLES
    private EditText email;
    private EditText password;
    private Button login;
    private Button new_login;
    private String txt_email;
    private String txt_password, type, mailid, uid, key,temp_key;
    private int exist = 0;




    //FIREBASE VARIABLES
    private FirebaseAuth auth;
    FirebaseDatabase db_ref = FirebaseDatabase.getInstance();
    DatabaseReference temp_users = db_ref.getReference("temp_users");
    DatabaseReference user_info = db_ref.getReference("user_info");


    //ONCREATE login_activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        //Intent r =getIntent();
        //temp_key=r.getStringExtra("tempkey");

        auth = FirebaseAuth.getInstance();
        //IF USER ALREADY SIGNED IN
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(login_activity.this,MainActivity.class);
            startActivity(i);
            finish();
        }

        //INSTANTIATION OF APP COMPONENTS
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        new_login = findViewById(R.id.bt_newuser_login);

        //LOGGING IN
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_email = email.getText().toString();
                txt_password = password.getText().toString();
                auth.signInWithEmailAndPassword(txt_email,txt_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(auth.getCurrentUser().isEmailVerified())
                        {
                            Intent i = new Intent(login_activity.this, MainActivity.class);

                            startActivity(i);
                            finish();
                        }
                        else{
                            Toast.makeText(login_activity.this, "PLEASE VERIFY MAIL", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        //NEW USER
        new_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            key = ds.getKey();
                            mailid = ds.child("email").getValue(String.class);
                            type = ds.child("type").getValue(String.class);
                            txt_email = email.getText().toString();
                            if (mailid.equals(txt_email)) {
                                exist = 1;
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

                if (exist == 1) {
                    Intent i = new Intent(login_activity.this, register_activity.class);
                    i.putExtra("mailid", txt_email);
                    i.putExtra("type", type);
                    i.putExtra("key",key);
                    exist = 0;
                    startActivity(i);

                }
                //startActivity(new Intent(login_activity.this,register_activity.class));

            }
        });

    }

   /* private String u_type(final String uid) {
        user_info.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    key = ds.getKey();
                    if (key.equals(uid)) {
                        type = ds.child("type").getValue(String.class);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return type;
    }*/
}
