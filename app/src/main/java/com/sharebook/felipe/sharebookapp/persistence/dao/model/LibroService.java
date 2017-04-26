package com.sharebook.felipe.sharebookapp.persistence.dao.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Felipe on 25/04/17.
 */

public interface LibroService {
    @GET( "libros.json" )
    Call<List<Libro>> getLibrosList( );
}
