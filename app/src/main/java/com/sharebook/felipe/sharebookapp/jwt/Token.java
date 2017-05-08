package com.sharebook.felipe.sharebookapp.jwt;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alejandro on 7/05/17.
 */



public class Token implements NetworkRequest.ApiResponse {
    @SerializedName("id_token")
    private String idToken;

    public String getIdToken() {
        return idToken;
    }

    @Override
    public String string() {
        return idToken;
    }
}