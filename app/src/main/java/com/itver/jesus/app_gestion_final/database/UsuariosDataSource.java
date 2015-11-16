package com.itver.jesus.app_gestion_final.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuariosDataSource {

    //Metainformación de la base de datos
    public static final String USERS_TABLE_NAME = "Users";
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";

    //Campos de la tabla Usuarios
    public static class ColumnUsuarios {

        public static final String ID_USER = "id_user";
        public static final String CLAVE_USER = "clave";

    }

    //Script de Creación de la tabla Usuarios
    public static final String CREATE_USERS_SCRIPT
            = "CREATE TABLE " + USERS_TABLE_NAME + "("
            + ColumnUsuarios.ID_USER + " " + STRING_TYPE + " PRIMARY KEY ,"
            + ColumnUsuarios.CLAVE_USER + " " + STRING_TYPE + " not null )";

    public static void imprimirScrip() {
        System.out.println(CREATE_USERS_SCRIPT);
    }

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

    public void insertarUsuario(String usuario, String clave) {
        bd.insert(USERS_TABLE_NAME, null, contentValuesNews(usuario, clave));
    }

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
