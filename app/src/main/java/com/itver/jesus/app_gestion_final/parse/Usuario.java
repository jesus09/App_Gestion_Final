package com.itver.jesus.app_gestion_final.parse;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Modelo de la clase Users de Parse.com
 * Created by Jesus on 17/10/2015.
 */
@ParseClassName("_User")
public class Usuario extends ParseUser {

    /**
     * Retorna el objectId en la clase _User de Parse.com
     *
     * @return String objectId.
     */
    public String getId_Usuario() {
        return getObjectId();
    }

    /**
     * Retorna el nombre del usuario en la clase _User de Parse.com.
     *
     * @return String nombre del usuario.
     */
    public String getNombre() {
        try {
            return fetchIfNeeded().getString("nombre");
        } catch (ParseException e) {
            return "Error";
        }
    }

    /**
     * Retorna el apellido paterno del usuario en la clase _User de Parse.com.
     *
     * @return String con el apellido Paterno del usuario.
     */
    public String getApPat() {
        try {
            return fetchIfNeeded().getString("apPat");
        } catch (ParseException e) {
            return "Error";
        }
    }

    /**
     * Retorna el apellido paterno del usuario en la clase _User de Parse.com
     *
     * @return String con el apellido Materno del usuario.
     */
    public String getApMat() {
        try {
            return fetchIfNeeded().getString("apMat");
        } catch (ParseException e) {
            return "Error";
        }
    }
}
