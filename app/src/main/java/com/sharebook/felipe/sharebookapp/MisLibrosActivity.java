package com.sharebook.felipe.sharebookapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MisLibrosActivity extends Fragment {

    private RecyclerView recyclerView;

    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setTitle(R.string.mis_libros);
        //setContentView(R.layout.activity_mis_libros);
        View resp =  inflater.inflate(R.layout.activity_mis_libros, null);
        configureRecyclerView(resp);

        return resp;

    }

    private void configureRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }
}
