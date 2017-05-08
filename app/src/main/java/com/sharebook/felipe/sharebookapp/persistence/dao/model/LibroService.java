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
    @GET( "libros/disponibles/diego@sharebook.com" )
    Call<List<Libro>> getLibrosList( );
    @POST("aca va el segmento que hace post de la URL")
    Call<Libro> addLibro(@Body Libro libro);

    @GET("mislibros/diego@sharebook.com")
    Call<Libro> getMisLibros();

    @GET("solicitud/usuario/diego@sharebook.com")
    Call<Solicitud> getSolicitudUsuario();


}
