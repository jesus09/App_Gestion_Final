package com.itver.jesus.app_gestion_final.parse;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.LinkedList;
import java.util.List;

/**
 * API para consultas entre la aplicación y la plataforma Parse.com
 * Created by Jesus on 17/10/2015.
 */
public class API {

    // Noticias
    public static List<NoticiaClass> getAllNews() {

        List<NoticiaClass> noticias = new LinkedList<NoticiaClass>();

        ParseQuery<NoticiaClass> consulta = ParseQuery.getQuery(NoticiaClass.class);
        consulta.orderByDescending("updatedAt");

        try {
            noticias = consulta.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return noticias;
    }

    // Calendario
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
