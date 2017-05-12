package com.sharebook.felipe.sharebookapp.persistence.dao.model;

import com.sharebook.felipe.sharebookapp.security.model.Logout;
import com.sharebook.felipe.sharebookapp.security.model.ServerRequest;
import com.sharebook.felipe.sharebookapp.security.model.ServerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Felipe on 25/04/17.
 */

public interface LibroService {

    @POST("registro")
    Call<Usuario> createUser(@Body Usuario usuario);

    @POST("/api/auth/login")
    Call<ServerResponse> login(@Body ServerRequest request);

    @GET("/api/auth/logout")
    Call<Logout> logout();

    @GET( "libros/disponibles/diego@sharebook.com" )
    Call<List<Libro>> getLibrosList( );

    @POST("libros/diego@sharebook.com")
    Call<Libro> addLibro(@Body Libro libro);

    @POST("solicitud/{id1}/{id2}")
    Call<Solicitud> addSolicitud(@Path("id1") String id1, @Path("id2") String id2, @Body Solicitud solicitud);

    @GET("mislibros/diego@sharebook.com")
    Call<List<Libro>> getMisLibros();

    @GET("libros/buscar/{bookname}")
    Call<List<Libro>> buscarLibros(@Path("bookname") String bookname);

    @GET("solicitud/usuario/diego@sharebook.com")
    Call<List<List<Libro>>> getMisSolicitudes();

   /* @GET("solicitud/usuario/diego@sharebook.com")
    Call<Solicitud> getSolicitudUsuario();*/




}
