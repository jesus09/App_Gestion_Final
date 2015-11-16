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

    private String id;
    private int id_Departamento;
    private String nombre;
    private String descripcion;

    public String getDescripcion() {
        return getString("descripcion");
    }

    public String getNombre() {
        return getString("nombre");
    }

    public int getId_Departamento() {
        try {
            return fetchIfNeeded().getInt("id_Departamento");
        } catch (ParseException e) {
            return 0;
        }
    }

    public String getDepartamentoId() {
        return getObjectId();
    }
}
