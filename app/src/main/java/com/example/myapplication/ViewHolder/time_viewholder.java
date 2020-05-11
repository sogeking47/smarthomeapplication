package com.example.myapplication.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class time_viewholder extends RecyclerView.ViewHolder {

    public TextView name_val,action_val,time_val;

    public time_viewholder(@NonNull View itemView) {
        super(itemView);
        name_val=itemView.findViewById(R.id.name_txt);
        action_val=itemView.findViewById(R.id.action_txt);
        time_val=itemView.findViewById(R.id.time_txt);

    }
}
