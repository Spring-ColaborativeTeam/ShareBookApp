package com.sharebook.felipe.sharebookapp.persistence.dao.model;

import android.text.TextUtils;
import android.util.Log;

import com.sharebook.felipe.sharebookapp.security.AuthenticationInterceptor;

import java.io.IOException;
import java.util.List;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Felipe on 25/04/17.
 */

public class RetrofiNetwork {

    private static final String BASE_URL = "https://sharebookapp.herokuapp.com/";
    public static final String BASE_URLIMG ="https://sharebookapp.herokuapp.com/libros/1/picture";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();
    private String username;


    private LibroService libroSrvc;

    public RetrofiNetwork(String email){

        Retrofit retrofit =
                new Retrofit.Builder().baseUrl( BASE_URL )
                        .addConverterFactory( GsonConverterFactory.create() )
                        .build();
        libroSrvc = retrofit.create(LibroService.class);
        username = email;
    }

    public void getLibros( RequestCallBack<List<Libro>> requestCallback )
    {
        try
        {
            Call<List<Libro>> call = libroSrvc.getLibrosList(username);
            Response<List<Libro>> execute = call.execute();
            requestCallback.onSuccess( execute.body() );
        }
        catch ( IOException e )
        {
            requestCallback.onFailed(new NetworkException());
        }
    }
    //aca tambien puede pensar en pasar loos parametros de un libro y luego construir el objeto
    // li = new libro(parametros)
    public void addLibro(Libro li){
        Call<Libro> call = libroSrvc.addLibro(li);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {

            }

            @Override
            public void onFailure(Call<Libro> call, Throwable t) {

            }

    });
    }

    public void registroUsuario(Usuario usuario){
        Call<Usuario> call = libroSrvc.createUser(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if(response.isSuccessful()){
                    Log.d("retrofit", response.body().toString());
                }
               // Log.d("Registro", response.body().toString());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.d("retrofit", call.toString());
            }
        });
    }

    public void misLibros(RequestCallBack<List<Libro>> requestCallBack){

        Call<List<Libro>> call = libroSrvc.getMisLibros(username);
        try {
            Response<List<Libro>> execute = call.execute();
            requestCallBack.onSuccess( execute.body() );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, String username, String password) {
        if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)) {

            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {

                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}