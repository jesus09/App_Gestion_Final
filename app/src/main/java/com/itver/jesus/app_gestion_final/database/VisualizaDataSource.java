package com.itver.jesus.app_gestion_final.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.itver.jesus.app_gestion_final.ui.modelos.Noticia;

import java.util.ArrayList;

public class VisualizaDataSource {

    //Metainformación de la base de datos
    public static final String VISUALIZA_TABLE_NAME = "Visualize";
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";

    //Campos de la tabla Visualiza
    public static class ColumnVisualiza {

        public static final String ID_VISUALIZA = "id_visualiza";
        public static final String NOTICIA_ELIMINADA = "noticia_eliminada";
        public static final String VISTO = "visto";
        public static final String FK_NOTIC = NewsDataSource.ColumnNoticias.ID_NOTIC;
        public static final String FK_USER = UsuariosDataSource.ColumnUsuarios.ID_USER;
    }

    //Script de Creación de la tabla Visualiza
    public static final String CREATE_VISUALIZA_SCRIPT
            = "CREATE TABLE " + VISUALIZA_TABLE_NAME + "("
            + ColumnVisualiza.ID_VISUALIZA + " " + INT_TYPE + " PRIMARY KEY AUTOINCREMENT, "
            + ColumnVisualiza.NOTICIA_ELIMINADA + " " + INT_TYPE + " ,"
            + ColumnVisualiza.VISTO + " " + INT_TYPE + " ,"
            + ColumnVisualiza.FK_USER + " " + STRING_TYPE + " not null, "
            + ColumnVisualiza.FK_NOTIC + " " + STRING_TYPE + " not null ,"
            + " FOREIGN KEY(" + ColumnVisualiza.FK_USER + ") REFERENCES " + UsuariosDataSource.USERS_TABLE_NAME + "("
            + UsuariosDataSource.ColumnUsuarios.ID_USER + ") ,"
            + " FOREIGN KEY(" + ColumnVisualiza.FK_NOTIC + ") REFERENCES " + NewsDataSource.NEWS_TABLE_NAME + "("
            + NewsDataSource.ColumnNoticias.ID_NOTIC + ") )";

    public static void imprimirScrip() {
        System.out.println(CREATE_VISUALIZA_SCRIPT);
    }

    private DataBase_Helper helper;
    private SQLiteDatabase bd;

    public VisualizaDataSource(Context context) {
        helper = new DataBase_Helper(context);
        bd = helper.getWritableDatabase();
    }

    private ContentValues contentValuesNews(String usuario, String id_Noticia) {
        ContentValues content = new ContentValues();
        content.put(ColumnVisualiza.NOTICIA_ELIMINADA, 0);
        content.put(ColumnVisualiza.VISTO, 0);
        content.put(ColumnVisualiza.FK_USER, usuario);
        content.put(ColumnVisualiza.FK_NOTIC, id_Noticia);

        return content;
    }

    private ContentValues contentValuesUpdateNews() {
        ContentValues content = new ContentValues();
        content.put(ColumnVisualiza.NOTICIA_ELIMINADA, 1);
        return content;
    }

    private ContentValues contentValuesUpdateVistoNews() {
        ContentValues content = new ContentValues();
        content.put(ColumnVisualiza.VISTO, 1);
        return content;
    }

    public void noticiaVista(String usuario, Noticia noticia) {
        String where = ColumnVisualiza.FK_NOTIC + " = \"" + noticia.getId() + "\"";
        where += "AND " + ColumnVisualiza.FK_USER + " = \"" + usuario + "\"";
        bd.update(VISUALIZA_TABLE_NAME, contentValuesUpdateVistoNews(), where, null);
    }

    public void insertarAUsuario_Noticia(String idUsuario, String idNoticia) {
        bd.insert(VISUALIZA_TABLE_NAME, null, contentValuesNews(idUsuario, idNoticia));
    }

    public void eliminarNoticiaDeLista(String usuario, Noticia noticia) {
        String where = ColumnVisualiza.FK_USER + " = \"" + usuario + "\"";
        where += " AND " + ColumnVisualiza.FK_NOTIC + " = \"" + noticia.getId() + "\"";
        bd.update(VISUALIZA_TABLE_NAME, contentValuesUpdateNews(), where, null);
    }

    public Cursor getCursorWithPreferences(String usuario, String[] departamentos, String[] categorias, String[] noCategorias) {

        String consulta = "SELECT DISTINCT (" + NewsDataSource.NEWS_TABLE_NAME + "." + NewsDataSource.ColumnNoticias.ID_NOTIC + ") , "
                + NewsDataSource.ColumnNoticias.IMAGEN_NOTIC + " , "
                + NewsDataSource.ColumnNoticias.TITULO_NOTIC + " , "
                + NewsDataSource.ColumnNoticias.AUTOR_NOTIC + " , "
                + NewsDataSource.ColumnNoticias.FECHA_NOTIC + " , "
                + NewsDataSource.ColumnNoticias.CONTENIDO_NOTIC + " , "
                + NewsDataSource.ColumnNoticias.DEPARTAMENTO + " , "
                + NewsDataSource.ColumnNoticias.CATEGORIA + " ,"
                + ColumnVisualiza.VISTO + " ";
        consulta += " FROM " + NewsDataSource.NEWS_TABLE_NAME + " , " + VISUALIZA_TABLE_NAME;
        consulta += " WHERE " + ColumnVisualiza.FK_USER + " = \"" + usuario + "\"";
        consulta += " AND " + VISUALIZA_TABLE_NAME + "." + ColumnVisualiza.FK_NOTIC + " = " + NewsDataSource.NEWS_TABLE_NAME + "." + NewsDataSource.ColumnNoticias.ID_NOTIC;
        consulta += " AND " + ColumnVisualiza.NOTICIA_ELIMINADA + " = 0";

        consulta += getPreferenciasDepartamentos(departamentos);

        consulta += getPreferenciasCategorias(categorias, noCategorias);

        Log.e("gestion", "CONSULTA :" + consulta);

        return bd.rawQuery(consulta, null);
    }


    public ArrayList<Noticia> getListWithPreferences(String usuario, String[] departamentos, String[] categorias, String[] noCategorias) {
        ArrayList<Noticia> noticias = new ArrayList<>();

        Cursor cursor = getCursorWithPreferences(usuario, departamentos, categorias, noCategorias);

        while (cursor.moveToNext()) {
            noticias.add(new Noticia(
                    cursor.getString(0), // ID
                    cursor.getInt(1),    // Imagen
                    cursor.getString(2), // Titulo
                    cursor.getString(3), // Autor
                    cursor.getString(4), // Fecha
                    cursor.getString(5),  // Contenido
                    cursor.getInt(6),  // Departamento
                    cursor.getInt(7)  // Contenido
            ));
        }
        cursor.close();
        return noticias;
    }

    private String getPreferenciasCategorias(String[] categorias, String[] noCategorias) {
        StringBuilder condicionDepartamentos = new StringBuilder();

        if (categorias.length > 0) {
            condicionDepartamentos.append(" AND (" + NewsDataSource.ColumnNoticias.CATEGORIA + " = ").append(categorias[0]);

            for (int i = 1; i < categorias.length; i++) {
                condicionDepartamentos.append(" OR " + NewsDataSource.ColumnNoticias.CATEGORIA + " = ").append(categorias[i]);
            }
            condicionDepartamentos.append(")");
        }

        // Excluyendo este tipo de noticias.
        condicionDepartamentos.append(" AND (" + NewsDataSource.ColumnNoticias.CATEGORIA + " <> ").append(noCategorias[0]);
        for (int i = 1; i < noCategorias.length; i++) {
            condicionDepartamentos.append(" OR " + NewsDataSource.ColumnNoticias.CATEGORIA + " <> ").append(noCategorias[i]);
        }
        condicionDepartamentos.append(")");

        return condicionDepartamentos.toString();
    }

    private String getPreferenciasDepartamentos(String[] departamentos) {
        String condicionDepartamentos = "";

        // Departamentos públicos
        condicionDepartamentos += " AND (" + NewsDataSource.ColumnNoticias.DEPARTAMENTO + " = 0 ";

        for (int i = 0; i < departamentos.length; i++) {
            condicionDepartamentos += " OR " + NewsDataSource.ColumnNoticias.DEPARTAMENTO + " = " + departamentos[i];
        }
        return condicionDepartamentos + " )";
    }
}