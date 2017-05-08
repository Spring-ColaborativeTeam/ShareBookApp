package com.sharebook.felipe.sharebookapp.persistence.dao.model;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created by alejandro on 8/05/17.
 */

public class Solicitud extends BaseModel{

    @DatabaseField
    private String Id;
    @DatabaseField
    private Date fecha;
    @DatabaseField
    private boolean estado;
    @DatabaseField
    private Libro libro1;
    @DatabaseField
    private Libro libro2;

    public Solicitud() {

    }

    public String getId() {
        return Id;
    }

    public Date getFecha() {
        return fecha;
    }

    public boolean isEstado() {
        return estado;
    }

    public Libro getLibro1() {
        return libro1;
    }

    public Libro getLibro2() {
        return libro2;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setLibro1(Libro libro1) {
        this.libro1 = libro1;
    }

    public void setLibro2(Libro libro2) {
        this.libro2 = libro2;
    }
}
