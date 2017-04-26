package com.sharebook.felipe.sharebookapp.persistence.dao.model;

/**
 * Created by Felipe on 25/04/17.
 */

public interface RequestCallBack<T> {
    void onSuccess( T response );

    void onFailed( NetworkException e );

}