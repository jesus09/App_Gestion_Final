package com.itver.jesus.app_gestion_final.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Modelo de Calendario en clase Parse.com
 * Created by Jesus on 17/10/2015.
 */
@ParseClassName("Calendario")
public class CalendarioClass extends ParseObject {

    private String fecha_num;
    private String mes;
    private String descripcion;
    private int mes_num;
    private int anio;

    public String getFecha_num() {
        return getString("fecha_num");
    }

    public String getMes() {
        return getString("mes");
    }

    public String getDescripcion() {
        return getString("descripcion");
    }

    public int getAnio() {
        return getInt("anio");
    }

    public int getMes_num() {
        return getInt("mes_num");
    }
}
