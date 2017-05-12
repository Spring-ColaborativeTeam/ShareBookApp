package com.sharebook.felipe.sharebookapp.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharebook.felipe.sharebookapp.R;
import com.sharebook.felipe.sharebookapp.adapter.IntercambiarLibroAdapter;
import com.sharebook.felipe.sharebookapp.adapter.LibroAdapter;
import com.sharebook.felipe.sharebookapp.adapter.MisLibrosAdapter;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.Libro;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.NetworkException;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.RequestCallBack;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.RetrofiNetwork;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Diego on 11/05/2017.
 */

public class IntercambiarFragment extends Fragment {

    private RetrofiNetwork resources;
    private ExecutorService executorService;
    private RecyclerView recyclerView;
    private List<Libro> libros;
    Libro libro;
    TextView nombreEdit;

    IntercambiarLibroAdapter adapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        resources = new RetrofiNetwork();
        View resp =  inflater.inflate(R.layout.activity_intercambiar, null);
        configureRecyclerView(resp);
        misLibros();
        nombreEdit = (TextView) resp.findViewById(R.id.nintercambiar);
        if(libro != null) {
            Log.d("wrerwer", "Vealo aqui despues --------> " + libro.getName());
            nombreEdit.setText(libro.getName());
            String base_tmp = "https://sharebookapp.herokuapp.com/libros/"+libro.getId()+"/picture";
            ImageView imagen = (ImageView) resp.findViewById(R.id.imageViewInt);
            Picasso.with(getActivity()).load(base_tmp).into(imagen);
        }

        return resp;
    }

    private void configureRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewIntercambiar);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setLibroSelect(Libro l){
        //nombreEdit = (TextView) getActivity().findViewById(R.id.textViewNombreInt);
        libro = l;
        Log.d("123sad4234", "Vealo tambien --------> "+l.getName());

    }

    private void misLibros(){
        executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                resources.misLibros(new RequestCallBack<List<Libro>>() {
                    @Override
                    public void onSuccess(List<Libro> response) {
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
                            adapter = new IntercambiarLibroAdapter(libros);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
            }

        });
    }


}
