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

    /**
     * Retorna el objectId de la clase Noticia en Parse.com
     *
     * @return String objectId.
     */
    public String getId_Noticia() {
        return getObjectId();
    }

    /**
     * Retorna el titulo de la Noticia almacenado en la clase Noticia en Parse.com
     *
     * @return String Titulo de la noticia.
     */
    public String getTitulo() {
        return getString("titulo");
    }

    /**
     * Retorna la fecha de la Noticia almacenado en la clase Noticia en Parse.com
     *
     * @return String fecha de la noticia.
     */
    public Date getFecha() {
        return getDate("fecha");
    }

    /**
     * Retorna la hora de la Noticia almacenada en la clase Noticia en Parse.com
     *
     * @return String hora de la noticia.
     */
    public String getHora() {
        return getString("hora");
    }

    /**
     * Retorna el contenido de la Noticia almacenada en la clase Noticia en Parse.com
     *
     * @return String contenido de la noticia.
     */
    public String getContenido() {
        return getString("contenido");
    }

    /**
     * Retorna la imagen de la Noticia almacenada en la clase Noticia en Parse.com
     *
     * @return int identificador de la imagen a mostrar en la aplicacion.
     */
    public int getImagen() {
        return getInt("imagen");
    }

    /**
     * Retorna el usuario Autor que escribio la Noticia.
     *
     * @return Objeto UsuarioClass.
     */
    public Usuario getAutor() {
        return (Usuario) getParseObject("id_Usuario");
    }

    /**
     * Retorna el departamento al que pertenece la Noticia en la clase Noticia en Parse.com
     *
     * @return Objeto DepartamentoClass.
     */
    public DepartamentoClass getDepartamentoClass() {
        return (DepartamentoClass) getParseObject("id_Departamento");
    }

    /**
     * Retorna la categoria a la que pertenece la Noticia en la clase Noticia en Parse.com
     *
     * @return int categoria a la que pertenece.
     */
    public int getCategoria() {
        return getInt("categoria");
    }
}
