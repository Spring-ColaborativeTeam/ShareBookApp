package com.sharebook.felipe.sharebookapp.persistence.dao;

/**
 * Created by Felipe on 25/04/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.Libro;

import java.sql.SQLException;
public class DatabaseHelper
        extends OrmLiteSqliteOpenHelper
{
    private final String TAG = DatabaseHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 5;

    private static final String DATABASE_NAME = "my_database.db";

    private LibroDao libroDao;

    public DatabaseHelper( Context context )
    {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    /**
     * Creates/updated tables if necessary
     *
     * @throws IllegalStateException if db operations failed
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource )
    {

        try
        {
            TableUtils.createTableIfNotExists( connectionSource, Libro.class );
        }
        catch ( SQLException e )
        {
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    @Override
    public void onUpgrade( SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion )
    {

    }


    public LibroDao getLibroDao()
            throws SQLException
    {
        if ( libroDao == null )
        {
            Dao<Libro, Long> dao = getDao( Libro.class );
            libroDao = new LibroOrmLiteDao( dao );
        }
        return libroDao;
    }

}