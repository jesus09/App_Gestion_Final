package com.itver.jesus.app_gestion_final.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Modelo de la clase Noticia en la plataforma Parse.com
 * Created by Jesus on 17/10/2015.
 */
@ParseClassName("Noticia")
public class NoticiaClass extends ParseObject {

    private String id_Noticia;
    private String titulo;
    private Date fecha;
    private String hora;
    private String contenido;
    private int imagen;
    private Usuario autor;
    private DepartamentoClass departamentoClass;
    private int categoria;

    public String getId_Noticia() {
        return getObjectId();
    }

    public String getTitulo() {
        return getString("titulo");
    }

    public Date getFecha() {
        return getDate("fecha");
    }

    public String getHora() {
        return getString("hora");
    }

    public String getContenido() {
        return getString("contenido");
    }

    public int getImagen() {
        return getInt("imagen");
    }

    public Usuario getAutor() {
        return (Usuario) getParseObject("id_Usuario");
    }

    public DepartamentoClass getDepartamentoClass() {
        return (DepartamentoClass) getParseObject("id_Departamento");
    }

    public int getCategoria() {
        return getInt("categoria");
    }
}
