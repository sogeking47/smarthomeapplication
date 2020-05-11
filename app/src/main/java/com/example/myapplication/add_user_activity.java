package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.myapplication.models.temp_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class add_user_activity extends AppCompatActivity {

    FirebaseDatabase lock_db=FirebaseDatabase.getInstance();
    DatabaseReference new_user=lock_db.getReference("temp_users");

    private Button adduserbt;
    private Spinner usertype;
    private EditText edit_mail;
    private String mailid,user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        adduserbt=findViewById(R.id.add_userbt);
        usertype=findViewById(R.id.type_user);
        edit_mail=findViewById(R.id.edit_mail);

        adduserbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mailid=edit_mail.getText().toString();
                user_type=String.valueOf(usertype.getSelectedItem());
                temp_user user = new temp_user(mailid,user_type);
                new_user.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(add_user_activity.this,"stored succesfully",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(add_user_activity.this,"error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

}
