package com.sharebook.felipe.sharebookapp.security.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alejandro on 11/05/17.
 */

public class Logout {

    @SerializedName("estado")
    @Expose
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
