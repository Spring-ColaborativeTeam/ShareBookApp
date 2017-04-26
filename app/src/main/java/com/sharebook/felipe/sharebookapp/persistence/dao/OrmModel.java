package com.sharebook.felipe.sharebookapp.persistence.dao;

import android.content.Context;

import java.sql.SQLException;

/**
 * Created by Felipe on 25/04/17.
 */

public class OrmModel implements Model {
    private DatabaseHelper helper;

    @Override
    public synchronized void init(Context context) {
        if (helper == null) {
            helper = new DatabaseHelper(context);
        }
    }

    @Override
    public LibroDao getLibroDao() {
        try {
            return helper.getLibroDao();
        } catch (SQLException e) {
            throw new IllegalStateException("We go DB error while creation dao. App cannot work further", e);
        }
    }
}
