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

    private String id_Usuario;
    private String nombre;
    private String apPat;
    private String apMat;

    public String getId_Usuario() {
        return getObjectId();
    }

    public String getNombre() {
        try {
            return fetchIfNeeded().getString("nombre");
        } catch (ParseException e) {
            return "Error";
        }
    }

    public String getApPat() {
        try {
            return fetchIfNeeded().getString("apPat");
        } catch (ParseException e) {
            return "Error";
        }
    }

    public String getApMat() {
        try {
            return fetchIfNeeded().getString("apMat");
        } catch (ParseException e) {
            return "Error";
        }
    }
}
