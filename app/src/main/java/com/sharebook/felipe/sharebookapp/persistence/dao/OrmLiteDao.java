package com.sharebook.felipe.sharebookapp.persistence.dao;

import android.util.Log;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.table.TableUtils;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.Libro;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Felipe on 20/04/17.
 */

public abstract class OrmLiteDao<T, K> implements BaseDao<T, K>{
    protected final Dao<T, K> dao;
    private final String TAG = getClass().getSimpleName();

    private final Class<T> type;

    OrmLiteDao(Dao<T, K> dao, Class<T> type )
    {
        this.dao = dao;
        this.type = type;
    }



    @Override
    public void create( T entity )
            throws DBException
    {
        try
        {
            dao.createOrUpdate( entity );
        }
        catch ( SQLException e )
        {
            Log.e( TAG, "An error happened at create", e );
            throw new DBException( "An error happened at create" + entity, e );
        }
    }

    @Override
    public void delete( T entity )
            throws DBException
    {
        try
        {
            dao.delete( entity );
        }
        catch ( SQLException e )
        {
            Log.e( TAG, "An error happened at delete: " + entity, e );
            throw new DBException( "An error happened at delete: " + entity, e );
        }
    }

    @Override
    public void update( T entity )
            throws DBException
    {
        try
        {
            dao.update( entity );
        }
        catch ( SQLException e )
        {
            Log.e( TAG, "An error happened at update: " + entity, e );
            throw new DBException( "An error happened at update: " + entity, e );
        }
    }

    @Override
    public T get( K id )
            throws DBException
    {
        try
        {
            return dao.queryForId( id );
        }
        catch ( SQLException e )
        {
            Log.e( TAG, "An error happened at get for id " + id, e );
            throw new DBException( "An error happened at get for id " + id, e );
        }
    }

    @Override
    public List<T> getAll()
            throws DBException
    {
        try
        {
            return dao.queryForAll();
        }
        catch ( SQLException e )
        {
            Log.e( TAG, "An error happened at getAll", e );
            throw new DBException( "An error happened at getAll", e );
        }
    }

    @Override
    public long count()
            throws DBException
    {

        try
        {
            return dao.queryBuilder().countOf();
        }
        catch ( SQLException e )
        {
            Log.e( TAG, "An error happened at count", e );
            throw new DBException( "An error happened at count", e );
        }
    }

    @Override
    public List<String[]> queryRaw( String statement, String... arguments )
            throws DBException
    {
        try
        {
            GenericRawResults<String[]> result = dao.queryRaw( statement, arguments );
            return result.getResults();
        }
        catch ( SQLException e )
        {
            Log.e( TAG, "An error happened at queryRaw with statement: " + statement, e );
            throw new DBException( "An error happened at queryRaw with statement: " + statement, e );
        }
    }

    /**
     * Create or update OrmLite implementation
     *
     * @see BaseDao#createOrUpdate(Object)
     */
    @Override
    public boolean createOrUpdate( T entry )
            throws DBException
    {
        try
        {
            Dao.CreateOrUpdateStatus status = dao.createOrUpdate( entry );

            return status.isCreated();
        }
        catch ( SQLException e )
        {
            throw new DBException( "Error on createOrUpdate for entry: " + entry, e );
        }
    }

    @Override
    public void clear()
            throws DBException
    {
        try
        {
            TableUtils.clearTable( dao.getConnectionSource(), type );
        }
        catch ( SQLException e )
        {
            throw new DBException( "An error happened at clear for " + dao.getClass().getSimpleName(), e );
        }
    }
}