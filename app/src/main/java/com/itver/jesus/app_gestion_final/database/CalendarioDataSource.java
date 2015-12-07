package com.itver.jesus.app_gestion_final.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itver.jesus.app_gestion_final.ui.modelos.Calendario;

import java.util.ArrayList;

/**
 * Clase Modelo de la tabla Calendario en SQLite.
 * Created by Jesus on 19/10/2015.
 */
public class CalendarioDataSource {

    /**
     * Atributo Objeto String con el nombre de la Tabla.
     */
    public static final String CALENDARIO_TABLE_NAME = "calendario";

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
    public static class ColumnCalendario {
        public static final String ID_CALENDARIO = "id_calendario";
        public static final String FECHA_NUM = "fecha_num";
        public static final String MES_CALEND = "mes";
        public static final String MES_NUM_CALEND = "mes_num";
        public static final String ANIO_CALEND = "anio";
        public static final String CONT_CALEND = "titulo";
    }

    /**
     * Script de Creacion de la tabla Noticias
     */
    public static final String CREATE_CALENDAR_SCRIPT =
            "CREATE TABLE " + CALENDARIO_TABLE_NAME + "("
                    + ColumnCalendario.ID_CALENDARIO + " " + INT_TYPE + " PRIMARY KEY AUTOINCREMENT, "
                    + ColumnCalendario.FECHA_NUM + " " + INT_TYPE + "  ,"
                    + ColumnCalendario.MES_CALEND + " " + STRING_TYPE + " not null ,"
                    + ColumnCalendario.MES_NUM_CALEND + " " + INT_TYPE + "  ,"
                    + ColumnCalendario.ANIO_CALEND + " " + INT_TYPE + "  ,"
                    + ColumnCalendario.CONT_CALEND + " " + STRING_TYPE + " not null )";

    /**
     * Columnas para la consulta de fechas almacenadas en la tabla.
     */
    public static final String[] COLUMNAS_QUERY_FECHAS = {
            ColumnCalendario.FECHA_NUM,
            ColumnCalendario.MES_CALEND,
            ColumnCalendario.CONT_CALEND,
            ColumnCalendario.ANIO_CALEND
    };

    /**
     * Columnas para la consulta de los meses almacenados en la tabla.
     */
    public static final String[] COLUMNAS_QUERY_MESES = {
            ColumnCalendario.ID_CALENDARIO,
            ColumnCalendario.MES_CALEND,
            ColumnCalendario.MES_NUM_CALEND,
            ColumnCalendario.ANIO_CALEND,
    };

    private DataBase_Helper helper;
    private SQLiteDatabase bd;


    /**
     * Constructor de la clase CalendarioDataSource.
     *
     * @param context Contexto de la actividad.
     */
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

    /**
     * Inserta una fecha en la tabla Calendario.
     *
     * @param calendario Objeto Calendario con la fecha a insertar.
     */
    public void insertarFecha(Calendario calendario) {
        bd.insert(CALENDARIO_TABLE_NAME, null, contentValuesNews(calendario));
    }

    /**
     * Elimina una fecha en la tabla Calendario.
     *
     * @param calendario Objeto con a fecha a eliminar.
     */
    public void eliminarFecha(Calendario calendario) {
        bd.delete(CALENDARIO_TABLE_NAME, ColumnCalendario.ID_CALENDARIO + "=?", new String[]{calendario.getIdCalendario() + ""});
    }

    /**
     * Retorna objeto Cursor con todas las fechas en la tabla Calendario
     *
     * @return Cursor con las fechas en la tabla Calendario.
     */
    public Cursor getCursorAllDates() {
        // Se extrae :
        // FECHA_NUM  -   MES_CALEND  -  CONT_CALEND
        return bd.query(CALENDARIO_TABLE_NAME, COLUMNAS_QUERY_FECHAS, null, null, null, null, null);
    }

    /**
     * Retorna objeto Cursor con todos los meses en la tabla Calendario.
     *
     * @return Cursor con los meses en la tabla Calendario.
     */
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

    /**
     * Retorna una lista con objetos tipo String, los cuales son los nombres de los meses almacenados en la tabla Calendario.
     *
     * @return Objeto ArrayList con elementos tipo String
     */
    public ArrayList<String> getCursorAll_Name_Meses() {
        ArrayList<String> meses = new ArrayList<>();

        Cursor cursor = getCursorAllMeses();

        while (cursor.moveToNext()) {
            meses.add(cursor.getString(1));

        }
        return meses;
    }

    /**
     * Retorna Cursor con fechas de un respectivo mes.
     *
     * @param mes Objeto tipo String conteniendo el mes.
     * @return Objeto Cursor con las fechas del mes.
     */
    public Cursor getCursorAllDatesForMonth(String mes) {

        String[] args = new String[]{mes};
        String where = ColumnCalendario.MES_CALEND + "=?";

        return bd.query(CALENDARIO_TABLE_NAME, COLUMNAS_QUERY_FECHAS, where, args, null, null, COLUMNAS_QUERY_FECHAS[0]);
    }
}