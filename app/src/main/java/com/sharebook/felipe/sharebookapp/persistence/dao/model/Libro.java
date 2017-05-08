package com.sharebook.felipe.sharebookapp.persistence.dao.model;

import com.j256.ormlite.field.DatabaseField;



/**
 * Created by Felipe on 25/04/17.
 */

public class Libro extends BaseModel{

    @DatabaseField
    String nombre;
    @DatabaseField
    String editorial;
    @DatabaseField
    String id;


    String imageUrl;
    public Libro() {
        imageUrl = "https://sharebookapp.herokuapp.com/libros/"+id+"/picture";
        //System.out.println("El" +id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return nombre;
    }

    public void setName(String name) {
        this.nombre = name;
    }

    public String getDescription() {
        return editorial;
    }

    public void setDescription(String editorial) {
        this.editorial = editorial;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id=").append(id);
        sb.append(", ").append("str=").append(id);
        sb.append(", ").append("ms=").append(nombre);

        //sb.append(", ").append("description=").append(description);
        return sb.toString();
    }
}
