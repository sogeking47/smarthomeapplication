package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.models.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class register_activity extends AppCompatActivity {

//private EditText email;
private TextView email;
private EditText password;
private EditText username;
private Button register;
private String mail_id,user_type,key;

private FirebaseAuth auth;
FirebaseDatabase lock_db= FirebaseDatabase.getInstance();
DatabaseReference user_info=lock_db.getReference("user_info");
DatabaseReference temp_users=lock_db.getReference("temp_users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent i =getIntent();
        mail_id=i.getStringExtra("mailid");
        user_type=i.getStringExtra("type");
        key=i.getStringExtra("key");

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        username=findViewById(R.id.username);
        register=findViewById(R.id.register);
        auth=FirebaseAuth.getInstance();

        email.setText(mail_id);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String txt_password=password.getText().toString();
                final String txt_username=username.getText().toString();

                if(txt_password.length()<6){
                    Toast.makeText(register_activity.this,"password too short",Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(mail_id,txt_password,txt_username,user_type);
                }

            }
        });
    }

    private void registerUser(final String email,final String password,final String username,final String type) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(register_activity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Objects.requireNonNull(auth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                temp_users.child(key).removeValue();
                                Toast.makeText(register_activity.this,"REGISTERED, VERIFY EMAIL",Toast.LENGTH_LONG)
                                        .show();
                                user lock_user=new user(username,email,type);
                                user_info.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(lock_user);

                                finish();
                            }
                        }
                    });


                }else{
                    Toast.makeText(register_activity.this,"failed",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}


