package com.sharebook.felipe.sharebookapp.persistence.dao.model;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Felipe on 25/04/17.
 */

public class RetrofiNetwork {
    private static final String BASE_URL = "https://raw.githubusercontent.com/Spring-ColaborativeTeam/ShareBookApp/master/";

    private LibroService libroSrvc;

    public RetrofiNetwork()
    {
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).build();
        libroSrvc = retrofit.create(LibroService.class);
    }

    public void getLibros( RequestCallBack<List<Libro>> requestCallback )
    {
        try
        {
            Call<List<Libro>> call = libroSrvc.getLibrosList( );
            Response<List<Libro>> execute = call.execute();
            requestCallback.onSuccess( execute.body() );
        }
        catch ( IOException e )
        {
            requestCallback.onFailed(new NetworkException());
        }
    }
}
