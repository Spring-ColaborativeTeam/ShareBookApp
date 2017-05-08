package com.sharebook.felipe.sharebookapp.persistence.dao.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Felipe on 25/04/17.
 */

public interface LibroService {

    @POST("registro")
    Call<Usuario> createUser(@Body Usuario usuario);

    @GET( "libros/disponibles/diego@sharebook.com" )
    Call<List<Libro>> getLibrosList( );
    @POST("diego@sharebook.com")
    Call<Libro> addLibro(@Body Libro libro);

    @GET("mislibros/diego@sharebook.com")
    Call<List<Libro>> getMisLibros();

    @GET("solicitud/usuario/diego@sharebook.com")
    Call<Solicitud> getSolicitudUsuario();


}
