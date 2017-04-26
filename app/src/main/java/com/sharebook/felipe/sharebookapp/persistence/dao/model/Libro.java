package com.sharebook.felipe.sharebookapp.persistence.dao.model;

import com.j256.ormlite.field.DatabaseField;



/**
 * Created by Felipe on 25/04/17.
 */

public class Libro extends BaseModel{
    @DatabaseField
    String name ;
    @DatabaseField
    String description;
    @DatabaseField
    String imageUrl;
    public Libro() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        sb.append(", ").append("ms=").append(name);

        //sb.append(", ").append("description=").append(description);
        return sb.toString();
    }
}
