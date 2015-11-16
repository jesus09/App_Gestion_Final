package com.itver.jesus.app_gestion_final.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

/**
 * Clase de la base de datos.
 * Created by Jesus on 16/09/2015.
 */
public class DataBase_Helper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notitec_db";
    private static final int DATABASE_VERSION = 1;

    public static final String[] TABLES = {
            NewsDataSource.NEWS_TABLE_NAME,
            UsuariosDataSource.USERS_TABLE_NAME,
            VisualizaDataSource.VISUALIZA_TABLE_NAME,
            CalendarioDataSource.CALENDARIO_TABLE_NAME
    };

    public static final String[] SCRIP_SCHEMA_DB = {
            NewsDataSource.CREATE_NEWS_SCRIPT,
            UsuariosDataSource.CREATE_USERS_SCRIPT,
            VisualizaDataSource.CREATE_VISUALIZA_SCRIPT,
            CalendarioDataSource.CREATE_CALENDAR_SCRIPT
    };

    public DataBase_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < SCRIP_SCHEMA_DB.length; i++) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLES[i]);
            db.execSQL(SCRIP_SCHEMA_DB[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se llama cuando se cambia de versión
    }
}
