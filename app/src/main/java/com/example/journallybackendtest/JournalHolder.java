package com.example.journallybackendtest;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JournalHolder extends RecyclerView.ViewHolder{

    public TextView bookName;
    public ImageView bookCover;


    public JournalHolder(@NonNull View itemView) {
        super(itemView);
        //bookName = itemView.findViewById(R.id.bookname);
        //bookCover = itemView.findViewById(R.id.bookcover);
    }
}
