package com.itver.jesus.app_gestion_final.parse;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * Modelo de Departamento en la clase Parse.com
 * Created by Jesus on 17/10/2015.
 */
@ParseClassName("Departamentos")
public class DepartamentoClass extends ParseObject {

    /**
     * Retorna la descripción del Departamento.
     *
     * @return String descripcion del departamento
     */
    public String getDescripcion() {
        return getString("descripcion");
    }

    /**
     * Retorna el nombre del Departamento
     *
     * @return String nombre del departamento.
     */
    public String getNombre() {
        return getString("nombre");
    }

    /**
     * Retorna el atributo Id creado en Parse.com de la clase Departamento.
     *
     * @return int id del Departamento.
     */
    public int getId_Departamento() {
        try {
            return fetchIfNeeded().getInt("id_Departamento");
        } catch (ParseException e) {
            return 0;
        }
    }

    /***
     * Retorna el objectId del Departamento de la clase en Parse.com
     *
     * @return String objectId del Departamento.
     */
    public String getDepartamentoId() {
        return getObjectId();
    }
}
