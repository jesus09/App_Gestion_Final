package com.itver.jesus.app_gestion_final.ui.modelos;

import android.content.Context;

import com.itver.jesus.app_gestion_final.database.CalendarioDataSource;
import com.itver.jesus.app_gestion_final.database.NewsDataSource;
import com.itver.jesus.app_gestion_final.database.UsuariosDataSource;
import com.itver.jesus.app_gestion_final.database.VisualizaDataSource;
import com.itver.jesus.app_gestion_final.parse.API;
import com.itver.jesus.app_gestion_final.parse.CalendarioClass;
import com.itver.jesus.app_gestion_final.parse.NoticiaClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Clase modelo creada para implementar los métodos para recibir e insertar las noticias en la BD.
 * Created by Jesus on 16/11/2015.
 */
public class API_Conexion {

    private Context context;
    private List<NoticiaClass> noticias;
    private List<CalendarioClass> fechas;

    public API_Conexion(Context context) {
        this.context = context;
    }

    public boolean insertarUsuarioEnBD(String usuario, String clave) {
        UsuariosDataSource usersTable = new UsuariosDataSource(context);
        usersTable.insertarUsuario(usuario, clave);
        return true;
    }

    public boolean insertarNoticiaEnBD(String usuario, Noticia noticia) {

        NewsDataSource newsTable = new NewsDataSource(context);
        VisualizaDataSource visualizaTable = new VisualizaDataSource(context);

        String idNoticia = newsTable.insertarNoticia(noticia);

        visualizaTable.insertarAUsuario_Noticia(usuario, idNoticia);
        return true;
    }

    public void insertarNoticiasEnBD(String usuario) {

        noticias = API.getAllNews();
        fechas = API.getAllDates();

        NewsDataSource newsTable = new NewsDataSource(context);
        VisualizaDataSource visualizaTable = new VisualizaDataSource(context);

        for (int i = 0; i < noticias.size(); i++) {

            String nombreAutor = "";
            nombreAutor += noticias.get(i).getAutor().getNombre() + " ";
            nombreAutor += noticias.get(i).getAutor().getApPat() + " ";
            nombreAutor += noticias.get(i).getAutor().getApMat();

            Date fecha = noticias.get(i).getFecha(); // String fecha.
            String fecha_Noticia = formatearFecha(fecha + "");

            Noticia noticia = new Noticia(
                    noticias.get(i).getObjectId(),// Noticia(String id
                    noticias.get(i).getImagen(),// int imagen,
                    noticias.get(i).getTitulo(),// String titulo,
                    nombreAutor,                // String autor,
                    fecha_Noticia,              // String fecha,
                    noticias.get(i).getContenido(), // String contenido,
                    noticias.get(i).getDepartamentoClass().getId_Departamento(),  // int departamento,
                    noticias.get(i).getCategoria(), // int categoria
                    false //boolean visto)
            );

            String idNoticia = newsTable.insertarNoticia(noticia);

            visualizaTable.insertarAUsuario_Noticia(usuario, idNoticia);
        }

        CalendarioDataSource calendar = new CalendarioDataSource(context);

        for (int i = 0; i < fechas.size(); i++) {
            String fecha_num = fechas.get(i).getFecha_num();
            String mes = fechas.get(i).getMes();
            String descrip = fechas.get(i).getDescripcion();
            int num_mes = fechas.get(i).getMes_num();
            int anio = fechas.get(i).getAnio();
            Calendario calendario = new Calendario((i + 1), fecha_num, mes, descrip, num_mes, anio);
            calendar.insertarFecha(calendario);
        }
    }

    private String formatearFecha(String fecha) {
        try {
            SimpleDateFormat sourceDateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.US);
            Date date = sourceDateFormat.parse(fecha);
            Locale mex = new Locale("es_ES");
            SimpleDateFormat targetDateFormat = new SimpleDateFormat("EEEE dd 'de' MMMM 'del' yyyy hh:mm aaa", mex);
            return targetDateFormat.format(date).toUpperCase();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
