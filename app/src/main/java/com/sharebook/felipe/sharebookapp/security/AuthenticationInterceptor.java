package com.sharebook.felipe.sharebookapp.security;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by alejandro on 9/05/17.
 */

public class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("X-Requested-With", "XMLHttpRequest")
                .header("Content-Type", "application/json")
                .header("User-Agent", "mobapp/aos")
                .header("Cache-Control","no-cache")
                .header("Authorization", authToken)
                .method(original.method(), original.body());
        Log.d("AUTH:", builder.toString());

        Request request = builder.build();
        return chain.proceed(request);
    }
}