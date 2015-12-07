package com.itver.jesus.app_gestion_final.parse;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.LinkedList;
import java.util.List;

/**
 * API para consultas entre la aplicacion y la plataforma Parse.com
 * Created by Jesus on 17/10/2015.
 */
public class API {

    /**
     * Realiza la peticion al servidor Parse.com y retorna un ArrayList con elementos tipo NoticiaClass.
     *
     * @return Objeto ArrayList con elementos tipo NoticiaClass.
     */
    public static List<NoticiaClass> getAllNews() {
        List<NoticiaClass> noticias = new LinkedList<NoticiaClass>();

        ParseQuery<NoticiaClass> consulta = ParseQuery.getQuery(NoticiaClass.class);
        consulta.orderByAscending("fecha");

        try {
            noticias = consulta.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return noticias;
    }

    /**
     * Realiza la peticion al servidor Parse.com y recibe el resultado y se inserta en
     * un ArrayList con elementos tipo CalendarioClass.
     *
     * @return Objecto ArrayList con elementos tipo CalendarioClass.
     */
    public static List<CalendarioClass> getAllDates() {

        List<CalendarioClass> fechas = new LinkedList<CalendarioClass>();

        ParseQuery<CalendarioClass> consulta = ParseQuery.getQuery(CalendarioClass.class);

        try {
            fechas = consulta.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechas;
    }
}
