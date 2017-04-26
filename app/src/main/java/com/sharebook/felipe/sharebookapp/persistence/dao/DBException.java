package com.sharebook.felipe.sharebookapp.persistence.dao;

/**
 * Created by Felipe on 25/04/17.
 */

class DBException extends Exception {
    public DBException( String detailMessage, Throwable throwable )
    {
        super( detailMessage, throwable );
    }
}
