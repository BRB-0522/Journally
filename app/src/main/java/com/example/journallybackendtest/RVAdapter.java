package com.example.journallybackendtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter <RVContentHolder> {
    private List<Content> data;
    private ArrayList<RVContentHolder> list = new ArrayList<RVContentHolder>();

    private RVContentHolder.eventImplementation updateEvent;

    public RVAdapter(List<Content> data, RVContentHolder.eventImplementation event) {
        this.updateEvent = event;
        this.data = data;
    }

    @NonNull
    @Override
    public RVContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View view = lf.inflate(R.layout.note_item,parent,false);
        return new RVContentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVContentHolder holder, int position) {
        holder.setDetails(data.get(position));
        holder.setHolderEvents(updateEvent);
        list.add(holder);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public List<RVContentHolder> getHolders() {
        return list;
    }
}

