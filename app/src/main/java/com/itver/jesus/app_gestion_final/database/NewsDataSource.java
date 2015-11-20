package com.itver.jesus.app_gestion_final.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itver.jesus.app_gestion_final.ui.modelos.Noticia;

import java.util.ArrayList;

public class NewsDataSource {

    //Metainformación de la base de datos
    public static final String NEWS_TABLE_NAME = "News";
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";

    //Campos de la tabla News
    public static class ColumnNoticias {

        public static final String ID_NOTIC = "id_news";
        public static final String IMAGEN_NOTIC = "imagen";
        public static final String AUTOR_NOTIC = "autor";
        public static final String TITULO_NOTIC = "titulo";
        public static final String FECHA_NOTIC = "fecha";
        public static final String HORA_NOTIC = "hora";
        public static final String CONTENIDO_NOTIC = "contenido";
        public static final String DEPARTAMENTO = "departamento";
        public static final String CATEGORIA = "categoria";
    }

    //Script de Creación de la tabla Noticias
    public static final String CREATE_NEWS_SCRIPT =
            "CREATE TABLE " + NEWS_TABLE_NAME + "("
                    + ColumnNoticias.ID_NOTIC + " " + STRING_TYPE + " PRIMARY KEY ,"
                    + ColumnNoticias.IMAGEN_NOTIC + " " + INT_TYPE + " ,"
                    + ColumnNoticias.AUTOR_NOTIC + " " + STRING_TYPE + " ,"
                    + ColumnNoticias.TITULO_NOTIC + " " + STRING_TYPE + " not null,"
                    + ColumnNoticias.FECHA_NOTIC + " " + STRING_TYPE + " not null,"
                    + ColumnNoticias.CONTENIDO_NOTIC + " " + STRING_TYPE + " not null,"
                    + ColumnNoticias.DEPARTAMENTO + " " + INT_TYPE + " ,"
                    + ColumnNoticias.CATEGORIA + " " + INT_TYPE + " )";

    public static void imprimirScrip() {
        System.out.println(CREATE_NEWS_SCRIPT);
    }

    public static final String[] COLUMNAS_QUERY_NOTICIA = {
            ColumnNoticias.ID_NOTIC,
            ColumnNoticias.IMAGEN_NOTIC,
            ColumnNoticias.TITULO_NOTIC,
            ColumnNoticias.AUTOR_NOTIC,
            ColumnNoticias.FECHA_NOTIC,
            ColumnNoticias.CONTENIDO_NOTIC,
            ColumnNoticias.DEPARTAMENTO,
            ColumnNoticias.CATEGORIA
    };

    private DataBase_Helper helper;
    private SQLiteDatabase bd;

    public NewsDataSource(Context context) {
        helper = new DataBase_Helper(context);
        bd = helper.getWritableDatabase();
    }

    private ContentValues contentValuesNews(Noticia noticia) {
        ContentValues content = new ContentValues();
        content.put(ColumnNoticias.ID_NOTIC, noticia.getId());
        content.put(ColumnNoticias.IMAGEN_NOTIC, noticia.getImagen());
        content.put(ColumnNoticias.TITULO_NOTIC, noticia.getTitulo());
        content.put(ColumnNoticias.AUTOR_NOTIC, noticia.getAutor());
        content.put(ColumnNoticias.FECHA_NOTIC, noticia.getFecha());
        content.put(ColumnNoticias.CONTENIDO_NOTIC, noticia.getContenido());
        content.put(ColumnNoticias.DEPARTAMENTO, noticia.getDepartamento());
        content.put(ColumnNoticias.CATEGORIA, noticia.getCategoria());
        return content;
    }

    public String insertarNoticia(Noticia noticia) {
        bd.insert(NEWS_TABLE_NAME, null, contentValuesNews(noticia));
        return noticia.getId();
    }

    public void eliminarNoticia(Noticia noticia) {
        bd.delete(NEWS_TABLE_NAME, ColumnNoticias.TITULO_NOTIC + "=?", new String[]{noticia.getTitulo()});
    }

    public void editarNoticia(Noticia anterior_noticia, Noticia nueva_Noticia) {
        bd.update(NEWS_TABLE_NAME,
                contentValuesNews(nueva_Noticia),
                ColumnNoticias.TITULO_NOTIC + "=?",
                new String[]{anterior_noticia.getTitulo()});
    }

    public ArrayList<Noticia> getTodasNoticias() {

        ArrayList<Noticia> noticias = new ArrayList<>();

        Cursor cursor = getCursorAllNews();
        while (cursor.moveToNext()) {
            noticias.add(new Noticia(
                    cursor.getString(0), // ID
                    cursor.getInt(1),    // Imagen
                    cursor.getString(2), // Titulo
                    cursor.getString(3), // Autor
                    cursor.getString(4), // Fecha
                    cursor.getString(6),  // Contenido
                    cursor.getInt(7),  // Departamento
                    cursor.getInt(8)  // Contenido
            ));
        }

        cursor.close();
        return noticias;
    }

    public Cursor getCursorAllNews() {
        // Se extrae :
        // Imagen - Titulo - Autor - Fecha - Hora - Contenido - Departamento - Categoria
        return bd.query(NEWS_TABLE_NAME, COLUMNAS_QUERY_NOTICIA, null, null, null, null, null);
    }

    public Cursor getCursorAllNews(int limit) {
        // Se extrae :
        // Imagen - Titulo - Autor - Fecha - Hora - Contenido - Departamento - Categoria
        return bd.query(NEWS_TABLE_NAME, COLUMNAS_QUERY_NOTICIA, null, null, null, null, null, limit + "");
    }
}
