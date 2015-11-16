package com.itver.jesus.app_gestion_final.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itver.jesus.app_gestion_final.ui.modelos.Calendario;

import java.util.ArrayList;

/**
 * Clase Modelado de la tabla Calendario en SQLite.
 * Created by Jesus on 19/10/2015.
 */

public class CalendarioDataSource {

    //Metainformación de la base de datos
    public static final String CALENDARIO_TABLE_NAME = "calendario";
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";

    //Campos de la tabla Calendario
    public static class ColumnCalendario {

        public static final String ID_CALENDARIO = "id_calendario";
        public static final String FECHA_NUM = "fecha_num";
        public static final String MES_CALEND = "mes";
        public static final String MES_NUM_CALEND = "mes_num";
        public static final String ANIO_CALEND = "anio";
        public static final String CONT_CALEND = "titulo";
    }

    //Script de Creación de la tabla Noticias
    public static final String CREATE_CALENDAR_SCRIPT =
            "CREATE TABLE " + CALENDARIO_TABLE_NAME + "("
                    + ColumnCalendario.ID_CALENDARIO + " " + INT_TYPE + " PRIMARY KEY AUTOINCREMENT, "
                    + ColumnCalendario.FECHA_NUM + " " + INT_TYPE + "  ,"
                    + ColumnCalendario.MES_CALEND + " " + STRING_TYPE + " not null ,"
                    + ColumnCalendario.MES_NUM_CALEND + " " + INT_TYPE + "  ,"
                    + ColumnCalendario.ANIO_CALEND + " " + INT_TYPE + "  ,"
                    + ColumnCalendario.CONT_CALEND + " " + STRING_TYPE + " not null )";

    public static void imprimirScrip() {
        System.out.println(CREATE_CALENDAR_SCRIPT);
    }

    public static final String[] COLUMNAS_QUERY_FECHAS = {
            ColumnCalendario.FECHA_NUM,
            ColumnCalendario.MES_CALEND,
            ColumnCalendario.CONT_CALEND,
            ColumnCalendario.ANIO_CALEND
    };

    public static final String[] COLUMNAS_QUERY_MESES = {
            ColumnCalendario.ID_CALENDARIO,
            ColumnCalendario.MES_CALEND,
            ColumnCalendario.MES_NUM_CALEND,
            ColumnCalendario.ANIO_CALEND,
    };

    private DataBase_Helper helper;
    private SQLiteDatabase bd;


    public CalendarioDataSource(Context context) {
        helper = new DataBase_Helper(context);
        bd = helper.getWritableDatabase();
    }

    private ContentValues contentValuesNews(Calendario calendario) {
        ContentValues content = new ContentValues();
        content.put(ColumnCalendario.ID_CALENDARIO, calendario.getIdCalendario());
        content.put(ColumnCalendario.FECHA_NUM, calendario.getFecha());
        content.put(ColumnCalendario.MES_CALEND, calendario.getMes());
        content.put(ColumnCalendario.MES_NUM_CALEND, calendario.getMes_num());
        content.put(ColumnCalendario.ANIO_CALEND, calendario.getAnio());
        content.put(ColumnCalendario.CONT_CALEND, calendario.getContenido());
        return content;
    }

    public void insertarFecha(Calendario calendario) {
        bd.insert(CALENDARIO_TABLE_NAME, null, contentValuesNews(calendario));
    }

    public void eliminarNoticia(Calendario calendario) {
        bd.delete(CALENDARIO_TABLE_NAME, ColumnCalendario.ID_CALENDARIO + "=?", new String[]{calendario.getIdCalendario() + ""});
    }

    public Cursor getCursorAllDates() {
        // Se extrae :
        // FECHA_NUM  -   MES_CALEND  -  CONT_CALEND
        return bd.query(CALENDARIO_TABLE_NAME, COLUMNAS_QUERY_FECHAS, null, null, null, null, null);
    }

    public Cursor getCursorAllMeses() {
        // Se extrae :
        // FECHA_NUM  -   MES_CALEND
        // distinct - tabla_name - columnas - selection - selectionArgs - groupBy - having - orderBy - limit
        return bd.query(true,
                CALENDARIO_TABLE_NAME,
                COLUMNAS_QUERY_MESES,
                null,
                null,
                COLUMNAS_QUERY_MESES[1],
                null,
                COLUMNAS_QUERY_MESES[2],
                null);
    }

    public ArrayList<String> getCursorAll_Name_Meses() {
        ArrayList<String> meses = new ArrayList<>();

        Cursor cursor = getCursorAllMeses();

        while (cursor.moveToNext()) {
            meses.add(cursor.getString(1));

        }
        return meses;
    }

    public Cursor getCursorAllDatesForMonth(String mes) {

        String[] args = new String[]{mes};
        String where = ColumnCalendario.MES_CALEND + "=?";

        return bd.query(CALENDARIO_TABLE_NAME, COLUMNAS_QUERY_FECHAS, where, args, null, null, COLUMNAS_QUERY_FECHAS[0]);
    }
}
