package com.sharebook.felipe.sharebookapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharebook.felipe.sharebookapp.adapter.LibroAdapter;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.Libro;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.NetworkException;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.RequestCallBack;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.RetrofiNetwork;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Felipe on 25/04/17.
 */

public class LibrosDispActivity extends Fragment{
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //configureRecyclerView();
        View resp =  inflater.inflate(R.layout.activity_lisdisp, null);
        configureRecyclerView(resp);
        ImplementadordeRetrofit bkg = new ImplementadordeRetrofit();
        return resp;
    }

    private void configureRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }
    private class ImplementadordeRetrofit {
        private final RetrofiNetwork network;
        private final ExecutorService executorService;
        private List<Libro> libros;

        public ImplementadordeRetrofit() {
            network = new RetrofiNetwork();
            executorService = Executors.newFixedThreadPool(1);
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    network.getLibros(new RequestCallBack<List<Libro>>() {
                        @Override
                        public void onSuccess(List<Libro>  response) {
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
                                recyclerView.setAdapter(new LibroAdapter(libros));
                            }
                        }
                    });
                }
            });
        }
    }
}
