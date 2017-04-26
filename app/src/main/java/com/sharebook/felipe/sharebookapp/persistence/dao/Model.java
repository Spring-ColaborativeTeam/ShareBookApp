package com.sharebook.felipe.sharebookapp.persistence.dao;

import android.content.Context;

/**
 * Created by Felipe on 25/04/17.
 */

public interface Model {
    void init( Context context );

    LibroDao getLibroDao();
}
