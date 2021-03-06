package com.sharebook.felipe.sharebookapp.activity;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharebook.felipe.sharebookapp.R;
import com.sharebook.felipe.sharebookapp.adapter.SolicitudesAdapter;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.Libro;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.NetworkException;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.RequestCallBack;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.RetrofiNetwork;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SolicitudAcivity extends Fragment {
    private RetrofiNetwork resources;
    private ExecutorService executorService;
    private RecyclerView recyclerView;
    private List<List<Libro>> libros;
    SolicitudesAdapter adapter;
    private SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        resources = new RetrofiNetwork(pref.getString("username", null));
        View resp =  inflater.inflate(R.layout.activity_solicitud_acivity, null);
        configureRecyclerView(resp);
        misSolicitudes();
        return resp;
    }

    private void configureRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewSolicitudes);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void misSolicitudes() {
        executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                resources.misSolicitudes(new RequestCallBack<List<List<Libro>>>() {
                    @Override
                    public void onSuccess(List<List<Libro>> response) {
                        libros = response;
                    }

                    @Override
                    public void onFailed(NetworkException e) {
                        libros = null;
                    }
                });

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (libros != null) {
                            adapter = new SolicitudesAdapter(libros);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
            }

        });
    }
}
