package com.example.myapplication.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class date_viewholder extends RecyclerView.ViewHolder {

    public TextView date;
    public RecyclerView recyclerviewdate;
    public RecyclerView.LayoutManager manager;
    public date_viewholder(@NonNull View itemView) {
        super(itemView);
        manager=new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.VERTICAL,false);
        date=itemView.findViewById(R.id.date_txt);
        recyclerviewdate=itemView.findViewById(R.id.datercv);
        recyclerviewdate.setLayoutManager(manager);

    }
}
