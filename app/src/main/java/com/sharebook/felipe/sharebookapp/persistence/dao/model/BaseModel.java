package com.sharebook.felipe.sharebookapp.persistence.dao.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Felipe on 25/04/17.
 */

public abstract class BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @DatabaseField( generatedId = true )
    Long id;

    public Long getId()
    {
        return id;
    }
}
