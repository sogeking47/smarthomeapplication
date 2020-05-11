package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class Settings_activity extends AppCompatActivity  {

    Calendar c = Calendar.getInstance();
    int hour=c.get(Calendar.HOUR_OF_DAY);
    int minute =c.get(Calendar.MINUTE);
    TextView settimetext;
    Button setr_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_activity);
        settimetext=findViewById(R.id.timeset);
        setr_btn=findViewById(R.id.time_picker_btn);

        setr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 TimePickerDialog timePickerDialog = new TimePickerDialog(Settings_activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        settimetext.setText(hourOfDay + ":" + minutes);
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minutes);
                        c.set(Calendar.SECOND,0);
                        updateTimeText(c);
                        startAlarm(c);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });
    }

    private void updateTimeText(Calendar c){
        String timetext="";
        timetext += DateFormat.getTimeFormat(this).format(c.getTime());
        settimetext.setText(timetext);
    }

    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent =new Intent(this, AlarmReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }
}
