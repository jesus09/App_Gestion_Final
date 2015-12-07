package com.itver.jesus.app_gestion_final.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Clase modelo de la tabla Users en SQLite.
 */
public class UsuariosDataSource {

    /**
     * Atributo Objeto String con el nombre de la Tabla.
     */
    public static final String USERS_TABLE_NAME = "Users";

    /**
     * Atributo Objeto String como tipo de dato TEXT admitido en la tabla.
     */
    public static final String STRING_TYPE = "TEXT";

    /**
     * Atributo Objeto String como tipo de dato INTEGER admitido en la tabla.
     */
    public static final String INT_TYPE = "INTEGER";

    /**
     * Clase contenedora de variables estaticas que componen la tabla Calendario
     */
    public static class ColumnUsuarios {

        public static final String ID_USER = "id_user";
        public static final String CLAVE_USER = "clave";

    }

    /**
     * Script de Creacion de la tabla Users.
     */
    public static final String CREATE_USERS_SCRIPT
            = "CREATE TABLE " + USERS_TABLE_NAME + "("
            + ColumnUsuarios.ID_USER + " " + STRING_TYPE + " PRIMARY KEY ,"
            + ColumnUsuarios.CLAVE_USER + " " + STRING_TYPE + " not null )";

    /**
     * Columnas para la consulta de Usuarios almacenadas en la tabla.
     */
    public static final String[] COLUMNAS_QUERY_USER = {
            ColumnUsuarios.ID_USER,
            ColumnUsuarios.CLAVE_USER
    };

    private DataBase_Helper helper;
    private SQLiteDatabase bd;


    public UsuariosDataSource(Context context) {
        helper = new DataBase_Helper(context);
        bd = helper.getWritableDatabase();
    }

    private ContentValues contentValuesNews(String usuario, String clave) {
        ContentValues content = new ContentValues();
        content.put(ColumnUsuarios.ID_USER, usuario);
        content.put(ColumnUsuarios.CLAVE_USER, clave);
        return content;
    }

    /**
     * Inserta un registro en la Tabla Users.
     *
     * @param usuario String con el username del usuario.
     * @param clave   String con la clave del usuario.
     */
    public void insertarUsuario(String usuario, String clave) {
        bd.insert(USERS_TABLE_NAME, null, contentValuesNews(usuario, clave));
    }

    /**
     * Retorna Boolean si el usuario se encuentra en la Tabla Users.
     *
     * @param usuario String username del usuario.
     * @param clave   String clave del usuario.
     * @return boolean - True, el usuario se encuentra en la tabla - False, el usuario no se encuentra en la tabla.
     */
    public boolean getUsuario(String usuario, String clave) {
        boolean estado = false;

        String consulta = "select \"" + usuario + "\" = id_user from " + USERS_TABLE_NAME + " where ";
        consulta += ColumnUsuarios.ID_USER + " = \"" + usuario + "\"";
        consulta += "and " + ColumnUsuarios.CLAVE_USER + " = \"" + clave + "\"";

        Cursor cursor = bd.rawQuery(consulta, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int aux = cursor.getInt(0);
            estado = (aux == 0) ? false : true;

            cursor.close();
        }

        return estado;
    }
}
