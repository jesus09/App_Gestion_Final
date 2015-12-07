package com.itver.jesus.app_gestion_final.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Modelo de un Calendario en la clase Parse.com llamada Calendario
 * Created by Jesus on 17/10/2015.
 */
@ParseClassName("Calendario")
public class CalendarioClass extends ParseObject {

    /**
     * Retorna el dia en numero de la fecha en el calendario.
     *
     * @return String conteniendo el numero de la fecha.
     */
    public String getFecha_num() {
        return getString("fecha_num");
    }

    /**
     * Retorna el mes de la fecha en el calendario
     *
     * @return String con el mes de la fecha.
     */
    public String getMes() {
        return getString("mes");
    }

    /**
     * Retorna la descripcion de la fecha en el calendario.
     *
     * @return String con la descripcion de la fecha.
     */
    public String getDescripcion() {
        return getString("descripcion");
    }

    /**
     * Retorna el año de la fecha en el calendario.
     *
     * @return String con el año de la fecha.
     */
    public int getAnio() {
        return getInt("anio");
    }

    /**
     * Retorna el mes en numero de la fecha.
     *
     * @return int - numero del mes en el año.
     */
    public int getMes_num() {
        return getInt("mes_num");
    }
}
