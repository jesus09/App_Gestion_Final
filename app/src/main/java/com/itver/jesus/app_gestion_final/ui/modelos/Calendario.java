package com.itver.jesus.app_gestion_final.ui.modelos;

import java.util.ArrayList;

/**
 * Clase Modelo Calendario
 * Created by Jesus on 04/10/2015.
 */
public class Calendario {

    private int idCalendario;
    private String fecha;
    private String mes;
    private String contenido;
    private int mes_num;
    private int anio;

    public Calendario(int id, String fecha, String mes, String contenido, int mes_num, int anio) {
        this.idCalendario = id;
        this.fecha = fecha;
        this.mes = mes;
        this.contenido = contenido;
        this.mes_num = mes_num;
        this.anio = anio;
    }

    public Calendario(String fecha, String mes, String contenido) {
        this.fecha = fecha;
        this.mes = mes;
        this.contenido = contenido;
    }

    public static ArrayList<Calendario> fechas_calendario;
    public static ArrayList<Calendario> fechas_meses;

    static {

        fechas_meses = new ArrayList<>();
        fechas_meses.add(new Calendario("01", "Enero", "ENERO"));
        fechas_meses.add(new Calendario("02", "Enero", "FEBRERO"));
        fechas_meses.add(new Calendario("03", "Enero", "MARZO"));
        fechas_meses.add(new Calendario("04", "Enero", "ABRIL"));
        fechas_meses.add(new Calendario("05", "Enero", "MAYO"));
        fechas_meses.add(new Calendario("06", "Enero", "JUNIO"));
        fechas_meses.add(new Calendario("07", "Diciembre", "JULIO"));
        fechas_meses.add(new Calendario("08", "Agosto", "AGOSTO"));
        fechas_meses.add(new Calendario("09", "Septiembre", "SEPTIEMBRE"));
        fechas_meses.add(new Calendario("10", "Octubre", "OCTUBRE"));
        fechas_meses.add(new Calendario("11", "Noviembre", "NOVIEMBRE"));
        fechas_meses.add(new Calendario("12", "Julio", "DICIEMBRE"));


        fechas_calendario = new ArrayList<>();
        fechas_calendario.add(new Calendario("28", "Agosto", "Inicio de curso Instituto Tecnologico de veracruz"));
        fechas_calendario.add(new Calendario("16", "Septiembre", "Día de la independencia de México"));
        fechas_calendario.add(new Calendario("11", "Enero", "Inicio de semestre"));
        fechas_calendario.add(new Calendario("12", "Diciembre", "Final de semestre"));
        fechas_calendario.add(new Calendario("09", "Julio", "Fecha del Calendario"));
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(int idCalendario) {
        this.idCalendario = idCalendario;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMes_num() {
        return mes_num;
    }

    public void setMes_num(int mes_num) {
        this.mes_num = mes_num;
    }
}
