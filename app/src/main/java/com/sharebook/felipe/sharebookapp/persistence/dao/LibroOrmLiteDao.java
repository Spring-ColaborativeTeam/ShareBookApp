package com.sharebook.felipe.sharebookapp.persistence.dao;

import com.j256.ormlite.dao.Dao;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.Libro;

/**
 * Created by Felipe on 25/04/17.
 */

public class LibroOrmLiteDao extends OrmLiteDao<Libro, Long>
        implements LibroDao
                {
public LibroOrmLiteDao( Dao<Libro, Long> dao )
        {
        super( dao, Libro.class );
        }
        }