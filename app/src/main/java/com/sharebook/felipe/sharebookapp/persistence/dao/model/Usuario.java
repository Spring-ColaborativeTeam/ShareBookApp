package com.sharebook.felipe.sharebookapp.persistence.dao.model;

/**
 * Created by daniel on 8/05/17.
 */

public class Usuario {

    //@SerializedName("email")
    private String email;

    //@SerializedName("password")
    private String password;

    //@SerializedName("nombre")
    private String nombre;

    //SerializedName("celular")
    private String celular;

    //@SerializedName("imagen")
    private String imagen;


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

    public String getImagen() {
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

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
