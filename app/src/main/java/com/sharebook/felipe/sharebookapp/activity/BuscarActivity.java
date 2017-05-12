package com.sharebook.felipe.sharebookapp.activity;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sharebook.felipe.sharebookapp.R;
import com.sharebook.felipe.sharebookapp.adapter.MisLibrosAdapter;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.Libro;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.LibroService;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.RetrofiNetwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BuscarActivity extends Fragment {

    private String query;
    private RecyclerView recyclerView;
    private List<Libro> libros;
    private EditText text;
    private Button buscar;
    private SharedPreferences pref;
    MisLibrosAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View resp =  inflater.inflate(R.layout.activity_buscar, null);
        configureRecyclerView(resp);
        pref = getApplicationContext().getSharedPreferences("userDetails", 0);
        return resp;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        buscar = (Button) view.findViewById(R.id.button6);
        text = (EditText) view.findViewById(R.id.query);

        buscar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                query = text.getText().toString();
                searchBook();
            }
        });
    }

    private void searchBook(){
        Log.d("BuscarActivity", "entro");
        RetrofiNetwork retrofiNetwork = new RetrofiNetwork(pref.getString("username", null));

        LibroService loginService = RetrofiNetwork.createService(LibroService.class, "sad", "sa");

        Call<List<Libro>> call = loginService.buscarLibros(query);
        call.enqueue(new Callback<List<Libro>>() {
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                libros = response.body();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (libros != null) {
                            adapter = new MisLibrosAdapter(libros);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {
                libros = null;
            }
        });



    }

    private void configureRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
