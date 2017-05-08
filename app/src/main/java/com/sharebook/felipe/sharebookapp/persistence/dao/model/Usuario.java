package com.sharebook.felipe.sharebookapp.persistence.dao.model;

import com.j256.ormlite.field.DatabaseField;

import java.sql.Blob;

/**
 * Created by daniel on 8/05/17.
 */

public class Usuario extends BaseModel {

    private String email;


    @DatabaseField
    private String password;

    @DatabaseField
    private String nombre;

    @DatabaseField
    private String celular;

    @DatabaseField
    private Blob imagen;

    public Usuario() {

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCelular() {
        return celular;
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }
}
